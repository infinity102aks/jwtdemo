package com.ankit.scheduler.services;

import com.ankit.scheduler.Entity.Goal;
import com.ankit.scheduler.Entity.Task;
import com.ankit.scheduler.Entity.User;
import com.ankit.scheduler.models.GoalRequestDTO;
import com.ankit.scheduler.models.GoalResponseDTO;
import com.ankit.scheduler.models.SchedulerEvent;
import com.ankit.scheduler.models.Tasks.BaseTask;
import com.ankit.scheduler.repositories.GoalRepository;
import com.ankit.scheduler.repositories.TaskRepository;
import com.ankit.scheduler.security.UserContextHolder;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SchedulerEventProducer schedulerEventProducer;

    @Transactional
    public Goal createNewGoal(GoalRequestDTO goalRequestDTO) {
        Goal goal = new Goal(goalRequestDTO, UserContextHolder.getUserDetails().getUser());
        Goal newGoal = this.goalRepository.save(goal);
        if (goalRequestDTO.getTasks().isPresent()) {
            this.saveAllTasks(goalRequestDTO.getTasks().orElse(new ArrayList<>()), newGoal.getId());
        }

        return newGoal;
    }

    @Transactional
    public Goal updateGoal(GoalRequestDTO goalRequestDTO) {
        Goal goal = this.getGoalById(goalRequestDTO.getId());
        goal.setExpiry(goalRequestDTO.getExpiry());
        goal.setName(goalRequestDTO.getName());
        Goal updatedGoal = this.goalRepository.save(goal);

        return updatedGoal;
    }

    @Transactional
    public Goal deleteGoal(long goalId) {
        Goal goal = this.getGoalById(goalId);
        this.goalRepository.delete(goal);
        return goal;
    }

    public List<GoalResponseDTO> getUserGoals() {
        return this.goalRepository.findAllByUserId(UserContextHolder.getUserDetails().getUserId())
                .stream().map(GoalResponseDTO::new).collect(Collectors.toList());
    }

    public List<BaseTask> getAllTasks(long id) throws NoSuchElementException {
        Optional<Goal> goal = this.goalRepository.findByIdAndUserId(id, UserContextHolder.getUserDetails().getUserId());
        return goal.map(Goal::getTasks)
                .orElseGet(ArrayList::new)
                .stream()
                .map(BaseTask::getTaskInstance)
                .collect(Collectors.toList());
    }

    public Goal getGoalById(long id) throws NoSuchElementException {
        return this.goalRepository.findById(id).orElseThrow();
    }

    public List<Task> saveAllTasks(List<Task> tasks, long goalId) {
        Goal goal = this.getGoalById(goalId);
        if (goal.getUser().getId() != UserContextHolder.getUserDetails().getUserId()) {
            throw new NoSuchElementException("Goal Not found");
        }
        tasks.stream().forEach(task -> task.setGoalId(goal.getId()));
        List<Task> newTasks = this.taskRepository.saveAll(tasks);
        this.publishSchedulerEvent(newTasks);

        return newTasks;
    }

    @Transactional
    public Task updateTask(long goalId, Task newTask) {
        Goal goal = this.getGoalById(goalId);
        Task oldTask = goal.getTasks().stream().filter(task -> task.getId() == newTask.getId()).findFirst().orElseThrow();
        oldTask.setName(newTask.getName());
        oldTask.setDescription(newTask.getDescription());
        oldTask.setFrequency(newTask.getFrequency());
        oldTask.setQuantity(newTask.getQuantity());
        oldTask.setFrequencyValue(newTask.getFrequencyValue());
        oldTask.setPreferredTime(newTask.getPreferredTime());
        oldTask.setRemindBefore(newTask.getRemindBefore());
        oldTask.setWeekDays(newTask.getWeekDays());
        return this.taskRepository.save(oldTask);
    }

    @Transactional
    public Task deleteTask(long goalId, long taskId) {
        Goal goal = this.getGoalById(goalId);
        Task oldTask = goal.getTasks().stream().filter(task -> task.getId() == taskId).findFirst().orElseThrow();
        goal.setTasks(goal.getTasks().stream().filter(task -> task.getId() != taskId).collect(Collectors.toList()));
        this.goalRepository.save(goal);
        this.taskRepository.deleteById(taskId);
        return oldTask;
    }

    private void publishSchedulerEvent(List<Task> tasks) {
        List<SchedulerEvent> events = this.createTaskSchedulerEventList(tasks);
        events.forEach(event -> this.schedulerEventProducer.sendCreateScheduleEvent(event));
    }

    private List<SchedulerEvent> createTaskSchedulerEventList(List<Task> tasks) {
        User user = UserContextHolder.getUserDetails().getUser();
        return tasks.stream().map(task -> new SchedulerEvent(task, null, user.getEmail(), user.getName())).collect(Collectors.toList());
    }
}

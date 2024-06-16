package com.ankit.scheduler.controllers;

import com.ankit.scheduler.Entity.Task;
import com.ankit.scheduler.models.GoalRequestDTO;
import com.ankit.scheduler.services.GoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/api/goal")
@Validated
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping
    public ResponseEntity<?> createNewGoal(@RequestBody @Valid GoalRequestDTO goalRequestDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.goalService.createNewGoal(goalRequestDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateGoal(@RequestBody @Valid GoalRequestDTO goalRequestDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.goalService.updateGoal(goalRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable(name = "id") long goalId) {
        return new ResponseEntity<>(this.goalService.deleteGoal(goalId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllGoals() {
        return new ResponseEntity<>(this.goalService.getUserGoals(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/tasks")
    public ResponseEntity<?> getAllTasks(@PathVariable(name = "id") long goalId) {
        return new ResponseEntity<>(this.goalService.getAllTasks(goalId), HttpStatus.OK);
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<?> createNewTasks(@PathVariable(name = "id") long goalId, @Valid @RequestBody List<Task> tasks, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.goalService.saveAllTasks(tasks, goalId), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateTasks(@PathVariable(name = "id") long goalId, @Valid @RequestBody Task task, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.goalService.updateTask(goalId, task), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{goalId}/task/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable(name = "goalId") long goalId, @PathVariable(name = "taskId") long taskId) {
        return new ResponseEntity<>(this.goalService.deleteTask(goalId, taskId), HttpStatus.OK);
    }
}

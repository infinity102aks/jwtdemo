package com.ankit.schedule_notification.scheduler_notification_service.consumer;

import com.ankit.schedule_notification.scheduler_notification_service.models.Task;
import com.ankit.schedule_notification.scheduler_notification_service.worker.SchedulerTriggerJob;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class TaskNotification implements SchedulerEvent {
    private Task task;
    private String email;
    private String userName;

    public TaskNotification(Map<String, Object> map) throws ClassCastException {
        if (!map.containsKey("task")) {
            throw new ClassCastException("Key task is required");
        }
        if (!map.containsKey("email")) {
            throw new ClassCastException("Key email is required");
        }
        if (!map.containsKey("userName")) {
            throw new ClassCastException("key userName is required");
        }
        this.task = (Task) map.get("task");
        this.email = String.valueOf(map.get("email"));
        this.userName = String.valueOf(map.get("userName"));
    }

    @Override
    public String getId() {
        return "task-"+this.task.getId();
    }

    @Override
    public String getMessage() {
        return this.task.getDescription();
    }

    @Override
    public String getGroupName() {
        return "task-notification";
    }

    @Override
    public String getUserEmail() {
        return this.email;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public void startSchedule() {
        switch (this.task.getFrequency()) {
            case ONCE_A_DAY : updateSchedulerForOncePerDay(); break;
            case TWICE_A_DAY : updateSchedulerForTwicePerDay(); break;
            case NO_OF_DAYS: updateSchedulerForNoOfDays(); break;
        }
    }

    private void updateSchedulerForOncePerDay() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobDetails(getDailyJobDetail());
        schedulerFactoryBean.setTriggers(getDailyTrigger(this.task.getPreferredTime()));
        try {
            schedulerFactoryBean.getScheduler().triggerJob(JobKey.jobKey(this.getId()));
        } catch (SchedulerException exception) {
            exception.printStackTrace();
        }
    }

    private void updateSchedulerForTwicePerDay() {

        SchedulerFactoryBean schedulerFactoryBean1 = new SchedulerFactoryBean();
        SchedulerFactoryBean schedulerFactoryBean2 = new SchedulerFactoryBean();
        schedulerFactoryBean1.setJobDetails(getDailyJobDetail());
        schedulerFactoryBean2.setJobDetails(getDailyJobDetail());
        String[] preferredTimes = this.task.getPreferredTime().split(",");
        schedulerFactoryBean1.setTriggers(getDailyTrigger(preferredTimes[0]));
        schedulerFactoryBean2.setTriggers(getDailyTrigger(preferredTimes[1]));
        try {
            schedulerFactoryBean1.getScheduler().triggerJob(JobKey.jobKey(this.getId()));
            schedulerFactoryBean2.getScheduler().triggerJob(JobKey.jobKey(this.getId()));
        } catch (SchedulerException exception) {
            exception.printStackTrace();
        }
    }

    private void updateSchedulerForNoOfDays() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

    }

    private JobDetail getDailyJobDetail() {
        return JobBuilder.newJob(SchedulerTriggerJob.class)
                .withIdentity(this.getId(), this.getGroupName())
                .storeDurably()
                .build();
    }

    private Trigger getDailyTrigger(String preferredTime) {
        String[] time = preferredTime.split(":");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withHour(Integer.parseInt(time[0])).withMinute(Integer.parseInt(time[1])).withSecond(0).withNano(0);
        if (now.compareTo(nextRun) > 0) {
            nextRun = nextRun.plusDays(1);
        }
        Date startTime = Date.from(nextRun.atZone(ZoneId.systemDefault()).toInstant());

        return TriggerBuilder.newTrigger()
                .forJob(getDailyJobDetail())
                .startAt(startTime)
                .withIdentity(this.getGroupName()+"-trigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(24)
                        .repeatForever())
                .build();
    }
}

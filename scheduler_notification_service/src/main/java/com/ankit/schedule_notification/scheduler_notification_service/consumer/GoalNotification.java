package com.ankit.schedule_notification.scheduler_notification_service.consumer;

import com.ankit.schedule_notification.scheduler_notification_service.models.Goal;
import org.quartz.ScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class GoalNotification implements SchedulerEvent {
    private Goal goal;
    private String email;
    private String userName;

    @Autowired
    private EventWorker eventWorker;

    public GoalNotification(Map<String, Object> map) throws ClassCastException {
        if (!map.containsKey("goal")) {
            throw new ClassCastException("Key goal is required");
        }
        if (!map.containsKey("email")) {
            throw new ClassCastException("Key email is required");
        }
        if (!map.containsKey("userId")) {
            throw new ClassCastException("key userId is required");
        }
        this.goal = (Goal) map.get("goal");
        this.email = String.valueOf(map.get("email"));
        this.userName = String.valueOf(map.get("userId"));
    }

    @Override
    public String getId() {
        return "goal-"+this.goal.getId();
    }

    @Override
    public String getMessage() {
        return "Goal created with name: "+ this.goal.getName();
    }

    @Override
    public String getGroupName() {
        return "goal-notification";
    }

    @Override
    public void startSchedule() throws ClassCastException{
        this.eventWorker.scheduleNotification(this);
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
    public ScheduleBuilder getScheduleBuilder() {
        return null;
    }
}

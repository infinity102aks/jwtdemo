package com.ankit.schedule_notification.scheduler_notification_service.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SchedulerEventConsumer {

    @KafkaListener(topics = "${scheduler.topic.create-task-schedule}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeTaskEvent(Map<String, Object> event) {
        try {
            new TaskNotification(event).startSchedule();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "${scheduler.topic.create-goal-expiry-schedule}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeGoalEvent(Map<String, Object> event) {
        try {
            new GoalNotification(event).startSchedule();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}

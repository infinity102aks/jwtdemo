package com.ankit.scheduler.services;

import com.ankit.scheduler.models.SchedulerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SchedulerEventProducer {

    private KafkaTemplate<String, Map<String, Object>> kafkaTemplate;
    private final String schedulerTopicName;

    @Autowired
    public SchedulerEventProducer(KafkaTemplate<String, Map<String, Object>> kafkaTemplate,
                                  @Value("${scheduler.topic.create-task-schedule}") String schedulerTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.schedulerTopicName = schedulerTopicName;
    }

    public void sendCreateScheduleEvent(SchedulerEvent event) {
        this.kafkaTemplate.send(this.schedulerTopicName, event.getMap());
    }
}

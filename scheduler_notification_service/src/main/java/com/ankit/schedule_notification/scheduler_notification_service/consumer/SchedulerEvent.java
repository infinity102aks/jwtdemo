package com.ankit.schedule_notification.scheduler_notification_service.consumer;

import org.quartz.ScheduleBuilder;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public interface SchedulerEvent {
    public String getId();
    public String getGroupName();
    public String getMessage();
    public String getUserEmail();
    public String getUserName();
    public void startSchedule() throws SchedulerException;
}


package com.ankit.schedule_notification.scheduler_notification_service.models;

import lombok.Data;
import org.apache.catalina.User;

@Data
public class Goal {
    private long id;
    private String name;
    private String expiry;
    private User user;
}

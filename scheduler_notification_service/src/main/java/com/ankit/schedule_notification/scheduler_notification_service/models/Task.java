package com.ankit.schedule_notification.scheduler_notification_service.models;

import com.ankit.schedule_notification.scheduler_notification_service.enums.TaskFrequency;
import com.ankit.schedule_notification.scheduler_notification_service.enums.WeekDaysValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Data
@ToString
public class Task {
    private Long id;
    private String name;
    private int quantity;
    private TaskFrequency frequency;
    private List<WeekDaysValue> weekDays;
    private int frequencyValue;
    private String preferredTime;
    private int remindBefore = 30;
    private String description;
    private long goalId;
}

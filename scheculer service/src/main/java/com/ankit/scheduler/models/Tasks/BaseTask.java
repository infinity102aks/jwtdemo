package com.ankit.scheduler.models.Tasks;

import com.ankit.scheduler.Entity.Task;
import com.ankit.scheduler.enums.TaskFrequency;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseTask {
    private long id;
    private String name;
    private int quantity;
    private TaskFrequency frequency;
    private int remindBefore;
    private String description;

    public BaseTask(Task task){
        this.id = task.getId();
        this.name = task.getName();
        this.quantity = task.getQuantity();
        this.frequency = task.getFrequency();
        this.remindBefore = task.getRemindBefore();
        this.description = task.getDescription();
    };

    public static BaseTask getTaskInstance(Task task) {
        switch (task.getFrequency()) {
            case ONCE_A_DAY : return new OnceADayTask(task);
            case TWICE_A_DAY: return new TwiceADayTask(task);
            case ONCE_A_WEEK: return new OnceAWeekTask(task);
            case DAYS_OF_WEEK: return new DaysOfWeekTask(task);
            case NO_OF_DAYS: return new MultipleDaysTask(task);
            default: return null;
        }
    }
}



package com.ankit.scheduler.models.Tasks;

import com.ankit.scheduler.Entity.Task;
import com.ankit.scheduler.enums.WeekDaysValue;
import lombok.Data;

import java.util.List;

@Data
public class DaysOfWeekTask extends BaseTask{
    private List<WeekDaysValue> weekDays;
    private String preferredTime;

    public DaysOfWeekTask(Task task) {
        super(task);
        this.preferredTime = task.getPreferredTime();
        this.weekDays = task.getWeekDays();
    }
}

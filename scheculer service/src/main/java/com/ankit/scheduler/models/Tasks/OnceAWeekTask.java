package com.ankit.scheduler.models.Tasks;

import com.ankit.scheduler.Entity.Task;
import com.ankit.scheduler.enums.WeekDaysValue;
import lombok.Data;

@Data
public class OnceAWeekTask extends BaseTask{
    private WeekDaysValue weekDay;
    private String preferredTime;

    public OnceAWeekTask(Task task) {
        super(task);
        this.preferredTime = task.getPreferredTime();
        this.weekDay = task.getWeekDays().get(0);
    }
}

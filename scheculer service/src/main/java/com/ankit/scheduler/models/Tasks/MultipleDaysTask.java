package com.ankit.scheduler.models.Tasks;

import com.ankit.scheduler.Entity.Task;
import lombok.Data;

@Data
public class MultipleDaysTask extends BaseTask{
    private int frequencyValue;
    private String preferredTime;

    public MultipleDaysTask(Task task) {
        super(task);
        this.preferredTime = task.getPreferredTime();
        this.frequencyValue = task.getFrequencyValue();
    }
}

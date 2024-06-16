package com.ankit.scheduler.models.Tasks;

import com.ankit.scheduler.Entity.Task;
import lombok.Data;

@Data
public class TwiceADayTask extends BaseTask{
    private String[] preferredTime;

    public TwiceADayTask(Task task) {
        super(task);
        this.preferredTime = task.getPreferredTime().split(",");
    }
}

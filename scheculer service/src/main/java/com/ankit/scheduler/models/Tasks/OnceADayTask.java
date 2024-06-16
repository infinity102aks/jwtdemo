package com.ankit.scheduler.models.Tasks;

import com.ankit.scheduler.Entity.Task;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OnceADayTask extends BaseTask{
    private String preferredTime;

    public OnceADayTask(Task task) {
        super(task);
        this.preferredTime = task.getPreferredTime();
    }
}

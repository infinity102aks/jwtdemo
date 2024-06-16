package com.ankit.scheduler.models;

import com.ankit.scheduler.Entity.Goal;
import com.ankit.scheduler.Entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@ToString
public class SchedulerEvent {
    private Task task;
    private Goal goal;
    private String email;
    private String userName;

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("task", this.task);
        map.put("goal", this.goal);
        map.put("email", this.email);
        map.put("userName", this.userName);

        return map;
    }
}

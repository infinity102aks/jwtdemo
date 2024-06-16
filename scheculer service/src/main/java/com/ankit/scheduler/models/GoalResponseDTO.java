package com.ankit.scheduler.models;

import com.ankit.scheduler.Entity.Goal;
import com.ankit.scheduler.Entity.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
public class GoalResponseDTO {
    private long id;
    private String name;
    @JsonIgnore
    private List<Task> tasks;
    private Date expiry;

    public GoalResponseDTO(Goal goal) {
        this.id = goal.getId();
        this.name = goal.getName();
        this.tasks = goal.getTasks();
        this.expiry = Date.from(Instant.parse(goal.getExpiry()));
    }
}

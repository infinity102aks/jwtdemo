package com.ankit.scheduler.models;

import com.ankit.scheduler.Entity.Task;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class GoalRequestDTO {
    private long id;

    @NotBlank(message = "Name of the goal is required")
    private String name;

    @NotBlank(message = "Expiry Date is mandatory")
    private String expiry;

    private Optional<List<Task>> tasks = Optional.empty();
}

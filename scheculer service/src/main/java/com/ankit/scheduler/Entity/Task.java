package com.ankit.scheduler.Entity;

import com.ankit.scheduler.enums.TaskFrequency;
import com.ankit.scheduler.enums.WeekDaysValue;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "name is mandatory")
    private String name;

    @NotNull(message = "At least 1 quantity is mandatory")
    private int quantity;

    @NotNull(message = "frequency is mandatory")
    @Enumerated(EnumType.STRING)
    private TaskFrequency frequency;

    @Enumerated(EnumType.STRING)
    private List<WeekDaysValue> weekDays;

    private int frequencyValue;

    @Valid
    @NotBlank(message = "Preferred time is required")
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d(,([01]\\d|2[0-3]):[0-5]\\d)*$", message = "Time constraint should be hh:mm")
    private String preferredTime;

    private int remindBefore = 30;
    private String description;

    @Column(name = "goal_id")
    private long goalId;
}

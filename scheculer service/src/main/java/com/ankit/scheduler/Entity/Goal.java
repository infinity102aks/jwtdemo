package com.ankit.scheduler.Entity;

import com.ankit.scheduler.models.GoalRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Goal {

    public Goal(GoalRequestDTO requestDTO, User user) {
        this.name = requestDTO.getName();
        this.expiry = requestDTO.getExpiry();
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name of the goal is required")
    private String name;

    @NotBlank(message = "Expiry Date is mandatory")
    private String expiry;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id")
    @JsonIgnore
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}

package com.ankit.scheduler.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {
    @Id
    private long id;

    private String type;
}

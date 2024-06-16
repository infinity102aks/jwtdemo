package com.ankit.scheduler.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Timestamp timestamp;
    private String level;
    private String logger;
    private String message;
}

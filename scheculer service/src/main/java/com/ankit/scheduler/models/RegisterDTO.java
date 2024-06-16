package com.ankit.scheduler.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "not a valid email")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private transient String password;
}

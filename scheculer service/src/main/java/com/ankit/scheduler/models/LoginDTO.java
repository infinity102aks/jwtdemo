package com.ankit.scheduler.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email is mandatory")
    String email;

    @NotBlank(message = "Password is mandatory")
    transient String  password;
}

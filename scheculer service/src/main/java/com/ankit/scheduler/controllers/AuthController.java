package com.ankit.scheduler.controllers;

import com.ankit.scheduler.models.AuthResponseDTO;
import com.ankit.scheduler.models.LoginDTO;
import com.ankit.scheduler.models.RegisterDTO;
import com.ankit.scheduler.Entity.User;
import com.ankit.scheduler.security.SecurityConfig;
import com.ankit.scheduler.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid RegisterDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        User newUser = this.userService.createNewUser(dto);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto, BindingResult result, HttpServletResponse response) throws IOException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        AuthResponseDTO responseDto = null;
        try {
            responseDto = this.userService.login(dto);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        SecurityConfig.createSecurityCookie(response, responseDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }
}

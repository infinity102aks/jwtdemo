package com.ankit.scheduler.controllers;

import com.ankit.scheduler.Entity.User;
import com.ankit.scheduler.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        User newUser = this.userService.createNewUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getAllUsersByName(@PathVariable("name") String name) {
        List<User> users = this.userService.getAllUserByName(name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAllUserById(@PathVariable("id") Long id) {
        User user = this.userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getUserDetails() {
        User user = this.userService.getCurrentUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getAllUserByEmail(@PathVariable("email") String email) {
        User user = this.userService.getByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

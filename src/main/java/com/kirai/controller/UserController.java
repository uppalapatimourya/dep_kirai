package com.kirai.controller;


import com.kirai.DTO.UserCreateRequest;
import com.kirai.model.User;
import com.kirai.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest request) {
        Optional<User> createdUser = userService.createUser(request);

        if (createdUser.isEmpty()) {
            // Return HTTP 409 if user with email already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with email already exists");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser.get());
    }
}
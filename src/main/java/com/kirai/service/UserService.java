package com.kirai.service;

import com.kirai.DTO.UserCreateRequest;
import com.kirai.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> createUser(UserCreateRequest request);
}

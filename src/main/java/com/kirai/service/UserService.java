package com.kirai.service;


import com.kirai.DTO.UserCreateRequest;
import com.kirai.model.User;
import com.kirai.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> createUser(UserCreateRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return Optional.empty(); // Return empty if user already exists
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setMiddlename(request.getMiddlename());
        user.setLastname(request.getLastname());
        user.setRole(request.getRole());
        user.setGender(request.getGender());
        user.setDob(request.getDob());
        user.setPhone(request.getPhone());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserType(request.getUserType());
        user.setEmailVerified(false);
        user.setActive(true);
        user.setDeleted(false);

        return Optional.of(userRepository.save(user));
    }
}
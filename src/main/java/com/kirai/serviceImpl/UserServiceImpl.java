package com.kirai.serviceImpl;

import com.kirai.DTO.UserCreateRequest;
import com.kirai.model.User;
import com.kirai.repository.UserRepository;
import com.kirai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> createUser(UserCreateRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return Optional.empty(); // Return empty if user already exists
        }

        User user = User.builder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .middlename(request.getMiddlename())
                .lastname(request.getLastname())
                .role(request.getRole())
                .gender(request.getGender())
                .dob(request.getDob())
                .phone(request.getPhone())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userType(request.getUserType())
                .isEmailVerified(false)
                .isActive(true)
                .deleted(false)
                .build();

        return Optional.of(userRepository.save(user));
    }
}

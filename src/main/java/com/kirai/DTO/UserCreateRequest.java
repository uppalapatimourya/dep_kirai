package com.kirai.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCreateRequest {
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "First name cannot be null")
    private String firstname;
    private String middlename;
    private String lastname;
    private String role;
    private String gender;
    private LocalDateTime dob;
    private String phone;
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 10, message = "Password must be at least 10 characters long")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{10,}$",
            message = "Password must contain at least one letter, one number, and one special character"
    )
    private String password;
    private String userType;
}
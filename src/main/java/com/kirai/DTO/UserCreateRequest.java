package com.kirai.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCreateRequest {
    private String email;
    private String firstname;
    private String middlename;
    private String lastname;
    private String role;
    private String gender;
    private LocalDateTime dob;
    private String phone;
    private String username;
    private String password;
    private String userType;
}
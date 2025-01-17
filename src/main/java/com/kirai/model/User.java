package com.kirai.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document("Users")
public class User {
    @Id
    private String id;
    private String email;
    private String firstname;
    private String middlename;
    private String lastname;
    private String role;
    private String gender;
    private LocalDateTime dob;
    private boolean isEmailVerified;
    private String phone;
    private String username;
    private String password;
    private String userType;
    private boolean isActive;
    private boolean deleted;
}
package com.kirai.controller;

import com.kirai.DTO.LoginResponse;
import com.kirai.DTO.LoginRequest;
import com.kirai.model.User;
import com.kirai.service.JwtService;
import com.kirai.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Attempt authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User principal) {
                // Fetch the complete user details from MongoDB
                User user = userRepository.findByEmail(principal.getUsername())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                // Generate tokens
                String accessToken = jwtService.generateAccessToken(user.getId());
                String refreshToken = jwtService.generateRefreshToken(user.getId());

                // Prepare response
                LoginResponse response = new LoginResponse();
                response.setUser(user);

                LoginResponse.TokenInfo tokens = new LoginResponse.TokenInfo();

                LoginResponse.TokenDetails accessTokenDetails = new LoginResponse.TokenDetails();
                accessTokenDetails.setToken(accessToken);
                accessTokenDetails.setExpires(LocalDateTime.now().plusHours(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant().toString());

                LoginResponse.TokenDetails refreshTokenDetails = new LoginResponse.TokenDetails();
                refreshTokenDetails.setToken(refreshToken);
                refreshTokenDetails.setExpires(LocalDateTime.now().plusDays(30)
                        .atZone(ZoneId.systemDefault())
                        .toInstant().toString());

                tokens.setAccess(accessTokenDetails);
                tokens.setRefresh(refreshTokenDetails);
                response.setTokens(tokens);

                return ResponseEntity.ok(response);
            }
        } catch (RuntimeException e) {
            // Handle "User not found"
            if (e.getMessage().equals("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found. Please check your email and try again.");
            }
        } catch (Exception e) {
            // Handle general authentication errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid email or password. Please try again.");
        }

        // Default fallback
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again later.");
    }

}
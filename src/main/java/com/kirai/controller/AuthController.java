package com.kirai.controller;


import com.kirai.DTO.LoginResponse;
import com.kirai.DTO.LoginRequest;
import com.kirai.model.User;
import com.kirai.service.JwtService;
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

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User principal) {
            String accessToken = jwtService.generateAccessToken(1L); // Replace with actual user ID
            String refreshToken = jwtService.generateRefreshToken(1L);

            LoginResponse response = new LoginResponse();
            User user = new User(); // Create a mock user for now
            user.setEmail(principal.getUsername());
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

        return ResponseEntity.badRequest().build();
    }
}
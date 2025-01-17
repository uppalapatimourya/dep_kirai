package com.kirai.controller;

import com.kirai.DTO.LoginResponse;
import com.kirai.DTO.LoginRequest;
import com.kirai.commonUtility.JwtService;
import com.kirai.model.User;
import com.kirai.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Attempt authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User principal) {
                // Fetch the complete user details from MongoDB

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
        }
        catch (BadCredentialsException e) {
            // Handle invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password. Please try again.");
        } catch (RuntimeException e) {
            // Handle "User not found"
            if ("User not found".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found. Please check your email and try again.");
            }
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }

        // Default fallback (should not be reached)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again later.");
    }


}
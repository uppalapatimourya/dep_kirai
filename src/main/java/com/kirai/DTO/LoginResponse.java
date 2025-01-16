package com.kirai.DTO;

import com.kirai.model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private User user;
    private TokenInfo tokens;

    @Data
    public static class TokenInfo {
        private TokenDetails access;
        private TokenDetails refresh;
    }

    @Data
    public static class TokenDetails {
        private String token;
        private String expires;
    }
}

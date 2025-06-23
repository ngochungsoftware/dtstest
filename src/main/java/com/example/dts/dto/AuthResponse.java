package com.example.dts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthResponse {
    private String token;
    private String refreshToken;
    private String tokenType = "Bearer";
    private UserDto user;

    public AuthResponse(String token, String refreshToken, UserDto user) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
    }
}

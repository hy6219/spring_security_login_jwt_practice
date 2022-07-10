package com.example.spring_security_jwt_login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LoginRequest {
    private Long userId;
    private String email;
    private String password;
    private String uuid;
}

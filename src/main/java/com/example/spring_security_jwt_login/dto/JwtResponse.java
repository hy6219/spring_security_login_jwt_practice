package com.example.spring_security_jwt_login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtResponse {
    private String username;
    private int age;
    private String email;

    @Builder
    public JwtResponse(String username, int age, String email) {
        this.username = username;
        this.age = age;
        this.email = email;
    }
}

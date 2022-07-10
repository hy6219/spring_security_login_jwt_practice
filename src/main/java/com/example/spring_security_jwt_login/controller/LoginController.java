package com.example.spring_security_jwt_login.controller;

import com.example.spring_security_jwt_login.common.ApiResponse;
import com.example.spring_security_jwt_login.dto.JwtResponse;
import com.example.spring_security_jwt_login.dto.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/api/login")
    public ApiResponse<JwtResponse> login(@RequestBody LoginRequest request){
        return ApiResponse.<JwtResponse>builder()
                .build();
    }
}

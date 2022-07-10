package com.example.spring_security_jwt_login.constant;

public class JwtConstant {
    private JwtConstant() {
        throw new IllegalStateException("Constant class");
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_TYPE = "Bearer";
}

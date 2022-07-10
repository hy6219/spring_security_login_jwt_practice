package com.example.spring_security_jwt_login.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDto {
    //access 토큰
    private String accessToken;
    //refresh 토큰
    private String refreshToken;

    private String grantType;

    @Builder
    public TokenDto(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

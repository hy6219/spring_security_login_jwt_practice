package com.example.spring_security_jwt_login.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpirationEnums {
    //ms 단위 기준
    ACCESS_TOKEN_EXPIRATION_TIME("ACCESS 토큰 만료 기간/30분", 1000L * 60 * 30),
    REFRESH_TOKEN_EXPIRATION_TIME("REFRESH 토큰 만료 기간/7일", 1000L * 60 * 60 * 24 * 7);

    private String description;
    private Long value;
}

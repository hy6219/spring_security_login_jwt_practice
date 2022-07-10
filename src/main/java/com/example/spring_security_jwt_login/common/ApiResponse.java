package com.example.spring_security_jwt_login.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse<E> {
    private ResultCode code;
    private E result;
    private String message;

    @Builder
    public ApiResponse(ResultCode code, E result, String message) {
        this.code = code;
        this.result = result;
        this.message = message;
    }
}

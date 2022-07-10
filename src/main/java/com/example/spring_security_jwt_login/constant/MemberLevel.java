package com.example.spring_security_jwt_login.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberLevel {
    NORMAL("일반"),
    ADMIN("관리자");

    private String name;
}

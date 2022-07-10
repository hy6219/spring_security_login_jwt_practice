package com.example.spring_security_jwt_login.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberStatus {
    ACTIVE("활동"),
    INACTIVE("휴면"),
    LEAVE("탈퇴");

    private String name;
}

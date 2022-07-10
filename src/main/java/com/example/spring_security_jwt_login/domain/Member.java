package com.example.spring_security_jwt_login.domain;

import com.example.spring_security_jwt_login.constant.MemberLevel;
import com.example.spring_security_jwt_login.constant.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private int age;
    @Enumerated(EnumType.STRING)
    private MemberLevel level;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;
    private String uuid;

    @Builder
    public Member(String email, String username, int age, MemberLevel level, MemberStatus status, String uuid) {
        this.email = email;
        this.username = username;
        this.age = age;
        this.level = level;
        this.status = status;
        this.uuid = uuid;
    }
}

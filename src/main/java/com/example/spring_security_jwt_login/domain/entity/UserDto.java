package com.example.spring_security_jwt_login.domain.entity;

import com.example.spring_security_jwt_login.domain.constant.Gender;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private String phoneNumber;
    @Setter
    private String name;

    @Enumerated(EnumType.STRING)
    @Setter
    private Gender gender;
}

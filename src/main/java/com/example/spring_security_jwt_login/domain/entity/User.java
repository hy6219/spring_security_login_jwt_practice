package com.example.spring_security_jwt_login.domain.entity;

import com.example.spring_security_jwt_login.domain.constant.Gender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String phoneNumber;
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ElementCollection//값 타입(jpa)이 하나 이상일 때 사용
     private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User updateInfo(UserDto userDto){
        this.email = userDto.getEmail();
        this.name = userDto.getName();
        this.gender = userDto.getGender();
        this.password = userDto.getPassword();
        this.phoneNumber = userDto.getPhoneNumber();
        return this;
    }
}

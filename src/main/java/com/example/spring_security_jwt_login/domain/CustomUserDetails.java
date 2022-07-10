package com.example.spring_security_jwt_login.domain;

import lombok.Builder;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class CustomUserDetails implements UserDetails, CredentialsContainer {

    private String username;
    private String password;
    //https://kobumddaring.tistory.com/60 이방식도 존재!
    private final Collection<? extends GrantedAuthority> authorities;//권한

    @Builder
    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetails of(Member member, GrantedAuthority authority) {
        CustomUserDetails details = CustomUserDetails.builder()
                .username(member.getUsername())
                .authorities(Collections.singleton(authority))
                .build();

        return details;
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

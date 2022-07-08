package com.example.spring_security_jwt_login.system.auth;

import com.example.spring_security_jwt_login.domain.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.example.spring_security_jwt_login.constant.JwtExpirationEnums.ACCESS_TOKEN_EXPIRATION_TIME;
import static com.example.spring_security_jwt_login.constant.JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String key;

    //1. JwtParser를 만들기 위한 secret key
    public Key generateSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //2. access, refresh 토큰 생성
    public TokenDto generateToken(String username) {
        //2-1. 클레임과 함께 access 토큰 만들기
        long now = new Date().getTime();
        Date expiration = new Date(now + ACCESS_TOKEN_EXPIRATION_TIME.getValue());

        String accessToken = Jwts.builder()
                .claim("username", username)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        //2-2. refresh 토큰 만들기
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRATION_TIME.getValue()))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        //2-3. access, refresh 토큰을 담아서 리턴해주기
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}

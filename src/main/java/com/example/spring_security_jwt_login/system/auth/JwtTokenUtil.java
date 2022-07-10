package com.example.spring_security_jwt_login.system.auth;

import com.example.spring_security_jwt_login.constant.JwtConstant;
import com.example.spring_security_jwt_login.domain.CustomUserDetails;
import com.example.spring_security_jwt_login.domain.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.spring_security_jwt_login.constant.JwtExpirationEnums.ACCESS_TOKEN_EXPIRATION_TIME;
import static com.example.spring_security_jwt_login.constant.JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String key;

    private final String AUTH_CLAIM = "auth";

    //1. JwtParser를 만들기 위한 secret key
    public Key generateSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //2. access, refresh 토큰 생성
    public TokenDto generateToken(Authentication authentication) {
        //2-1. 클레임과 함께 access 토큰 만들기
        long now = new Date().getTime();
        Date expiration = new Date(now + ACCESS_TOKEN_EXPIRATION_TIME.getValue());

        //권한 가져오기
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = Jwts.builder()
                .claim("username", details.getUsername())
                .claim("auth",authorities)
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
                .grantType(JwtConstant.AUTHORIZATION_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //3. 클레임 가져오기
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(generateSigningKey())
                .parseClaimsJws(token)
                .getBody();
    }


    public String getUserName(String token) {
        return extractAllClaims(token).get("username", String.class);
    }



    //4. 토큰 검증
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //5. 토큰 만료 확인
    public Boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    //6. 만료까지 남은 시간 확인
    public long getRemainMilliSec(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }

    //7. 토큰값으로 검증
    public boolean validateToken(String bearerToken) {
        try {
            Jwts.parser()
                    .setSigningKey(generateSigningKey())
                    .parseClaimsJws(bearerToken);//ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException

            return true;
        }catch (ExpiredJwtException e1){
            log.warn("유효시간이 만료되었습니다");
        }catch (UnsupportedJwtException e2){
            log.warn("형식이 올바르지 않습니다");
        }catch (MalformedJwtException e3){
            log.warn("잘못된 JWT 서명입니다");
        }catch (IllegalArgumentException e4){
            log.warn("JWT 토큰이 잘못되었습니다");
        }catch (SignatureException e5){
            log.warn("서명 검증에 실패했습니다");
        }

        return false;
    }

    //8. authentication(인증) 가져오기 by 토큰값
    public Authentication getAuthentication(String accessToken){
        //토큰 복호화<-auth 클레임 여부 확인
        Claims claims = extractAllClaims(accessToken);

        if(claims.get(AUTH_CLAIM) == null){
            throw new JwtException("권한 정보가 없는 토큰입니다");
        }

        //auth 클레임값 가져오기
        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTH_CLAIM).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        //username claim
        String username = claims.get("username", String.class);

        return new UsernamePasswordAuthenticationToken(username, "", authorities);
    }
}

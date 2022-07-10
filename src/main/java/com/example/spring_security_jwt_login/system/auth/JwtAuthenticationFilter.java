package com.example.spring_security_jwt_login.system.auth;

import com.example.spring_security_jwt_login.constant.JwtConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 1. getToken 메서드로 헤더에서 jwt를 'Bearer' 제외하고 가져옴
     * 2. 해당 토큰이 정상이면 security context에 넣어주기
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.
        String bearerToken = getToken(request);
        //2
        if(StringUtils.hasText(bearerToken) && jwtTokenUtil.validateToken(bearerToken)){
            Authentication authentication = jwtTokenUtil.getAuthentication(bearerToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request){
        String bearerToken = request.getHeader(JwtConstant.AUTHORIZATION_HEADER);

        //bearer 빼고 가져오기(stringutils.hasText - null, empty, str값 모두 확인)
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtConstant.AUTHORIZATION_HEADER)){
            return bearerToken.substring(7);
        }

        return null;
    }
}

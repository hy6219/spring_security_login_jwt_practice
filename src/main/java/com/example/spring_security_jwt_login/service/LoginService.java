package com.example.spring_security_jwt_login.service;

import com.example.spring_security_jwt_login.domain.Member;
import com.example.spring_security_jwt_login.dto.JwtResponse;
import com.example.spring_security_jwt_login.dto.LoginRequest;
import com.example.spring_security_jwt_login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    @CacheEvict(value = "UUID", key = "#request.userId", condition = "#request.userId != null")
    public JwtResponse doLogin(LoginRequest request){

        if(request.getUserId() != null){
            Optional<Member> byId = memberRepository.findById(request.getUserId());

            if(byId.isPresent()){
                Member member = byId.get();

                //탈퇴회원
                
            }
        }
        return null;
    }
}

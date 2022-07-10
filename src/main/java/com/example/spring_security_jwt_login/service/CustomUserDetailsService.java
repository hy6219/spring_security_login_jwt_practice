package com.example.spring_security_jwt_login.service;

import com.example.spring_security_jwt_login.constant.MemberStatus;
import com.example.spring_security_jwt_login.domain.CustomUserDetails;
import com.example.spring_security_jwt_login.domain.Member;
import com.example.spring_security_jwt_login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //email, status active로 찾기
        Optional<Member> member = memberRepository.findByEmailAndStatus(username, MemberStatus.ACTIVE);

        //찾는 멤버가 없으면 예외 발생시키기
        if (member.isEmpty()) {
            throw new UsernameNotFoundException("해당 정보를 가진 회원이 존재하지 않습니다");
        }

        //찾는 멤버가 존재하면 CustomUserDetail 객체 생성
        //권한
        GrantedAuthority authority = new SimpleGrantedAuthority(member.get().getLevel().getName());

        return CustomUserDetails.of(member.get(), authority);
    }
}

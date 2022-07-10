package com.example.spring_security_jwt_login.repository;

import com.example.spring_security_jwt_login.constant.MemberStatus;
import com.example.spring_security_jwt_login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndStatus(String email, MemberStatus status);
}

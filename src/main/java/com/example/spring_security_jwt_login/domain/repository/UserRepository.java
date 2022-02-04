package com.example.spring_security_jwt_login.domain.repository;

import com.example.spring_security_jwt_login.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String username);
}

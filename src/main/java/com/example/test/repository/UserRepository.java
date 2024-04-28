package com.example.test.repository;

import com.example.test.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findById(int id);
    Optional<UserEntity> findByLoginId(String username);
    boolean existsByLoginId(String email);
    boolean existsByNickname1(String nickname);
    boolean existsByRiotId(String riotId);
}

package com.kennedy.demospringsecurityapp.repository;

import com.kennedy.demospringsecurityapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
   Optional<UserEntity> findByEmail(String username);
}

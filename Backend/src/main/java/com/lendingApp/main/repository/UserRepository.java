package com.lendingApp.main.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lendingApp.main.entity.User;

public interface UserRepository extends JpaRepository<User, UUID>{

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}

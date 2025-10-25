package com.lendingApp.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lendingApp.main.entity.PasswordResetToken;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long>{
	Optional<PasswordResetToken> findTopByEmailOrderByExpiryDateDesc(String email);
}

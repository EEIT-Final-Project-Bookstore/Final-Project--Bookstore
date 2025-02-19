package com.finalproject.ispan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.EmailVerificationCodes;

public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCodes, Long> {
	// 查詢最新的驗證碼
    Optional<EmailVerificationCodes> findTopByEmailOrderByCreateTimeDesc(String email);
}

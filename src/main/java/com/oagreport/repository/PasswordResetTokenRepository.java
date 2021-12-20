package com.oagreport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oagreport.domain.ResetPasswordTokenDto;

public interface PasswordResetTokenRepository extends JpaRepository<ResetPasswordTokenDto,Long> {

	Object findByResetPasswordToken(String resetPasswordToken);

	

}

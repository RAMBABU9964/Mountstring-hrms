package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ForgotPasswordToken;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, Long> {

	ForgotPasswordToken findByToken(String token);
}

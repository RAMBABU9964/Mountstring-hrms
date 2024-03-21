package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.BankAccountDetails;
import java.util.Optional;


@Repository
public interface BankAccountDetailsRepo extends JpaRepository<BankAccountDetails, Long> {

	
	@Query("SELECT b FROM BankAccountDetails b WHERE b.user.id = :userId")
    BankAccountDetails findByUserId(Long userId);
	
	Optional<BankAccountDetails> findById(Long id);
}

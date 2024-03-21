package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.BankAccountDetails;
import com.example.demo.repository.BankAccountDetailsRepo;

@Service
public class BankAccountDetailsService {
	@Autowired
	BankAccountDetailsRepo accountDetailsRepo;

	public BankAccountDetails saveBankAccountDetails(BankAccountDetails bankAccountDetails) {
		// You may want to perform any additional business logic or validation here

		// Save bank account details using the repository
		return accountDetailsRepo.save(bankAccountDetails);
	}

	public void updateEmployee(BankAccountDetails bankAccountDetails) {
		if (accountDetailsRepo.existsById(bankAccountDetails.getId())) {
			// Save the updated employee
			accountDetailsRepo.save(bankAccountDetails);
		} else {
			throw new RuntimeException("Employee not found for id: " + bankAccountDetails.getId());
		}
	}

	public BankAccountDetails getEmployeeById(long id) {
		Optional<BankAccountDetails> optional = accountDetailsRepo.findById(id);
		BankAccountDetails accountDetails = null;
		if (optional.isPresent()) {
			accountDetails = optional.get();
		} else {
			throw new RuntimeException("Emplyee not found for id::" + id);
		}
		return accountDetails;
	}

}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.demo.model.BankAccountDetails;
import com.example.demo.model.User;
import com.example.demo.repository.BankAccountDetailsRepo;
import com.example.demo.service.BankAccountDetailsService;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BankAccountDetailsController {
	@Autowired
	BankAccountDetailsRepo accountDetailsRepo;

	@Autowired
	UserService userService;

	@Autowired
	BankAccountDetailsService accountDetailsService;

	@GetMapping("/bankAccount/{id}")
	public String bankDetails(@PathVariable("id") Long id, Model model) {
		User user = userService.findById(id);
		BankAccountDetails accountDetails = accountDetailsRepo.findByUserId(id);
		if (accountDetails == null) {
			accountDetails = new BankAccountDetails();
			accountDetails.setUser(user);
			model.addAttribute("bankAccountDetails", accountDetails);
		} else {
			model.addAttribute("bankAccountDetails1", accountDetails);
			model.addAttribute("message1", "The Employee Bank Details already Added");
			return "BankAlreadyMessage";
		}
		return "AddBankDetails";
	}

	@PostMapping("/saveBankDetails/{id}")
	public String saveBankDetails(@PathVariable("id") Long id,
			@ModelAttribute("bankAccountDetails") BankAccountDetails bankAccountDetails, Model model) {
		// Retrieve the user associated with the bank account
		User user = userService.findById(id);
		if (user != null) {
			// Set the user field of bankAccountDetails
			bankAccountDetails.setUser(user);
			// Save bank account details
			BankAccountDetails savedBankAccountDetails = accountDetailsService
					.saveBankAccountDetails(bankAccountDetails);
			// Add success message if needed
			model.addAttribute("message", "Bank details saved successfully");
			// Redirect to a different page or return the same page with a success message
			return "redirect:/admin-page"; // Redirect to a different page
		} else {
			// Handle the case where the user is not found
			model.addAttribute("message", "User not found");
			return "error-page"; // Redirect to an error page
		}
	}

	@GetMapping("/showFormForUpdateBankDetails/{id}")
	public String updateAttendanceForm(@PathVariable(value = "id") long id, Model model) {
		// Fetch the employee by ID
		BankAccountDetails accountDetails = accountDetailsService.getEmployeeById(id);
		// You may want to fetch the latest attendance record for this employee here

		// Pass necessary data to the view
		model.addAttribute("employee", accountDetails);
		// Assuming you have a form for updating attendance, you can pass necessary data
		// to it

		return "updateBankDetails";// Return the view for updating attendance
	}

	@PostMapping("/saveBankDetailsForUpdate")
	public String saveData(@ModelAttribute BankAccountDetails bankAccountDetails) {
		BankAccountDetails existingEmployee = accountDetailsService.getEmployeeById(bankAccountDetails.getId());

		accountDetailsService.updateEmployee(bankAccountDetails);

		return "redirect:/admin-page";

	}
}

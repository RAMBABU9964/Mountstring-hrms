package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class BankAccountDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String accountNumber;
	private String bankName;
	private String branchName;
	private String ifscCode;
	private String accountType;
	private String aadherNumber;
	private String panNumber;

	@OneToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	public BankAccountDetails() {
		super();
	}

	public BankAccountDetails(Long id, String accountNumber, String bankName, String branchName, String ifscCode,
			String accountType, String aadherNumber, String panNumber, User user) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.bankName = bankName;
		this.branchName = branchName;
		this.ifscCode = ifscCode;
		this.accountType = accountType;
		this.aadherNumber = aadherNumber;
		this.panNumber = panNumber;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAadherNumber() {
		return aadherNumber;
	}

	public void setAadherNumber(String aadherNumber) {
		this.aadherNumber = aadherNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "BankAccountDetails [id=" + id + ", accountNumber=" + accountNumber + ", bankName=" + bankName
				+ ", branchName=" + branchName + ", ifscCode=" + ifscCode + ", accountType=" + accountType
				+ ", aadherNumber=" + aadherNumber + ", panNumber=" + panNumber + ", user=" + user + "]";
	}

}

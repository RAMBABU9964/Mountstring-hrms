package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class SalaryCalculator {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private double dailySalary;
	
	
	
	@OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

	public SalaryCalculator() {
		super();
	}

	public SalaryCalculator(long id, double dailySalary, User user) {
		super();
		this.id = id;
		this.dailySalary = dailySalary;
		this.user = user;
	}

	@Override
	public String toString() {
		return "SalaryCalculator [id=" + id + ", dailySalary=" + dailySalary + ", user=" + user + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getDailySalary() {
		return dailySalary;
	}

	public void setDailySalary(double dailySalary) {
		this.dailySalary = dailySalary;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}

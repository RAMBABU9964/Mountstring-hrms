package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class SalaryCalculator {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private double dailySalary;
	
	@OneToOne
    @JoinColumn(name = "attendance_id", nullable = false)
    private Attendance attendance;

	public SalaryCalculator() {
		super();
	}

	
	

	public SalaryCalculator(long id, double dailySalary, Attendance attendance) {
		super();
		this.id = id;
		this.dailySalary = dailySalary;
		
		this.attendance = attendance;
	}

	@Override
	public String toString() {
		return "SalaryCalculator [id=" + id + ", dailySalary=" + dailySalary + ", attendance="
				+ attendance + "]";
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

	

	public Attendance getAttendance() {
		return attendance;
	}

	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}
	
	
	
	
}

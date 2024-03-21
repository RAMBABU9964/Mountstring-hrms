package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private LocalDate date;

	private LocalTime inTime;

	private LocalTime outTime;
	
	private double totalHours; 
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Attendance() {
		super();
	}

	public Attendance(long id, LocalDate date, LocalTime inTime, LocalTime outTime, double totalHours, User user) {
		super();
		this.id = id;
		this.date = date;
		this.inTime = inTime;
		this.outTime = outTime;
		this.totalHours = totalHours;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getInTime() {
		return inTime;
	}

	public void setInTime(LocalTime inTime) {
		this.inTime = inTime;
	}

	public LocalTime getOutTime() {
		return outTime;
	}

	public void setOutTime(LocalTime outTime) {
		this.outTime = outTime;
	}

	public double getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(double totalHours) {
		this.totalHours = totalHours;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Attendance [id=" + id + ", date=" + date + ", inTime=" + inTime + ", outTime=" + outTime
				+ ", totalHours=" + totalHours + ", user=" + user + "]";
	}

	
}

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
	
	private LocalTime fixedTime;
	
	private long lateMinutes;
	

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Attendance() {
		super();
	}
	
	
	public Attendance(long id, LocalDate date, LocalTime inTime, LocalTime outTime, double totalHours,
			LocalTime fixedTime, long lateMinutes, User user) {
		super();
		this.id = id;
		this.date = date;
		this.inTime = inTime;
		this.outTime = outTime;
		this.totalHours = totalHours;
		this.fixedTime = fixedTime;
		this.lateMinutes = lateMinutes;
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

	public LocalTime getFixedTime() {
		return fixedTime;
	}

	public void setFixedTime(LocalTime fixedTime) {
		this.fixedTime = fixedTime;
	}
	
	

	public long getLateMinutes() {
		return lateMinutes;
	}


	public void setLateMinutes(long lateMinutes) {
		this.lateMinutes = lateMinutes;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "Attendance [id=" + id + ", date=" + date + ", inTime=" + inTime + ", outTime=" + outTime
				+ ", totalHours=" + totalHours + ", fixedTime=" + fixedTime + ", lateMinutes=" + lateMinutes + ", user="
				+ user + "]";
	}

	

	

	
}

package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

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
	@JoinColumn(name = "fixeddetails_id")
	private FixedDetails fixedDeatilsId;



	private long lateMinutes;

	private double overtime;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Attendance() {
	}

	public Attendance(long id, LocalDate date, LocalTime inTime, LocalTime outTime, double totalHours, FixedDetails fixedDeatilsId, long lateMinutes, double overtime, User user) {
		this.id = id;
		this.date = date;
		this.inTime = inTime;
		this.outTime = outTime;
		this.totalHours = totalHours;
		this.fixedDeatilsId = fixedDeatilsId;
		this.lateMinutes = lateMinutes;
		this.overtime = overtime;
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

	public FixedDetails getFixedDeatilsId() {
		return fixedDeatilsId;
	}

	public void setFixedDeatilsId(FixedDetails fixedDeatilsId) {
		this.fixedDeatilsId = fixedDeatilsId;
	}

	public long getLateMinutes() {
		return lateMinutes;
	}

	public void setLateMinutes(long lateMinutes) {
		this.lateMinutes = lateMinutes;
	}

	public double getOvertime() {
		return overtime;
	}

	public void setOvertime(double overtime) {
		this.overtime = overtime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

package com.example.demo.model;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String email;
	private String password;
	private String role;
	private String fullname;
	private String phoneNumber;
	private String salary;
	private String empRole;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] image;

	@ManyToMany
	List<Teams> teams;

	public User() {
		super();
	}

	public User(String email, String password, String role, String fullname, String phoneNumber, String salary,
			String empRole, byte[] image) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
		this.fullname = fullname;
		this.phoneNumber = phoneNumber;
		this.salary = salary;
		this.empRole = empRole;
		this.image = image;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getEmpRole() {
		return empRole;
	}

	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<Teams> getTeams() {
		return teams;
	}

	public void setTeams(List<Teams> teams) {
		this.teams = teams;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", role=" + role + ", fullname="
				+ fullname + ", phoneNumber=" + phoneNumber + ", salary=" + salary + ", empRole=" + empRole + ", image="
				+ (image != null ? Arrays.toString(Arrays.copyOf(image, Math.min(image.length, maxLen))) : null)
				+ ", teams=" + (teams != null ? teams.subList(0, Math.min(teams.size(), maxLen)) : null) + "]";
	}

	
}

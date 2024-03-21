package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Teams {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String teamName;
    
	@ManyToMany
	List<User> user;

	public Teams() {
		super();
	}

	public Teams(Long id, String teamName, List<User> user) {
		super();
		this.id = id;
		this.teamName = teamName;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "Teams [id=" + id + ", teamName=" + teamName + ", user="
				+ (user != null ? user.subList(0, Math.min(user.size(), maxLen)) : null) + "]";
	}
	
	
}

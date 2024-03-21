package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Teams;
import com.example.demo.repository.TeamsRepo;

@Service
public class TeamService {

	
	@Autowired
	TeamsRepo repo;
	
	public List<Teams> getAllTeams() {
		return repo.findAll();
		
	}
}

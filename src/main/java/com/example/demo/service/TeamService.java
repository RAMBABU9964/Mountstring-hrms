package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Teams;
import com.example.demo.model.User;
import com.example.demo.repository.TeamsRepo;
import com.example.demo.repository.UserRepository;

@Service
public class TeamService {

	
	@Autowired
	TeamsRepo repo;
	
	@Autowired
	UserRepository repository;
	
	public List<Teams> getAllTeams() {
		return repo.findAll();
		
	}
	
	 public void removeUserFromAllTeams(User userToRemove) {
	        List<Teams> allTeams = repo.findAll();

	        for (Teams team : allTeams) {
	            if (team.getUser().contains(userToRemove)) {
	                team.getUser().remove(userToRemove);
	                repo.save(team); // Save the updated team
	            }
	        }
	    }

	
	 public void addUserToTeam(User userToAdd, Teams team) {
		    team.getUser().add(userToAdd);
		    repo.save(team);
		}

	
		 public Teams getTeamById(Long teamId) {
		        Optional<Teams> teamOptional = repo.findById(teamId);
		        return teamOptional.orElse(null);
		    } 
		 public void removeUserFromTeam(User userToRemove, Teams team) {
			    team.getUser().remove(userToRemove);
			    repo.save(team);
			}


}
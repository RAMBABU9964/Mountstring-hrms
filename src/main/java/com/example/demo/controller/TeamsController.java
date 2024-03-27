package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Teams;
import com.example.demo.model.User;
import com.example.demo.repository.TeamsRepo;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TeamService;
import com.example.demo.service.UserService;

@Controller
public class TeamsController {

	@Autowired
	TeamsRepo teamsRepo;

	@Autowired
	UserService service;

	@Autowired
	UserRepository repository;

	@Autowired
	TeamService service2;

	@GetMapping("/Teams")
	public String getregisterpag() {

		return "Team";
	}

	@GetMapping("/CreatTeams")
	public String creatTeams(Model model) {
		List<User> users = service.fetchAlluser();

		model.addAttribute("user", users);

		return "CreateTeams";
	}

	@PostMapping("/createTeam")
	public String createTeam(@ModelAttribute Teams team) {

		teamsRepo.save(team);
		return "redirect:/Teams";
	}

	

	@PostMapping("/removeUserFromAllTeams/{userId}")
	public String removeUserFromAllTeams(@PathVariable("userId") Long userId) {
		User userToRemove = service.getEmployeeById(userId);
		if (userToRemove != null) {
			service2.removeUserFromAllTeams(userToRemove);
		}
		return "redirect:/admin-page"; // Redirect to the appropriate page
	}
	
	@GetMapping("/viewTeams")
	public String viewTeams(Model model) {
	    List<Teams> teamList = service2.getAllTeams();
	    List<User> allUsers = service.fetchAlluser(); 
	    // Fetch all users
	    model.addAttribute("allTeams", teamList);
	    model.addAttribute("allUsers", allUsers); // Add all users to the model
	    return "Teamlist";
	}

	@PostMapping("/addUserToTeam/{teamId}")
	public String addUserToTeam(@ModelAttribute("userId") Long userId, @PathVariable("teamId") Long teamId) {
	    User userToAdd = service.getEmployeeById(userId);
	    Teams team = service2.getTeamById(teamId);
	    System.out.println(userToAdd);
	    if (userToAdd != null && team != null&& !team.getUser().contains(userToAdd)) {
	        service2.addUserToTeam(userToAdd, team);
	    }else {
	    	return "redirect:/viewTeams";
	    }
	    return "redirect:/viewTeams";
	}
	@PostMapping("/removeUserFromTeam/{userId}")
	public String removeUserFromTeam(@PathVariable("userId") Long userId, @RequestParam("teamId") Long teamId) {
	    User userToRemove = service.getEmployeeById(userId);
	    Teams team = service2.getTeamById(teamId);
	    if (userToRemove != null && team != null && team.getUser().contains(userToRemove)) {
	        service2.removeUserFromTeam(userToRemove, team);
	    }
	    return "redirect:/viewTeams";
	}


}

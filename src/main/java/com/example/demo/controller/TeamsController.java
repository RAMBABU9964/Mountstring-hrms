package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
		List<User> users=service.fetchAlluser();

		model.addAttribute("user", users);
		
		return "CreateTeams";
	}
	
	 @PostMapping("/createTeam")
	    public String createTeam(@ModelAttribute Teams team) {
	       
	        teamsRepo.save(team);
	        return "redirect:/Teams";
	    }
	 
	 @GetMapping("/viewTeams")
		public String viewTeams(Model model) {
			List<Teams> teamList =service2.getAllTeams();
			model.addAttribute("allTeams", teamList);
			return "Teamlist";
		}
}

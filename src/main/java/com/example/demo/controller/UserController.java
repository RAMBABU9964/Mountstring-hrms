package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.AttendanceRepo;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.UserService;

@Controller
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	AttendanceRepo attendanceRepo;
	
	@Autowired
	AttendanceService attendanceService;

//******************************************************************************************************	
	@GetMapping("/")
	public String back() {
		return "login";
		
	}
	
	
	@GetMapping("/registration")
	public String getregisterpag() {
		return "register";
		
	}
	
	@PostMapping("/registration")
	public String savUser(@ModelAttribute("user") UserDto userDto ,
			Model model,@RequestParam("imageFile") MultipartFile imageFile) {
       
		
		try {
			if (!imageFile.isEmpty()) {
				userDto.setImage(imageFile.getBytes());
			}
			 userService.sava(userDto);
		       model.addAttribute("message", "Registered successfuly");
				System.out.println("User Added");
		
			return "redirect:/admin-page";
		} catch (IOException e) {
			System.err.println("Error occurred while saving employee data: " + e.getMessage());
			// You can add error handling logic here, such as logging the error or returning
			// an error page
			return "redirect:/admin-page"; // Replace "error-page" with the appropriate error page name
		}
	}
//*************************************************************************************************
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
//**************************************************************************************************
	
	
	@GetMapping("employee-page")
	public String userPage(Model model, Principal principal) {
		UserDetails userDetails= userDetailsService.loadUserByUsername(principal.getName());
		
		model.addAttribute("user", userDetails);
		User employee = userService.findByEmail(principal.getName());
		
		
		
		if (employee != null && employee.getImage() != null) {
			String imageBase64 = Base64.getEncoder().encodeToString(employee.getImage());
			model.addAttribute("imageBase64", imageBase64);
		}
		
		// Add employee object to the model to pass it to the view
	    if (employee != null) {
	        model.addAttribute("employee", employee);
	    } else {
	        // Handle the case where the employee is not found
	        // You can add appropriate error handling or redirect to an error page
	        return "employeeNotFound"; // Assuming you have a template for displaying employee not found
	    }
	    
	    LocalTime a = attendanceRepo.findInTimeByUserIdAndDate(employee.getId(),LocalDate.now());
		System.out.println(a);
		LocalTime b = attendanceRepo.findInTimeByUserIdAndDate1(employee.getId(),LocalDate.now());
		System.out.println(b);
		
		 model.addAttribute("intime", a);
		 model.addAttribute("outtime", b);
	    
		return "employee";
	}
//************************************************************************************************
	
	
	@GetMapping("admin-page")
	public String adminPage(Model model, Principal principal) {
		UserDetails userDetails= userDetailsService.loadUserByUsername(principal.getName());
		User user = userService.findByEmail(principal.getName());
		
		 LocalTime presentFixedTime = attendanceService.getCurrentFixedTime();
		 LocalTime presentFixedTime2 = attendanceService.getCurrentFixedOutTime();

		    // Pass the present fixed time to the HTML template
		 model.addAttribute("currentFixedTime", presentFixedTime);
		 model.addAttribute("currentFixedTime1", presentFixedTime2);
		
		List<User> allDate=userService.fetchAllData();
		model.addAttribute("user", userDetails);
		model.addAttribute("list", allDate);
		return "admin";
	}
	
	
//****************************************************************************************************
//view the admin profile to the admin
	@GetMapping("myprofile")
	public String adminProfile(Model model, Principal principal) {
		UserDetails userDetails= userDetailsService.loadUserByUsername(principal.getName());
		
		model.addAttribute("user", userDetails);
		User employee = userService.findByEmail(principal.getName());
		
		
		
		if (employee != null && employee.getImage() != null) {
			String imageBase64 = Base64.getEncoder().encodeToString(employee.getImage());
			model.addAttribute("imageBase64", imageBase64);
		}
		
		// Add employee object to the model to pass it to the view
	    if (employee != null) {
	        model.addAttribute("employee", employee);
	    } else {
	        // Handle the case where the employee is not found
	        // You can add appropriate error handling or redirect to an error page
	        return "employeeNotFound"; // Assuming you have a template for displaying employee not found
	    }
	    
	    LocalTime a = attendanceRepo.findInTimeByUserIdAndDate(employee.getId(),LocalDate.now());
		System.out.println(a);
		LocalTime b = attendanceRepo.findInTimeByUserIdAndDate1(employee.getId(),LocalDate.now());
		System.out.println(b);
		
		 model.addAttribute("intime", a);
		 model.addAttribute("outtime", b);
	    
		return "Admin-Profile";
	}
//******************************************************************************************************
	@GetMapping("pro-admin-page")
	public String proadminPage(Model model, Principal principal) {
		UserDetails userDetails= userDetailsService.loadUserByUsername(principal.getName());
		List<User> allDate=userService.fetchAllData();
		model.addAttribute("user", userDetails);
		model.addAttribute("list", allDate);
		return "Pro-Admin";
	}
//******************************************************************************************************
	
	//Delete function only for the admin	
		@GetMapping("/deleteEmployee/{id}")
		public String deleteEmployee(@PathVariable(value = "id") Long id) {
			userService.deleteEmplyeeById(id);
			System.out.println("Delete Sucessfully");
			return "redirect:/admin-page";
		}
		
//*************************************************************************************************************************
		//view the employee details to admin
		
		@GetMapping("/viewEmployee/{id}")
		public String viewEmployee(@PathVariable("id") Long id, Model model) {
			// Retrieve employee details by ID from the service
			User employee = userService.findById(id);
			
			if (employee != null && employee.getImage() != null) {
				String imageBase64 = Base64.getEncoder().encodeToString(employee.getImage());
				model.addAttribute("imageBase64", imageBase64);
			}
			
			// Add employee object to the model to pass it to the view
		    if (employee != null) {
		        model.addAttribute("employee", employee);
		    } else {
		        // Handle the case where the employee is not found
		        // You can add appropriate error handling or redirect to an error page
		        return "employeeNotFound"; // Assuming you have a template for displaying employee not found
		    }
			// Return the view name to display employee details
			return "employeeViewForm"; // Create a corresponding HTML template for displaying employee details
		}
//**************************************************************************************************************
		
		
		@PostMapping("/updateEmployeeImage/{id}")
	    public String updateEmployeeImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
	        try {
	            // Retrieve employee by ID
	            User employee = userService.findById(id);
	            
	            if (employee != null && !imageFile.isEmpty()) {
	                // Update employee image
	                employee.setImage(imageFile.getBytes());
	                userService.updateEmployee(employee);
	                //redirectAttributes.addFlashAttribute("successMessage", "Employee image updated successfully.");
	                return "redirect:/viewEmployee/{id}";
	            } else {
	                //redirectAttributes.addFlashAttribute("errorMessage", "Employee not found or image is empty.");
	                return "DataAll";
	            }
	        } catch (IOException e) {
	            //redirectAttributes.addFlashAttribute("errorMessage", "Error updating image: " + e.getMessage());
	            return "redirect:/employeeDetails";
	        }
	    }
		
//************************************************************************************************************************************************************
		
		
		//update for admin
		@GetMapping("/showFormForUpdateAdmin/{id}")
		public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {

			User user = userService.getEmployeeById(id);

			model.addAttribute("employee", user);
			
			return "UpdateForm";
		}
		

//***************************************************************************************************
		
	//Update the detailes in the update form and save to database	
		@PostMapping("/save")
		public String saveData(@ModelAttribute User employee) {
		    try {
		        // Retrieve the existing employee from the database
		        User existingEmployee = userService.getEmployeeById(employee.getId());
		        
		        // Set the existing image data to the employee object
		        employee.setPassword(existingEmployee.getPassword());
		        employee.setImage(existingEmployee.getImage());
		        
		        // Update the employee data
		        userService.updateEmployee(employee);
		        
		        // Redirect to the page displaying all employees
		        return "redirect:/admin-page";
		    } catch (Exception e) {
		        System.err.println("Error occurred while saving employee data: " + e.getMessage());
		        // You can add error handling logic here, such as logging the error or returning
		        // an error page
		        return "error-page"; // Replace "error-page" with the appropriate error page name
		    }}
		

//**************************************************************************************************************************	
		//Function for the forgot password

		/*	 @GetMapping("/forgot-password")
			    public String showForgotPasswordForm() {
			        return "forgotPassword";
			    }

			   
			 @PostMapping("/forgot-passwords")
			    public String processForgotPassword(@RequestParam("email") String email) {
			       if( userService.validemail(email)==true) {
			    	   return "resetPassword";
			       }
			        return "redirect:/login";
			    }

			    @PostMapping("/reset-passwords")
			    public String processResetPassword(@RequestParam("email") String email,
			                                       @RequestParam("password") String password) {
			        userService.resetPassword(email, password);
			        System.out.println("Successfuly chang the password");
			        return "redirect:/login";
			    }*/
	
}

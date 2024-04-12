package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.example.demo.model.Attendance;
import com.example.demo.model.BankAccountDetails;
import com.example.demo.model.EmpDocs;
import com.example.demo.model.FixedDetails;
import com.example.demo.model.User;
import com.example.demo.repository.AttendanceRepo;
import com.example.demo.repository.BankAccountDetailsRepo;
import com.example.demo.repository.EmpDocsRepository;
import com.example.demo.repository.FixedDetailsRepo;
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
	
	@Autowired
	FixedDetailsRepo detailsRepo;
	
	@Autowired
	BankAccountDetailsRepo accountDetailsRepo;
	
	@Autowired
	EmpDocsRepository docsRepository;

//******************************************************************************************************	
	@GetMapping("/")
	public String back() {
		return "HomePage";

	}

	@GetMapping("/registration")
	public String getregisterpag() {
		return "register";

	}

	@PostMapping("/registration")
	public String savUser(@ModelAttribute("user") UserDto userDto, Model model,
			@RequestParam("imageFile") MultipartFile imageFile) {

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
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());

		model.addAttribute("user", userDetails);
		User employee = userService.findByEmail(principal.getName());

		if (employee != null && employee.getImage() != null) {
			String imageBase64 = Base64.getEncoder().encodeToString(employee.getImage());
			
			model.addAttribute("imageBase64", imageBase64);
			
		}
		if(employee.getBackGroundImage() !=null) {
			String imageBase65 = Base64.getEncoder().encodeToString(employee.getBackGroundImage());
			model.addAttribute("imageBase65", imageBase65);
		}

		// Add employee object to the model to pass it to the view
		if (employee != null) {
			model.addAttribute("employee", employee);
		}

		LocalTime a = attendanceRepo.findInTimeByUserIdAndDate(employee.getId(), LocalDate.now());
		System.out.println(a);
		LocalTime b = attendanceRepo.findInTimeByUserIdAndDate1(employee.getId(), LocalDate.now());
		System.out.println(b);

		model.addAttribute("intime", a);
		model.addAttribute("outtime", b);

		return "employee";
	}
//************************************************************************************************

	@GetMapping("admin-page")
	public String adminPage(Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		User user = userService.findByEmail(principal.getName());

		LocalTime presentFixedTime = attendanceService.getCurrentFixedTime();
		LocalTime presentFixedTime2 = attendanceService.getCurrentFixedOutTime();
		double time=attendanceService.getCurrentfixedworkingHrs();

		// Pass the present fixed time to the HTML template
		model.addAttribute("currentFixedTime", presentFixedTime);
		model.addAttribute("currentFixedTime1", presentFixedTime2);
		model.addAttribute("currentFixedTime2", time);

		List<User> allDate = userService.fetchAllData();
		Map<Long, String> imageBase64Map = new HashMap<>(); // Map to associate imageBase64 with user id
	    for (User us : allDate) {
	        if (us.getImage() != null) { // Check if the image source is not null
	            String imageBase64 = Base64.getEncoder().encodeToString(us.getImage());
	            imageBase64Map.put(us.getId(), imageBase64); // Associate imageBase64 with user id
	        }
	    }
	    model.addAttribute("imageBase64Map", imageBase64Map); // Add 
		List<FixedDetails> a=detailsRepo.findByCreatedAt(LocalDate.now());
		model.addAttribute("fixed", a);
		model.addAttribute("user", userDetails);
		model.addAttribute("list", allDate);
		return "admin";
	}

//****************************************************************************************************
//view the admin profile to the admin
	@GetMapping("myprofile")
	public String adminProfile(Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());

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

		LocalTime a = attendanceRepo.findInTimeByUserIdAndDate(employee.getId(), LocalDate.now());
		System.out.println(a);
		LocalTime b = attendanceRepo.findInTimeByUserIdAndDate1(employee.getId(), LocalDate.now());
		System.out.println(b);

		model.addAttribute("intime", a);
		model.addAttribute("outtime", b);

		return "Admin-Profile";
	}

//******************************************************************************************************
	@GetMapping("pro-admin-page")
	public String proadminPage(Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		List<User> allDate = userService.fetchAllData();
		model.addAttribute("user", userDetails);
		model.addAttribute("list", allDate);
		return "Pro-Admin";
	}
//******************************************************************************************************

	// Delete function only for the admin
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") Long id) {
		
	try {
		BankAccountDetails accountDetails=accountDetailsRepo.findByUserId(id);
		List<Attendance> attendance=attendanceRepo.findByUserId(id);
		EmpDocs empDocs=docsRepository.findByUserId(id);
	if(accountDetails !=null && attendance !=null && empDocs !=null) {
		accountDetailsRepo.delete(accountDetails);
		attendanceService.deletByUserId(id);
		docsRepository.delete(empDocs);
		userService.deleteEmplyeeById(id);
	}else if(accountDetails !=null && attendance ==null && empDocs ==null ) {
		accountDetailsRepo.delete(accountDetails);
	    userService.deleteEmplyeeById(id);
	}else if(accountDetails ==null && attendance !=null && empDocs ==null) {
		attendanceService.deletByUserId(id);
	    userService.deleteEmplyeeById(id);
	}else if(accountDetails ==null && attendance ==null && empDocs !=null) {
		docsRepository.delete(empDocs);
	    userService.deleteEmplyeeById(id);
	}else if(accountDetails !=null && attendance ==null && empDocs !=null ) {
		accountDetailsRepo.delete(accountDetails);
		docsRepository.delete(empDocs);
	    userService.deleteEmplyeeById(id);
	}else if(accountDetails ==null && attendance !=null && empDocs !=null) {
		attendanceService.deletByUserId(id);
		docsRepository.delete(empDocs);
	    userService.deleteEmplyeeById(id);
	}else if(accountDetails !=null && attendance !=null && empDocs ==null) {
		attendanceService.deletByUserId(id);
		accountDetailsRepo.delete(accountDetails);
	    userService.deleteEmplyeeById(id);
	}
		System.out.println("Delete Sucessfully");
		return "redirect:/admin-page";
		
	}catch(Exception e){
		return "errorPageForDelete";
	}
	}

//*************************************************************************************************************************
	// view the employee details to admin

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
	public String updateEmployeeImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile imageFile,
			RedirectAttributes redirectAttributes) {
		try {
			// Retrieve employee by ID
			User employee = userService.findById(id);

			if (employee != null && !imageFile.isEmpty()) {
				// Update employee image
				employee.setImage(imageFile.getBytes());
				userService.updateEmployee(employee);
				// redirectAttributes.addFlashAttribute("successMessage", "Employee image
				// updated successfully.");
				return "redirect:/employee-page";
			} else {
				// redirectAttributes.addFlashAttribute("errorMessage", "Employee not found or
				// image is empty.");
				return "redirect:/employee-page";
			}
		} catch (IOException e) {
			// redirectAttributes.addFlashAttribute("errorMessage", "Error updating image: "
			// + e.getMessage());
			return "redirect:/employee-page";
		}
	}
	
//*******************************************************************************************************************************
	
	@PostMapping("/updateEmployeebackGroundImage/{id}")
	public String updateEmployeebackGroundImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile imageFile,
			RedirectAttributes redirectAttributes) {
		try {
			// Retrieve employee by ID
			User employee = userService.findById(id);

			if (employee != null && !imageFile.isEmpty()) {
				// Update employee image
				employee.setBackGroundImage(imageFile.getBytes());
				userService.updateEmployee(employee);
				// redirectAttributes.addFlashAttribute("successMessage", "Employee image
				// updated successfully.");
				return "redirect:/employee-page";
			} else {
				// redirectAttributes.addFlashAttribute("errorMessage", "Employee not found or
				// image is empty.");
				return "redirect:/employee-page";
			}
		} catch (IOException e) {
			// redirectAttributes.addFlashAttribute("errorMessage", "Error updating image: "
			// + e.getMessage());
			return "redirect:/employee-page";
		}
	}

//************************************************************************************************************************************************************

	// update for admin
	@GetMapping("/showFormForUpdateAdmin/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {

		User user = userService.getEmployeeById(id);

		model.addAttribute("employee", user);

		return "UpdateForm";
	}

//***************************************************************************************************

	// Update the detailes in the update form and save to database
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
		}
	}

//**************************************************************************************************************************	
	
//***************************************************************************************************************************
	 @GetMapping("/updatemessage")
	    public String updateMessageForm(Model model) {
	        model.addAttribute("message", ""); // Add an empty message attribute to the model
	        return "updateMessageForm"; // Return the name of your HTML template for the message form
	    }

	    @PostMapping("/updatemessage")
	    public String sendMessageToEmployees(@RequestParam("message") String message) {
	       
	            userService.sendEmail( "Update From The Mount String", message);
	        
	        return "redirect:/admin-page"; // Redirect to the admin page after sending the message
	    }
	    
//***************************************************************************************************************************
		 @GetMapping("/leaveMessage/{id}")
		    public String leaveMessageForm(Model model,@PathVariable(value = "id") Long id) {
			 User user=userService.findById(id);
			 model.addAttribute("user", user);
		        model.addAttribute("message", ""); // Add an empty message attribute to the model
		        return "LeaveMessageForm"; // Return the name of your HTML template for the message form
		    }

		    @PostMapping("/leaveMessage/{id}")
		    public String sendMessageToAdmin(@RequestParam("message") String message,@RequestParam("id") long id) {
		       User user=userService.findById(id);
		       
		            userService.sendEmail2( "Leave Request", message,user);
		        
		        return "redirect:/employee-page"; // Redirect to the admin page after sending the message
		    }
	
//**************************************************************************************************************************************
	// Function for the forgot password

	/*
	 * @GetMapping("/forgot-password") public String showForgotPasswordForm() {
	 * return "forgotPassword"; }
	 * 
	 * 
	 * @PostMapping("/forgot-passwords") public String
	 * processForgotPassword(@RequestParam("email") String email) { if(
	 * userService.validemail(email)==true) { return "resetPassword"; } 
	 * return "redirect:/login"; }
	 * @PostMapping("/reset-passwords") public String
	 * processResetPassword(@RequestParam("email") String email,
	 * 
	 * @RequestParam("password") String password) { userService.resetPassword(email,
	 * password); System.out.println("Successfuly chang the password"); 
	 * return"redirect:/login"; }
	 */

}



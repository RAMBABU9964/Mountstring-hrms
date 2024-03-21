package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Attendance;
import com.example.demo.model.User;
import com.example.demo.repository.AttendanceRepo;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.UserService;

import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AttendanceController {

	
	@Autowired
	UserService userService;

	@Autowired
	AttendanceService attendanceService;
	
	@Autowired
	AttendanceRepo attendanceRepo;

	@GetMapping("/markIn/{id}")
	public String markInTime(@PathVariable long id, Model model) {
		// Assuming you have a method to fetch the employee by ID
		User user = userService.getEmployeeById(id);
//		boolean canMarkAttendance = attendanceService.canMarkInTime(employee);
		
		java.util.List<Attendance> a = attendanceRepo.findByUserIdAndDate(id, LocalDate.now());
		model.addAttribute("EmployeeAttendace", a);
		System.out.println(a);
		model.addAttribute("message", "Successfully Marked InTime Attendance");
//		 if (canMarkAttendance) {
		attendanceService.markInTime(user);
		if(user.getEmail()!=null) {
			
			try {
				attendanceService.sendEmail(user.getEmail(), "Attendance Update Message");
				
			} catch (UnsupportedEncodingException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		 }else {
//	            // If the employee cannot mark attendance, display an error message
//	            modelAndView.addObject("error", "Cannot mark attendance for today. Please mark out time for the previous day first.");
//	        }
		return "redirect:/employee-page";
	}

	@GetMapping("/markOut/{id}")
	public String markOutTime(@PathVariable long id ,Model model) {
		User user = userService.getEmployeeById(id);
		attendanceService.markOutTime(user.getId());
		model.addAttribute("message", "Successfully Marked OutTime Attendance");
		
		Optional<Attendance> latestAttendanceOptional = attendanceRepo.findTopByEmplyeeIdOrderByIdDesc(user.getId());
	    latestAttendanceOptional.ifPresent(attendance -> {
	        // Calculate duration between inTime and outTime
	        Duration duration = Duration.between(attendance.getInTime(), attendance.getOutTime());
	        
	        // Calculate total hours
	        long hours = duration.toHours();
	        long minutes = duration.toMinutesPart();
	        double totalHours = hours + (minutes / 60.0);
	        
	        // Set total hours in the attendance record
	        attendance.setTotalHours(totalHours);
	        
	        // Save updated attendance record
            attendanceRepo.save(attendance);
	    });
		
		if(user.getEmail()!=null) {
			Attendance attendance=new Attendance();
			try {
				attendanceService.sendEmail2(user.getEmail(), "Attendance Update Message");
				
			} catch (UnsupportedEncodingException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:/employee-page";
	}
	
//************************************************************************************************	
	//Attendance view for admin
	@GetMapping("/viewAttendance/{id}")
	public String getMethodName(@PathVariable("id") long id,Model model) {
		User user=userService.getEmployeeById(id);
		
		if(user!=null) {
			List<Attendance> attendance=attendanceRepo.findByUserId(id);
			model.addAttribute("view", attendance);
		}else {
			return "redirect:/admin-page";
		}
		
		return "viewAttendance";
	}
	
	
//**************************************************************************************************
	
	//Attendance view for employee
		@GetMapping("/viewAttendanceEmployee/{id}")
		public String attendanceView(@PathVariable("id") long id,Model model) {
			User user=userService.getEmployeeById(id);
			
			if(user!=null) {
				List<Attendance> attendance=attendanceRepo.findByUserId(id);
				model.addAttribute("viewEmployee", attendance);
			}else {
				return "redirect:/employee-page";
			}
			
			return "viewAttendanceForEmployee";
		}
	
//****************************************************************************************************
		
//update attendance for admin
	
	@GetMapping("/showFormForUpdateAttendanceForAdmin/{id}")
	public String updateAttendanceForm(@PathVariable("id") long id, Model model) {
	    // Fetch the employee by ID
	   Attendance attendance=attendanceService.getEmployeeById(id);
	    // You may want to fetch the latest attendance record for this employee here
	    if(attendance.getUser().getRole().equals("EMPLOYEE")) {
	    // Pass necessary data to the view
	    model.addAttribute("employee", attendance);
	    // Assuming you have a form for updating attendance, you can pass necessary data to it
	   
	    }else {
			return "redirect:/admin-page";
		}
	    return "updateAttendance";// Return the view for updating attendance
	}
//*************************************************************************************************
	//save the Attendance ofter update
	@PostMapping("/saveAttendance/{id}")
	public String saveData(@PathVariable("id") long id, @ModelAttribute Attendance attendance) {
	    Attendance existingEmployee = attendanceService.getEmployeeById(id);
		
	    attendance.setUser(existingEmployee.getUser());
	    attendance.setDate(existingEmployee.getDate());
		attendanceService.updateEmployee(attendance);
		
		
		Duration duration = Duration.between(attendance.getInTime(), attendance.getOutTime());
	    
	    // Calculate total hours worked
	    long hours = duration.toHours();
	    long minutes = duration.toMinutesPart();
	    double totalHours = hours + (minutes / 60.0);
	    
	    // Set the calculated total hours in the attendance record
	    attendance.setTotalHours(totalHours);
	    
	    // Save the updated attendance record
	    attendanceRepo.save(attendance);
		 
		
		
		return "redirect:/admin-page";
		
	}
	
	 @GetMapping("/markAttendance/{id}")
	    public String showMarkAttendanceForm(@PathVariable("id") Long id, Model model) {
	        User user = userService.findById(id);
	        Attendance attendance = new Attendance();
	        attendance.setUser(user);
	        model.addAttribute("attendance", attendance);
	        return "markAttendance";
	    }

	    @PostMapping("/markAttendance/{id}")
	    public String markAttendance(@PathVariable("id") Long id, Attendance attendance) {
	        User user = userService.findById(id);
	        attendance.setUser(user);
	        attendance.setDate(LocalDate.now());
	        
	        
	        Duration duration = Duration.between(attendance.getInTime(), attendance.getOutTime());
		    
		    // Calculate total hours worked
		    long hours = duration.toHours();
		    long minutes = duration.toMinutesPart();
		    double totalHours = hours + (minutes / 60.0);
		    
		    // Set the calculated total hours in the attendance record
		    attendance.setTotalHours(totalHours);
		    
		    // Save the updated attendance record
		    attendanceRepo.save(attendance);
		    if(user.getEmail()!=null) {
				
				try {
					attendanceService.sendEmail(user.getEmail(), "Attendance Update Message");
					attendanceService.sendEmail2(user.getEmail(), "Attendance Update Message");
					
					
				} catch (UnsupportedEncodingException | MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
	        
	        return "redirect:/admin-page"; // Redirect to your employee list page
	    }
	    
	    @GetMapping("/ListPresent")
	    public String presentMarkedController(Model model) {
	    	List<Attendance> a =attendanceRepo.findByDate(LocalDate.now());
	    	model.addAttribute("PresentList", a);
	    	return "AdminPresentList";
	    	
	    }

	
	
}

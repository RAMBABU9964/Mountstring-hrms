package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import com.example.demo.repository.FixedDetailsRepo;
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

	@Autowired
	FixedDetailsRepo DeatailsRepo;

	@GetMapping("/markIn/{id}")
	public String markInTime(@PathVariable long id, Model model) {
		// Fetch the employee by ID
		User user = userService.getEmployeeById(id);

		// Get the fixed time for attendance marking
		LocalTime fixedTime = DeatailsRepo.getCurrentFixedTimeFromDatabase();

		// Check if the current time is after the fixed time
		if (LocalTime.now().isAfter(fixedTime)) {
			// Calculate the minutes late
			long minutesLate = Duration.between(fixedTime, LocalTime.now()).toMinutes();
			model.addAttribute("minutesLate", minutesLate);
		}

		try {
			// Check if the employee has already marked in time
			List<Attendance> inTimeRecords = attendanceRepo.findByUserIdAndDate(id, LocalDate.now());

			if (!inTimeRecords.isEmpty()) {
				// Handle the case where there are multiple in-time records for the same user
				// and date
				// For demonstration, let's consider the latest record
				Attendance latestAttendance = inTimeRecords.get(inTimeRecords.size() - 1);

				// Add logic here based on your requirements

				// For now, let's just add a message to the model
				model.addAttribute("message", "You have already marked today's in-time attendance.");
				return "redirect:/employee-page";
			}

			// Mark in time attendance
			attendanceService.markInTime(user);

			// Send email notification
			if (user.getEmail() != null) {
				try {
					attendanceService.sendEmail(user.getEmail(), "In Time Attendance Marked Successfully");
				} catch (UnsupportedEncodingException | MessagingException e) {
					e.printStackTrace();
				}
			} else {
				return "redirect:/employee-page";
			}
		} catch (Exception e) {
			// Handle the exception (e.g., log it, return an error page)
			e.printStackTrace(); // Logging the exception
			model.addAttribute("error", "An error occurred while fetching attendance records.");
			return "error-page"; // Or whatever error handling mechanism you have
		}

		return "redirect:/employee-page";
	}

	@GetMapping("/markOut/{id}")
	public String markOutTime(@PathVariable long id, Model model) {
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

		if (user.getEmail() != null) {
			Attendance attendance = new Attendance();
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
	// Attendance view for admin
	@GetMapping("/viewAttendance/{id}")
	public String getMethodName(@PathVariable("id") long id, Model model) {
		User user = userService.getEmployeeById(id);

		if (user != null) {
			List<Attendance> attendance = attendanceRepo.findByUserId(id);
			model.addAttribute("view", attendance);
		} else {
			return "redirect:/admin-page";
		}

		return "viewAttendance";
	}

//**************************************************************************************************

	// Attendance view for employee
	@GetMapping("/viewAttendanceEmployee/{id}")
	public String attendanceView(@PathVariable("id") long id, Model model) {
		User user = userService.getEmployeeById(id);

		if (user != null) {
			List<Attendance> attendance = attendanceRepo.findByUserId(id);
			model.addAttribute("viewEmployee", attendance);
		} else {
			return "redirect:/employee-page";
		}

		return "viewAttendanceForEmployee";
	}

//****************************************************************************************************

//update attendance for admin

	@GetMapping("/showFormForUpdateAttendanceForAdmin/{id}")
	public String updateAttendanceForm(@PathVariable("id") long id, Model model) {
		// Fetch the employee by ID
		Attendance attendance = attendanceService.getEmployeeById(id);
		// You may want to fetch the latest attendance record for this employee here
		if (attendance.getUser().getRole().equals("EMPLOYEE")) {
			// Pass necessary data to the view
			model.addAttribute("employee", attendance);
			// Assuming you have a form for updating attendance, you can pass necessary data
			// to it

		} else {
			return "redirect:/admin-page";
		}
		return "updateAttendance";// Return the view for updating attendance
	}

//*************************************************************************************************
	// save the Attendance ofter update
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

		// Fixed time for attendance marking (9:30 AM)
		LocalTime fixedTime = DeatailsRepo.getCurrentFixedTimeFromDatabase();

		// Check if the current time is after the fixed time
		if (LocalTime.now().isAfter(fixedTime)) {
			// Calculate the difference between the current time and the fixed time
			Duration lateDuration = Duration.between(fixedTime, LocalTime.now());
			long minutesLate = lateDuration.toMinutes();

			// Set minutes late in the model attribute
			model.addAttribute("minutesLate", minutesLate);
		}

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
		if (user.getEmail() != null) {

			try {
				attendanceService.sendEmail(user.getEmail(), "Attendance Update Message");
				attendanceService.sendEmail2(user.getEmail(), "Attendance Update Message");

			} catch (UnsupportedEncodingException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "redirect:/admin-page"; // Redirect to your employee list page
	}

	@GetMapping("/ListPresent")
	public String presentMarkedController(Model model) {
		List<Attendance> a = attendanceRepo.findByDate(LocalDate.now());
		model.addAttribute("PresentList", a);
		return "AdminPresentList";

	}

	@PostMapping("/updateFixedTime")
	public String updateFixedTime(@RequestParam("fixedTime") String fixedTimeStr,
			@RequestParam("fixedoutTime") String fixedTimeStr1, @RequestParam("FixedWorkingHours") String fixedWorkinghrs) {
		try {
			// Parse the input string to LocalTime
			LocalTime fixedTime = LocalTime.parse(fixedTimeStr);
			LocalTime fixedTime1 = LocalTime.parse(fixedTimeStr1);
			long seconds = Duration.between(fixedTime, fixedTime1).getSeconds();
			double fixedWorkingHours = seconds / 3600.0;

			// Update the fixed time using the service method
			attendanceService.updateFixedTime(fixedTime, fixedTime1, fixedWorkingHours);

			// Optionally, you can add a success message or handle errors
		} catch (DateTimeParseException e) {
			// Handle parsing errors
		}

		return "redirect:/admin-page";
	}

}

package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.model.Attendance;
import com.example.demo.model.User;
import com.example.demo.repository.AttendanceRepo;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class AttendanceService {

	@Autowired
	AttendanceRepo attendanceRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JavaMailSender javaMailSender;

	public Attendance markInTime(User user) {
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		LocalTime fixedTime = attendanceRepo.getCurrentFixedTimeFromDatabase();
		LocalTime fixedOutTime = attendanceRepo.getCurrentFixedOutTimeFromDatabase();

		// Check if fixed time is retrieved from the database
		if (fixedTime == null && fixedOutTime==null) {
			// Set a default fixed time, assuming it's 9:30 AM
			fixedTime = LocalTime.of(9, 30); // Default fixed time
			fixedOutTime = LocalTime.of(18, 30); // Default fixed time
		}

		Attendance attendance = new Attendance();
		attendance.setDate(currentDate);
		attendance.setInTime(currentTime);
		attendance.setUser(user);
		attendance.setFixedTime(fixedTime);
        attendance.setFixedOutTime(fixedOutTime);
		// Calculate late minutes
		long lateMinutes = 0;
		if (currentTime.isAfter(fixedTime)) {
			lateMinutes = Duration.between(fixedTime, currentTime).toMinutes();
		}
		attendance.setLateMinutes(lateMinutes);

		return attendanceRepo.save(attendance);
	}


	public Attendance markOutTime(long id) {
		System.out.println(id);
		LocalDate currentDate = LocalDate.now();
		List<Attendance> attendances = attendanceRepo.findByUserId(id);
		
		attendances = attendanceRepo.findByDate(currentDate);
		if (!attendances.isEmpty()) {
			// Assuming you want to update the latest attendance record,
			// you can retrieve the last one from the list
			Attendance attendance = attendances.get(attendances.size() - 1);

			// Update the out time
			attendance.setOutTime(LocalTime.now());

			// Save the updated attendance record
			return attendanceRepo.save(attendance);
		} else {
			// Handle the case where no attendance record is found for the given employee
			// and date
			// You can choose to log a message, throw an exception, or handle it as per your
			// requirement
			// For demonstration purposes, let's create a new attendance record
			User user = userService.getEmployeeById(id);
			return markInTime(user); // This will create a new attendance record for the employee
		}
	}

	public void sendEmail(String to,String subject) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message =  javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		LocalDate date;
		date=LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = date.format(formatter);
		String emailContent = "<p>Hello,</p>" 
                + "<p>Successfully Marked InTime Attendance today.</p>"
                + "<p><a href=\"" + formattedDate + "\">" + formattedDate + "</a></p>"
                + "<p>Thank You For Marking Today's Attendance.</p>"
                + "<p>Ignore this email if you did not make the request.</p>";
		
		
		helper.setText(emailContent,true);
		helper.setFrom("rambabumech.rymec@gmail.com", "Mount String Technologies Support");
		helper.setSubject(subject);
		helper.setTo(to);
		javaMailSender.send(message);
		
	}

	public void sendEmail2(String to,String subject) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message =  javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		LocalDateTime dateTime = LocalDateTime.now();

		// Format the date and time using DateTimeFormatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = dateTime.format(formatter);

		// Construct the email content
		String emailContent = "<p>Hello,</p>" 
		                      + "<p>Successfully Marked OutTime Attendance today at " + formattedDateTime + ".</p>"
		                      + "<p><a href=\"" + formattedDateTime + "\">" + "</a></p>"
		                      + "<p>Thank You For Marking Today's Attendance.</p>"
		                      +"Bye Take Care We Will Meet you again Tomorrow"
		                      + "<p>Ignore this email if you did not make the request.</p>";

		
		helper.setText(emailContent,true);
		helper.setFrom("rambabumech.rymec@gmail.com", "Mount String Technologies Support");
		helper.setSubject(subject);
		helper.setTo(to);
		javaMailSender.send(message);
		
	}

	

	public Attendance getEmployeeById(long id) {
		Attendance optional=attendanceRepo.findById(id);
		
		return optional;
}

	public void updateEmployee(Attendance attendance) {
		if (attendanceRepo.existsById(attendance.getId())) {
	        // Save the updated employee
	        attendanceRepo.save(attendance);
	    } else {
	        throw new RuntimeException("Employee not found for id: " + attendance.getId());
	    }
	}
	
	
	
     public void saveAttendance(Attendance attendance) {
	        attendanceRepo.save(attendance);
	    }

     public void updateFixedTime(LocalTime fixedTime,LocalTime fixedTime1) {
         // Get all attendance records
         List<Attendance> allAttendances = attendanceRepo.findAll();

         // Update the fixed time for each attendance record
         for (Attendance attendance : allAttendances) {
             attendance.setFixedTime(fixedTime);
             attendance.setFixedOutTime(fixedTime1);
         }

         // Save the updated attendance records to the database
         attendanceRepo.saveAll(allAttendances);
     }


     public LocalTime getCurrentFixedTime() {
         // Implement logic to fetch the current fixed time from the database or any other source
         return attendanceRepo.getCurrentFixedTimeFromDatabase(); // You need to implement this method in your repository
     }


	public LocalTime getCurrentFixedOutTime() {
		// TODO Auto-generated method stub
		return attendanceRepo.getCurrentFixedOutTimeFromDatabase();
	}

}

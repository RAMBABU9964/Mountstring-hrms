package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.Teams;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserServiceIMP implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender javaMailSender;

	
	@Override
	public User sava(UserDto userDto) {
		
		User user=new User(userDto.getEmail(),passwordEncoder.encode(userDto.getPassword()) , userDto.getRole(), userDto.getFullname(),userDto.getPhoneNumber(),userDto.getSalary(),userDto.getEmpRole(),userDto.getImage());
		return userRepository.save(user);
	}
	
	@Override
	public List<User> fetchAllData() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public void deleteEmplyeeById(Long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public User getEmployeeById(Long id) {
		Optional<User> optional=userRepository.findById(id);
		User employee=null;
		if(optional.isPresent()) {
			employee=optional.get();
		}else {
			throw new RuntimeException("Emplyee not found for id::"+id);
		}
		return employee;
		
	}

	@Override
	public void updateEmployee(User employee) {
		// TODO Auto-generated method stub
		if (userRepository.existsById(employee.getId())) {
	        // Save the updated employee
	        userRepository.save(employee);
	    } else {
	        throw new RuntimeException("Employee not found for id: " + employee.getId());
	    }
		
	}

	@Override
	public boolean validemail(String email) {
	User emplyee=userRepository.findByEmail(email);
		if(emplyee != null) {
		return true;
		}else {
			return false;
		}
	}

	@Override
	public void resetPassword(String email, String password) {
		User emplyee = userRepository.findByEmail(email);
	        if (emplyee != null) {
	            emplyee.setPassword(passwordEncoder.encode(password));
	            userRepository.save(emplyee);
	        }
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findOneById(id);
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public User save(User user) {
		
		return userRepository.save(user);
	}

	

	@Override
	public List<User> fetchAlluser() {
		return userRepository.findAll();
	}

	@Override
	public void sendEmail(String subject, String message) {
	    List<User> allEmployees = fetchAlluser();
	    for (User employee : allEmployees) {
	        if ("EMPLOYEE".equals(employee.getRole())) { // Check if the user's role is EMPLOYEE
	            try {
	                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	                String emailContent = "<p>Hello " + employee.getFullname() + ",</p>"
	                        + "<p>" + message + "</p>"
	                        + "<p>Thank you.</p>"
	                        + "<p>Mount String Technologies Support</p>";

	                helper.setText(emailContent, true);
	                helper.setFrom("your-email@example.com", "Mount String");
	                helper.setSubject(subject);
	                helper.setTo(employee.getEmail());
	                javaMailSender.send(mimeMessage);
	            } catch (MessagingException | UnsupportedEncodingException e) {
	                e.printStackTrace(); // Handle or log the exception as needed
	            }
	        }
	    }
	}

	
	
	@Override
	public void sendEmail2(String subject, String message,User user) {
	    List<User> allEmployees = fetchAlluser();
	    for (User employee : allEmployees) {
	        if ("ADMIN".equals(employee.getRole())) { // Check if the user's role is Admin
	            try {
	                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	                String emailContent = "<p>Hi This is  " + employee.getFullname() + " This message regerading for request for the leave,</p>"
	                        + "<p>" + message + "</p>"
	                        + "<p>Thank you.</p>";

	                helper.setText(emailContent, true);
	                helper.setFrom(user.getEmail(), "Mount String");
	                helper.setSubject(subject);
	                helper.setTo(employee.getEmail());
	                javaMailSender.send(mimeMessage);
	            } catch (MessagingException | UnsupportedEncodingException e) {
	                e.printStackTrace(); // Handle or log the exception as needed
	            }
	        }
	    }
	}
	
}

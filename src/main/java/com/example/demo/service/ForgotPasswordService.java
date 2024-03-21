package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.model.ForgotPasswordToken;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ForgotPasswordService {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	private final int MINUTES=10;

	public String generateToken() {
		return UUID.randomUUID().toString();
	}
	
	public LocalDateTime expireTimeRang() {
		return LocalDateTime.now().plusMinutes(MINUTES);
	}
	
	public void sendEmail(String to,String subject, String emailLink) throws MessagingException, UnsupportedEncodingException {
		
		MimeMessage message =  javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		String emailContent = "<p>Hello</p>" 
				              + "Click the link below to reset password"
				              + "<p><a href=\"" + emailLink + "\">Chang My Password</a></p>"
				              + "<br>"+"Ignore this email if you did not made the request";
		
		
		helper.setText(emailContent,true);
		helper.setFrom("rambabumech.rymec@gmail.com", "Mount String Technologies Support");
		helper.setSubject(subject);
		helper.setTo(to);
		javaMailSender.send(message);
		
		
	}
	
	public boolean isExpired(ForgotPasswordToken forgotPasswordToken) {
	
		return LocalDateTime.now().isAfter(forgotPasswordToken.getExpireTime());
	}
	
	public String checkValidity (ForgotPasswordToken forgotPasswordToken,Model model) {
		
		if(forgotPasswordToken == null) {
			model.addAttribute("error", "Invalid Token");
			return "error-page";
		}
		else if(forgotPasswordToken.isUsed()) {
			model.addAttribute("error", "The Token is already used");
			return "error-page";
		}
		else if(isExpired(forgotPasswordToken)) {
			model.addAttribute("error", "The token is expired");
			return "error-page";
		}
		else {
		
		return "reset-password";
		}
	}
}

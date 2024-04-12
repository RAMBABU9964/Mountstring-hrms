package com.example.demo.service;

import java.util.List;


import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

public interface UserService {

	User sava (UserDto userDto);

	List<User> fetchAllData();

	void deleteEmplyeeById(Long id);

	User getEmployeeById(Long id);

	void updateEmployee(User employee);

	boolean validemail(String email);

	void resetPassword(String email, String password);

	User findById(Long id);

	User findByEmail(String email);

	User save(User user);

	List<User> fetchAlluser();

	void sendEmail( String string, String message);

	void sendEmail2(String string, String message, User user);
	
	

	

	

	

	
	
}

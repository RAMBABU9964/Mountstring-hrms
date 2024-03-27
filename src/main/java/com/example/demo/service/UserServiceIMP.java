package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.Teams;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceIMP implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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

	
	
}

package com.erasm.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.erasm.dto.request.UserRequestDTO;
import com.erasm.dto.response.UserResponseDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.User;
import com.erasm.exception.EmployeeNotFoundException;
import com.erasm.exception.UserNotFoundException;
import com.erasm.mapper.UserMapper;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final EmployeeRepository employeeRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	public UserService(UserRepository userRepository, EmployeeRepository employeeRepository, UserMapper userMapper,
			PasswordEncoder passwordEncoder) {
		
		this.userRepository = userRepository;
		this.employeeRepository = employeeRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserResponseDTO createUser(UserRequestDTO dto) {
		if(userRepository.existsByUsername(dto.getUsername())) {
			throw new IllegalArgumentException("Username already exists");
		}
		if(userRepository.existsByEmail(dto.getEmail())) {
			throw new IllegalArgumentException("Email alreasy exists");
		}
		Employee employee =employeeRepository.findById(dto.getEmployeeId()).orElseThrow(()->new EmployeeNotFoundException("Employee not found with id: "+dto.getEmployeeId()));
		
		if(userRepository.existsByEmployee(employee)) {
			throw new IllegalArgumentException("Employee already has a user account");
		}
		User user=userMapper.toEntity(dto, employee);
		
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		User savedUser=userRepository.save(user);
		return userMapper.toResponseDTO(savedUser);
	}
	public List<UserResponseDTO> getAllUsers(){
		List<User> users=userRepository.findAll();
		return userMapper.toResponseDTOList(users);
		
		
	}
	public UserResponseDTO getUserById(Long id) {
		User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found with id: "+id));
		return userMapper.toResponseDTO(user);
	}
	
	public UserResponseDTO updateUser(Long id,UserRequestDTO dto) {
		
		User existingUser=userRepository.findById(id).orElseThrow(()->new UserNotFoundException("USer not found with id: "+id));
		if(userRepository.existsByUsername(dto.getUsername())
				&& !existingUser.getUsername().equals(dto.getUsername())) {
			throw new IllegalArgumentException("Username already exists");
		}
		if(userRepository.existsByEmail(dto.getEmail())
				&& !existingUser.getEmail().equals(dto.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}
		Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(() ->new EmployeeNotFoundException("Employee not found with id: " + dto.getEmployeeId()));

		if(userRepository.existsByEmployee(employee)
		            && !existingUser.getEmployee().getId().equals(employee.getId())) {

		        throw new IllegalArgumentException(
		                "Employee already has a user account");
		 }
		 existingUser.setUsername(dto.getUsername());
		 existingUser.setEmail(dto.getEmail());
		 existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
		 existingUser.setEmployee(employee);
		 User updatedUser = userRepository.save(existingUser);
		 
		 return userMapper.toResponseDTO(updatedUser);
	}
	public void deleteUser(Long id) {
		User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found with the id: "+id));
		userRepository.delete(user);
	}
	

}

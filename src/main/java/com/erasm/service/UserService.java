package com.erasm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.erasm.dto.request.ChangePasswordRequestDTO;
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
	private final AuditLogService auditLogService;
	
	private static final Logger logger =LoggerFactory.getLogger(UserService.class);
	
	public UserService(UserRepository userRepository, EmployeeRepository employeeRepository, UserMapper userMapper,
			PasswordEncoder passwordEncoder,AuditLogService auditLogService) {
		
		this.userRepository = userRepository;
		this.employeeRepository = employeeRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.auditLogService=auditLogService;
	}
	
	public UserResponseDTO createUser(UserRequestDTO dto) {
		logger.info(
			    "Creating user '{}'",
			    dto.getUsername()
			);
		if(userRepository.existsByUsername(dto.getUsername())) {
			logger.warn(
				    "Username '{}' already exists.",
				    dto.getUsername()
				);
			throw new IllegalArgumentException("Username already exists");
		}
		if(userRepository.existsByEmail(dto.getEmail())) {
			logger.warn(
				    "User email '{}' already exists.",
				    dto.getEmail()
				);
			throw new IllegalArgumentException("Email alreasy exists");
		}
		Employee employee =employeeRepository.findById(dto.getEmployeeId()).orElseThrow(()->new EmployeeNotFoundException("Employee not found with id: "+dto.getEmployeeId()));
		
		if(userRepository.existsByEmployee(employee)) {
			throw new IllegalArgumentException("Employee already has a user account");
		}
		User user=userMapper.toEntity(dto, employee);
		
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		User savedUser=userRepository.save(user);
		logger.info(
			    "User '{}' created successfully.",
			    savedUser.getUsername()
			);
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
		logger.info("Updating user with id: {}", id);
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
		 logger.info("Updateduser with id: {}", id);
		 return userMapper.toResponseDTO(updatedUser);
	}
	public void deleteUser(Long id) {
		logger.info("Deleting user with id: {}", id);
		User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found with the id: "+id));
		userRepository.delete(user);
		logger.info("Deleted user with id: {}", id);
	}
	public void changePassword(
	        Long userId,
	        ChangePasswordRequestDTO dto) {

	    logger.info("Changing password for user id {}", userId);

	    User user = userRepository.findById(userId)
	            .orElseThrow(() ->
	                    new UserNotFoundException(
	                            "User not found with id: " + userId));

	    if (!passwordEncoder.matches(
	            dto.getCurrentPassword(),
	            user.getPassword())) {

	        logger.warn(
	                "Invalid current password for user '{}'",
	                user.getUsername());

	        throw new IllegalArgumentException(
	                "Current password is incorrect");
	    }

	    if (passwordEncoder.matches(
	            dto.getNewPassword(),
	            user.getPassword())) {

	        logger.warn(
	                "User '{}' tried to reuse the old password",
	                user.getUsername());

	        throw new IllegalArgumentException(
	                "New password must be different from current password");
	    }

	    user.setPassword(
	            passwordEncoder.encode(dto.getNewPassword()));

	    userRepository.save(user);

	    auditLogService.recordAction(
	            user.getEmployee(),
	            com.erasm.enums.AuditAction.UPDATE,
	            "User",
	            user.getId(),
	            "Password changed");

	    logger.info(
	            "Password changed successfully for user '{}'",
	            user.getUsername());
	}
	

}

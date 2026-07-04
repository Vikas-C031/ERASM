package com.erasm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.erasm.dto.request.LoginRequestDTO;
import com.erasm.dto.request.RegisterRequestDTO;
import com.erasm.dto.response.LoginResponseDTO;
import com.erasm.dto.response.RegisterResponseDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.User;
import com.erasm.enums.AuditAction;
import com.erasm.exception.EmployeeNotFoundException;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.UserRepository;
import com.erasm.security.CustomUserDetails;
import com.erasm.security.JwtUtil;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;
    
    private static final Logger logger =LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserRepository userRepository,
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder,
            AuditLogService auditLogService) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.auditLogService = auditLogService;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()));
        
        logger.info("User '{}' logged in successfully.", request.getUsername());

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String token =
                jwtUtil.generateToken(userDetails);

        LoginResponseDTO response =
                new LoginResponseDTO();

        response.setToken(token);
        response.setUsername(userDetails.getUsername());

        response.setRole(
                userDetails.getUser()
                        .getEmployee()
                        .getRole()
                        .getRoleName()
                        .name());
        response.setEmployeeId(
                userDetails.getEmployeeId());

        return response;
    }
    
    public RegisterResponseDTO register(RegisterRequestDTO request) {

        logger.info("Registering new user '{}'", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {

            logger.warn("Username '{}' already exists.", request.getUsername());

            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {

            logger.warn("Email '{}' already exists.", request.getEmail());

            throw new IllegalArgumentException("Email already exists");
        }

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: "
                                        + request.getEmployeeId()));

        if (userRepository.existsByEmployee(employee)) {

            logger.warn(
                    "Employee {} already has a user account.",
                    employee.getEmployeeCode());

            throw new IllegalArgumentException(
                    "Employee already has a user account");
        }

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword()));

        user.setEmail(request.getEmail());

        user.setEnabled(true);

        user.setEmployee(employee);

        User savedUser = userRepository.save(user);

        auditLogService.recordAction(
                employee,
                AuditAction.CREATE,
                "User",
                savedUser.getId(),
                "User account created");

        logger.info(
                "User '{}' registered successfully.",
                savedUser.getUsername());

        RegisterResponseDTO response =
                new RegisterResponseDTO();

        response.setId(savedUser.getId());

        response.setUsername(savedUser.getUsername());

        response.setEmail(savedUser.getEmail());

        response.setEmployeeCode(employee.getEmployeeCode());

        response.setEmployeeName(
                employee.getFirstName() + " " + employee.getLastName());

        response.setRole(
                employee.getRole()
                        .getRoleName()
                        .name());

        response.setEnabled(savedUser.getEnabled());

        return response;
    }
    
    

}
package com.erasm.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestDTO {
	
	@NotBlank(message="Employee code is required")
	private String employeeCode;
	
	@NotBlank(message="First name is required")
	private String firstName;
	
	@NotBlank(message="Last name is required")
	private String lastName;
	
	@Email(message="Invalid email format")
	@NotBlank(message="Email is required")
	private String email;
	
	@NotBlank(message="Phone number is required")
	private String phone;
	
	@NotBlank(message="Department is required")
	private String department;
	
	@NotBlank(message="Designation is required")
	private String designation;
	
	@NotNull(message="Experience is required")
	@PositiveOrZero(message="Experience cannot be negative")
	private Double experience;
	
	@NotNull(message="Joining date is required")
	private LocalDate joiningDate;
	
	@NotNull(message="Role is required")
	private Long roleId;

}

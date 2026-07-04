package com.erasm.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.erasm.enums.RoleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDTO {
	
	private Long id;
	private String employeeCode;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String department;
	private String designation;
	private Double experience;
	private LocalDate joiningDate;
	private RoleType roleName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}

package com.erasm.dto.response;

import java.time.LocalDateTime;

import com.erasm.enums.SkillLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSkillResponseDTO {
	
	private Long id;
	private String employeeCode;
	private String employeeName;
	private String skillName;
	private String category;
	private SkillLevel skillLevel;
	private Double yearsOfExperience;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}

package com.erasm.dto.request;

import com.erasm.enums.SkillLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSkillRequestDTO {
	
	private Long employeeId;
	private Long skillId;
	private SkillLevel skillLevel;
	private Double yearsOfExperience;

}

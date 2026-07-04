package com.erasm.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.erasm.dto.request.EmployeeSkillRequestDTO;
import com.erasm.dto.response.EmployeeSkillResponseDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.EmployeeSkill;
import com.erasm.entity.Skill;

@Component
public class EmployeeSkillMapper {

	  public EmployeeSkill toEntity(EmployeeSkillRequestDTO dto,Employee employee,Skill skill) {

		  EmployeeSkill employeeSkill = new EmployeeSkill();
		  employeeSkill.setEmployee(employee);
		  employeeSkill.setSkill(skill);
		  employeeSkill.setSkillLevel(dto.getSkillLevel());
		  employeeSkill.setYearsOfExperience(dto.getYearsOfExperience());
			
		  return employeeSkill;
	  }
	  public EmployeeSkillResponseDTO toResponseDTO(EmployeeSkill employeeSkill) {

	        EmployeeSkillResponseDTO dto =new EmployeeSkillResponseDTO();

	        dto.setId(employeeSkill.getId());
	        dto.setEmployeeCode(employeeSkill.getEmployee().getEmployeeCode());
	        dto.setEmployeeName(
	                employeeSkill.getEmployee().getFirstName()
	                        +" "+employeeSkill.getEmployee().getLastName());
	        dto.setSkillName(employeeSkill.getSkill().getSkillName());
	        dto.setCategory( employeeSkill.getSkill().getCategory());
	        dto.setSkillLevel(employeeSkill.getSkillLevel());
	        dto.setYearsOfExperience( employeeSkill.getYearsOfExperience());
	        dto.setCreatedAt(employeeSkill.getCreatedAt());
	        dto.setUpdatedAt(employeeSkill.getUpdateAt());

	        return dto;
	  }
	  public List<EmployeeSkillResponseDTO> toResponseDTOList(List<EmployeeSkill> employeeSkills) {
	        return employeeSkills.stream()
	                .map(this::toResponseDTO)
	                .toList();
	  }
}

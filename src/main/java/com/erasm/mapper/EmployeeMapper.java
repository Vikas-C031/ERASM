package com.erasm.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.erasm.dto.request.EmployeeRequestDTO;
import com.erasm.dto.response.EmployeeResponseDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.Role;

@Component
public class EmployeeMapper {
	
	public Employee toEntity(EmployeeRequestDTO dto, Role role) {

        Employee employee = new Employee();

        employee.setEmployeeCode(dto.getEmployeeCode());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setDepartment(dto.getDepartment());
        employee.setDesignation(dto.getDesignation());
        employee.setExperience(dto.getExperience());
        employee.setJoiningDate(dto.getJoiningDate());

        employee.setRole(role);

        return employee;
    }
	
	public EmployeeResponseDTO toResponseDTO(Employee employee) {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(employee.getId());
        dto.setEmployeeCode(employee.getEmployeeCode());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setDepartment(employee.getDepartment());
        dto.setDesignation(employee.getDesignation());
        dto.setExperience(employee.getExperience());
        dto.setJoiningDate(employee.getJoiningDate());

        dto.setRoleName(
                employee.getRole()
                        .getRoleName()
        );

        dto.setCreatedAt(employee.getCreatedAt());
        dto.setUpdatedAt(employee.getUpdateAt());

        return dto;
    }
	public List<EmployeeResponseDTO> toResponseDTOList(List<Employee> employees) {

        return employees.stream()
                .map(this::toResponseDTO)
                .toList();
    }
	

}

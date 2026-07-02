package com.erasm.service;

import java.util.List;



import org.springframework.stereotype.Service;

import com.erasm.dto.request.EmployeeRequestDTO;
import com.erasm.dto.response.EmployeeResponseDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.Role;
import com.erasm.exception.EmployeeNotFoundException;
import com.erasm.mapper.EmployeeMapper;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.RoleRepository;

@Service
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	private final RoleRepository roleRepository;
	private final EmployeeMapper employeeMapper;
	public EmployeeService(EmployeeRepository employeeRepository, RoleRepository roleRepository,
			EmployeeMapper employeeMapper) {
		super();
		this.employeeRepository = employeeRepository;
		this.roleRepository = roleRepository;
		this.employeeMapper = employeeMapper;
	}
	
	public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
		if(employeeRepository.existsByEmployeeCode(dto.getEmployeeCode())) {
			throw new IllegalArgumentException("Employee code already exists");
		}
		if(employeeRepository.existsByEmail(dto.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}
		if(dto.getExperience()<0) {
			throw new IllegalArgumentException("Experience cannot be negative");
		}
		
		Role role=roleRepository.findById(dto.getRoleId()).orElseThrow(() ->new com.erasm.exception.RoleNotFoundException("Role not found with id: " + dto.getRoleId()));
		
		Employee employee=employeeMapper.toEntity(dto, role);
		Employee savedEmployee=employeeRepository.save(employee);
		return employeeMapper.toResponseDTO(savedEmployee);
	}
	
	public List<EmployeeResponseDTO> getAllEmployee(){
		List<Employee> employees=employeeRepository.findAll();
		return employeeMapper.toResponseDTOList(employees);
	}
	
	public EmployeeResponseDTO getEmployeeById(Long id) {
		Employee employee=employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee not found with id:"+id));
		return employeeMapper.toResponseDTO(employee);
	}
	
	public EmployeeResponseDTO updateEmployee(Long id,EmployeeRequestDTO dto) {
		Employee existingEmployee=employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee not found with the id: "+id));
		if(employeeRepository.existsByEmployeeCode(dto.getEmployeeCode()) &&
				!existingEmployee.getEmployeeCode().equals(dto.getEmployeeCode())) {
			throw new IllegalArgumentException("Employee code already present");
			
		}
		
		if(employeeRepository.existsByEmail(dto.getEmail())&&
				!existingEmployee.getEmail().equals(dto.getEmail())) {
			throw new IllegalArgumentException("Employee code already present");
		}
		
		if(dto.getExperience()<0) {
			throw new IllegalArgumentException("Experience cannot be negative");
		}
		
		Role role=roleRepository.findById(dto.getRoleId()).orElseThrow(()->new com.erasm.exception.RoleNotFoundException("Role not found with the id: "+dto.getRoleId()));
		
		existingEmployee.setEmployeeCode(dto.getEmployeeCode());
	    existingEmployee.setFirstName(dto.getFirstName());
	    existingEmployee.setLastName(dto.getLastName());
	    existingEmployee.setEmail(dto.getEmail());
	    existingEmployee.setPhone(dto.getPhone());
	    existingEmployee.setDepartment(dto.getDepartment());
	    existingEmployee.setDesignation(dto.getDesignation());
	    existingEmployee.setExperience(dto.getExperience());
	    existingEmployee.setJoiningDate(dto.getJoiningDate());
	    existingEmployee.setRole(role);
	    
	    Employee updatedEmployee=employeeRepository.save(existingEmployee);
	    return employeeMapper.toResponseDTO(updatedEmployee);
	}
	public void deleteEmployee(Long id) {
		Employee employee=employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee not found with id: "+id));
		employeeRepository.delete(employee);
	}
	
	public List<EmployeeResponseDTO> searchEmployeeByName(String firstName){
		List<Employee> employees=employeeRepository.findByFirstNameContainingIgnoreCase(firstName);
		return employeeMapper.toResponseDTOList(employees);
	}
	
	public List<EmployeeResponseDTO> getEmployeeByDepartment(String department){
		List<Employee> employees=employeeRepository.findByDepartment(department);
		return employeeMapper.toResponseDTOList(employees);
	}
	
	public List<EmployeeResponseDTO> getEmployeeByRole(Long roleId){
		Role role =roleRepository.findById(roleId).orElseThrow(()->new com.erasm.exception.RoleNotFoundException("Role not found with id: "+roleId));
		List<Employee> employees=employeeRepository.findByRole(role);
		return employeeMapper.toResponseDTOList(employees);
	}

}

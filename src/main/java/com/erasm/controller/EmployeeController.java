package com.erasm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erasm.dto.request.EmployeeRequestDTO;
import com.erasm.dto.response.EmployeeResponseDTO;
import com.erasm.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@PostMapping
	public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO dto) {
	    EmployeeResponseDTO employee =employeeService.createEmployee(dto);

	    return ResponseEntity.status(HttpStatus.CREATED).body(employee);
	}
	
	@GetMapping
	public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
		
		List<EmployeeResponseDTO> employees=employeeService.getAllEmployee();
		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id){
		EmployeeResponseDTO employee=employeeService.getEmployeeById(id);
		return ResponseEntity.status(HttpStatus.OK).body(employee);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id,@RequestBody EmployeeRequestDTO dto) {
		EmployeeResponseDTO updatedEmployee=employeeService.updateEmployee(id, dto);
		return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee( @PathVariable Long id) {
	    employeeService.deleteEmployee(id);
	    return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<EmployeeResponseDTO>> searchEmployeeByName(@RequestParam String name) {

	    return ResponseEntity.ok(employeeService.searchEmployeeByName(name));
	}
	
	@GetMapping("/department/{department}")
	public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeByDepartment(@PathVariable String department) {
		List<EmployeeResponseDTO> employees=employeeService.getEmployeeByDepartment(department);
		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}
	
	@GetMapping("/role/{roleId}")
	public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeByRole(@PathVariable Long roleId){
		List<EmployeeResponseDTO> employees=employeeService.getEmployeeByRole(roleId);
		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}
	
	
	

}

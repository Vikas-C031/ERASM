package com.erasm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erasm.dto.request.AllocationRequestDTO;
import com.erasm.dto.response.AllocationResponseDTO;
import com.erasm.entity.Allocation;
import com.erasm.entity.Employee;
import com.erasm.entity.Project;
import com.erasm.enums.AllocationStatus;
import com.erasm.enums.AuditAction;
import com.erasm.exception.EmployeeNotFoundException;
import com.erasm.exception.ProjectNotFoundException;
import com.erasm.mapper.AllocationMapper;
import com.erasm.repository.AllocationRepository;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.ProjectRepository;

@Service
public class AllocationService {
	private final AllocationRepository allocationRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final AllocationMapper allocationMapper;
    private final AuditLogService auditLogService;

    public AllocationService(
            AllocationRepository allocationRepository,
            EmployeeRepository employeeRepository,
            ProjectRepository projectRepository,
            AllocationMapper allocationMapper,
            AuditLogService auditLogService) {

        this.allocationRepository = allocationRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.allocationMapper = allocationMapper;
        this.auditLogService = auditLogService;
    }
    
    private void validateAllocationLimit(Employee employee, Integer newAllocationPercentage,Long allocationIdToIgnore) {

		List<Allocation> activeAllocations =allocationRepository.findByEmployeeAndStatus(employee,AllocationStatus.ACTIVE);
		
		int currentAllocation = 0;
		
		for (Allocation allocation : activeAllocations) {
		
			if (allocationIdToIgnore == null||!allocation.getId().equals(allocationIdToIgnore)) {
			
				currentAllocation += allocation.getAllocationPercentage();
			}
		}
		
		if (currentAllocation + newAllocationPercentage > 100) {
			throw new IllegalArgumentException("Employee allocation cannot exceed 100%");
		}
    }
    public AllocationResponseDTO createAllocation(AllocationRequestDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeID())
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + dto.getEmployeeID()));

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() ->
                        new ProjectNotFoundException(
                                "Project not found with id: " + dto.getProjectId()));

        if (allocationRepository.existsByEmployeeAndProject(employee, project)) {
            throw new IllegalArgumentException(
                    "Employee is already allocated to this project");
        }

        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date");
        }

        validateAllocationLimit(employee, null, null);

        Allocation allocation =
                allocationMapper.toEntity(dto, employee, project);

        allocation.setStatus(AllocationStatus.ACTIVE);

        Allocation savedAllocation =
                allocationRepository.save(allocation);

        auditLogService.recordAction(
                employee,
                AuditAction.CREATE,
                "Allocation",
                savedAllocation.getId(),
                "Employee allocated to project " + project.getProjectName());

        return allocationMapper.toResponseDTO(savedAllocation);
    }
    public List<AllocationResponseDTO> getAllAllocations() {

        List<Allocation> allocations = allocationRepository.findAll();

        return allocationMapper.toResponseDTOList(allocations);
    }
    public AllocationResponseDTO getAllocationById(Long id) {

        Allocation allocation = allocationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Allocation not found with id: " + id));

        return allocationMapper.toResponseDTO(allocation);
    }
    public AllocationResponseDTO updateAllocation(Long id,
            AllocationRequestDTO dto) {

		Allocation existingAllocation = allocationRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Allocation not found with id: " + id));
		
		Employee employee = employeeRepository.findById(dto.getEmployeeID()).orElseThrow(() ->new EmployeeNotFoundException("Employee not found with id: " + dto.getEmployeeID()));
		
		Project project = projectRepository.findById(dto.getProjectId()).orElseThrow(() ->new ProjectNotFoundException("Project not found with id: " + dto.getProjectId()));
		
		if (allocationRepository.existsByEmployeeAndProject(employee, project)
		&& !(existingAllocation.getEmployee().getId().equals(employee.getId())
		&& existingAllocation.getProject().getId().equals(project.getId()))) {
		
			throw new IllegalArgumentException("Employee is already allocated to this project");
		}
		
		if (dto.getStartDate().isAfter(dto.getEndDate())) {
			throw new IllegalArgumentException("Start date cannot be after end date");
		}
		
		List<Allocation> activeAllocations =allocationRepository.findByEmployeeAndStatus(employee,AllocationStatus.ACTIVE);
		
		int currentAllocation = 0;
		
		for (Allocation allocation : activeAllocations) {
		
			if (!allocation.getId().equals(existingAllocation.getId())) {
				currentAllocation += allocation.getAllocationPercentage();
			}
		}
		
		if (currentAllocation + dto.getAllocationPercentage() > 100) {
			throw new IllegalArgumentException("Employee allocation cannot exceed 100%");
		}
		
		existingAllocation.setEmployee(employee);
		existingAllocation.setProject(project);
		existingAllocation.setAllocationPercentage(
		dto.getAllocationPercentage());
		existingAllocation.setProjectRole(dto.getProjectRole());
		existingAllocation.setStartDate(dto.getStartDate());
		existingAllocation.setEndDate(dto.getEndDate());
		
		Allocation updatedAllocation =allocationRepository.save(existingAllocation);
		
		auditLogService.recordAction(
		employee,
		AuditAction.UPDATE,
		"Allocation",
		updatedAllocation.getId(),
		"Allocation updated");
		
		return allocationMapper.toResponseDTO(updatedAllocation);
	}
    
    public void deleteAllocation(Long id) {

        Allocation allocation = allocationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Allocation not found with id: " + id));

        allocationRepository.delete(allocation);

        auditLogService.recordAction(
                allocation.getEmployee(),
                AuditAction.DELETE,
                "Allocation",
                allocation.getId(),
                "Allocation deleted");
    }
    public List<AllocationResponseDTO> getAllocationsByEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + employeeId));

        List<Allocation> allocations =
                allocationRepository.findByEmployee(employee);

        return allocationMapper.toResponseDTOList(allocations);
    }
    public List<AllocationResponseDTO> getAllocationsByProject(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ProjectNotFoundException(
                                "Project not found with id: " + projectId));

        List<Allocation> allocations =
                allocationRepository.findByProject(project);

        return allocationMapper.toResponseDTOList(allocations);
    }
    
    
    

}

package com.erasm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger logger =LoggerFactory.getLogger(AllocationService.class);

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

    	logger.info(
    		    "Allocating employee {} to project {}",
    		    dto.getEmployeeID(),
    		    dto.getProjectId()
    		);
    	
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

        validateAllocationLimit(employee,dto.getAllocationPercentage(),null);

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

        logger.info(
        	    "Allocation created successfully with id: {}",
        	    savedAllocation.getId()
        	);
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

    	logger.info(
    		    "Updating allocation with id: {}",
    		    id
    		);
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
		
		logger.info(
			    "Allocation {} updated successfully.",
			    updatedAllocation.getId()
			);
		return allocationMapper.toResponseDTO(updatedAllocation);
	}
    
    public void deleteAllocation(Long id) {

    	logger.info(
    		    "Deleting allocation with id: {}",
    		    id
    		);
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
        logger.info(
        	    "Allocation deleted successfully."
        	);
    }
    public AllocationResponseDTO releaseAllocation(Long id) {

        logger.info("Releasing allocation {}", id);

        Allocation allocation = allocationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Allocation not found with id: " + id));

        if (allocation.getStatus() != AllocationStatus.ACTIVE) {

            logger.warn(
                    "Allocation {} cannot be released because it is {}",
                    id,
                    allocation.getStatus());

            throw new IllegalArgumentException(
                    "Only ACTIVE allocations can be released");
        }

        allocation.setStatus(AllocationStatus.COMPLETED);

        Allocation updatedAllocation =
                allocationRepository.save(allocation);

        auditLogService.recordAction(
                allocation.getEmployee(),
                AuditAction.UPDATE,
                "Allocation",
                updatedAllocation.getId(),
                "Allocation released");

        logger.info(
                "Allocation {} released successfully.",
                updatedAllocation.getId());

        return allocationMapper.toResponseDTO(updatedAllocation);
    }
    
    public AllocationResponseDTO reallocateAllocation(
            Long id,
            AllocationRequestDTO dto) {

        logger.info("Reallocating allocation {}", id);

        Allocation allocation = allocationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Allocation not found with id: " + id));

        if (allocation.getStatus() != AllocationStatus.ACTIVE) {

            logger.warn(
                    "Allocation {} is {} and cannot be reallocated.",
                    id,
                    allocation.getStatus());

            throw new IllegalArgumentException(
                    "Only ACTIVE allocations can be reallocated");
        }

        Employee employee = employeeRepository.findById(dto.getEmployeeID())
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: "
                                        + dto.getEmployeeID()));

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() ->
                        new ProjectNotFoundException(
                                "Project not found with id: "
                                        + dto.getProjectId()));

        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date");
        }

        validateAllocationLimit(
                employee,
                dto.getAllocationPercentage(),
                allocation.getId());

        allocation.setEmployee(employee);
        allocation.setProject(project);
        allocation.setAllocationPercentage(dto.getAllocationPercentage());
        allocation.setProjectRole(dto.getProjectRole());
        allocation.setStartDate(dto.getStartDate());
        allocation.setEndDate(dto.getEndDate());

        Allocation updatedAllocation =
                allocationRepository.save(allocation);

        auditLogService.recordAction(
                employee,
                AuditAction.UPDATE,
                "Allocation",
                updatedAllocation.getId(),
                "Allocation reallocated");

        logger.info(
                "Allocation {} reallocated successfully.",
                updatedAllocation.getId());

        return allocationMapper.toResponseDTO(updatedAllocation);
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

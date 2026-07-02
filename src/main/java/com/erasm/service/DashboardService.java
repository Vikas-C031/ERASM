package com.erasm.service;

import com.erasm.mapper.AllocationMapper;
import com.erasm.mapper.EmployeeMapper;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.erasm.dto.response.AllocationResponseDTO;
import com.erasm.dto.response.DashboardResponseDTO;
import com.erasm.dto.response.EmployeeResponseDTO;
import com.erasm.entity.Allocation;
import com.erasm.entity.Employee;
import com.erasm.entity.Project;
import com.erasm.enums.AllocationStatus;
import com.erasm.exception.EmployeeNotFoundException;
import com.erasm.exception.ProjectNotFoundException;
import com.erasm.repository.AllocationRepository;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.ProjectRepository;

@Service
public class DashboardService {
	
    private final AllocationMapper allocationMapper;
	private final EmployeeMapper employeeMapper;
	private final EmployeeRepository employeeRepository;
    private final AllocationRepository allocationRepository;
    private final ProjectRepository projectRepository;
    
    public DashboardService(EmployeeRepository employeeRepository,ProjectRepository projectRepository,
                            AllocationRepository allocationRepository, EmployeeMapper employeeMapper, AllocationMapper allocationMapper) {

        this.employeeRepository = employeeRepository;
        this.allocationRepository = allocationRepository;
		this.employeeMapper = employeeMapper;
		this.projectRepository=projectRepository;
		this.allocationMapper = allocationMapper;
    }

    public DashboardResponseDTO getDashboardSummary() {
    	
    	long totalEmployees = employeeRepository.count();

        List<Allocation> activeAllocations =
                allocationRepository.findByStatus(AllocationStatus.ACTIVE);

        long allocatedEmployees =
                activeAllocations.stream()
                        .map(allocation -> allocation.getEmployee().getId())
                        .distinct()
                        .count();

        long benchEmployees = totalEmployees - allocatedEmployees;

        double utilizationPercentage = 0;
        double benchPercentage = 0;

        if (totalEmployees > 0) {

            utilizationPercentage =
                    (allocatedEmployees * 100.0) / totalEmployees;

            benchPercentage =
                    (benchEmployees * 100.0) / totalEmployees;
        }

        DashboardResponseDTO dashboard =
                new DashboardResponseDTO();

        dashboard.setTotalEmployees(totalEmployees);
        dashboard.setAllocatedEmployees(allocatedEmployees);
        dashboard.setBenchEmployees(benchEmployees);
        dashboard.setUtilizationPercentage(utilizationPercentage);
        dashboard.setBenchPercentage(benchPercentage);

        return dashboard;
    	
    }
    public Double getEmployeeUtilization(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + employeeId));

        List<Allocation> allocations =
                allocationRepository.findByEmployeeAndStatus(
                        employee,
                        AllocationStatus.ACTIVE);

        int utilization = allocations.stream()
                .mapToInt(Allocation::getAllocationPercentage)
                .sum();

        return Double.valueOf(utilization);
    }
    public List<EmployeeResponseDTO> getBenchEmployees() {

        List<Employee> employees = employeeRepository.findAll();

        List<Allocation> activeAllocations =
                allocationRepository.findByStatus(
                        AllocationStatus.ACTIVE);

        Set<Long> allocatedEmployeeIds =
                activeAllocations.stream()
                        .map(allocation ->
                                allocation.getEmployee().getId())
                        .collect(Collectors.toSet());

        List<Employee> benchEmployees =
                employees.stream()
                        .filter(employee ->
                                !allocatedEmployeeIds.contains(
                                        employee.getId()))
                        .toList();

        return employeeMapper.toResponseDTOList(
                benchEmployees);
    }
    public List<AllocationResponseDTO> getProjectAllocations(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ProjectNotFoundException(
                                "Project not found with id: " + projectId));

        List<Allocation> allocations =
                allocationRepository.findByProject(project);

        return allocationMapper.toResponseDTOList(allocations);
    }
    

}

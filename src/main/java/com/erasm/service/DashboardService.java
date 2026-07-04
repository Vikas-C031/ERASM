package com.erasm.service;

import com.erasm.mapper.AllocationMapper;
import com.erasm.mapper.EmployeeMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erasm.dto.response.AllocationResponseDTO;
import com.erasm.dto.response.DashboardResponseDTO;
import com.erasm.dto.response.EmployeeResponseDTO;
import com.erasm.dto.response.ProjectSummaryReportDTO;
import com.erasm.dto.response.ResourceRequestReportDTO;
import com.erasm.dto.response.SkillInventoryReportDTO;
import com.erasm.entity.Allocation;
import com.erasm.entity.Employee;
import com.erasm.entity.Project;
import com.erasm.entity.ResourceRequest;
import com.erasm.entity.Skill;
import com.erasm.enums.AllocationStatus;
import com.erasm.exception.EmployeeNotFoundException;
import com.erasm.exception.ProjectNotFoundException;
import com.erasm.repository.AllocationRepository;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.EmployeeSkillRepository;
import com.erasm.repository.ProjectRepository;
import com.erasm.repository.ResourceRequestRepository;
import com.erasm.repository.SkillRepository;

@Service
public class DashboardService {
	
    private final AllocationMapper allocationMapper;
	private final EmployeeMapper employeeMapper;
	private final EmployeeRepository employeeRepository;
    private final AllocationRepository allocationRepository;
    private final ProjectRepository projectRepository;
    private final ResourceRequestRepository resourceRequestRepository;
    
    private final SkillRepository skillRepository;
    private final EmployeeSkillRepository employeeSkillRepository;
    private static final Logger logger =LoggerFactory.getLogger(DashboardService.class);
    
    public DashboardService(EmployeeRepository employeeRepository,ProjectRepository projectRepository,SkillRepository skillRepository, ResourceRequestRepository resourceRequestRepository,
            EmployeeSkillRepository employeeSkillRepository,AllocationRepository allocationRepository, EmployeeMapper employeeMapper, AllocationMapper allocationMapper) {

        this.employeeRepository = employeeRepository;
        this.allocationRepository = allocationRepository;
		this.employeeMapper = employeeMapper;
		this.projectRepository=projectRepository;
		this.allocationMapper = allocationMapper;
		this.skillRepository = skillRepository;
	    this.employeeSkillRepository = employeeSkillRepository;
	    this.resourceRequestRepository = resourceRequestRepository;
    }

    public DashboardResponseDTO getDashboardSummary() {
    	
    	logger.info("Dashboard summary requested.");
    	
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
    public List<SkillInventoryReportDTO> getSkillInventoryReport() {

        logger.info("Generating skill inventory report.");

        List<Skill> skills = skillRepository.findAll();

        List<SkillInventoryReportDTO> report = new ArrayList<>();

        for (Skill skill : skills) {

            SkillInventoryReportDTO dto =
                    new SkillInventoryReportDTO();

            dto.setSkillName(skill.getSkillName());

            dto.setCategory(skill.getCategory());

            dto.setEmployeeCount(
                    (long) employeeSkillRepository
                            .findBySkill(skill)
                            .size());

            report.add(dto);
        }

        return report;
    }
    
    public List<ProjectSummaryReportDTO> getProjectSummaryReport() {

        logger.info("Generating project summary report.");

        List<Project> projects = projectRepository.findAll();

        List<ProjectSummaryReportDTO> report = new ArrayList<>();

        for (Project project : projects) {

            ProjectSummaryReportDTO dto =
                    new ProjectSummaryReportDTO();

            dto.setProjectCode(project.getProjectCode());

            dto.setProjectName(project.getProjectName());

            dto.setClientName(project.getClientName());

            dto.setStatus(project.getStatus());

            long allocatedEmployees =
                    allocationRepository
                            .findByProject(project)
                            .stream()
                            .filter(allocation ->
                                    allocation.getStatus() == AllocationStatus.ACTIVE)
                            .count();

            dto.setAllocatedEmployees(allocatedEmployees);

            report.add(dto);
        }

        return report;
    }
    public List<ResourceRequestReportDTO> getResourceRequestReport() {

        logger.info("Generating resource request report.");

        List<ResourceRequest> requests =
                resourceRequestRepository.findAll();

        List<ResourceRequestReportDTO> report =
                new ArrayList<>();

        for (ResourceRequest request : requests) {

            ResourceRequestReportDTO dto =
                    new ResourceRequestReportDTO();

            dto.setProjectCode(
                    request.getProject().getProjectCode());

            dto.setProjectName(
                    request.getProject().getProjectName());

            dto.setRequestedSkill(
                    request.getRequiredSkill().getSkillName());

            dto.setRequestedRole(
                    request.getRequestedRole());

            dto.setNumberOfResources(
                    request.getNumberOfResources());

            dto.setPriority(
                    request.getPriority());

            dto.setStatus(
                    request.getStatus());

            dto.setRequestedBy(
                    request.getRequestedBy().getEmployeeCode()
                    + " - "
                    + request.getRequestedBy().getFirstName()
                    + " "
                    + request.getRequestedBy().getLastName());

            dto.setRequestedDate(
                    request.getRequestedDate());

            report.add(dto);
        }

        return report;
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

package com.erasm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erasm.dto.response.AllocationResponseDTO;
import com.erasm.dto.response.DashboardResponseDTO;
import com.erasm.dto.response.EmployeeResponseDTO;
import com.erasm.dto.response.ProjectSummaryReportDTO;
import com.erasm.dto.response.ResourceRequestReportDTO;
import com.erasm.dto.response.SkillInventoryReportDTO;
import com.erasm.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponseDTO> getDashboardSummary() {

        return ResponseEntity.ok(
                dashboardService.getDashboardSummary());
    }

    @GetMapping("/utilization/{employeeId}")
    public ResponseEntity<Double> getEmployeeUtilization(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                dashboardService.getEmployeeUtilization(employeeId));
    }

    @GetMapping("/bench")
    public ResponseEntity<List<EmployeeResponseDTO>> getBenchEmployees() {

        return ResponseEntity.ok(
                dashboardService.getBenchEmployees());
    }
    @GetMapping("/skills")
    public ResponseEntity<List<SkillInventoryReportDTO>>
    getSkillInventoryReport() {

        return ResponseEntity.ok(
                dashboardService.getSkillInventoryReport());
    }
    
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectSummaryReportDTO>> getProjectSummaryReport() {

        return ResponseEntity.ok(
                dashboardService.getProjectSummaryReport());
    }
    @GetMapping("/resource-requests")
    public ResponseEntity<List<ResourceRequestReportDTO>>
    getResourceRequestReport() {

        return ResponseEntity.ok(
                dashboardService.getResourceRequestReport());
    }
    

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<AllocationResponseDTO>> getProjectAllocations(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                dashboardService.getProjectAllocations(projectId));
    }

}
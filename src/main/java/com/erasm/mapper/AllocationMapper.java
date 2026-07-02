package com.erasm.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.erasm.dto.request.AllocationRequestDTO;
import com.erasm.dto.response.AllocationResponseDTO;
import com.erasm.entity.Allocation;
import com.erasm.entity.Employee;
import com.erasm.entity.Project;

@Component
public class AllocationMapper {

    public Allocation toEntity(AllocationRequestDTO dto,Employee employee,Project project) {

        Allocation allocation = new Allocation();
        allocation.setEmployee(employee);
        allocation.setProject(project);
        allocation.setAllocationPercentage(dto.getAllocationPercentage());
        allocation.setProjectRole(dto.getProjectRole());
        allocation.setStartDate(dto.getStartDate());
        allocation.setEndDate(dto.getEndDate());
        allocation.setStatus(dto.getStatus());
        return allocation;
    }
    public AllocationResponseDTO toResponseDTO(Allocation allocation) {

        AllocationResponseDTO dto = new AllocationResponseDTO();
        dto.setId(allocation.getId());
        dto.setEmployeeCode(allocation.getEmployee().getEmployeeCode());
        dto.setEmployeeName(
                allocation.getEmployee().getFirstName()
                        + " "
                        + allocation.getEmployee().getLastName());
        dto.setProjectCode(allocation.getProject().getProjectCode());
        dto.setAllocationPercentage(allocation.getAllocationPercentage());

        dto.setProjectRole(allocation.getProjectRole());
        dto.setStartDate( allocation.getStartDate());
        dto.setEndDate(allocation.getEndDate());

        dto.setStatus(allocation.getStatus());

        dto.setCreatedAt( allocation.getCreatedAt());
        dto.setUpdatedAt(allocation.getUpdateAt());

        return dto;
    }

    public List<AllocationResponseDTO> toResponseDTOList(List<Allocation> allocations) {
        return allocations.stream()
                .map(this::toResponseDTO)
                .toList();
    }

}
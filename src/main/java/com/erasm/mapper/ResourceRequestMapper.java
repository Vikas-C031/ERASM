package com.erasm.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.erasm.dto.request.ResourceRequestDTO;
import com.erasm.dto.response.ResourceRequestResponseDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.Project;
import com.erasm.entity.ResourceRequest;
import com.erasm.entity.Skill;

@Component
public class ResourceRequestMapper {

    public ResourceRequest toEntity(ResourceRequestDTO dto,
                                    Project project,
                                    Skill skill,
                                    Employee requestedBy) {

        ResourceRequest resourceRequest = new ResourceRequest();

        resourceRequest.setProject(project);
        resourceRequest.setRequiredSkill(skill);
        resourceRequest.setRequestedRole(dto.getRequestedRole());
        resourceRequest.setExperienceRequired(dto.getExperienceRequired());
        resourceRequest.setNumberOfResources(dto.getNumberOfResources());
        resourceRequest.setPriority(dto.getPriority());

        // Related entity supplied by the Service layer
        resourceRequest.setRequestedBy(requestedBy);

        return resourceRequest;
    }

    public ResourceRequestResponseDTO toResponseDTO(ResourceRequest resourceRequest) {

        ResourceRequestResponseDTO dto = new ResourceRequestResponseDTO();

        dto.setId(resourceRequest.getId());

        dto.setProjectCode(resourceRequest.getProject().getProjectCode());
        dto.setProjectName(resourceRequest.getProject().getProjectName());

        dto.setSkillName(resourceRequest.getRequiredSkill().getSkillName());

        dto.setRequestedRole(resourceRequest.getRequestedRole());
        dto.setExperienceRequired(resourceRequest.getExperienceRequired());
        dto.setNumberOfResources(resourceRequest.getNumberOfResources());

        dto.setPriority(resourceRequest.getPriority());
        dto.setStatus(resourceRequest.getStatus());

        dto.setRequestedByEmployeeCode(
                resourceRequest.getRequestedBy().getEmployeeCode());

        dto.setRequestedByEmployeeName(
                resourceRequest.getRequestedBy().getFirstName()
                        + " "
                        + resourceRequest.getRequestedBy().getLastName());

        dto.setRequestedDate(resourceRequest.getRequestedDate());

        dto.setCreatedAt(resourceRequest.getCreatedAt());
        dto.setUpdatedAt(resourceRequest.getUpdateAt());

        return dto;
    }

    public List<ResourceRequestResponseDTO> toResponseDTOList(
            List<ResourceRequest> resourceRequests) {

        return resourceRequests.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
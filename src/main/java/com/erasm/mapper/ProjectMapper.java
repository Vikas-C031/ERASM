package com.erasm.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.erasm.dto.request.ProjectRequestDTO;
import com.erasm.dto.response.ProjectResponseDTO;
import com.erasm.entity.Project;

@Component
public class ProjectMapper {
	
	public Project toEntity(ProjectRequestDTO dto) {

        Project project = new Project();

        project.setProjectCode(dto.getProjectCode());
        project.setProjectName(dto.getProjectName());
        project.setClientName(dto.getClientName());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setTechnologyStack(dto.getTechnologyStack());
        project.setBudget(dto.getBudget());
        project.setStatus(dto.getStatus());
        return project;
    }
	
	
	public ProjectResponseDTO toResponseDTO(Project project) {

	        ProjectResponseDTO dto = new ProjectResponseDTO();

	        dto.setId(project.getId());
	        dto.setProjectCode(project.getProjectCode());
	        dto.setProjectName(project.getProjectName());
	        dto.setClientName(project.getClientName());
	        dto.setStartDate(project.getStartDate());
	        dto.setEndDate(project.getEndDate());
	        dto.setTechnologyStack(project.getTechnologyStack());
	        dto.setBudget(project.getBudget());
	        dto.setStatus(project.getStatus());
	        dto.setCreatedAt(project.getCreatedAt());
	        dto.setUpdatedAt(project.getUpdateAt());

	        return dto;
	 }
	 public List<ProjectResponseDTO> toResponseDTOList(List<Project> projects) {

	        return projects.stream()
	                .map(this::toResponseDTO)
	                .toList();
	 }


}

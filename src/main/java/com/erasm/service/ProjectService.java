package com.erasm.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.erasm.dto.request.ProjectRequestDTO;
import com.erasm.dto.response.ProjectResponseDTO;
import com.erasm.entity.Project;
import com.erasm.enums.ProjectStatus;
import com.erasm.exception.ProjectNotFoundException;
import com.erasm.mapper.ProjectMapper;
import com.erasm.repository.ProjectRepository;

@Service
public class ProjectService {

	
	
	private final ProjectRepository projectRepository;
	private final ProjectMapper projectMapper;
	public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
		this.projectRepository = projectRepository;
		this.projectMapper = projectMapper;
	}
	
	public ProjectResponseDTO createProject(ProjectRequestDTO dto) {
		if(projectRepository.existsByProjectCode(dto.getProjectCode())) {
			throw new IllegalArgumentException("Project code already exists");
		}
		if(dto.getStartDate().isAfter(dto.getEndDate())) {
			throw new IllegalArgumentException("Start date cannot be after end date");
		}
		if(dto.getBudget().compareTo(BigDecimal.ZERO)<=0){//Object can not be compared using<= so we use comaperTo and it return -1 if smaller ,0 if equal and 1 if greater 
			throw new IllegalArgumentException("Budget must be greater than zero");
		}
		Project project=projectMapper.toEntity(dto);
		Project savedProject=projectRepository.save(project);
		return projectMapper.toResponseDTO(savedProject);
		
	}
	
	public List<ProjectResponseDTO> getAllProjects(){
		List<Project> projects=projectRepository.findAll();
		return projectMapper.toResponseDTOList(projects);
	}
	
	public ProjectResponseDTO getProjectById(Long id) {
		Project project=projectRepository.findById(id).orElseThrow(()->new ProjectNotFoundException("Project not found with the id"+id));
		return projectMapper.toResponseDTO(project);
	}
	
	public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO dto) {

	    Project existingProject = projectRepository.findById(id).orElseThrow(() ->new ProjectNotFoundException("Project not found with id: " + id));

	    if (projectRepository.existsByProjectCode(dto.getProjectCode())
	            && !existingProject.getProjectCode().equals(dto.getProjectCode())) {
	        throw new IllegalArgumentException("Project code already exists");
	    }

	    if (dto.getStartDate().isAfter(dto.getEndDate())) {
	        throw new IllegalArgumentException("Start date cannot be after end date");
	    }

	    if (dto.getBudget().compareTo(BigDecimal.ZERO) <= 0) {
	        throw new IllegalArgumentException("Budget must be greater than zero");
	    }
	    
	    existingProject.setProjectCode(dto.getProjectCode());
	    existingProject.setProjectName(dto.getProjectName());
	    existingProject.setClientName(dto.getClientName());
	    existingProject.setTechnologyStack(dto.getTechnologyStack());
	    existingProject.setBudget(dto.getBudget());
	    existingProject.setStartDate(dto.getStartDate());
	    existingProject.setEndDate(dto.getEndDate());
	    existingProject.setStatus(dto.getStatus());

	    Project updatedProject = projectRepository.save(existingProject);
	    return projectMapper.toResponseDTO(updatedProject);
	}
	
	public void deleteProject(Long id) {
		Project project=projectRepository.findById(id).orElseThrow(()->new ProjectNotFoundException("project not found with id"+id));
		projectRepository.delete(project);
	}
	
	public List<ProjectResponseDTO> searchProjectsByname(String projectName){
		List<Project> projects=projectRepository.findByProjectNameContainingIgnoreCase(projectName);
		return projectMapper.toResponseDTOList(projects);
	}
	public List<ProjectResponseDTO> searchProjectByStatus(ProjectStatus status){
		List<Project> projects=projectRepository.findByStatus(status);
		return projectMapper.toResponseDTOList(projects);
	}
	

}

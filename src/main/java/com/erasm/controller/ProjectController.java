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

import com.erasm.dto.request.ProjectRequestDTO;
import com.erasm.dto.response.ProjectResponseDTO;
import com.erasm.enums.ProjectStatus;
import com.erasm.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {
	
	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		super();
		this.projectService = projectService;
	}
	
	@PostMapping
	public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO dto){
		
		ProjectResponseDTO project=projectService.createProject(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(project);
		
		
	}
	
	@GetMapping
	public ResponseEntity<List<ProjectResponseDTO>> getAllPRoject(){
		
		List<ProjectResponseDTO> projects=projectService.getAllProjects();
		return ResponseEntity.status(HttpStatus.OK).body(projects);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
		
		ProjectResponseDTO project=projectService.getProjectById(id);
		return ResponseEntity.status(HttpStatus.OK).body(project);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id,@RequestBody ProjectRequestDTO dto) {

	    return ResponseEntity.ok(projectService.updateProject(id, dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
	    projectService.deleteProject(id);

	    return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ProjectResponseDTO>> searchProjectsByName(@RequestParam String name) {
		List<ProjectResponseDTO> projects=projectService.searchProjectsByname(name);
		return ResponseEntity.status(HttpStatus.OK).body(projects);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<List<ProjectResponseDTO>> searchProjectsByStatus(@PathVariable ProjectStatus status) {

		List<ProjectResponseDTO> projects=projectService.searchProjectByStatus(status);
	    return ResponseEntity.status(HttpStatus.OK).body(projects);
	}
	
	

}

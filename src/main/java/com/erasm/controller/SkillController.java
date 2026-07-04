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
import org.springframework.web.bind.annotation.RestController;

import com.erasm.dto.request.SkillRequestDTO;
import com.erasm.dto.response.SkillResponseDTO;
import com.erasm.service.SkillService;

@RestController
@RequestMapping("/skills")
public class SkillController {
	
	private final SkillService skillService;

	public SkillController(SkillService skillService) {
	
		this.skillService = skillService;
	}
	
	@PostMapping
	public ResponseEntity<SkillResponseDTO> createSkill(@RequestBody SkillRequestDTO dto){
		
		SkillResponseDTO skill=skillService.createSkill(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(skill);
	}
	
	@GetMapping
	public ResponseEntity<List<SkillResponseDTO>> getAllSkills(){
		List<SkillResponseDTO> skills=skillService.getAllSkill();
		return ResponseEntity.status(HttpStatus.OK).body(skills);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SkillResponseDTO> getSkillByID(@PathVariable Long id){
		
		SkillResponseDTO skill=skillService.getSkillById(id);
		return ResponseEntity.status(HttpStatus.OK).body(skill);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SkillResponseDTO> updateSkill(@PathVariable Long id,@RequestBody SkillRequestDTO dto){
		
		SkillResponseDTO updatedSkill=skillService.updateSkill(id, dto);
		return ResponseEntity.status(HttpStatus.OK).body(updatedSkill);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSkill(@PathVariable Long id){
		skillService.deleteSkill(id);
		return ResponseEntity.noContent().build();
	}
	
	

}

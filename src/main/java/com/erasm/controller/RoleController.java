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

import com.erasm.dto.request.RoleRequestDTO;
import com.erasm.dto.response.RoleResponseDTO;
import com.erasm.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
	
	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@PostMapping
	public ResponseEntity<RoleResponseDTO> createRole(@RequestBody RoleRequestDTO dto){
		
		RoleResponseDTO createdRole=roleService.createRole(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
	}
	
	@GetMapping
	public ResponseEntity<List<RoleResponseDTO>> getAllRoles(){
		List<RoleResponseDTO> roles=roleService.getAllRoles();
		return ResponseEntity.status(HttpStatus.OK).body(roles);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id){
		
		RoleResponseDTO role=roleService.getRoleById(id);
		return ResponseEntity.status(HttpStatus.OK).body(role);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Long id,@RequestBody RoleRequestDTO dto){
		
		RoleResponseDTO updatedRole=roleService.updateRole(id, dto);
		return ResponseEntity.status(HttpStatus.OK).body(updatedRole);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRole(@PathVariable Long id){
		
		roleService.deleteRole(id);
		return ResponseEntity.noContent().build();
	}
	

}

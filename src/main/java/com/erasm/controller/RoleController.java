package com.erasm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	

}

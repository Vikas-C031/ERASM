package com.erasm.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.erasm.dto.request.RoleRequestDTO;
import com.erasm.dto.response.RoleResponseDTO;
import com.erasm.entity.Role;

@Component
public class RoleMapper {

	public Role toEntity(RoleRequestDTO dto) {
		Role role=new Role();
		role.setRoleName(dto.getRoleName());
		
		return role;
	}
	
	public RoleResponseDTO toResponseDTO(Role role) {
		RoleResponseDTO dto =new RoleResponseDTO();
		dto.setId(role.getId());
		dto.setRoleName(role.getRoleName());
		dto.setCreatedAt(role.getCreatedAt());
		dto.setUpdatedAt(role.getUpdateAt());
		
		return dto;
	}
	
	 public List<RoleResponseDTO> toResponseDTOList(List<Role> roles) {

	        return roles.stream()
	        		.map(this::toResponseDTO)
	        		.toList();
	    }
	
}

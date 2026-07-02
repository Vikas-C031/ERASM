package com.erasm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erasm.dto.request.RoleRequestDTO;
import com.erasm.dto.response.RoleResponseDTO;
import com.erasm.entity.Role;
import com.erasm.exception.RoleNotFoundException;
import com.erasm.mapper.RoleMapper;
import com.erasm.repository.RoleRepository;

@Service
public class RoleService {
	
	private final RoleRepository roleRepository;
	private final RoleMapper roleMapper;
	
	public RoleService(RoleRepository roleRepository,RoleMapper roleMapper) {
		this.roleRepository=roleRepository;
		this.roleMapper=roleMapper;
	}
	
	public RoleResponseDTO createRole(RoleRequestDTO dto) {
		if(roleRepository.existsByRoleName(dto.getRoleName())) {
			throw new IllegalArgumentException("Role already exists");
		}
		Role role=roleMapper.toEntity(dto);//dto to entity
		Role savedRole=roleRepository.save(role);
		
		return roleMapper.toResponseDTO(savedRole);
	}
	
	public List<RoleResponseDTO> getAllRoles(){
		List<Role> roles=roleRepository.findAll();
		return roleMapper.toResponseDTOList(roles);
	}
	public RoleResponseDTO getRoleById(Long id) {
		Role role=roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException("Role not find with id:"+id));
		return roleMapper.toResponseDTO(role);
	}
	
	public RoleResponseDTO updateRole(Long id,RoleRequestDTO dto) {
		
		Role existingRole=roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException("Role not found with the id:"+id));
		if(roleRepository.existsByRoleName(dto.getRoleName())&& existingRole.getRoleName()!=dto.getRoleName()) {
			throw new IllegalArgumentException("Role already exixsts");
		}
		existingRole.setRoleName(dto.getRoleName());
		Role updatedRole=roleRepository.save(existingRole);
		
		return roleMapper.toResponseDTO(updatedRole);
	}
	
	public void deleteRole(Long id) {
		Role role =roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException("Role not found"));
		roleRepository.delete(role);	
	}

}

package com.erasm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erasm.dto.request.RoleRequestDTO;
import com.erasm.dto.response.RoleResponseDTO;
import com.erasm.entity.Role;
import com.erasm.exception.RoleNotFoundException;
import com.erasm.mapper.RoleMapper;
import com.erasm.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class RoleService {
	
	private final RoleRepository roleRepository;
	private final RoleMapper roleMapper;
	
	 private static final Logger logger =LoggerFactory.getLogger(RoleService.class);
	
	public RoleService(RoleRepository roleRepository,RoleMapper roleMapper) {
		this.roleRepository=roleRepository;
		this.roleMapper=roleMapper;
	}
	
	public RoleResponseDTO createRole(RoleRequestDTO dto) {
		logger.info("Creating role '{}'", dto.getRoleName());
		if(roleRepository.existsByRoleName(dto.getRoleName())) {
			logger.warn("Role '{}' already exists.", dto.getRoleName());
			throw new IllegalArgumentException("Role already exists");
		}
		Role role=roleMapper.toEntity(dto);//dto to entity
		Role savedRole=roleRepository.save(role);
		logger.info("Role '{}' created successfully.", savedRole.getRoleName());
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
		
		logger.info("Updating role with id: {}", id);
		Role existingRole=roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException("Role not found with the id:"+id));
		if(roleRepository.existsByRoleName(dto.getRoleName())&& existingRole.getRoleName()!=dto.getRoleName()) {
			throw new IllegalArgumentException("Role already exixsts");
		}
		existingRole.setRoleName(dto.getRoleName());
		Role updatedRole=roleRepository.save(existingRole);
		logger.info("Updated role with id: {}", id);
		return roleMapper.toResponseDTO(updatedRole);
	}
	
	public void deleteRole(Long id) {
		logger.info("Deleting role with id: {}", id);
		Role role =roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException("Role not found"));
		roleRepository.delete(role);	
		logger.info("Deleted role with id: {}", id);
	}

}

package com.erasm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erasm.dto.request.SkillRequestDTO;
import com.erasm.dto.response.SkillResponseDTO;
import com.erasm.entity.Skill;
import com.erasm.exception.SkillNotFoundException;
import com.erasm.mapper.SkillMapper;
import com.erasm.repository.SkillRepository;

@Service
public class SkillService {
	
	private final SkillRepository skillRepository;
	private final SkillMapper skillMapper;
	
	private static final Logger logger =LoggerFactory.getLogger(SkillService.class);
	
	public SkillService(SkillRepository skillRepository,SkillMapper skillMapper) {
		this.skillMapper=skillMapper;
		this.skillRepository=skillRepository;
	}
	public SkillResponseDTO createSkill(SkillRequestDTO dto) {
		logger.info("Creating skill '{}'", dto.getSkillName());
		if(skillRepository.existsBySkillName(dto.getSkillName())){
			logger.warn("Skill '{}' already exists.", dto.getSkillName());
			throw new IllegalArgumentException("Skill already exists");
		}
		Skill skill=skillMapper.toEntity(dto);
		Skill savedSkill=skillRepository.save(skill);
		logger.info("Skill '{}' created successfully.", savedSkill.getSkillName());
		return skillMapper.toResponseDTO(savedSkill);
	}
	public List<SkillResponseDTO> getAllSkill(){
		List<Skill> skills=skillRepository.findAll();
		return skillMapper.toResponseDTOList(skills);
	}
	public SkillResponseDTO getSkillById(Long id) {
		Skill skill=skillRepository.findById(id).orElseThrow(()->new SkillNotFoundException("Skill not found with id:"+id));
		return skillMapper.toResponseDTO(skill);
		
	}
	public SkillResponseDTO updateSkill(Long id,SkillRequestDTO dto) {
		Skill existingSkill =skillRepository.findById(id).orElseThrow(()->new SkillNotFoundException("Skill not found with id:"+id));
		if(skillRepository.existsBySkillName(dto.getSkillName())
				&& !existingSkill.getSkillName().equals(dto.getSkillName())) {
			throw new IllegalArgumentException("Skill already exists");
		}
		existingSkill.setSkillName(dto.getSkillName());
		Skill updatedSkill=skillRepository.save(existingSkill);
		return skillMapper.toResponseDTO(updatedSkill);
	}
	public void deleteSkill(Long id) {
		Skill skill=skillRepository.findById(id).orElseThrow(()->new SkillNotFoundException("Skill not found with id:"+id));
		skillRepository.delete(skill);
	}

}

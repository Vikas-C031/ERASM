package com.erasm.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.erasm.dto.request.SkillRequestDTO;
import com.erasm.dto.response.SkillResponseDTO;
import com.erasm.entity.Skill;

@Component
public class SkillMapper {

	public Skill toEntity(SkillRequestDTO dto) {

        Skill skill = new Skill();
        skill.setSkillName(dto.getSkillName());
        skill.setCategory(dto.getCategory());

        return skill;
    }
	public SkillResponseDTO toResponseDTO(Skill skill) {

        SkillResponseDTO dto = new SkillResponseDTO();

        dto.setId(skill.getId());
        dto.setSkillName(skill.getSkillName());
        dto.setCategory(skill.getCategory());
        dto.setCreatedAt(skill.getCreatedAt());
        dto.setUpdatedAt(skill.getUpdateAt());

        return dto;
    }
    public List<SkillResponseDTO> toResponseDTOList(List<Skill> skills) {

        return skills.stream()
                .map(this::toResponseDTO)
                .toList();
    }

	
}

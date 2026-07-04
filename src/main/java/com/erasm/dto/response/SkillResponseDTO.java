package com.erasm.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SkillResponseDTO {
	
	private Long id;
	private String skillName;
	private String category;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}

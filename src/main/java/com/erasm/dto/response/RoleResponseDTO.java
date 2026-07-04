package com.erasm.dto.response;

import java.time.LocalDateTime;

import com.erasm.enums.RoleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDTO {
	
	private Long id;
	private RoleType roleName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}

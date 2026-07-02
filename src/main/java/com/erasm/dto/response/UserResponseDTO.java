package com.erasm.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
	
	private Long id;
	private String username;
	private String email;
	private Boolean enabled;
	private String employeeCode;
	private String employeeName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}

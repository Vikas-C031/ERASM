package com.erasm.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginResponseDTO {
	
	private String token;
	private String tokenType="Bearer";
	private String username;
	private String role;
	private Long employeeId;
}

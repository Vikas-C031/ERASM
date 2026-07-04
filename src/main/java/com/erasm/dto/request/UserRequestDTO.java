package com.erasm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
	
	@NotBlank(message="Username is required")
	private String username;
	
	@Email(message="Invalid email format")
	@NotBlank(message="email is required")
	private String email;
	
	@NotBlank(message="Password is required")
	@Size(min=8,message="Password must contain atleast 8 characters")
	private String password;
	
	@NotNull(message="Employee is required")
	private Long employeeId;

}

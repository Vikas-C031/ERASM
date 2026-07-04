package com.erasm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erasm.dto.request.LoginRequestDTO;
import com.erasm.dto.request.RegisterRequestDTO;
import com.erasm.dto.response.LoginResponseDTO;
import com.erasm.dto.response.RegisterResponseDTO;
import com.erasm.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthenticationService authenticationService;

	public AuthController(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto){
		
		return ResponseEntity.ok(authenticationService.login(dto));
	}
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDTO> register(
	        @RequestBody RegisterRequestDTO request) {

	    RegisterResponseDTO response =
	            authenticationService.register(request);

	    return ResponseEntity
	            .status(HttpStatus.CREATED)
	            .body(response);
	}

}

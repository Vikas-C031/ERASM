package com.erasm.exception;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger =
	        LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
		logger.warn("User not found: {}", ex.getMessage());
		ErrorResponse error=new ErrorResponse();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProjectNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProjectNotFoundException(ProjectNotFoundException ex){
		logger.warn("Project not found: {}", ex.getMessage());
		ErrorResponse error=new ErrorResponse();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex){
		logger.warn("Employee not found: {}", ex.getMessage());
		ErrorResponse error=new ErrorResponse();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SkillNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSkillNotFoundException(SkillNotFoundException ex){
		logger.warn("Skill not found: {}", ex.getMessage());
		ErrorResponse error=new ErrorResponse();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AllocationException.class)
	public ResponseEntity<ErrorResponse> handleAllocationException(AllocationException ex){
		logger.warn("Allocation not found: {}", ex.getMessage());
		ErrorResponse error=new ErrorResponse();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex){
		logger.warn("Role not found: {}", ex.getMessage());
		ErrorResponse error=new ErrorResponse();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {

	    logger.error("Unexpected system error occurred.", ex);

	    ErrorResponse error = new ErrorResponse();

	    error.setTimestamp(LocalDateTime.now());
	    error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	    error.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	    error.setMessage("An unexpected error occurred.");

	    return new ResponseEntity<>(
	            error,
	            HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

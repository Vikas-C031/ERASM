package com.erasm.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificationResponseDTO {
	
	private Long id;
	private String employeeCode;
	private String employeeName;
	private String certificationName;
	private String issuingOrganization;
	private LocalDate issueDate;
	private LocalDate expiryDate;
	private String certificateNumber;
	private String certificateUrl;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}

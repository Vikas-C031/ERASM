package com.erasm.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificationRequestDTO {
	
	private Long employeeId;
	private String certificationName;
	private String issuingOrganization;
	private LocalDate issueDate;
	private LocalDate expiryDate;
	private String certificateNumber;
	private String certificateUrl;

}

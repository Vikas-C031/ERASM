package com.erasm.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.erasm.enums.AllocationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllocationResponseDTO {

	
	 private Long id;
	 private String employeeCode;
	 private String employeeName;
	 private String projectCode;
	 private String projectName;
	 private Integer allocationPercentage;
	 private String projectRole;
	 private LocalDate startDate;
	 private LocalDate endDate;
	 private AllocationStatus status;
	 private LocalDateTime createdAt;
	 private LocalDateTime updatedAt;
}

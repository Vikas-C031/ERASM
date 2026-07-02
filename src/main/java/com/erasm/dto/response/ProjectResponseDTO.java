package com.erasm.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.erasm.enums.ProjectStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponseDTO {
	
	private Long id;
	private String projectCode;
	private String projectName;
	private String clientName;
	private String technologyStack;
	private BigDecimal budget;
	private LocalDate startDate;
	private LocalDate endDate;
	private ProjectStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}

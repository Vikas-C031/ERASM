package com.erasm.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.erasm.enums.ProjectStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequestDTO {
	
	private String projectCode;
	private String projectName;
	private String clientName;
	private String technologyStack;
	private BigDecimal budget;
	private LocalDate startDate;
	private LocalDate endDate;
	private ProjectStatus status;

}

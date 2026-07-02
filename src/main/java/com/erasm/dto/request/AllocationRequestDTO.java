package com.erasm.dto.request;

import java.time.LocalDate;

import com.erasm.enums.AllocationStatus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllocationRequestDTO {

	private Long employeeID;
	private Long projectId;
	@Min(value = 1, message = "Allocation percentage must be at least 1")
    @Max(value = 100, message = "Allocation percentage cannot exceed 100")
	private Integer allocationPercentage;
	private String projectRole;
	private LocalDate startDate;
	private LocalDate endDate;
	private AllocationStatus status;
}

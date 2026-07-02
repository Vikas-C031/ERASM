package com.erasm.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardResponseDTO {

	private Long totalEmployees;
    private Long allocatedEmployees;
    private Long benchEmployees;
    private Double utilizationPercentage;
    private Double benchPercentage;
}

package com.erasm.dto.request;

import com.erasm.enums.RequestPriority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceRequestDTO {

	private Long projectId;
	private Long skillId;
	private String requestedRole;
	private Double experienceRequired;
	private Integer numberOfResources;
	private RequestPriority priority;
	private Long requestedByEmployeeId;
	
}

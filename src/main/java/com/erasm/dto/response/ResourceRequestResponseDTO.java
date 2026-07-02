package com.erasm.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.erasm.entity.Skill;
import com.erasm.enums.RequestPriority;
import com.erasm.enums.RequestStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceRequestResponseDTO {

	private Long id;

    private String projectCode;

    private String projectName;

    private String skillName;

    private Skill requiredSkill;
    
    private String requestedRole;

    private Double experienceRequired;

    private Integer numberOfResources;

    private RequestPriority priority;

    private RequestStatus status;

    private String requestedByEmployeeCode;

    private String requestedByEmployeeName;

    private LocalDate requestedDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

package com.erasm.dto.response;

import java.time.LocalDate;

import com.erasm.enums.RequestPriority;
import com.erasm.enums.RequestStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceRequestReportDTO {

    private String projectCode;

    private String projectName;

    private String requestedSkill;

    private String requestedRole;

    private Integer numberOfResources;

    private RequestPriority priority;

    private RequestStatus status;

    private String requestedBy;

    private LocalDate requestedDate;
}
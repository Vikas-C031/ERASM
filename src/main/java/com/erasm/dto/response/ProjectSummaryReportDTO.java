package com.erasm.dto.response;

import com.erasm.enums.ProjectStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectSummaryReportDTO {

    private String projectCode;

    private String projectName;

    private String clientName;

    private ProjectStatus status;

    private Long allocatedEmployees;

}
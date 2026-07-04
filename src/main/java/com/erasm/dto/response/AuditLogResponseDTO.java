package com.erasm.dto.response;

import java.time.LocalDateTime;

import com.erasm.enums.AuditAction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditLogResponseDTO {
	
	private Long id;

    private String employeeCode;

    private String employeeName;

    private AuditAction action;

    private String entityName;

    private Long entityId;

    private String description;

    private LocalDateTime actionTime;



}

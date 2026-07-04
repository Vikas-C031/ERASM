package com.erasm.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.erasm.dto.response.AuditLogResponseDTO;
import com.erasm.entity.AuditLog;

@Component
public class AuditLogMapper {

    public AuditLogResponseDTO toResponseDTO(AuditLog auditLog) {

        AuditLogResponseDTO dto = new AuditLogResponseDTO();

        dto.setId(auditLog.getId());
        dto.setEmployeeCode(auditLog.getEmployee().getEmployeeCode());
        dto.setEmployeeName(
                auditLog.getEmployee().getFirstName()
                        + " "
                        + auditLog.getEmployee().getLastName());
        dto.setAction(auditLog.getAction());
        dto.setEntityName(auditLog.getEntityName());
        dto.setEntityId(auditLog.getEntityId());
        dto.setDescription(auditLog.getDescription());
        dto.setActionTime(auditLog.getActionTime());


        return dto;
    }

    public List<AuditLogResponseDTO> toResponseDTOList(List<AuditLog> auditLogs) {

        return auditLogs.stream()
                .map(this::toResponseDTO)
                .toList();
    }

}
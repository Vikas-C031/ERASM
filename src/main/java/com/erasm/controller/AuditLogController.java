package com.erasm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erasm.dto.response.AuditLogResponseDTO;
import com.erasm.enums.AuditAction;
import com.erasm.service.AuditLogService;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public ResponseEntity<List<AuditLogResponseDTO>> getAllAuditLogs() {

        return ResponseEntity.ok(
                auditLogService.getAllAuditLogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponseDTO> getAuditLogById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogById(id));
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLogResponseDTO>> getAuditLogsByAction(
            @PathVariable AuditAction action) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogsByAction(action));
    }

    @GetMapping("/entity/{entityName}")
    public ResponseEntity<List<AuditLogResponseDTO>> getAuditLogsByEntity(
            @PathVariable String entityName) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogsByEntity(entityName));
    }

}
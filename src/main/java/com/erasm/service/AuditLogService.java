package com.erasm.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erasm.dto.response.AuditLogResponseDTO;
import com.erasm.entity.AuditLog;
import com.erasm.entity.Employee;
import com.erasm.enums.AuditAction;
import com.erasm.mapper.AuditLogMapper;
import com.erasm.repository.AuditLogRepository;

@Service
public class AuditLogService {
	
	private final AuditLogRepository auditLogRepository;
	private final AuditLogMapper auditLogMapper;
	
	private static final Logger logger =LoggerFactory.getLogger(AuditLogService.class);
	
	public AuditLogService(AuditLogRepository auditLogRepository, AuditLogMapper auditLogMapper) {
		super();
		this.auditLogRepository = auditLogRepository;
		this.auditLogMapper = auditLogMapper;
	}
	
	public void recordAction(Employee employee,AuditAction action,String entityName,Long entityId,String description) {
		
		AuditLog auditLog = new AuditLog();

	    auditLog.setEmployee(employee);
	    auditLog.setAction(action);
	    auditLog.setEntityName(entityName);
	    auditLog.setEntityId(entityId);
	    auditLog.setDescription(description);
	    auditLog.setActionTime(LocalDateTime.now());

	    auditLogRepository.save(auditLog);
	}
	public List<AuditLogResponseDTO> getAllAuditLogs() {

		logger.info("Retrieving all audit logs.");
	    List<AuditLog> auditLogs = auditLogRepository.findAll();

	    return auditLogMapper.toResponseDTOList(auditLogs);
	}
	public AuditLogResponseDTO getAuditLogById(Long id) {

	    AuditLog auditLog = auditLogRepository.findById(id)
	            .orElseThrow(() ->
	                    new IllegalArgumentException(
	                            "Audit log not found with id: " + id));

	    return auditLogMapper.toResponseDTO(auditLog);
	}
	public List<AuditLogResponseDTO> getAuditLogsByAction(AuditAction action) {

	    List<AuditLog> auditLogs = auditLogRepository.findByAction(action);

	    return auditLogMapper.toResponseDTOList(auditLogs);
	}
	public List<AuditLogResponseDTO> getAuditLogsByEntity(String entityName) {

	    List<AuditLog> auditLogs =auditLogRepository.findByEntityName(entityName);
	    return auditLogMapper.toResponseDTOList(auditLogs);
	}
	

}

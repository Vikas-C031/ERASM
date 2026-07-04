package com.erasm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erasm.entity.AuditLog;
import com.erasm.enums.AuditAction;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>{

	List<AuditLog> findByAction(AuditAction action);
	List<AuditLog> findByEntityName(String entityName);
}

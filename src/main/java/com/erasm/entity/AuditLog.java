package com.erasm.entity;

import java.time.LocalDateTime;

import com.erasm.enums.AuditAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="audit_logs")
public class AuditLog extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	@Column(name="audit_log_id")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="employee_id",nullable=false)
	private Employee employee;
	
	@Enumerated(EnumType.STRING)
	@Column(name="action",nullable=false)
	private AuditAction action;
	
	@Column(name="entity_name",nullable=false,length=100)
	private String entityName;
	
	@Column(name="entity_id",nullable=false)
	private Long entityId;
	
	@Column(name="description",nullable=false,length=500)
	private String description;

	@Column(name="action_time",nullable=false)
	private LocalDateTime actionTime;
}

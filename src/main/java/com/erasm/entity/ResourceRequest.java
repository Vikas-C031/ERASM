package com.erasm.entity;

import java.time.LocalDate;

import com.erasm.enums.RequestPriority;
import com.erasm.enums.RequestStatus;

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
@Table(name="resource_requests")
public class ResourceRequest extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	@Column(name="request_id")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="project_id",nullable=false)
	private Project project;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="skill_id",nullable=false)
	private Skill requiredSkill;

	@Column(name="requested_role",nullable=false,length=100)
	private String requestedRole;
	
	@Column(name="experience_required",nullable=false)
	private Double experienceRequired;
	
	@Column(name="number_of_resources",nullable=false)
	private Integer numberOfResources;
	
	@Enumerated(EnumType.STRING)
	@Column(name="priority",nullable=false)
	private RequestPriority priority;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status",nullable=false)
	private RequestStatus status;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="requested_by",nullable=false)
	private Employee requestedBy;
	
	@Column(name="request_date",nullable=false)
	private LocalDate requestedDate;
}

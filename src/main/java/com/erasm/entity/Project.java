package com.erasm.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.erasm.enums.ProjectStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="projects")
public class Project extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="project_id")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(name="project_code",nullable=false,unique=true,length=20)
	private String projectCode;
	
	@Column(name="project_name",nullable=false,length=100)
	private String projectName;
	
	@Column(name="client_name",nullable=false,length=100)
	private String clientName;
	
	@Column(name="start_date",nullable=false)
	private LocalDate startDate;
	
	@Column(name="end_date",nullable=false)
	private LocalDate endDate;
	
	@Column(name = "technology_stack", nullable = false, length = 500)
	private String technologyStack;
	
	@Column(name = "budget", nullable = false, precision = 15, scale = 2)
	private BigDecimal budget;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status",nullable=false)
	private ProjectStatus status;
	

}

package com.erasm.entity;

import java.time.LocalDate;

import com.erasm.enums.AllocationStatus;

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
@Table(name="allocations")
public class Allocation extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	@Column(name="allocation_id")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="employee_id",nullable=false)
	private Employee employee;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="project_id",nullable=false)
	private Project project;
	
	@Column(name="allocation_percentage",nullable=false)
	private Integer allocationPercentage;
	
	@Column(name="project_role",nullable=false,length=100)
	private String projectRole;
	
	@Column(name="start_date",nullable=false)
	private LocalDate startDate;
	
	@Column(name="end_date",nullable=false)
	private LocalDate endDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status",nullable=false)
	private AllocationStatus status;
	
}

package com.erasm.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="certifications")
public class Certifications extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	@Column(name="certification_id")
	private Long id;
	
	@Column(name="certification_name",nullable=false,length=150)
	private String certificateName;
	
	@Column(name="issuing_organization",nullable=false,length=100)
	private String issuingOrganization;
	
	@Column(name="issue_date",nullable=false)
	private LocalDate issueDate;
	
	@Column(name="expiry_date")
	private LocalDate expiryDate;
	
	@Column(name="certificate_number",unique=true,length=100)
	private String certificateNumber;
	
	@Column(name="certificate_url",length=255)
	private String certificateUrl;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="employee_id",nullable=false)
	private Employee employee;

}

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
@Table(name="employees")
public class Employee extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="employee_id")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(name="employee_code",nullable=false,unique=true,length=20)
	private String employeeCode;
	
	@Column(name="first_name",nullable=false,length=50)
	private String firstName;
	
	@Column(name="last_name",nullable=false,length=50)
	private String lastName;
	
	@Column(name="email",nullable=false,unique=true,length=100)
	private String email;
	
	@Column(name="phone",nullable=false,unique=true,length=10)
	private String phone;
	
	@Column(name="department",nullable=false,length=100)
	private String department;
	
	@Column(name="designation",nullable=false,length=100)
	private String designation;
	
	@Column(name="experience",nullable=false)
	private Double experience;
	
	@Column(name="joining_date",nullable=false)
	private LocalDate joiningDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="role_id",nullable=false)
	private Role role;
	

}

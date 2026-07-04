package com.erasm.entity;

import com.erasm.enums.SkillLevel;

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
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="employee_skills",uniqueConstraints = {
								@UniqueConstraint(columnNames= 
									{"employee_id","skill_id"})
	  							}		
	  )
public class EmployeeSkill extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	@Column(name="employee_skill_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="employee_id",nullable=false)
	private Employee employee;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="skill_id",nullable=false)
	private Skill skill;
	
	@Enumerated(EnumType.STRING)
	@Column(name="skill_level",nullable=false)
	private SkillLevel skillLevel;
	
	@Column(name="years_of_experience",nullable=false)
	private Double yearsOfExperience;

}

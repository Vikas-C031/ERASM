package com.erasm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="skills")
public class Skill extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="skill_id")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(name="skill_name",nullable=false,unique=true,length=100)
	private String skillName;
	
	@Column(name="category",nullable=false,length=50)
	private String category;

}

package com.erasm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users")
public class User extends BaseEntity{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	@Column(name="user_id")
	private Long id;
	
	@Column(name="username",nullable=false,unique=true,length=50)
	private String username;
	
	@Column(name="password",nullable=false,length=255)
	private String password;
	
	@Column(name="email",nullable=false,unique=true)
	private String email;
	
	@Column(name="enabled",nullable=false)
	private Boolean enabled=true;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="employee_id",nullable=false,unique=true)
	private Employee employee;

}

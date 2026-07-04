package com.erasm.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

	@CreationTimestamp
	@Column(name="created_at",nullable=false,updatable=false)//updatable=false ensures that no can modify the exact time and also when update sql query is performed then this is not included
	@Setter(AccessLevel.NONE)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name="updated_at")
	@Setter(AccessLevel.NONE)
	private LocalDateTime updateAt;
}

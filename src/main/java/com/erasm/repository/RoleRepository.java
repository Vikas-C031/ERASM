package com.erasm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erasm.entity.Role;
import com.erasm.enums.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByRoleName(RoleType roleName);
	boolean existsByRoleName(RoleType roleName);
}

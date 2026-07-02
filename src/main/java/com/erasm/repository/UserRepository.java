package com.erasm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erasm.entity.Employee;
import com.erasm.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailAndEnabledTrue(String email);
	boolean existsByEmail(String email);
	boolean existsByEmployee(Employee employee);
	boolean existsByUsername(String username);
}

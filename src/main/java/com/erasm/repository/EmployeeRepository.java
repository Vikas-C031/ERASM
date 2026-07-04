package com.erasm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erasm.entity.Employee;
import com.erasm.entity.Role;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByEmployeeCode(String employeeCode);
	Optional<Employee> findByEmail(String email);
	boolean existsByEmployeeCode(String employeeCode);
	boolean existsByEmail(String email);
	List<Employee> findByDepartment(String department);
	List<Employee> findByRole(Role role);
	List<Employee> findByFirstNameContainingIgnoreCase(String firstName);
}

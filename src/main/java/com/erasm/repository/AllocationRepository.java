package com.erasm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erasm.entity.Allocation;
import com.erasm.entity.Employee;
import com.erasm.entity.Project;
import com.erasm.enums.AllocationStatus;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long>{

	List<Allocation> findByStatus(AllocationStatus status);
	List<Allocation> findByEmployeeAndStatus(Employee employee, AllocationStatus status);
	List<Allocation> findByEmployee(Employee employee);
	List<Allocation> findByProject(Project project);
	boolean existsByEmployeeAndProject(Employee employee,Project project);
}

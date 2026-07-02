package com.erasm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erasm.entity.Employee;
import com.erasm.entity.EmployeeSkill;
import com.erasm.entity.Skill;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long>{

	List<EmployeeSkill> findByEmployee(Employee employee);
	List<EmployeeSkill> findBySkill(Skill skill);
	boolean existsByEmployeeAndSkill(Employee employee,Skill skill);
}

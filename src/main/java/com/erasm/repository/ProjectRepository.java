package com.erasm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erasm.entity.Project;
import com.erasm.enums.ProjectStatus;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

	Optional<Project> findByProjectCode(String projectCode);//this is required for fetching or to view the project 
	boolean existsByProjectCode(String projectCode);//this is  required during project creation
	List<Project> findByProjectNameContainingIgnoreCase(String projectName);// LIKE '%value%' and ignore the case
	//containingignorecases is better than findByProjectName in searching because it allows users to search using partial words without worrying about letter casing 
	List<Project> findByStatus(ProjectStatus status);// to search by status and this return list of project
}

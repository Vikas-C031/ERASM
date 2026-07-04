package com.erasm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erasm.entity.Employee;
import com.erasm.entity.Project;
import com.erasm.entity.ResourceRequest;
import com.erasm.enums.RequestStatus;

@Repository
public interface ResourceRequestRepository extends JpaRepository<ResourceRequest,Long> {

	List<ResourceRequest> findByRequestedBy(Employee employee);
	List<ResourceRequest> findByProject(Project project);
	List<ResourceRequest> findByStatus(RequestStatus status);
	List<ResourceRequest> findByProjectAndStatus(Project project,RequestStatus status);
}

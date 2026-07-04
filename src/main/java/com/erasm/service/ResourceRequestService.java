package com.erasm.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erasm.dto.request.ResourceRequestDTO;
import com.erasm.dto.response.ResourceRequestResponseDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.Project;
import com.erasm.entity.ResourceRequest;
import com.erasm.entity.Skill;
import com.erasm.enums.AuditAction;
import com.erasm.enums.RequestStatus;
import com.erasm.exception.EmployeeNotFoundException;
import com.erasm.exception.ProjectNotFoundException;
import com.erasm.exception.ResourceRequestNotFoundException;
import com.erasm.exception.SkillNotFoundException;
import com.erasm.mapper.ResourceRequestMapper;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.ProjectRepository;
import com.erasm.repository.ResourceRequestRepository;
import com.erasm.repository.SkillRepository;

@Service
public class ResourceRequestService {
	
	private final ResourceRequestRepository resourceRequestRepository;
	private final ProjectRepository projectRepository;
	private final SkillRepository skillRepository;
	private final EmployeeRepository employeeRepository;
	private final ResourceRequestMapper resourceRequestMapper;
	private final AuditLogService auditLogService;
	
	private static final Logger logger =
		    LoggerFactory.getLogger(ResourceRequestService.class);
	
	public ResourceRequestService(ResourceRequestRepository resourceRequestRepository,
			ProjectRepository projectRepository, SkillRepository skillRepository, EmployeeRepository employeeRepository,
			ResourceRequestMapper resourceRequestMapper,AuditLogService auditLogService) {
		this.resourceRequestRepository = resourceRequestRepository;
		this.projectRepository = projectRepository;
		this.skillRepository = skillRepository;
		this.employeeRepository = employeeRepository;
		this.resourceRequestMapper = resourceRequestMapper;
		this.auditLogService=auditLogService;
	}
	
	public ResourceRequestResponseDTO createResourceRequest(ResourceRequestDTO dto) {
		logger.info(
			    "Creating resource request for project {}",
			    dto.getProjectId()
			);
		
		Project project =projectRepository.findById(dto.getProjectId()).orElseThrow(()->new ProjectNotFoundException("Project not found with the id: "+dto.getProjectId() ));
		Skill skill=skillRepository.findById(dto.getSkillId()).orElseThrow(()->new SkillNotFoundException("Skill not found with id: "+dto.getSkillId()));
		Employee requestedBy=employeeRepository.findById(dto.getRequestedByEmployeeId()).orElseThrow(()->new EmployeeNotFoundException("Employee not found with id: "+dto.getRequestedByEmployeeId()));
		
		if(dto.getExperienceRequired()<0) {
			throw new IllegalArgumentException("Experience cannot be neagtive");
			
		}
		if(dto.getNumberOfResources()<=0) {
			throw new IllegalArgumentException("Number of resourxes must be greater than zero");
		}
		ResourceRequest resourceRequest=resourceRequestMapper.toEntity(dto, project, skill, requestedBy);
		
		resourceRequest.setStatus(RequestStatus.DRAFT);
	    resourceRequest.setRequestedDate(LocalDate.now());
	    
	    ResourceRequest savedRequest =resourceRequestRepository.save(resourceRequest);

	    logger.info("Resource request created successfully.");
	    
	    return resourceRequestMapper.toResponseDTO(savedRequest);
	}
	public List<ResourceRequestResponseDTO> getAllResourceRequests() {

	    List<ResourceRequest> resourceRequests =resourceRequestRepository.findAll();

	    return resourceRequestMapper.toResponseDTOList(resourceRequests);
	}
	public ResourceRequestResponseDTO submitRequest(Long id) {

		logger.info("Submitting resource request {}", id);
		
	    ResourceRequest request = resourceRequestRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceRequestNotFoundException(
	                            "Resource request not found with id: " + id));

	    if (request.getStatus() != RequestStatus.DRAFT) {
	        throw new IllegalArgumentException(
	                "Only draft requests can be submitted");
	    }

	    request.setStatus(RequestStatus.SUBMITTED);

	    ResourceRequest updatedRequest =
	            resourceRequestRepository.save(request);

	    auditLogService.recordAction(
	            request.getRequestedBy(),
	            AuditAction.UPDATE,
	            "Resource Request",
	            updatedRequest.getId(),
	            "Resource request submitted");

	    logger.info(
	    	    "Resource request {} submitted successfully.",
	    	    updatedRequest.getId()
	    	);
	    
	    return resourceRequestMapper.toResponseDTO(updatedRequest);
	}
	public ResourceRequestResponseDTO reviewRequest(Long id) {

		logger.info("Moving resource request {} to review.", id);
	    ResourceRequest request = resourceRequestRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceRequestNotFoundException(
	                            "Resource request not found with id: " + id));

	    if (request.getStatus() != RequestStatus.SUBMITTED) {
	        throw new IllegalArgumentException(
	                "Only submitted requests can be moved to review");
	    }

	    request.setStatus(RequestStatus.REVIEW);

	    ResourceRequest updatedRequest =
	            resourceRequestRepository.save(request);

	    auditLogService.recordAction(
	            request.getRequestedBy(),
	            AuditAction.UPDATE,
	            "Resource Request",
	            updatedRequest.getId(),
	            "Resource request moved to review");

	    logger.info(
	    	    "Resource request {} moved to review.",
	    	    updatedRequest.getId()
	    	);
	    return resourceRequestMapper.toResponseDTO(updatedRequest);
	}
	public ResourceRequestResponseDTO approveRequest(Long id) {

		logger.info(
			    "Approving resource request {}",
			    id
			);
	    ResourceRequest request = resourceRequestRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceRequestNotFoundException(
	                            "Resource request not found with id: " + id));

	    if (request.getStatus() != RequestStatus.REVIEW) {
	        throw new IllegalArgumentException(
	                "Only requests under review can be approved");
	    }

	    request.setStatus(RequestStatus.APPROVED);

	    ResourceRequest updatedRequest =
	            resourceRequestRepository.save(request);

	    auditLogService.recordAction(
	            request.getRequestedBy(),
	            AuditAction.UPDATE,
	            "Resource Request",
	            updatedRequest.getId(),
	            "Resource request approved");

	    logger.info(
	    	    "Resource request {} approved.",
	    	    updatedRequest.getId()
	    	);
	    
	    return resourceRequestMapper.toResponseDTO(updatedRequest);
	}
	
	public ResourceRequestResponseDTO rejectRequest(Long id) {

	    logger.info("Rejecting resource request {}", id);

	    ResourceRequest request = resourceRequestRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceRequestNotFoundException(
	                            "Resource request not found with id: " + id));

	    if (request.getStatus() != RequestStatus.REVIEW) {

	        logger.warn(
	                "Cannot reject resource request {} because current status is {}",
	                id,
	                request.getStatus());

	        throw new IllegalArgumentException(
	                "Only requests under review can be rejected");
	    }

	    request.setStatus(RequestStatus.REJECTED);

	    ResourceRequest updatedRequest =
	            resourceRequestRepository.save(request);

	    auditLogService.recordAction(
	            request.getRequestedBy(),
	            AuditAction.UPDATE,
	            "Resource Request",
	            updatedRequest.getId(),
	            "Resource request rejected");

	    logger.info(
	            "Resource request {} rejected successfully.",
	            updatedRequest.getId());

	    return resourceRequestMapper.toResponseDTO(updatedRequest);
	}
	
	public ResourceRequestResponseDTO allocateRequest(Long id) {

		logger.info(
			    "Allocating resource request {}",
			    id
			);
	    ResourceRequest request = resourceRequestRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceRequestNotFoundException(
	                            "Resource request not found with id: " + id));

	    if (request.getStatus() != RequestStatus.APPROVED) {
	        throw new IllegalArgumentException(
	                "Only approved requests can be allocated");
	    }

	    request.setStatus(RequestStatus.ALLOCATED);

	    ResourceRequest updatedRequest =
	            resourceRequestRepository.save(request);

	    auditLogService.recordAction(
	            request.getRequestedBy(),
	            AuditAction.UPDATE,
	            "Resource Request",
	            updatedRequest.getId(),
	            "Resource request allocated");

	    logger.info(
	    	    "Resource request {} allocated.",
	    	    updatedRequest.getId()
	    	);
	    return resourceRequestMapper.toResponseDTO(updatedRequest);
	}
	public ResourceRequestResponseDTO completeRequest(Long id) {

		logger.info(
			    "Completing resource request {}",
			    id
			);
	    ResourceRequest request = resourceRequestRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceRequestNotFoundException(
	                            "Resource request not found with id: " + id));

	    if (request.getStatus() != RequestStatus.ALLOCATED) {
	        throw new IllegalArgumentException(
	                "Only allocated requests can be completed");
	    }

	    request.setStatus(RequestStatus.COMPLETED);

	    ResourceRequest updatedRequest =
	            resourceRequestRepository.save(request);

	    auditLogService.recordAction(
	            request.getRequestedBy(),
	            AuditAction.UPDATE,
	            "Resource Request",
	            updatedRequest.getId(),
	            "Resource request completed");

	    logger.info(
	    	    "Resource request {} completed.",
	    	    updatedRequest.getId()
	    	);
	    
	    return resourceRequestMapper.toResponseDTO(updatedRequest);
	}
	public ResourceRequestResponseDTO getResourceRequestById(Long id) {

	    ResourceRequest resourceRequest =
	            resourceRequestRepository.findById(id)
	                    .orElseThrow(()->new IllegalArgumentException("Resource request not found with id: " + id));

	    return resourceRequestMapper.toResponseDTO(resourceRequest);
	}
	public ResourceRequestResponseDTO updateResourceRequest(Long id,
            ResourceRequestDTO dto) {

		logger.info("Updating resource request {}", id);
		ResourceRequest existingRequest = resourceRequestRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Resource request not found with id: " + id));
		
		Project project = projectRepository.findById(dto.getProjectId()).orElseThrow(() ->new ProjectNotFoundException("Project not found with id: " + dto.getProjectId()));
		
		Skill skill = skillRepository.findById(dto.getSkillId()).orElseThrow(() ->new SkillNotFoundException("Skill not found with id: " + dto.getSkillId()));
		
		Employee requestedBy = employeeRepository.findById(dto.getRequestedByEmployeeId()).orElseThrow(() ->new EmployeeNotFoundException("Employee not found with id: " + dto.getRequestedByEmployeeId()));
		
		if (dto.getExperienceRequired() < 0) {
			throw new IllegalArgumentException("Experience cannot be negative");
		}
		
		if (dto.getNumberOfResources() <= 0) {
			throw new IllegalArgumentException("Number of resources must be greater than zero");
		}
		
		existingRequest.setProject(project);
		existingRequest.setRequiredSkill(skill);
		existingRequest.setRequestedRole(dto.getRequestedRole());
		existingRequest.setExperienceRequired(dto.getExperienceRequired());
		existingRequest.setNumberOfResources(dto.getNumberOfResources());
		existingRequest.setPriority(dto.getPriority());
		existingRequest.setRequestedBy(requestedBy);
		
		ResourceRequest updatedRequest=resourceRequestRepository.save(existingRequest);
		
		logger.info("Updated resource request {}", id);
		return resourceRequestMapper.toResponseDTO(updatedRequest);
	}
	public void deleteResourceRequest(Long id) {

		logger.info("Deleting resource request {}", id);
		
	    ResourceRequest resourceRequest = resourceRequestRepository.findById(id)
	            .orElseThrow(() ->
	                    new IllegalArgumentException(
	                            "Resource request not found with id: " + id));

	    resourceRequestRepository.delete(resourceRequest);
	    
	    logger.info("Deleted resource request {}", id);
	}
	public List<ResourceRequestResponseDTO> getRequestsByEmployee(Long employeeId) {

	    Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() ->
	                    new EmployeeNotFoundException(
	                            "Employee not found with id: " + employeeId));

	    List<ResourceRequest> resourceRequests =
	            resourceRequestRepository.findByRequestedBy(employee);

	    return resourceRequestMapper.toResponseDTOList(resourceRequests);
	}
	public List<ResourceRequestResponseDTO> getRequestsByProject(Long projectId) {

	    Project project = projectRepository.findById(projectId)
	            .orElseThrow(() ->
	                    new ProjectNotFoundException(
	                            "Project not found with id: " + projectId));

	    List<ResourceRequest> resourceRequests =
	            resourceRequestRepository.findByProject(project);

	    return resourceRequestMapper.toResponseDTOList(resourceRequests);
	}
	public List<ResourceRequestResponseDTO> getRequestsByStatus(RequestStatus status) {

	    List<ResourceRequest> resourceRequests =
	            resourceRequestRepository.findByStatus(status);

	    return resourceRequestMapper.toResponseDTOList(resourceRequests);
	}
	public List<ResourceRequestResponseDTO> getRequestsByProjectAndStatus(
	        Long projectId,
	        RequestStatus status) {

	    Project project = projectRepository.findById(projectId)
	            .orElseThrow(() ->
	                    new ProjectNotFoundException(
	                            "Project not found with id: " + projectId));

	    List<ResourceRequest> resourceRequests =
	            resourceRequestRepository.findByProjectAndStatus(project, status);

	    return resourceRequestMapper.toResponseDTOList(resourceRequests);
	}

}

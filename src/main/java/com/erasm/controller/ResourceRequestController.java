package com.erasm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erasm.dto.request.ResourceRequestDTO;
import com.erasm.dto.response.ResourceRequestResponseDTO;
import com.erasm.enums.RequestStatus;
import com.erasm.service.ResourceRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/resource-requests")
public class ResourceRequestController {

    private final ResourceRequestService resourceRequestService;

    public ResourceRequestController(ResourceRequestService resourceRequestService) {
        this.resourceRequestService = resourceRequestService;
    }

    @PostMapping
    public ResponseEntity<ResourceRequestResponseDTO> createResourceRequest(
            @Valid @RequestBody ResourceRequestDTO dto) {

        ResourceRequestResponseDTO resourceRequest =
                resourceRequestService.createResourceRequest(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resourceRequest);
    }

    @GetMapping
    public ResponseEntity<List<ResourceRequestResponseDTO>> getAllResourceRequests() {

        return ResponseEntity.ok(
                resourceRequestService.getAllResourceRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceRequestResponseDTO> getResourceRequestById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                resourceRequestService.getResourceRequestById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceRequestResponseDTO> updateResourceRequest(
            @PathVariable Long id,
            @Valid @RequestBody ResourceRequestDTO dto) {

        return ResponseEntity.ok(
                resourceRequestService.updateResourceRequest(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResourceRequest(
            @PathVariable Long id) {

        resourceRequestService.deleteResourceRequest(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ResourceRequestResponseDTO>> getRequestsByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                resourceRequestService.getRequestsByEmployee(employeeId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ResourceRequestResponseDTO>> getRequestsByProject(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                resourceRequestService.getRequestsByProject(projectId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ResourceRequestResponseDTO>> getRequestsByStatus(
            @PathVariable RequestStatus status) {

        return ResponseEntity.ok(
                resourceRequestService.getRequestsByStatus(status));
    }

    @GetMapping("/project/{projectId}/status/{status}")
    public ResponseEntity<List<ResourceRequestResponseDTO>> getRequestsByProjectAndStatus(
            @PathVariable Long projectId,
            @PathVariable RequestStatus status) {

        return ResponseEntity.ok(
                resourceRequestService.getRequestsByProjectAndStatus(
                        projectId,
                        status));
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<ResourceRequestResponseDTO> submitRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                resourceRequestService.submitRequest(id));
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<ResourceRequestResponseDTO> reviewRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                resourceRequestService.reviewRequest(id));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<ResourceRequestResponseDTO> approveRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                resourceRequestService.approveRequest(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<ResourceRequestResponseDTO> rejectRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                resourceRequestService.rejectRequest(id));
    }
    
    @PutMapping("/{id}/allocate")
    public ResponseEntity<ResourceRequestResponseDTO> allocateRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                resourceRequestService.allocateRequest(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ResourceRequestResponseDTO> completeRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                resourceRequestService.completeRequest(id));
    }

}
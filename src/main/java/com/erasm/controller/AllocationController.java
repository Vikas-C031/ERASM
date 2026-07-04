package com.erasm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.erasm.dto.request.AllocationRequestDTO;
import com.erasm.dto.response.AllocationResponseDTO;
import com.erasm.service.AllocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/allocations")
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping
    public ResponseEntity<AllocationResponseDTO> createAllocation(
            @Valid @RequestBody AllocationRequestDTO dto) {

        AllocationResponseDTO allocation =
                allocationService.createAllocation(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(allocation);
    }

    @GetMapping
    public ResponseEntity<List<AllocationResponseDTO>> getAllAllocations() {

        return ResponseEntity.ok(
                allocationService.getAllAllocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllocationResponseDTO> getAllocationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                allocationService.getAllocationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AllocationResponseDTO> updateAllocation(
            @PathVariable Long id,
            @Valid @RequestBody AllocationRequestDTO dto) {

        return ResponseEntity.ok(
                allocationService.updateAllocation(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllocation(
            @PathVariable Long id) {

        allocationService.deleteAllocation(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AllocationResponseDTO>> getAllocationsByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                allocationService.getAllocationsByEmployee(employeeId));
    }

    @PutMapping("/{id}/release")
    public ResponseEntity<AllocationResponseDTO> releaseAllocation(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                allocationService.releaseAllocation(id));
    }
    @PutMapping("/{id}/reallocate")
    public ResponseEntity<AllocationResponseDTO> reallocateAllocation(
            @PathVariable Long id,
            @Valid @RequestBody AllocationRequestDTO dto) {

        return ResponseEntity.ok(
                allocationService.reallocateAllocation(id, dto));
    }
    
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<AllocationResponseDTO>> getAllocationsByProject(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                allocationService.getAllocationsByProject(projectId));
    }

}
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

import com.erasm.dto.request.CertificationRequestDTO;
import com.erasm.dto.response.CertificationResponseDTO;
import com.erasm.service.CertificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/certifications")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @PostMapping
    public ResponseEntity<CertificationResponseDTO> createCertification(
            @Valid @RequestBody CertificationRequestDTO dto) {

        CertificationResponseDTO certification =
                certificationService.createCertification(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(certification);
    }

    @GetMapping
    public ResponseEntity<List<CertificationResponseDTO>> getAllCertifications() {

        return ResponseEntity.ok(
                certificationService.getAllCertifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationResponseDTO> getCertificationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                certificationService.getCertificationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificationResponseDTO> updateCertification(
            @PathVariable Long id,
            @Valid @RequestBody CertificationRequestDTO dto) {

        return ResponseEntity.ok(
                certificationService.updateCertification(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertification(
            @PathVariable Long id) {

        certificationService.deleteCertification(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<CertificationResponseDTO>> getCertificationsByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                certificationService.getCertificationsByEmployee(employeeId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CertificationResponseDTO>> searchCertifications(
            @RequestParam String name) {

        return ResponseEntity.ok(
                certificationService.searchCertifications(name));
    }

    @GetMapping("/expired")
    public ResponseEntity<List<CertificationResponseDTO>> getExpiredCertifications() {

        return ResponseEntity.ok(
                certificationService.getExpiredCertifications());
    }

}
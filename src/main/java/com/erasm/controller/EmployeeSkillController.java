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
import org.springframework.web.bind.annotation.RestController;

import com.erasm.dto.request.EmployeeSkillRequestDTO;
import com.erasm.dto.response.EmployeeSkillResponseDTO;
import com.erasm.service.EmployeeSkillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee-skills")
public class EmployeeSkillController {

    private final EmployeeSkillService employeeSkillService;

    public EmployeeSkillController(EmployeeSkillService employeeSkillService) {
        this.employeeSkillService = employeeSkillService;
    }
    @GetMapping
    public ResponseEntity<List<EmployeeSkillResponseDTO>> getAllEmployeeSkills() {

        return ResponseEntity.ok(
                employeeSkillService.getAllEmployeeSkills());
    }

    @PostMapping
    public ResponseEntity<EmployeeSkillResponseDTO> createEmployeeSkill(
            @Valid @RequestBody EmployeeSkillRequestDTO dto) {

        EmployeeSkillResponseDTO employeeSkill =
                employeeSkillService.createEmployeeSkill(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeSkill);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeSkillResponseDTO> getEmployeeSkillById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                employeeSkillService.getEmployeeSkillById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeSkillResponseDTO> updateEmployeeSkill(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeSkillRequestDTO dto) {

        return ResponseEntity.ok(
                employeeSkillService.updateEmployeeSkill(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeSkill(
            @PathVariable Long id) {

        employeeSkillService.deleteEmployeeSkill(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeSkillResponseDTO>> getSkillsByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                employeeSkillService.getSkillsByEmployee(employeeId));
    }

    @GetMapping("/skill/{skillId}")
    public ResponseEntity<List<EmployeeSkillResponseDTO>> getEmployeesBySkill(
            @PathVariable Long skillId) {

        return ResponseEntity.ok(
                employeeSkillService.getEmployeesBySkill(skillId));
    }

}
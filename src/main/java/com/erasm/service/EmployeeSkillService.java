package com.erasm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erasm.dto.request.EmployeeSkillRequestDTO;
import com.erasm.dto.response.EmployeeSkillResponseDTO;
import com.erasm.entity.Employee;
import com.erasm.entity.EmployeeSkill;
import com.erasm.entity.Skill;
import com.erasm.exception.EmployeeNotFoundException;
import com.erasm.exception.SkillNotFoundException;
import com.erasm.mapper.EmployeeSkillMapper;
import com.erasm.repository.EmployeeRepository;
import com.erasm.repository.EmployeeSkillRepository;
import com.erasm.repository.SkillRepository;

@Service
public class EmployeeSkillService {
	
	private final EmployeeSkillRepository employeeSkillRepository;
	private final EmployeeRepository employeeRepository;
	private final SkillRepository skillRepository;
	private final EmployeeSkillMapper employeeSkillMapper;
	
	private static final Logger logger =LoggerFactory.getLogger(EmployeeSkillService.class);
	
	public EmployeeSkillService(EmployeeSkillRepository employeeSkillRepository, EmployeeRepository employeeRepository,
			SkillRepository skillRepository, EmployeeSkillMapper employeeSkillMapper) {
		this.employeeSkillRepository = employeeSkillRepository;
		this.employeeRepository = employeeRepository;
		this.skillRepository = skillRepository;
		this.employeeSkillMapper = employeeSkillMapper;
	}
	public EmployeeSkillResponseDTO createEmployeeSkill(EmployeeSkillRequestDTO dto) {
		logger.info(
			    "Assigning skill {} to employee {}",
			    dto.getSkillId(),
			    dto.getEmployeeId()
			);
	    Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(() ->new EmployeeNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
	    Skill skill = skillRepository.findById(dto.getSkillId()).orElseThrow(() ->new SkillNotFoundException("Skill not found with id: "+dto.getSkillId()));

	    if (employeeSkillRepository.existsByEmployeeAndSkill(employee,skill)) {
	        throw new IllegalArgumentException("Employee already has this skill");
	    }

	    if (dto.getYearsOfExperience()<0) {
	        throw new IllegalArgumentException("Years of experience cannot be negative");
	    }

	    EmployeeSkill employeeSkill =employeeSkillMapper.toEntity(dto,employee,skill);
	    EmployeeSkill savedEmployeeSkill =employeeSkillRepository.save(employeeSkill);

	    logger.info("Skill assigned successfully.");
	    return employeeSkillMapper.toResponseDTO(savedEmployeeSkill);
	}
	
	public List<EmployeeSkillResponseDTO> getAllEmployeeSkills() {

	    List<EmployeeSkill> employeeSkills =
	            employeeSkillRepository.findAll();

	    return employeeSkillMapper.toResponseDTOList(employeeSkills);
	}
	
	public EmployeeSkillResponseDTO getEmployeeSkillById(Long id) {
		EmployeeSkill employeeSkill =employeeSkillRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee Skill not found id: "+id));
		return employeeSkillMapper.toResponseDTO(employeeSkill);
	}
	
	public EmployeeSkillResponseDTO updateEmployeeSkill(Long id, EmployeeSkillRequestDTO dto) {
		logger.info("updating skill assignment with id: {}", id);
	    EmployeeSkill existingEmployeeSkill = employeeSkillRepository.findById(id).orElseThrow(() ->new SkillNotFoundException ("Employee skill not found with id: " + id));

	    Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(()->new EmployeeNotFoundException("Employee not found with id: " + dto.getEmployeeId()));

	    Skill skill = skillRepository.findById(dto.getSkillId()).orElseThrow(()->new SkillNotFoundException("Skill not found with id: "+dto.getSkillId()));

	    if (employeeSkillRepository.existsByEmployeeAndSkill(employee, skill)
	            && (!existingEmployeeSkill.getEmployee().getId().equals(employee.getId())
	            || !existingEmployeeSkill.getSkill().getId().equals(skill.getId()))) {
	        throw new IllegalArgumentException("Employee already has this skill");
	    }

	    if (dto.getYearsOfExperience() < 0) {
	        throw new IllegalArgumentException("Years of experience cannot be negative");
	    }
	    existingEmployeeSkill.setEmployee(employee);
	    existingEmployeeSkill.setSkill(skill);
	    existingEmployeeSkill.setSkillLevel(dto.getSkillLevel());
	    existingEmployeeSkill.setYearsOfExperience(dto.getYearsOfExperience());

	    EmployeeSkill updatedEmployeeSkill =employeeSkillRepository.save(existingEmployeeSkill);
	    logger.info("Updated skill assignment with id: {}", id);
	    return employeeSkillMapper.toResponseDTO(updatedEmployeeSkill);
	}
	public void deleteEmployeeSkill(Long id) {
		logger.info("Removing skill assignment with id: {}", id);
		EmployeeSkill employeeSkill=employeeSkillRepository.findById(id).orElseThrow(()->new SkillNotFoundException("Skill not found with id"+id));
		employeeSkillRepository.delete(employeeSkill);
		logger.info("Removed skill assignment with id: {}", id);
	}
	public List<EmployeeSkillResponseDTO> getSkillsByEmployee(Long employeeId) {
	    Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->new EmployeeNotFoundException("Employee not found with id: " + employeeId));
	    List<EmployeeSkill> employeeSkills =employeeSkillRepository.findByEmployee(employee);

	    return employeeSkillMapper.toResponseDTOList(employeeSkills);
	}
	public List<EmployeeSkillResponseDTO> getEmployeesBySkill(Long skillId) {
	    Skill skill = skillRepository.findById(skillId).orElseThrow(() ->new SkillNotFoundException("Skill not found with id: " + skillId));
	    List<EmployeeSkill> employeeSkills=employeeSkillRepository.findBySkill(skill);

	    return employeeSkillMapper.toResponseDTOList(employeeSkills);
	}
	
	
	
	

}

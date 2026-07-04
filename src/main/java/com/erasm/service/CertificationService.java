package com.erasm.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erasm.dto.request.CertificationRequestDTO;
import com.erasm.dto.response.CertificationResponseDTO;
import com.erasm.entity.Certifications;
import com.erasm.entity.Employee;
import com.erasm.exception.EmployeeNotFoundException;
import com.erasm.mapper.CertificationMapper;
import com.erasm.repository.CertificationsRepository;
import com.erasm.repository.EmployeeRepository;

@Service
public class CertificationService {
	
	private final CertificationsRepository certificationsRepository;
	private final EmployeeRepository employeeRepository;
	private final CertificationMapper certificationMapper;
	
	private static final Logger logger =LoggerFactory.getLogger(CertificationService.class);
	
	public CertificationService(CertificationsRepository certificationsRepository,
			EmployeeRepository employeeRepository, CertificationMapper certificationMapper) {
		this.certificationsRepository = certificationsRepository;
		this.employeeRepository = employeeRepository;
		this.certificationMapper = certificationMapper;
	}

	public CertificationResponseDTO createCertification(CertificationRequestDTO dto) {

		logger.info(
			    "Adding certification '{}' for employee {}",
			    dto.getCertificationName(),
			    dto.getEmployeeId()
			);
	    Employee employee=employeeRepository.findById(dto.getEmployeeId()).orElseThrow(()->new EmployeeNotFoundException("Employee not found with id: "+dto.getEmployeeId()));

	    if (dto.getExpiryDate()!=null
	            && dto.getExpiryDate().isBefore(dto.getIssueDate())){
	        throw new IllegalArgumentException("Expiry date cannot be before issue date");
	    }
	    Certifications certification=certificationMapper.toEntity(dto, employee);
	    Certifications savedCertification=certificationsRepository.save(certification);

	    logger.info("Certification added successfully.");
	    
	    return certificationMapper.toResponseDTO(savedCertification);
	}
	public List<CertificationResponseDTO> getAllCertifications(){

	    List<Certifications> certifications=certificationsRepository.findAll();
	    return certificationMapper.toResponseDTOList(certifications);
	}
	public CertificationResponseDTO getCertificationById(Long id) {

	    Certifications certification=certificationsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Certification not found with id: "+id));
	    return certificationMapper.toResponseDTO(certification);
	}
	
	public CertificationResponseDTO updateCertification(Long id,CertificationRequestDTO dto) {

		logger.info("Certification updating successfully.");
	    Certifications existingCertification=certificationsRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Certification not found with id: "+ id));

	    Employee employee=employeeRepository.findById(dto.getEmployeeId()).orElseThrow(() ->new EmployeeNotFoundException("Employee not found with id: "+dto.getEmployeeId()));

	    if(dto.getCertificateNumber()!=null
	            && certificationsRepository.existsByCertificateNumber(dto.getCertificateNumber())
	            && !dto.getCertificateNumber().equals(existingCertification.getCertificateNumber())) {
	        throw new IllegalArgumentException("Certificate number already exists");
	    }

	    if (dto.getExpiryDate()!=null&& dto.getExpiryDate().isBefore(dto.getIssueDate())){
	        throw new IllegalArgumentException("Expiry date cannot be before issue date");
	    }
	    existingCertification.setEmployee(employee);
	    existingCertification.setCertificationName(dto.getCertificationName());
	    existingCertification.setIssuingOrganization(dto.getIssuingOrganization());
	    existingCertification.setIssueDate(dto.getIssueDate());
	    existingCertification.setExpiryDate(dto.getExpiryDate());
	    existingCertification.setCertificateNumber(dto.getCertificateNumber());
	    existingCertification.setCertificateUrl(dto.getCertificateUrl());

	    Certifications updatedCertification=certificationsRepository.save(existingCertification);
	    
	    logger.info("Certification updated successfully.");
	    
	    return certificationMapper.toResponseDTO(updatedCertification);
	}
	public void deleteCertification(Long id){

		logger.info("Certification deleting successfully.");
	    Certifications certification=certificationsRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Certification not found with id: "+id));
	    certificationsRepository.delete(certification);
	    logger.info("Certification deleted successfully.");
	}
	
	public List<CertificationResponseDTO> getCertificationsByEmployee(Long employeeId){

	    Employee employee=employeeRepository.findById(employeeId).orElseThrow(() ->new EmployeeNotFoundException("Employee not found with id: "+employeeId));
	    List<Certifications> certifications =certificationsRepository.findByEmployee(employee);

	    return certificationMapper.toResponseDTOList(certifications);
	}
	public List<CertificationResponseDTO> searchCertifications(String certificationName){
	    List<Certifications> certifications=certificationsRepository.findByCertificationNameContainingIgnoreCase(certificationName);

	    return certificationMapper.toResponseDTOList(certifications);
	}
	public List<CertificationResponseDTO> getExpiredCertifications() {

	    List<Certifications> certifications=certificationsRepository.findByExpiryDateBefore(LocalDate.now());
	    return certificationMapper.toResponseDTOList(certifications);
	}
	
	
}

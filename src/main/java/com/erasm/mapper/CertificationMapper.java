package com.erasm.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.erasm.dto.request.CertificationRequestDTO;
import com.erasm.dto.response.CertificationResponseDTO;
import com.erasm.entity.Certifications;
import com.erasm.entity.Employee;

@Component
public class CertificationMapper {
	
	public Certifications  toEntity(CertificationRequestDTO dto,Employee employee) {

		Certifications certification = new Certifications();
		certification.setCertificationName(dto.getCertificationName());
		certification.setIssuingOrganization(dto.getIssuingOrganization());
		certification.setIssueDate(dto.getIssueDate());
		certification.setExpiryDate(dto.getExpiryDate());
		certification.setCertificateNumber(dto.getCertificateNumber());
		certification.setCertificateUrl(dto.getCertificateUrl());
		certification.setEmployee(employee);
		return certification;
		
	}
		
	public CertificationResponseDTO toResponseDTO(Certifications certification) {
		
		CertificationResponseDTO dto =new CertificationResponseDTO();
		dto.setId(certification.getId());
		dto.setEmployeeCode(certification.getEmployee().getEmployeeCode());
		dto.setEmployeeName(certification.getEmployee().getFirstName()+" "
						   +certification.getEmployee().getLastName());
		dto.setCertificationName(certification.getCertificationName());
		dto.setIssuingOrganization(certification.getIssuingOrganization());
		dto.setIssueDate(certification.getIssueDate());
		dto.setExpiryDate(certification.getExpiryDate());
		dto.setCertificateNumber(certification.getCertificateNumber());
		dto.setCertificateUrl(certification.getCertificateUrl());
		dto.setCreatedAt(certification.getCreatedAt());
		dto.setUpdatedAt(certification.getUpdateAt());
		return dto;
	}
		
	public List<CertificationResponseDTO> toResponseDTOList(List<Certifications> certifications) {
		
		return certifications.stream()
		.map(this::toResponseDTO)
		.toList();
	}

}

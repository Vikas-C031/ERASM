package com.erasm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erasm.entity.Certifications;
import com.erasm.entity.Employee;

@Repository
public interface CertificationsRepository extends JpaRepository<Certifications, Long>{

	List<Certifications> findByEmployee(Employee employee);
	List<Certifications> findByCertificationNameContainingIgnoreCase(String certification_name);
	List<Certifications> findByExpiryDateBefore(LocalDate date);
	boolean existsByCertificateNumber(String certificateNumber);
}

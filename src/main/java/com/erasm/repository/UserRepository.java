package com.erasm.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erasm.entity.Employee;
import com.erasm.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("""
		    SELECT u
		    FROM User u
		    JOIN FETCH u.employee e
		    JOIN FETCH e.role
		    WHERE u.username = :username
		""")
		Optional<User> findByUsername(@Param("username") String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailAndEnabledTrue(String email);
	boolean existsByEmail(String email);
	boolean existsByEmployee(Employee employee);
	boolean existsByUsername(String username);
}

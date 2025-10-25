package com.lendingApp.main.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lendingApp.main.entity.Customer;
import com.lendingApp.main.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
	Optional<Employee> findFirstByCityIgnoreCaseAndDesignation(String city, String designation);
	List<Employee> findByDesignation(String designation);
	Page<Employee> findAll(Pageable pageable);
	Optional<Employee> findByUser_UserId(UUID userId);
}

package com.lendingApp.main.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lendingApp.main.entity.Installment;
import com.lendingApp.main.enums.InstallmentStatus;

@Repository
public interface InstallmentsRepository extends JpaRepository<Installment, UUID> {
    
	List<Installment> findByStatusAndInstEndDate(InstallmentStatus status, LocalDate instEndDate);
	Page<Installment> findByStatusAndInstStartDateLessThanEqualAndInstEndDateGreaterThanEqualAndApplicationCustomerCustomerId(
		    InstallmentStatus status,
		    LocalDate instStartDate,
		    LocalDate instEndDate,
		    UUID customerId,
		    Pageable pageable);
	List<Installment> findByStatusAndInstEndDateBefore(InstallmentStatus status, LocalDate instEndDate);
	List<Installment> findByStatus(InstallmentStatus status);
}
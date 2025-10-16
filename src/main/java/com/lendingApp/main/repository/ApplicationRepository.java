package com.lendingApp.main.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lendingApp.main.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
//    List<Application> findByStatus(String status);
//    List<Application> findByCustomer_PhoneNumberAndStatus(String phoneNumber, String status);
    Page<Application> findByCustomerCustomerId(UUID customerId,Pageable pageable);
    Page<Application> findByCustomerCustomerIdAndStatus(UUID customerId, String status,Pageable pageable);
    List<Application> findByAssignedManager_eId(UUID eId);



}
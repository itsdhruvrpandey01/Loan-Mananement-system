package com.lendingApp.main.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lendingApp.main.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,UUID>{
    
}

package com.lendingApp.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lendingApp.main.entity.LoanTypeEntity;

public interface LoanTypeRepository extends JpaRepository<LoanTypeEntity, Long> {
    LoanTypeEntity findByTypeName(String typeName);
}

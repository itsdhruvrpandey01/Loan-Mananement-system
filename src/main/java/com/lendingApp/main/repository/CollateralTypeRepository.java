package com.lendingApp.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lendingApp.main.entity.CollateralTypeEntity;

public interface CollateralTypeRepository extends JpaRepository<CollateralTypeEntity, Long> {
    CollateralTypeEntity findByTypeName(String typeName);
}


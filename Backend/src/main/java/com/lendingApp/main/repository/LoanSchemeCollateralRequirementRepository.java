package com.lendingApp.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lendingApp.main.entity.LoanSchemeCollateralRequirement;

public interface LoanSchemeCollateralRequirementRepository extends JpaRepository<LoanSchemeCollateralRequirement, Long> {
}

package com.lendingApp.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lendingApp.main.entity.LoanScheme;
import com.lendingApp.main.entity.LoanTypeEntity;

public interface LoanSchemeRepository extends JpaRepository<LoanScheme,Long>{
	List<LoanScheme> findByLoanType_TypeName(String typeName);

}

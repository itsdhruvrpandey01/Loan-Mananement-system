package com.lendingApp.main.service;

import java.util.List;

import com.lendingApp.main.dto.LoanTypeDto;
import com.lendingApp.main.dto.LoanTypeResponseDto;

public interface LoanTypeService {
	LoanTypeResponseDto addLoanType(LoanTypeDto dto);
	List<LoanTypeResponseDto> getAllLoanTypes();
}

package com.lendingApp.main.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lendingApp.main.dto.LoanCollateralRequestDto;
import com.lendingApp.main.dto.LoanResponseDto;
import com.lendingApp.main.dto.LoanSchemeDto;
import com.lendingApp.main.entity.CollateralTypeEntity;
import com.lendingApp.main.entity.LoanScheme;
import com.lendingApp.main.entity.LoanSchemeCollateralRequirement;
import com.lendingApp.main.entity.LoanTypeEntity;
import com.lendingApp.main.exception.LoanException;
import com.lendingApp.main.repository.CollateralTypeRepository;
import com.lendingApp.main.repository.LoanSchemeRepository;
import com.lendingApp.main.repository.LoanTypeRepository;
import com.lendingApp.main.service.LoanSchemeService;

import jakarta.transaction.Transactional;

@Service
public class LoanSchemeServiceImpl implements LoanSchemeService {

	@Autowired
	private LoanSchemeRepository loanSchemeRepository;
	@Autowired
	private LoanTypeRepository loanTypeRepository;

	@Autowired
	private CollateralTypeRepository collateralTypeRepository;
	@Autowired
	ModelMapper mapper;

	@Override
	public LoanResponseDto findLoandById(Long id) {
		LoanScheme loan = loanSchemeRepository.findById(id)
				.orElseThrow(() -> new LoanException("No loan with id " + id));
		return mapper.map(loan, LoanResponseDto.class);
	}

	@Override
	@Transactional
	public LoanResponseDto addLoanScheme(LoanSchemeDto dto) {

		// Validate LoanType
		LoanTypeEntity loanType = loanTypeRepository.findById(dto.getLoanTypeId())
				.orElseThrow(() -> new IllegalArgumentException("Loan Type not found with ID: " + dto.getLoanTypeId()));

		// Basic cross-field validation
		if (dto.getMinLoanAmount() >= dto.getMaxLoanAmount()) {
			throw new IllegalArgumentException("Minimum loan amount must be less than maximum loan amount");
		}

		if (dto.getMinAge() > dto.getMaxAge()) {
			throw new IllegalArgumentException("Minimum age must be less than or equal to maximum age");
		}

		// Step 1: Use ModelMapper for direct fields
		LoanScheme loanScheme = mapper.map(dto, LoanScheme.class);
		loanScheme.setLoanSchemeId(null);
		// Step 2: Set unmapped or entity-based fields
		loanScheme.setLoanType(loanType);
		loanScheme.setActive(true); // default active

		// Step 3: Manually map nested collateral requirements
		if (dto.getCollateralRequired() && dto.getCollateralRequirements() != null) {
			List<LoanSchemeCollateralRequirement> collateralRequirements = new ArrayList<>();

			for (LoanCollateralRequestDto collateralDto : dto.getCollateralRequirements()) {
				CollateralTypeEntity collateralType = collateralTypeRepository.findById(collateralDto.getCollatoralId())
						.orElseThrow(() -> new IllegalArgumentException(
								"Collateral Type not found with ID: " + collateralDto.getCollatoralId()));

				LoanSchemeCollateralRequirement requirement = new LoanSchemeCollateralRequirement();
				requirement.setLoanScheme(loanScheme);
				requirement.setCollateralType(collateralType);
				requirement.setRequiredDocuments(collateralDto.getRequiredDocuments());

				collateralRequirements.add(requirement);
			}

			loanScheme.setCollateralRequirements(collateralRequirements);
		}

		// Step 4: Save and return
		LoanScheme addedLoan = loanSchemeRepository.save(loanScheme);
		// ✅ Return DTO response
		return mapper.map(addedLoan, LoanResponseDto.class);
	}

	@Override
	public List<LoanResponseDto> getAllLoans() {
		List<LoanScheme> loanSchemes = loanSchemeRepository.findAll();
		List<LoanResponseDto> loanResponseDtos = new ArrayList<>();
		for (LoanScheme loanScheme : loanSchemes) {
			if (loanScheme.isActive())
				loanResponseDtos.add(mapper.map(loanScheme, LoanResponseDto.class));
		}
		return loanResponseDtos;
	}

	@Override
	public List<LoanResponseDto> findLoanByLoanType(String loanType) {
		List<LoanScheme> loanSchemes = loanSchemeRepository.findByLoanType_TypeName(loanType);
		List<LoanResponseDto> loanResponseDtos = new ArrayList<>();
		for (LoanScheme loanScheme : loanSchemes) {
			loanResponseDtos.add(mapper.map(loanScheme, LoanResponseDto.class));
		}
		return loanResponseDtos;
	}

	@Override
	public LoanResponseDto deactiveLoanScheme(Long loanId) {
		LoanScheme loan = loanSchemeRepository.findById(loanId)
				.orElseThrow(() -> new LoanException("No loan with id " + loanId));
		loan.setActive(false);
		loan = loanSchemeRepository.save(loan);
		return mapper.map(loan, LoanResponseDto.class);
	}

	@Override
	public LoanResponseDto activateLoanScheme(Long loanId) {
		LoanScheme loan = loanSchemeRepository.findById(loanId)
				.orElseThrow(() -> new LoanException("No loan with id " + loanId));
		loan.setActive(true);
		loan = loanSchemeRepository.save(loan);
		return mapper.map(loan, LoanResponseDto.class);
	}
}
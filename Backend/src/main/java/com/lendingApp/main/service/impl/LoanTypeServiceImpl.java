package com.lendingApp.main.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lendingApp.main.dto.CollatoralTypeResponseDto;
import com.lendingApp.main.dto.LoanTypeDto;
import com.lendingApp.main.dto.LoanTypeResponseDto;
import com.lendingApp.main.entity.CollateralTypeEntity;
import com.lendingApp.main.entity.LoanTypeEntity;
import com.lendingApp.main.repository.LoanTypeRepository;
import com.lendingApp.main.service.LoanTypeService;

@Service
public class LoanTypeServiceImpl implements LoanTypeService{
	
	@Autowired
	private LoanTypeRepository loanTypeRepository;
	@Autowired
	private ModelMapper mapper;

	@Override
	public LoanTypeResponseDto addLoanType(LoanTypeDto dto) {
		LoanTypeEntity loanType = mapper.map(dto, LoanTypeEntity.class);
		LoanTypeEntity loanTypeAdded = this.loanTypeRepository.save(loanType);
		return mapper.map(loanTypeAdded,LoanTypeResponseDto.class);
	}

	@Override
	public List<LoanTypeResponseDto> getAllLoanTypes() {
		List<LoanTypeEntity> loanTypeEntities = this.loanTypeRepository.findAll();
		List<LoanTypeResponseDto> loanTypes = new ArrayList<>();
		for(LoanTypeEntity loanTypeEntity:loanTypeEntities) {
			loanTypes.add(mapper.map(loanTypeEntity, LoanTypeResponseDto.class));
		}
		return loanTypes;
	}

}

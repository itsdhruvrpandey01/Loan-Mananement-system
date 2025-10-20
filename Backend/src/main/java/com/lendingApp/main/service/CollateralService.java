package com.lendingApp.main.service;

import java.util.List;

import com.lendingApp.main.dto.CollateralTypeDto;
import com.lendingApp.main.dto.CollatoralTypeResponseDto;

public interface CollateralService {
	CollatoralTypeResponseDto addCollatoralType(CollateralTypeDto collateralTypeDto);
	List<CollatoralTypeResponseDto> getAllColatorals();
}

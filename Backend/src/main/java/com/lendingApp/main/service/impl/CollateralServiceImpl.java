package com.lendingApp.main.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lendingApp.main.dto.CollateralTypeDto;
import com.lendingApp.main.dto.CollatoralTypeResponseDto;
import com.lendingApp.main.entity.CollateralTypeEntity;
import com.lendingApp.main.repository.CollateralTypeRepository;
import com.lendingApp.main.service.CollateralService;

@Service
public class CollateralServiceImpl implements CollateralService{
	
	@Autowired
	private CollateralTypeRepository collateralTypeRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CollatoralTypeResponseDto addCollatoralType(CollateralTypeDto collateralTypeDto) {
		CollateralTypeEntity collateralTypeEntity = mapper.map(collateralTypeDto, CollateralTypeEntity.class);
		CollateralTypeEntity savedCollateralTypeEntity = this.collateralTypeRepository.save(collateralTypeEntity);
		return mapper.map(savedCollateralTypeEntity, CollatoralTypeResponseDto.class);
	}

	@Override
	public List<CollatoralTypeResponseDto> getAllColatorals() {
		List<CollateralTypeEntity> collateralTypeEntities = this.collateralTypeRepository.findAll();
		List<CollatoralTypeResponseDto> collatoralTypes = new ArrayList<>();
		for(CollateralTypeEntity collateralTypeEntity:collateralTypeEntities) {
			collatoralTypes.add(mapper.map(collateralTypeEntity, CollatoralTypeResponseDto.class));
		}
		return collatoralTypes;
	}

}

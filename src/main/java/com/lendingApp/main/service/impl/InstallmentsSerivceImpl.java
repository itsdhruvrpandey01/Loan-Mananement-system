package com.lendingApp.main.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lendingApp.main.dto.InstallmentDto;
import com.lendingApp.main.dto.PageResponseDto;
import com.lendingApp.main.entity.Installment;
import com.lendingApp.main.enums.InstallmentStatus;
import com.lendingApp.main.repository.InstallmentsRepository;
import com.lendingApp.main.service.InstallmentsSerivce;

@Service
// @RequiredArgsConstructor
// @Data
public class InstallmentsSerivceImpl implements InstallmentsSerivce {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private InstallmentsRepository installmentsRepository;
	@Override
	public PageResponseDto<InstallmentDto> getUnpaidInstallmentsBeforeMonth(UUID customerId,int page,int size) {
		YearMonth currentMonth = YearMonth.now();
		LocalDate monthStart = currentMonth.atDay(1); // e.g., 2025-10-01
		LocalDate monthEnd = currentMonth.atEndOfMonth(); // e.g., 2025-10-31
		Pageable pageable = PageRequest.of(page, size);
	    Page<Installment> installments = installmentsRepository
	        .findByStatusAndInstStartDateLessThanEqualAndInstEndDateGreaterThanEqualAndApplicationCustomerCustomerId(
	                InstallmentStatus.UPCOMING,
	                monthEnd,    // instStartDate <= monthEnd
	                monthStart,  // instEndDate >= monthStart
	                customerId,
	                pageable
	            );
	    PageResponseDto<InstallmentDto> pageres = new PageResponseDto<>();
	    pageres.setTotalElements(installments.getTotalElements());
	    pageres.setTotalPage(installments.getTotalPages());
		pageres.setSize(installments.getSize());
		pageres.setFirst(installments.isFirst());
		pageres.setLast(installments.isLast());
	    List<InstallmentDto> installmentDtos = new ArrayList<>();
	    
	    for(Installment installment:installments) {
	    	installmentDtos.add(mapper.map(installment,InstallmentDto.class));
	    }
	    pageres.setContents(installmentDtos);

	    return pageres;
	}

}

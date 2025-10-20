package com.lendingApp.main.service;

import java.util.UUID;

import com.lendingApp.main.dto.InstallmentDto;
import com.lendingApp.main.dto.PageResponseDto;

public interface InstallmentsSerivce {
	PageResponseDto<InstallmentDto> getUnpaidInstallmentsBeforeMonth(UUID customerId,int limit,int offset);
}

package com.lendingApp.main.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.lendingApp.main.enums.InstallmentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentDto {
	private UUID installmentId;
    private Double instAmt;
    private LocalDate instStartDate;
    private LocalDate instEndDate;
    private InstallmentStatus installmentStatus;
    private LocalDate paidDate;
    private Double fineAmt;
}

package com.lendingApp.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanCollateralRequestDto {
	private Long collatoralId;
	private List<String> requiredDocuments;
}

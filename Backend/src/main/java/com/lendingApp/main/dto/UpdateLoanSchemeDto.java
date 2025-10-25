package com.lendingApp.main.dto;

import java.util.List;

import com.lendingApp.main.enums.InstallmentDurationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLoanSchemeDto {
	
	    private String loanName;

	    private Double minLoanAmount;

	    private Double maxLoanAmount;

	    private Double interestRate;

	    private Integer maxTenure;

	    private Integer minAge;

	    private Integer maxAge;

	    private Double minIncome;

	    private Boolean collateralRequired;

	    private List<LoanCollateralRequestDto> collateralRequirements;

	    private String otherConditions;

	    private InstallmentDurationType installmentDurationType;

	    private Double processingFeeFlat;
	    
	    private Double earlyClosureCharge;

	    private Double defaultPenaltyRate;
}

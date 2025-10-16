package com.lendingApp.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDto {
    private Long loanSchemeId;
    private String loanName;
    private String loanType;
    private Double minLoanAmount;
    private Double maxLoanAmount;
    private Double interestRate;
    private Integer maxTenure;

    private Integer minAge;
    private Integer maxAge;
    private Double minIncome;
    private Boolean collateralRequired;
    private String otherConditions;

    private String installmentDurationType;

    // New fields
    private List<CollateralRequirementDto> collateralRequirements; // Nested list

    private boolean active;  // isActive flag

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Added new fields for fees and penalties
    private Double processingFeeFlat;
    private Double earlyClosureCharge;
    private Double defaultPenaltyRate;
}

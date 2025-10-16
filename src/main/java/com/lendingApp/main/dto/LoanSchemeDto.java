package com.lendingApp.main.dto;

import com.lendingApp.main.enums.InstallmentDurationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanSchemeDto {

    @NotBlank(message = "Loan name is required")
    private String loanName;
    
    @NotNull
    private Long loanTypeId;

    @NotNull(message = "Minimum loan amount is required")
    @Positive(message = "Minimum loan amount must be greater than zero")
    private Double minLoanAmount;

    @NotNull(message = "Maximum loan amount is required")
    @Positive(message = "Maximum loan amount must be greater than zero")
    private Double maxLoanAmount;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Interest rate must be greater than 0")
    private Double interestRate;

    @NotNull(message = "Maximum tenure is required")
    @Positive(message = "Maximum tenure must be greater than zero")
    private Integer maxTenure;

    @NotNull(message = "Minimum age is required")
    @Min(value = 18, message = "Minimum age must be at least 18")
    private Integer minAge;

    @NotNull(message = "Maximum age is required")
    @Min(value = 18, message = "Maximum age must be at least 18")
    private Integer maxAge;

    @NotNull(message = "Minimum income is required")
    @PositiveOrZero(message = "Minimum income cannot be negative")
    private Double minIncome;

    @NotNull(message = "Collateral requirement must be specified")
    private Boolean collateralRequired;

    private List<LoanCollateralRequestDto> collateralRequirements;

    private String otherConditions;

    @NotNull(message = "Installment duration type is required")
    private InstallmentDurationType installmentDurationType;

    // New fields added:
    @NotNull(message = "Processing fee is required")
    @PositiveOrZero(message = "Processing fee cannot be negative")
    private Double processingFeeFlat;

    @NotNull(message = "Early closure charge is required")
    @PositiveOrZero(message = "Early closure charge cannot be negative")
    private Double earlyClosureCharge;

    @NotNull(message = "Default penalty rate is required")
    @PositiveOrZero(message = "Default penalty rate cannot be negative")
    private Double defaultPenaltyRate;
}

package com.lendingApp.main.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {

    @NotNull(message = "Loan amount is required")
    @Positive(message = "Loan amount must be greater than 0")
    private Double loanAmount;

    @NotNull(message = "Tenure is required")
    @Positive(message = "Tenure must be greater than 0")
    private Integer tenure;

    @NotNull(message = "Loan scheme ID is required")
    @Positive(message = "Loan scheme ID must be a positive number")
    private Long loanSchemeId;

    @NotBlank(message = "Occupation is required")
    private String occupation;

    @NotNull(message = "Monthly income is required")
    @Positive(message = "Monthly income must be greater than 0")
    private Double monthlyIncome;
    
    @NotNull(message ="Age is required")
    @Positive(message="Age must be greater than 0")
    private Integer applicantAge;
}

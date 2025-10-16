package com.lendingApp.main.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanEligibilityRequestDto {
	@Min(value = 0)
	private Integer age;
	@Min(value =0)
    private Double minIncome;
}

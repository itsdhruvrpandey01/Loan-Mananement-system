package com.lendingApp.main.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppliedLoanApplications {
	
	private UUID applicationId;
	private String customerEmail;
	private String customerName;
	private String customerMobileNumber;

    private String status; // pending / approved / rejected
    private Double requestedAmount;
    private Integer requestedTenure;
    private Double totalLoanAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
   
    private List<DocumentResponseDto> documentResponseDto;
    
    private LoanResponseDto loanResponse;
    
}

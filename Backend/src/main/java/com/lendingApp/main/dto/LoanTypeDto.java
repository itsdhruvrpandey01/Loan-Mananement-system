package com.lendingApp.main.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoanTypeDto {
    
    @NotBlank(message = "Type name is required")
    private String typeName;

    private String description;
}

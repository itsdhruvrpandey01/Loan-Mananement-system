package com.lendingApp.main.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollateralTypeDto {

    @NotBlank(message = "Type name is required")
    private String typeName;

    private String description;
}

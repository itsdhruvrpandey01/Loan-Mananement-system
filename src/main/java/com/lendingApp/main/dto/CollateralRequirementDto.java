package com.lendingApp.main.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollateralRequirementDto {

    @NotNull(message = "Collateral type is required")
    private Long collateralTypeId; 

    private List<String> requiredDocuments;

}

package com.lendingApp.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollatoralTypeResponseDto {
	private Long collateralTypeId;
	 private String typeName;
	    private String description;
}

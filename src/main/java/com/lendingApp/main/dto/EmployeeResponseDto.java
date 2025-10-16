package com.lendingApp.main.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {
	private UUID eId;
    private String city;
    private String designation;
    private Double salary;
    private String pincode;
}

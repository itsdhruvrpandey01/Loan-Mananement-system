package com.lendingApp.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {
	private String email;
    private String password;
	private String city;
	private String pincode;
    private String designation;
    private Double salary;
}

package com.lendingApp.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
	private String street;
    private String area;
    private String city;
    private String pincode;
}

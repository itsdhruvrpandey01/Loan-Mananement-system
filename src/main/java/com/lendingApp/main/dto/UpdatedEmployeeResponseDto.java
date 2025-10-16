package com.lendingApp.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedEmployeeResponseDto {
	private String firstName;
    private String middleName;
    private String lastName;
    private String mobile;
    private String gender;
}

package com.lendingApp.main.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsUpdateDto {
	private String firstName;
    private String middleName;
    private String lastName;
    private String mobile;
    private String gender;
    private LocalDate createdAt;
}

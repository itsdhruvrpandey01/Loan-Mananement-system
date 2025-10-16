package com.lendingApp.main.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponseDto {
	private UUID userId;

    private String firstName;
    private String middleName;
    private String lastName;
    private String mobile;
    private String email;
    private Boolean isActive;
    private String gender;
    private LocalDate createdAt;
    
    private ProfilePictureResponseDto profilePictureResponseDto;
}

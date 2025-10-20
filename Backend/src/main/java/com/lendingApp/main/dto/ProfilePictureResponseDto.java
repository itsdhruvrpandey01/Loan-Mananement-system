package com.lendingApp.main.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePictureResponseDto {
	private UUID profilePictureId;
	private String picName;
	private String picURL;
}

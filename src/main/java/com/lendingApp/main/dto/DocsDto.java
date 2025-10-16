package com.lendingApp.main.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocsDto {
	private String docName;
	 private String docURL;
	 private LocalDateTime docUploadedAt;
}

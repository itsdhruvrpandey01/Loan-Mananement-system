package com.lendingApp.main.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadDto {
    
    private String docName;
    private String docURL;
    private UUID applicationId;
}
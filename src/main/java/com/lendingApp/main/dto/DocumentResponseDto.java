package com.lendingApp.main.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDto {
    
	private UUID docId;
    private String docName;
    private String documentType;
    private String docURL;
    private LocalDateTime docUploadedAt;
    private UUID customerId;
    private UUID applicationId; // null if not linked to application
}
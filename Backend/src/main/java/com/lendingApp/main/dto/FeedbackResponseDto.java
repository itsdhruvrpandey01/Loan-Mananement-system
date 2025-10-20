package com.lendingApp.main.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponseDto {
    
    private UUID feedbackId;
    private String subject;
    private String message;
    private String status;
    private String response; // Admin/Manager response
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
}
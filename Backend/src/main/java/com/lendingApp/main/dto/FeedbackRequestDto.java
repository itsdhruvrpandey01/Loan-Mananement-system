package com.lendingApp.main.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestDto {
    
    private String subject;
    private String message;
}

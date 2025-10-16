package com.lendingApp.main.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDto {
    
    private String to;
    private String subject;
    private String body;
    private boolean isHtml; // true for HTML emails, false for plain text
}
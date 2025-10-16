package com.lendingApp.main.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    
    private UUID notificationId;
    private String message;
    private String type;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
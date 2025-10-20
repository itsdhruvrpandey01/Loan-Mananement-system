package com.lendingApp.main.service;

import com.lendingApp.main.dto.NotificationDto;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    
    void createNotification(UUID userId, String message, String type);
    
    List<NotificationDto> getAllNotifications(UUID userId);
    
    List<NotificationDto> getUnreadNotifications(UUID userId);
    
    NotificationDto markAsRead(UUID notificationId);
    
    void markAllAsRead(UUID userId);
    
    long getUnreadCount(UUID userId);
}
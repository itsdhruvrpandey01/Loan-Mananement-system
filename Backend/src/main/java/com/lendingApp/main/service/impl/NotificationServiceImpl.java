package com.lendingApp.main.service.impl;

import com.lendingApp.main.dto.NotificationDto;
import com.lendingApp.main.entity.Notification;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.exception.ResourceNotFoundException;
import com.lendingApp.main.repository.NotificationRepository;
import com.lendingApp.main.repository.UserRepository;
import com.lendingApp.main.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createNotification(UUID userId, String message, String type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setType(type);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUser(user);

        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDto> getAllNotifications(UUID userId) {
        List<Notification> notifications = notificationRepository.findByUser_UserIdOrderByCreatedAtDesc(userId);
        return notifications.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getUnreadNotifications(UUID userId) {
        List<Notification> notifications = notificationRepository.findByUser_UserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        return notifications.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public NotificationDto markAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));
        
        notification.setIsRead(true);
        Notification updatedNotification = notificationRepository.save(notification);
        return mapToDto(updatedNotification);
    }

    @Override
    public void markAllAsRead(UUID userId) {
        List<Notification> unreadNotifications = notificationRepository.findByUser_UserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        unreadNotifications.forEach(notification -> notification.setIsRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }

    @Override
    public long getUnreadCount(UUID userId) {
        return notificationRepository.countByUser_UserIdAndIsReadFalse(userId);
    }

    private NotificationDto mapToDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setNotificationId(notification.getNotificationId());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setIsRead(notification.getIsRead());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }
}
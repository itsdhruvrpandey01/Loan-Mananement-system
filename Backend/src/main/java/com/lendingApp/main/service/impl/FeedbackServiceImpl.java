package com.lendingApp.main.service.impl;

import com.lendingApp.main.dto.FeedbackRequestDto;
import com.lendingApp.main.dto.FeedbackResponseDto;
import com.lendingApp.main.entity.Feedback;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.exception.ResourceNotFoundException;
import com.lendingApp.main.repository.FeedbackRepository;
import com.lendingApp.main.repository.UserRepository;
import com.lendingApp.main.service.FeedbackService;
import com.lendingApp.main.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public FeedbackResponseDto submitFeedback(FeedbackRequestDto feedbackRequestDto, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Feedback feedback = new Feedback();
        feedback.setSubject(feedbackRequestDto.getSubject());
        feedback.setMessage(feedbackRequestDto.getMessage());
        feedback.setStatus("OPEN");
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setUser(user);

        Feedback savedFeedback = feedbackRepository.save(feedback);

        // Create notification for user
        notificationService.createNotification(
                userId,
                "Your feedback/query has been submitted successfully",
                "FEEDBACK_SUBMITTED"
        );

        return mapToDto(savedFeedback);
    }

    @Override
    public List<FeedbackResponseDto> getMyFeedbacks(UUID userId) {
        List<Feedback> feedbacks = feedbackRepository.findByUser_UserIdOrderByCreatedAtDesc(userId);
        return feedbacks.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public FeedbackResponseDto getFeedbackById(UUID feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + feedbackId));
        return mapToDto(feedback);
    }

    @Override
    public List<FeedbackResponseDto> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public FeedbackResponseDto updateFeedbackStatus(UUID feedbackId, String status, String response) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + feedbackId));

        feedback.setStatus(status);
        feedback.setResponse(response);
        
        if ("RESOLVED".equals(status) || "CLOSED".equals(status)) {
            feedback.setResolvedAt(LocalDateTime.now());
        }

        Feedback updatedFeedback = feedbackRepository.save(feedback);

        // Notify user about feedback response
        notificationService.createNotification(
                feedback.getUser().getUserId(),
                "Your feedback has been updated: " + status,
                "FEEDBACK_UPDATED"
        );

        return mapToDto(updatedFeedback);
    }

    private FeedbackResponseDto mapToDto(Feedback feedback) {
        FeedbackResponseDto dto = new FeedbackResponseDto();
        dto.setFeedbackId(feedback.getFeedbackId());
        dto.setSubject(feedback.getSubject());
        dto.setMessage(feedback.getMessage());
        dto.setStatus(feedback.getStatus());
        dto.setResponse(feedback.getResponse());
        dto.setCreatedAt(feedback.getCreatedAt());
        dto.setResolvedAt(feedback.getResolvedAt());
        return dto;
    }
}
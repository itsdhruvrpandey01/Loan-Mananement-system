package com.lendingApp.main.service;

import com.lendingApp.main.dto.FeedbackRequestDto;
import com.lendingApp.main.dto.FeedbackResponseDto;
import java.util.List;
import java.util.UUID;

public interface FeedbackService {
    
    FeedbackResponseDto submitFeedback(FeedbackRequestDto feedbackRequestDto, UUID userId);
    
    List<FeedbackResponseDto> getMyFeedbacks(UUID userId);
    
    FeedbackResponseDto getFeedbackById(UUID feedbackId);
    
    List<FeedbackResponseDto> getAllFeedbacks(); // For admin/manager
    
    FeedbackResponseDto updateFeedbackStatus(UUID feedbackId, String status, String response);
}
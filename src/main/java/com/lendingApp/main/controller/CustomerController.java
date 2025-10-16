package com.lendingApp.main.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lendingApp.main.dto.ApplicationDto;
import com.lendingApp.main.dto.ApplicationResponse;
import com.lendingApp.main.dto.DocumentResponseDto;
import com.lendingApp.main.dto.DocumentUploadDto;
import com.lendingApp.main.dto.FeedbackRequestDto;
import com.lendingApp.main.dto.FeedbackResponseDto;
import com.lendingApp.main.dto.InstallmentDto;
import com.lendingApp.main.dto.NotificationDto;
import com.lendingApp.main.dto.PageResponseDto;
import com.lendingApp.main.service.DocumentService;
import com.lendingApp.main.service.FeedbackService;
import com.lendingApp.main.service.InstallmentsSerivce;
import com.lendingApp.main.service.NotificationService;
import com.lendingApp.main.service.UserService;

@RestController
@RequestMapping("/loan-app/customer")
public class CustomerController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private FeedbackService feedbackService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private InstallmentsSerivce installmentsSerivce;
    
    @GetMapping("/{customerId}/loans/application/installments")
    public ResponseEntity<PageResponseDto<InstallmentDto>> getAppliedLoans(@PathVariable UUID customerId,@RequestParam(name="page") int page,@RequestParam(name = "size") int size) { 
    	return ResponseEntity.ok(installmentsSerivce.getUnpaidInstallmentsBeforeMonth(customerId,page,size));
    }
    
    @GetMapping("/{customerId}/loans/application")
    public ResponseEntity<PageResponseDto<ApplicationResponse>> getAppliedLoans(@PathVariable UUID customerId,@RequestParam(required = false) String status,@RequestParam(name="page") int page,@RequestParam(name = "size") int size) { 
    	System.out.println(status);
    	return ResponseEntity.ok(userService.getAllAppliedLoans(customerId,status,page,size));
    }
    
    @PostMapping("/{customerId}/loans/application")
    public ResponseEntity<ApplicationResponse> applyLoans(@PathVariable UUID customerId,@RequestBody ApplicationDto applicationDto) { 
        return ResponseEntity.ok(userService.applyLoan(applicationDto,customerId));
    }
    
    @PutMapping("/loans/application/{applicationId}")
    public ResponseEntity<DocumentResponseDto> uploadRequiredDocuments(
    		@PathVariable UUID applicationId,
		    @RequestParam("files") List<MultipartFile> files,
		    @RequestParam("docTypes") List<String> docTypes) throws IOException{
    			return ResponseEntity.ok(this.userService.uploadDocuments(applicationId, files, docTypes));
    		}

    /**
     * Upload KYC Document
     * POST /loan-app/customer/documents
     */
    @PostMapping("/documents")
    public ResponseEntity<DocumentResponseDto> uploadDocument(
            @RequestBody DocumentUploadDto documentUploadDto,
            @RequestParam UUID customerId) {
        DocumentResponseDto response = documentService.uploadDocument(documentUploadDto, customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all documents for a customer
     * GET /loan-app/customer/documents?customerId=xxx
     */
    @GetMapping("/documents")
    public ResponseEntity<List<DocumentResponseDto>> getDocuments(@RequestParam UUID customerId) {
        List<DocumentResponseDto> documents = documentService.getDocumentsByCustomerId(customerId);
        return ResponseEntity.ok(documents);
    }

    /**
     * Get document by ID
     * GET /loan-app/customer/documents/{docId}
     */
    @GetMapping("/documents/{docId}")
    public ResponseEntity<DocumentResponseDto> getDocumentById(@PathVariable UUID docId) {
        DocumentResponseDto document = documentService.getDocumentById(docId);
        return ResponseEntity.ok(document);
    }

    /**
     * Delete document
     * DELETE /loan-app/customer/documents/{docId}
     */
    @DeleteMapping("/documents/{docId}")
    public ResponseEntity<Map<String, String>> deleteDocument(@PathVariable UUID docId) {
        documentService.deleteDocument(docId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Document deleted successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Get all notifications for logged-in user
     * GET /loan-app/customer/notifications?userId=xxx
     */
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDto>> getNotifications(@RequestParam UUID userId) {
        List<NotificationDto> notifications = notificationService.getAllNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get unread notifications
     * GET /loan-app/customer/notifications/unread?userId=xxx
     */
    @GetMapping("/notifications/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(@RequestParam UUID userId) {
        List<NotificationDto> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get unread notification count
     * GET /loan-app/customer/notifications/unread/count?userId=xxx
     */
    @GetMapping("/notifications/unread/count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@RequestParam UUID userId) {
        long count = notificationService.getUnreadCount(userId);
        Map<String, Long> response = new HashMap<>();
        response.put("unreadCount", count);
        return ResponseEntity.ok(response);
    }

    /**
     * Mark notification as read
     * PUT /loan-app/customer/notifications/{notificationId}/read
     */
    @PutMapping("/notifications/{notificationId}/read")
    public ResponseEntity<NotificationDto> markAsRead(@PathVariable UUID notificationId) {
        NotificationDto notification = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(notification);
    }

    /**
     * Mark all notifications as read
     * PUT /loan-app/customer/notifications/read-all?userId=xxx
     */
    @PutMapping("/notifications/read-all")
    public ResponseEntity<Map<String, String>> markAllAsRead(@RequestParam UUID userId) {
        notificationService.markAllAsRead(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "All notifications marked as read");
        return ResponseEntity.ok(response);
    }

    // ============= FEEDBACK/QUERY APIs =============

    /**
     * Submit feedback or query
     * POST /loan-app/customer/feedback
     */
    @PostMapping("/feedback")
    public ResponseEntity<FeedbackResponseDto> submitFeedback(
            @RequestBody FeedbackRequestDto feedbackRequestDto,
            @RequestParam UUID userId) {
        FeedbackResponseDto response = feedbackService.submitFeedback(feedbackRequestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all feedbacks submitted by customer
     * GET /loan-app/customer/feedback?userId=xxx
     */
    @GetMapping("/feedback")
    public ResponseEntity<List<FeedbackResponseDto>> getMyFeedbacks(@RequestParam UUID userId) {
        List<FeedbackResponseDto> feedbacks = feedbackService.getMyFeedbacks(userId);
        return ResponseEntity.ok(feedbacks);
    }

    /**
     * Get feedback by ID
     * GET /loan-app/customer/feedback/{feedbackId}
     */
    @GetMapping("/feedback/{feedbackId}")
    public ResponseEntity<FeedbackResponseDto> getFeedbackById(@PathVariable UUID feedbackId) {
        FeedbackResponseDto feedback = feedbackService.getFeedbackById(feedbackId);
        return ResponseEntity.ok(feedback);
    }
    
}
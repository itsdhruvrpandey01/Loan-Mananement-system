package com.lendingApp.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lendingApp.main.entity.Installment;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.repository.InstallmentsRepository;
import com.lendingApp.main.repository.UserRepository;
import com.lendingApp.main.service.EmailService;
import com.lendingApp.main.service.impl.InstallmentReminderSchedulers;


/**
 * Test Controller for Email Functionality
 * Use this to test emails manually before relying on scheduler
 * 
 * REMOVE THIS IN PRODUCTION!
 */
@RestController
@RequestMapping("/loan-app/test/email")
@CrossOrigin(origins = "*")
public class EmailTestController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstallmentsRepository installmentsRepository;

    @Autowired
    private InstallmentReminderSchedulers reminderScheduler;

    /**
     * Test 1: Send welcome email
     * GET /loan-app/test/email/welcome?userId=xxx
     */
    @GetMapping("/welcome")
    public ResponseEntity<Map<String, String>> testWelcomeEmail(@RequestParam UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        emailService.sendWelcomeEmail(user);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome email sent to: " + user.getEmail());
        response.put("status", "SUCCESS");
        return ResponseEntity.ok(response);
    }

    /**
     * Test 2: Send installment reminder
     * GET /loan-app/test/email/reminder?installmentId=xxx&days=3
     */
    @GetMapping("/reminder")
    public ResponseEntity<Map<String, String>> testReminderEmail(
            @RequestParam UUID installmentId,
            @RequestParam(defaultValue = "3") int days) {
        
        Installment installment = installmentsRepository.findById(installmentId)
                .orElseThrow(() -> new RuntimeException("Installment not found"));
        
        User user = installment.getApplication().getCustomer().getUser();
        emailService.sendInstallmentReminderEmail(user, installment, days);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Reminder email sent to: " + user.getEmail());
        response.put("installmentId", installmentId.toString());
        response.put("daysUntilDue", String.valueOf(days));
        response.put("status", "SUCCESS");
        return ResponseEntity.ok(response);
    }

    /**
     * Test 3: Send overdue notice
     * GET /loan-app/test/email/overdue?installmentId=xxx&days=2
     */
    @GetMapping("/overdue")
    public ResponseEntity<Map<String, String>> testOverdueEmail(
            @RequestParam UUID installmentId,
            @RequestParam(defaultValue = "2") int days) {
        
        Installment installment = installmentsRepository.findById(installmentId)
                .orElseThrow(() -> new RuntimeException("Installment not found"));
        
        User user = installment.getApplication().getCustomer().getUser();
        emailService.sendOverdueInstallmentEmail(user, installment, days);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Overdue notice sent to: " + user.getEmail());
        response.put("installmentId", installmentId.toString());
        response.put("daysOverdue", String.valueOf(days));
        response.put("status", "SUCCESS");
        return ResponseEntity.ok(response);
    }

    /**
     * Test 4: Send payment confirmation
     * GET /loan-app/test/email/payment?installmentId=xxx
     */
    @GetMapping("/payment")
    public ResponseEntity<Map<String, String>> testPaymentConfirmation(@RequestParam UUID installmentId) {
        Installment installment = installmentsRepository.findById(installmentId)
                .orElseThrow(() -> new RuntimeException("Installment not found"));
        
        User user = installment.getApplication().getCustomer().getUser();
        emailService.sendPaymentConfirmationEmail(user, installment);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Payment confirmation sent to: " + user.getEmail());
        response.put("installmentId", installmentId.toString());
        response.put("status", "SUCCESS");
        return ResponseEntity.ok(response);
    }

    /**
     * Test 5: Manually trigger the scheduler (run all reminders now)
     * GET /loan-app/test/email/trigger-scheduler
     */
//    @GetMapping("/trigger-scheduler")
//    public ResponseEntity<Map<String, String>> triggerScheduler() {
//        reminderScheduler.sendRemindersManually();
//        
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Scheduler executed successfully");
//        response.put("status", "SUCCESS");
//        return ResponseEntity.ok(response);
//    }

    /**
     * Test 6: Check email configuration
     * GET /loan-app/test/email/config
     */
    @GetMapping("/config")
    public ResponseEntity<Map<String, String>> checkConfig() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Email service is configured and ready");
        response.put("status", "SUCCESS");
        response.put("note", "If you see this, Spring Boot successfully loaded email configuration");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{applicationId}/doc-missing")
    public ResponseEntity<String> getMethodName(@PathVariable UUID applicationId,@RequestBody List<String> requiredDocuments) {
        boolean isSend = emailService.sendMissingDocumentsEmail(applicationId, requiredDocuments);
    	if(isSend)
    	return	ResponseEntity.ok("Email Sent Successfully for missing documents");
    	
    	return ResponseEntity.ok("All Documents request were already uploaded");
    	
    }
    
}
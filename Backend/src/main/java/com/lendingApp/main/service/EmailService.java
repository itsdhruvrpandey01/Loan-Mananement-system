package com.lendingApp.main.service;

import java.util.List;
import java.util.UUID;

import com.lendingApp.main.dto.EmailDto;
import com.lendingApp.main.entity.Installment;
import com.lendingApp.main.entity.User;

public interface EmailService {
    
    /**
     * Send a simple email
     */
    void sendEmail(EmailDto emailDto);
    
    /**
     * Send installment reminder email
     */
    void sendInstallmentReminderEmail(User user, Installment installment, int daysUntilDue);
    
    /**
     * Send overdue installment email
     */
    void sendOverdueInstallmentEmail(User user, Installment installment, int daysOverdue);
    
    /**
     * Send payment confirmation email
     */
    void sendPaymentConfirmationEmail(User user, Installment installment);
    
    /**
     * Send welcome email (bonus feature)
     */
    void sendWelcomeEmail(User user);
    
    boolean sendMissingDocumentsEmail(UUID applicationId, List<String> missingDocs);
}
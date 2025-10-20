package com.lendingApp.main.service.impl;

import com.lendingApp.main.entity.Customer;
import com.lendingApp.main.entity.Installment;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.repository.InstallmentsRepository;
import com.lendingApp.main.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Scheduler Service to automatically send installment reminder emails
 * 
 * This runs daily at 9:00 AM and checks:
 * 1. Installments due in 3 days - sends reminder
 * 2. Installments due today - sends reminder
 * 3. Overdue installments - sends urgent notice
 */
@Service
public class InstallmentReminderSchedulers {

//    @Autowired
//    private InstallmentsRepository installmentsRepository;
//
//    @Autowired
//    private EmailService emailService;
//
//    /**
//     * Runs every day at 9:00 AM
//     * Cron expression: "0 0 9 * * ?" 
//     * Format: second minute hour day month weekday
//     * 
//     * For testing, use: @Scheduled(fixedRate = 60000) // Runs every 1 minute
//     */
//    @Scheduled(cron = "0 0 9 * * ?")
//    public void sendInstallmentReminders() {
//        System.out.println("📧 Starting installment reminder scheduler...");
//        
//        LocalDate today = LocalDate.now();
//        
//        // Get all unpaid installments
//        List<Installment> unpaidInstallments = installmentsRepository.findByIsPaidFalse();
//        
//        int remindersSent = 0;
//        int overdueNoticesSent = 0;
//        
//        for (Installment installment : unpaidInstallments) {
//            try {
//                // Get user from customer
//                Customer customer = installment.getApplication().getCustomer();
//                if (customer == null || customer.getUser() == null) {
//                    System.err.println("⚠️  Skipping installment " + installment.getInstallmentId() + " - No user found");
//                    continue;
//                }
//                
//                User user = customer.getUser();
//                LocalDate dueDate = installment.getInstEndDate();
//                long daysUntilDue = ChronoUnit.DAYS.between(today, dueDate);
//                
//                // Case 1: Installment due in 3 days
//                if (daysUntilDue == 3) {
//                    emailService.sendInstallmentReminderEmail(user, installment, 3);
//                    remindersSent++;
//                    System.out.println("✅ Reminder sent (3 days) to: " + user.getEmail());
//                }
//                
//                // Case 2: Installment due today
//                else if (daysUntilDue == 0) {
//                    emailService.sendInstallmentReminderEmail(user, installment, 0);
//                    remindersSent++;
//                    System.out.println("✅ Reminder sent (due today) to: " + user.getEmail());
//                }
//                
//                // Case 3: Installment is overdue
//                else if (daysUntilDue < 0) {
//                    int daysOverdue = Math.abs((int) daysUntilDue);
//                    emailService.sendOverdueInstallmentEmail(user, installment, daysOverdue);
//                    overdueNoticesSent++;
//                    System.out.println("🚨 Overdue notice sent (" + daysOverdue + " days) to: " + user.getEmail());
//                }
//                
//            } catch (Exception e) {
//                System.err.println("❌ Failed to process installment: " + installment.getInstallmentId());
//                e.printStackTrace();
//            }
//        }
//        
//        System.out.println("📊 Scheduler Summary:");
//        System.out.println("   - Reminders sent: " + remindersSent);
//        System.out.println("   - Overdue notices sent: " + overdueNoticesSent);
//        System.out.println("   - Total processed: " + unpaidInstallments.size());
//        System.out.println("✅ Installment reminder scheduler completed!");
//    }
//    
//    /**
//     * Optional: Manual trigger for testing
//     * Can be called from a controller endpoint
//     */
//    public void sendRemindersManually() {
//        System.out.println("🔧 Manual reminder trigger initiated...");
//        sendInstallmentReminders();
//    }
}
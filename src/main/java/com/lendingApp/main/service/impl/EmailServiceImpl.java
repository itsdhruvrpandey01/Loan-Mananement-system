package com.lendingApp.main.service.impl;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lendingApp.main.dto.EmailDto;
import com.lendingApp.main.entity.Installment;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.service.ApplicationService;
import com.lendingApp.main.service.EmailService;
import com.lendingApp.main.service.NotificationService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private ApplicationService applicationService;

    @Value("${app.email.from}")
    private String fromEmail;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

    @Override
    public void sendEmail(EmailDto emailDto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(emailDto.getTo());
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getBody(), emailDto.isHtml());

            mailSender.send(message);
            
            System.out.println("✅ Email sent successfully to: " + emailDto.getTo());
            
        } catch (MessagingException e) {
            System.err.println("❌ Failed to send email to: " + emailDto.getTo());
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public void sendInstallmentReminderEmail(User user, Installment installment, int daysUntilDue) {
        String subject = "⏰ Installment Payment Reminder - Due in " + daysUntilDue + " days";
        String body = buildInstallmentReminderEmailBody(user, installment, daysUntilDue);

        EmailDto emailDto = EmailDto.builder()
                .to(user.getEmail())
                .subject(subject)
                .body(body)
                .isHtml(true)
                .build();

        sendEmail(emailDto);

        // Create in-app notification
        notificationService.createNotification(
                user.getUserId(),
                "Your installment of " + CURRENCY_FORMATTER.format(installment.getInstAmt()) + 
                " is due in " + daysUntilDue + " days",
                "INSTALLMENT_REMINDER"
        );
    }

    @Override
    public void sendOverdueInstallmentEmail(User user, Installment installment, int daysOverdue) {
        String subject = "🚨 URGENT: Overdue Installment Payment - " + daysOverdue + " days overdue";
        String body = buildOverdueInstallmentEmailBody(user, installment, daysOverdue);

        EmailDto emailDto = EmailDto.builder()
                .to(user.getEmail())
                .subject(subject)
                .body(body)
                .isHtml(true)
                .build();

        sendEmail(emailDto);

        // Create in-app notification
        notificationService.createNotification(
                user.getUserId(),
                "URGENT: Your installment of " + CURRENCY_FORMATTER.format(installment.getInstAmt()) + 
                " is " + daysOverdue + " days overdue. Fine amount: " + CURRENCY_FORMATTER.format(installment.getFineAmt()),
                "INSTALLMENT_OVERDUE"
        );
    }

    @Override
    public void sendPaymentConfirmationEmail(User user, Installment installment) {
        String subject = "✅ Payment Confirmation - Installment Paid Successfully";
        String body = buildPaymentConfirmationEmailBody(user, installment);

        EmailDto emailDto = EmailDto.builder()
                .to(user.getEmail())
                .subject(subject)
                .body(body)
                .isHtml(true)
                .build();

        sendEmail(emailDto);

        // Create in-app notification
        notificationService.createNotification(
                user.getUserId(),
                "Payment of " + CURRENCY_FORMATTER.format(installment.getInstAmt()) + " received successfully",
                "PAYMENT_CONFIRMED"
        );
    }

    @Override
    public void sendWelcomeEmail(User user) {
        String subject = "🎉 Welcome to LendingApp!";
        String body = buildWelcomeEmailBody(user);

        EmailDto emailDto = EmailDto.builder()
                .to(user.getEmail())
                .subject(subject)
                .body(body)
                .isHtml(true)
                .build();

        sendEmail(emailDto);
    }

    // ============== EMAIL BODY BUILDERS ==============

    private String buildInstallmentReminderEmailBody(User user, Installment installment, int daysUntilDue) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; }
                    .content { background-color: #f9f9f9; padding: 20px; margin: 20px 0; }
                    .installment-details { background-color: white; padding: 15px; margin: 15px 0; border-left: 4px solid #4CAF50; }
                    .amount { font-size: 24px; font-weight: bold; color: #4CAF50; }
                    .footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }
                    .button { background-color: #4CAF50; color: white; padding: 12px 30px; text-decoration: none; display: inline-block; margin: 10px 0; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>⏰ Installment Payment Reminder</h1>
                    </div>
                    <div class="content">
                        <p>Dear %s %s,</p>
                        <p>This is a friendly reminder that your installment payment is due in <strong>%d days</strong>.</p>
                        
                        <div class="installment-details">
                            <h3>Payment Details:</h3>
                            <p><strong>Installment Amount:</strong> <span class="amount">%s</span></p>
                            <p><strong>Due Date:</strong> %s</p>
                            <p><strong>Installment ID:</strong> %s</p>
                        </div>
                        
                        <p>Please ensure timely payment to avoid any late fees.</p>
                        
                        <center>
                            <a href="#" class="button">Pay Now</a>
                        </center>
                    </div>
                    <div class="footer">
                        <p>This is an automated email. Please do not reply.</p>
                        <p>© 2025 LendingApp. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                user.getFirstName(),
                user.getLastName(),
                daysUntilDue,
                CURRENCY_FORMATTER.format(installment.getInstAmt()),
                installment.getInstEndDate().format(DATE_FORMATTER),
                installment.getInstallmentId()
            );
    }

    private String buildOverdueInstallmentEmailBody(User user, Installment installment, int daysOverdue) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #f44336; color: white; padding: 20px; text-align: center; }
                    .content { background-color: #fff3cd; padding: 20px; margin: 20px 0; border: 2px solid #f44336; }
                    .installment-details { background-color: white; padding: 15px; margin: 15px 0; border-left: 4px solid #f44336; }
                    .amount { font-size: 24px; font-weight: bold; color: #f44336; }
                    .fine { color: #f44336; font-weight: bold; }
                    .footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }
                    .button { background-color: #f44336; color: white; padding: 12px 30px; text-decoration: none; display: inline-block; margin: 10px 0; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🚨 URGENT: Overdue Payment</h1>
                    </div>
                    <div class="content">
                        <p>Dear %s %s,</p>
                        <p><strong>IMPORTANT:</strong> Your installment payment is now <strong class="fine">%d days overdue</strong>.</p>
                        
                        <div class="installment-details">
                            <h3>Payment Details:</h3>
                            <p><strong>Installment Amount:</strong> <span class="amount">%s</span></p>
                            <p><strong>Fine Amount:</strong> <span class="fine">%s</span></p>
                            <p><strong>Total Amount Due:</strong> <span class="amount">%s</span></p>
                            <p><strong>Original Due Date:</strong> %s</p>
                            <p><strong>Installment ID:</strong> %s</p>
                        </div>
                        
                        <p><strong>Please make the payment immediately to avoid further penalties.</strong></p>
                        
                        <center>
                            <a href="#" class="button">Pay Now</a>
                        </center>
                    </div>
                    <div class="footer">
                        <p>For assistance, contact our support team.</p>
                        <p>© 2025 LendingApp. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                user.getFirstName(),
                user.getLastName(),
                daysOverdue,
                CURRENCY_FORMATTER.format(installment.getInstAmt()),
                CURRENCY_FORMATTER.format(installment.getFineAmt()),
                CURRENCY_FORMATTER.format(installment.getInstAmt() + installment.getFineAmt()),
                installment.getInstEndDate().format(DATE_FORMATTER),
                installment.getInstallmentId()
            );
    }

    private String buildPaymentConfirmationEmailBody(User user, Installment installment) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; }
                    .content { background-color: #f9f9f9; padding: 20px; margin: 20px 0; }
                    .payment-details { background-color: white; padding: 15px; margin: 15px 0; border-left: 4px solid #4CAF50; }
                    .amount { font-size: 24px; font-weight: bold; color: #4CAF50; }
                    .footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>✅ Payment Successful!</h1>
                    </div>
                    <div class="content">
                        <p>Dear %s %s,</p>
                        <p>We have successfully received your installment payment. Thank you!</p>
                        
                        <div class="payment-details">
                            <h3>Payment Confirmation:</h3>
                            <p><strong>Amount Paid:</strong> <span class="amount">%s</span></p>
                            <p><strong>Payment Date:</strong> %s</p>
                            <p><strong>Installment ID:</strong> %s</p>
                            <p><strong>Status:</strong> <span style="color: #4CAF50;">PAID ✓</span></p>
                        </div>
                        
                        <p>Your payment has been recorded successfully.</p>
                    </div>
                    <div class="footer">
                        <p>Thank you for your timely payment!</p>
                        <p>© 2025 LendingApp. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                user.getFirstName(),
                user.getLastName(),
                CURRENCY_FORMATTER.format(installment.getInstAmt()),
                installment.getPaidDate() != null ? installment.getPaidDate().format(DATE_FORMATTER) : "N/A",
                installment.getInstallmentId()
            );
    }

    private String buildWelcomeEmailBody(User user) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #2196F3; color: white; padding: 30px; text-align: center; }
                    .content { background-color: #f9f9f9; padding: 20px; margin: 20px 0; }
                    .footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🎉 Welcome to LendingApp!</h1>
                    </div>
                    <div class="content">
                        <p>Dear %s %s,</p>
                        <p>Welcome to LendingApp! We're excited to have you on board.</p>
                        <p>Your account has been successfully created. You can now apply for loans and manage your applications.</p>
                        <p>If you have any questions, feel free to reach out to our support team.</p>
                    </div>
                    <div class="footer">
                        <p>© 2025 LendingApp. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(user.getFirstName(), user.getLastName());
    }
    
    @Override
    public boolean sendMissingDocumentsEmail(UUID applicationId, List<String> missingDocs) {
        if (missingDocs == null || missingDocs.isEmpty()) return false; // No missing docs

        String subject = "📄 Missing Documents for Your Loan Application";
        User user = this.applicationService.findApplicationById(applicationId).getCustomer().getUser();
        String body = buildMissingDocumentsEmailBody(user, applicationId, missingDocs);

        EmailDto emailDto = EmailDto.builder()
                .to(user.getEmail())
                .subject(subject)
                .body(body)
                .isHtml(true)
                .build();

        sendEmail(emailDto);

        // Also send an in-app notification
        notificationService.createNotification(
                user.getUserId(),
                "You have " + missingDocs.size() + " missing documents for application " + applicationId,
                "MISSING_DOCUMENTS"
        );
        return true;
    }
    private String buildMissingDocumentsEmailBody(User user, UUID applicationId, List<String> missingDocs) {
        StringBuilder docListHtml = new StringBuilder();
        for (String doc : missingDocs) {
            docListHtml.append("<li>").append(doc).append("</li>");
        }

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #ff9800; color: white; padding: 20px; text-align: center; }
                    .content { background-color: #fff3cd; padding: 20px; margin: 20px 0; border-left: 5px solid #ff9800; }
                    .footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }
                    ul { margin: 10px 0 0 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>📄 Missing Documents</h1>
                    </div>
                    <div class="content">
                        <p>Dear %s %s,</p>
                        <p>We noticed that your loan application <strong>%s</strong> is missing the following required documents:</p>
                        <ul>
                            %s
                        </ul>
                        <p>Please upload the missing documents as soon as possible to avoid delays in processing your application.</p>
                    </div>
                    <div class="footer">
                        <p>This is an automated message from LendingApp.</p>
                        <p>© 2025 LendingApp. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                user.getFirstName(),
                user.getLastName(),
                applicationId,
                docListHtml.toString()
            );
    }

}
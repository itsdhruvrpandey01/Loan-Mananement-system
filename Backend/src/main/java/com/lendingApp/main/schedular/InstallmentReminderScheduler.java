package com.lendingApp.main.schedular;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lendingApp.main.entity.Installment;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.enums.InstallmentStatus;
import com.lendingApp.main.repository.InstallmentsRepository;
import com.lendingApp.main.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstallmentReminderScheduler {
	
	private final InstallmentsRepository installmentRepository;
	private final EmailService emailService;
	
	@Scheduled(cron = "0 8 21 * * ?") // Runs daily at 8:00 AM
    public void sendInstallmentReminders() {
        int daysUntilDue = 3;
        LocalDate targetDate = LocalDate.now().plusDays(daysUntilDue);

        List<Installment> upcomingInstallments = installmentRepository
                .findByStatusAndInstEndDate(InstallmentStatus.UPCOMING,targetDate);

        for (Installment installment : upcomingInstallments) {
            User user = installment.getApplication().getCustomer().getUser(); // Adjust this line if needed

            emailService.sendInstallmentReminderEmail(user, installment, daysUntilDue);
        }
    }
}

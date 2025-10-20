package com.lendingApp.main.schedular;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
public class OverDueSchedular {
	private final EmailService emailService;
	private final InstallmentsRepository installmentRepository;

	@Scheduled(cron = "0 52 21 * * ?")
	public void sendOverDueEmail() {
		LocalDate today = LocalDate.now();
		List<Installment> overdueInstallments = installmentRepository
		        .findByStatus(InstallmentStatus.OVERDUE);
		    for (Installment installment : overdueInstallments) {
		    	LocalDate dueDate = installment.getInstEndDate();
		    	int daysOverdue = (int)ChronoUnit.DAYS.between(dueDate, today);
		        User user = installment.getApplication().getCustomer().getUser();
		        emailService.sendOverdueInstallmentEmail(user, installment, daysOverdue);
		    }
	}

}

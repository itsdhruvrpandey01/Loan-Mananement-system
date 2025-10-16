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
public class FIneSchedular {
	private final InstallmentsRepository installmentRepository;
	private final EmailService emailService;
	@Scheduled(cron = "0 49 21 * * ?")
	public void applyFineToOverdueInstallments() {
	    

	    // ✅ Only get installments where due date has passed
	    List<Installment> overdueInstallments = installmentRepository
	        .findByStatusAndInstEndDateBefore(InstallmentStatus.UPCOMING, LocalDate.now());

	    for (Installment installment : overdueInstallments) {
	        double currentFine = installment.getFineAmt() != null ? installment.getFineAmt() : 0.0;
	        double fineToAdd = installment.getInstAmt() * 0.02;
	        double updatedFine = currentFine + fineToAdd;

	        installment.setFineAmt(updatedFine);
	        installment.setStatus(InstallmentStatus.OVERDUE);
	        installmentRepository.save(installment);
	        User user = installment.getApplication().getCustomer().getUser();
	        this.emailService.sendOverdueInstallmentEmail(user, installment, 0);
	    }

	}

}

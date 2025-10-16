package com.lendingApp.main.helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.lendingApp.main.entity.Application;
import com.lendingApp.main.entity.Installment;
import com.lendingApp.main.entity.LoanScheme;
import com.lendingApp.main.enums.InstallmentDurationType;
import com.lendingApp.main.enums.InstallmentStatus;

public class InstallmentCreation {
	public static List<Installment> generateInstallments(Application application) {
        List<Installment> installments = new ArrayList<>();

        LoanScheme scheme = application.getLoanRequirement();
        if (scheme == null) throw new IllegalArgumentException("LoanScheme is missing in Application");

        double principal = application.getTotalLoanAmount();
        int totalTenureMonths = application.getRequestedTenure(); // always in months

        // Fetch from scheme
        double annualInterestRate = scheme.getInterestRate();
        InstallmentDurationType durationType = scheme.getInstallmentDurationType();

        int stepMonths;
        switch (durationType) {
            case MONTHLY -> stepMonths = 1;
            case QUARTERLY -> stepMonths = 3;
            case YEARLY -> stepMonths = 12;
            default -> throw new IllegalArgumentException("Unsupported installment duration type: " + durationType);
        }

        int numberOfInstallments = totalTenureMonths / stepMonths;
        double periodicRate = (annualInterestRate / 12 / 100) * stepMonths;

        // EMI formula adapted to periodic rate
        double emi = (principal * periodicRate * Math.pow(1 + periodicRate, numberOfInstallments)) /
                     (Math.pow(1 + periodicRate, numberOfInstallments) - 1);

        LocalDate startDate = LocalDate.now();

        for (int i = 0; i < numberOfInstallments; i++) {
            LocalDate instStart = startDate.plusMonths(i * stepMonths);
            LocalDate instEnd = instStart.plusMonths(stepMonths).minusDays(1);

            Installment installment = new Installment();
            installment.setInstAmt(Math.round(emi * 100.0) / 100.0);
            installment.setInstStartDate(instStart);
            installment.setInstEndDate(instEnd);
            installment.setStatus(InstallmentStatus.UPCOMING);
            installment.setFineAmt(0.0);
            installment.setPaidDate(null);
            installment.setApplication(application); // FK link

            installments.add(installment);
        }

        return installments;
    }
}

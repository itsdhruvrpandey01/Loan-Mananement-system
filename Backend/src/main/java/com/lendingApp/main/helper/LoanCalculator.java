package com.lendingApp.main.helper;

import org.springframework.stereotype.Component;

@Component
public class LoanCalculator {
    public Double calculateEMI(double principal, double annualInterestRate, int tenureYears) {
        double monthlyInterestRate = annualInterestRate / (12 * 100); // Converts annual rate to monthly and percentage to decimal
        int totalMonths = tenureYears * 12;

        double emi;

        if (monthlyInterestRate == 0) {
            // If interest rate is 0, EMI is simply principal / months
            emi = principal / totalMonths;
        } else {
            // EMI formula
            emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalMonths)) /
                  (Math.pow(1 + monthlyInterestRate, totalMonths) - 1);
        }

        return emi;
    }
    public Double calculateTotalAmount(double principal, double annualInterestRate, int tenureYears) {
        double emi = calculateEMI(principal, annualInterestRate, tenureYears);
        int totalMonths = tenureYears * 12;
        return emi * totalMonths;
    }
}

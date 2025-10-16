package com.lendingApp.main.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID applicationId;

    private String status; // pending / approved / rejected
    private String rejectionReason;
    private Integer applicantAge;
    private Double requestedAmount;
    private Integer requestedTenure;
    private Double totalLoanAmount;
    
    private String occupation;
    private Double monthlyIncome;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "loan_scheme_id", nullable = false)
    private LoanScheme loanRequirement;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval
    = true)
    private List<Document> documents;
    
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval
    = true)
    private List<Installment> installments;
    
    @ManyToOne
    @JoinColumn(name = "e_id")  // <-- Add this line
    private Employee assignedManager;
}

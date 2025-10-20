package com.lendingApp.main.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.lendingApp.main.enums.InstallmentDurationType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan_scheme")
public class LoanScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_scheme_id")
    private Long loanSchemeId;

    private String loanName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_type_id", nullable = false)
    private LoanTypeEntity loanType;
    
    private Double minLoanAmount;
    private Double maxLoanAmount;
    private Double interestRate;
    private Integer maxTenure;

    private Integer minAge;
    private Integer maxAge;
    private Double minIncome;
    private Boolean collateralRequired;
    private String otherConditions;

    @Enumerated(EnumType.STRING)
    private InstallmentDurationType installmentDurationType; // monthly/quarterly/yearly

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean isActive;
    private Double processingFeeFlat;
    private Double earlyClosureCharge;
    private Double defaultPenaltyRate;

    @OneToMany(mappedBy = "loanScheme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanSchemeCollateralRequirement> collateralRequirements;

    @OneToMany(mappedBy = "loanRequirement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

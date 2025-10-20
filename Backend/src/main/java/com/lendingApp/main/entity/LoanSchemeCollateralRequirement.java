package com.lendingApp.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loan_scheme_collateral_requirement")
public class LoanSchemeCollateralRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_scheme_id", nullable = false)
    private LoanScheme loanScheme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collateral_type_id", nullable = false)
    private CollateralTypeEntity collateralType;

    @ElementCollection
    @CollectionTable(
        name = "collateral_required_documents",
        joinColumns = @JoinColumn(name = "collateral_requirement_id")
    )
    @Column(name = "document")
    private List<String> requiredDocuments;
}

package com.lendingApp.main.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.lendingApp.main.enums.InstallmentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "installment")
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID installmentId;
    private Double instAmt;
    private LocalDate instStartDate;
    private LocalDate instEndDate;
    @Enumerated(EnumType.STRING)
    private InstallmentStatus status;
    private LocalDate paidDate;
    private Double fineAmt;
    
    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;
}

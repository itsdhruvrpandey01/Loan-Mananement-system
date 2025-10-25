package com.lendingApp.main.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;

    private UUID installmentId; // Foreign key reference (not JPA relationship for simplicity)

    private Double amountPaid;

    private String paymentStatus; // e.g., "SUCCESS", "FAILED"

    private String paymentMethod; // e.g., "Stripe"

    private String stripePaymentId; // Stripe charge/session ID

    private LocalDateTime paymentDate;

    private String remarks;
}


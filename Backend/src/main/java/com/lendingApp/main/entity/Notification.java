package com.lendingApp.main.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID notificationId;
    
    @Column(nullable = false)
    private String message;
    
    @Column(nullable = false)
    private String type; // APPLICATION_APPROVED, APPLICATION_REJECTED, DOCUMENT_UPLOADED, INSTALLMENT_DUE, etc.
    
    @Column(nullable = false)
    private Boolean isRead = false;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
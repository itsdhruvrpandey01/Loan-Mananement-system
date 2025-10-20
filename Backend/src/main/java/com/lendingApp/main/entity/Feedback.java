package com.lendingApp.main.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID feedbackId;
    
    @Column(nullable = false)
    private String subject;
    
    @Column(nullable = false, length = 1000)
    private String message;
    
    @Column(nullable = false)
    private String status; // OPEN, IN_PROGRESS, RESOLVED, CLOSED
    
    private String response; // Admin/Manager response
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime resolvedAt;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
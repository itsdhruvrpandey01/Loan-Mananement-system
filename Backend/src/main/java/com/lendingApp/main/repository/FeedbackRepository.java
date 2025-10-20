package com.lendingApp.main.repository;

import com.lendingApp.main.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    
    List<Feedback> findByUser_UserIdOrderByCreatedAtDesc(UUID userId);
    
    List<Feedback> findByStatusOrderByCreatedAtDesc(String status);
}
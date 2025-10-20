package com.lendingApp.main.repository;

import com.lendingApp.main.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    
    List<Document> findByCustomer_CustomerId(UUID customerId);
    
    List<Document> findByApplication_ApplicationId(UUID applicationId);
}
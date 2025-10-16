package com.lendingApp.main.service;

import com.lendingApp.main.dto.DocumentResponseDto;
import com.lendingApp.main.dto.DocumentUploadDto;

import java.util.List;
import java.util.UUID;

public interface DocumentService {
    
    DocumentResponseDto uploadDocument(DocumentUploadDto documentUploadDto, UUID customerId);
    
    List<DocumentResponseDto> getDocumentsByCustomerId(UUID customerId);
    
    List<DocumentResponseDto> getDocumentsByApplicationId(UUID applicationId);
    
    DocumentResponseDto getDocumentById(UUID docId);
    
    void deleteDocument(UUID docId);
}
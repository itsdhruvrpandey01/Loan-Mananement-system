package com.lendingApp.main.service.impl;

import com.lendingApp.main.dto.DocumentResponseDto;
import com.lendingApp.main.dto.DocumentUploadDto;
import com.lendingApp.main.entity.Application;
import com.lendingApp.main.entity.Customer;
import com.lendingApp.main.entity.Document;
import com.lendingApp.main.exception.ResourceNotFoundException;
import com.lendingApp.main.repository.ApplicationRepository;
import com.lendingApp.main.repository.CustomerRepository;
import com.lendingApp.main.repository.DocumentRepository;
import com.lendingApp.main.service.DocumentService;
import com.lendingApp.main.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public DocumentResponseDto uploadDocument(DocumentUploadDto documentUploadDto, UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        Document document = new Document();
        document.setDocName(documentUploadDto.getDocName());
        document.setDocURL(documentUploadDto.getDocURL());
        document.setDocUploadedAt(LocalDateTime.now());
        document.setCustomer(customer);

        // If document is linked to an application
        if (documentUploadDto.getApplicationId() != null) {
            Application application = applicationRepository.findById(documentUploadDto.getApplicationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + documentUploadDto.getApplicationId()));
            document.setApplication(application);
        }

        Document savedDocument = documentRepository.save(document);

        // Create notification
        notificationService.createNotification(
                customer.getUser().getUserId(),
                "Document '" + documentUploadDto.getDocName() + "' uploaded successfully",
                "DOCUMENT_UPLOADED"
        );

        return mapToDto(savedDocument);
    }

    @Override
    public List<DocumentResponseDto> getDocumentsByCustomerId(UUID customerId) {
        List<Document> documents = documentRepository.findByCustomer_CustomerId(customerId);
        return documents.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<DocumentResponseDto> getDocumentsByApplicationId(UUID applicationId) {
        List<Document> documents = documentRepository.findByApplication_ApplicationId(applicationId);
        return documents.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public DocumentResponseDto getDocumentById(UUID docId) {
        Document document = documentRepository.findById(docId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + docId));
        return mapToDto(document);
    }

    @Override
    public void deleteDocument(UUID docId) {
        Document document = documentRepository.findById(docId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + docId));
        documentRepository.delete(document);
    }

    private DocumentResponseDto mapToDto(Document document) {
        DocumentResponseDto dto = new DocumentResponseDto();
        dto.setDocId(document.getDocId());
        dto.setDocName(document.getDocName());
        dto.setDocURL(document.getDocURL());
        dto.setDocUploadedAt(document.getDocUploadedAt());
        dto.setCustomerId(document.getCustomer().getCustomerId());
        dto.setApplicationId(document.getApplication() != null ? document.getApplication().getApplicationId() : null);
        return dto;
    }
}
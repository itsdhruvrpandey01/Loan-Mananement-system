package com.lendingApp.main.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lendingApp.main.dto.ApplicationResponse;
import com.lendingApp.main.dto.AppliedLoanApplications;
import com.lendingApp.main.dto.DocumentResponseDto;
import com.lendingApp.main.dto.InstallmentDto;
import com.lendingApp.main.dto.LoanResponseDto;
import com.lendingApp.main.entity.Application;
import com.lendingApp.main.entity.Document;
import com.lendingApp.main.entity.Installment;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.exception.ResourceNotFoundException;
import com.lendingApp.main.exception.UserNotFoundException;
import com.lendingApp.main.helper.InstallmentCreation;
import com.lendingApp.main.repository.ApplicationRepository;
import com.lendingApp.main.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private ModelMapper model;

    // @Override
    // public List<Application> getAllRequestedLoans() {
    //     return applicationRepository.findByStatus("PENDING");
    // }

    // @Override
    // public List<Application> getRequestedLoansByPhone(String phoneNumber) {
    //     return applicationRepository.findByCustomer_PhoneNumberAndStatus(phoneNumber, "PENDING");
    // }

    @Override
    public ApplicationResponse approveLoan(UUID id) {
        Application app = applicationRepository.findById(id).orElseThrow();

        // Update application status
        app.setStatus("APPROVED");
        app.setUpdatedAt(LocalDateTime.now());

        // Clear old installments safely
        if (app.getInstallments() != null) {
            app.getInstallments().clear(); // Mark old ones for deletion
        }

        // Generate new installments
        List<Installment> generatedInstallments = InstallmentCreation.generateInstallments(app);

        // Set the application in each installment
        for (Installment installment : generatedInstallments) {
            installment.setApplication(app); // 🔁 Maintain bidirectional mapping
        }

        // Add new installments (don't replace the list object)
        app.getInstallments().addAll(generatedInstallments);

        // Save application with new installments
        Application savedApplication = applicationRepository.save(app);

        // Map to response DTO
        ApplicationResponse applicationResponse = model.map(savedApplication, ApplicationResponse.class);
        List<InstallmentDto> installmentDtos = new ArrayList<>();
        for (Installment installment : savedApplication.getInstallments()) {
            InstallmentDto installmentDto = model.map(installment, InstallmentDto.class);
            installmentDtos.add(installmentDto);
        }
        applicationResponse.setInstallmentDtos(installmentDtos);

        return applicationResponse;
    }


    @Override
    public ApplicationResponse rejectLoan(UUID id, String reason) {
        Application app = applicationRepository.findById(id).orElseThrow();
        app.setStatus("REJECTED");
        app.setRejectionReason(reason);
        app.setUpdatedAt(LocalDateTime.now());
        return model.map(applicationRepository.save(app),ApplicationResponse.class);
    }


	@Override
	public List<String> getMissingDocuments(UUID applicationID) {
		Application application = applicationRepository.findById(applicationID)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        // Get required documents from loan scheme's collateral requirements
        Set<String> requiredDocuments = application.getLoanRequirement().getCollateralRequirements().stream()
            .flatMap(req -> req.getRequiredDocuments().stream())
            .collect(Collectors.toSet());

        // Get uploaded document types
        Set<String> uploadedDocuments = application.getDocuments().stream()
            .map(Document::getDocumentType)
            .collect(Collectors.toSet());

        // Find missing documents
        List<String> missingDocuments = requiredDocuments.stream()
            .filter(doc -> !uploadedDocuments.contains(doc))
            .collect(Collectors.toList());

        return missingDocuments;
	}


	@Override
	public Application findApplicationById(UUID applicationId) {
		
		return this.applicationRepository.findById(applicationId).orElseThrow(()->new UserNotFoundException("no application with id :"+applicationId));
	}


	@Override
	public AppliedLoanApplications getAppliedLoanById(UUID applicationID) {
		Application application = findApplicationById(applicationID);
		AppliedLoanApplications appliedApplication = model.map(application,AppliedLoanApplications.class);
		appliedApplication.setDocumentResponseDto(mapDocument(application.getDocuments()));
		appliedApplication.setLoanResponse(model.map(application.getLoanRequirement(),LoanResponseDto.class));
		User user = application.getCustomer().getUser();
		appliedApplication.setCustomerEmail(user.getEmail());
		appliedApplication.setCustomerMobileNumber(user.getMobile());
		appliedApplication.setCustomerName(user.getFirstName()+" "+user.getLastName());
		return appliedApplication;
	}
	private List<DocumentResponseDto> mapDocument(List<Document>docs){
		List<DocumentResponseDto> documentResponseDtos = new ArrayList<>();
		for(Document doc:docs) {
			documentResponseDtos.add(model.map(doc, DocumentResponseDto.class));
		}
		return documentResponseDtos;
	}
	
	
}

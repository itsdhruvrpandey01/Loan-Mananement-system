package com.lendingApp.main.service;

import com.lendingApp.main.dto.ApplicationResponse;
import com.lendingApp.main.entity.Application;
import java.util.List;
import java.util.UUID;

public interface ApplicationService {
    // List<Application> getAllRequestedLoans();
    // List<Application> getRequestedLoansByPhone(String phoneNumber);
	ApplicationResponse approveLoan(UUID id);
	ApplicationResponse rejectLoan(UUID id, String reason);
	List<String> getMissingDocuments(UUID applicationID);
	Application findApplicationById(UUID applicationID);
}

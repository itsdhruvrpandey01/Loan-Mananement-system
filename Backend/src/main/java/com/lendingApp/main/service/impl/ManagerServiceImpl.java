package com.lendingApp.main.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lendingApp.main.dto.AppliedLoanApplications;
import com.lendingApp.main.dto.DocumentResponseDto;
import com.lendingApp.main.dto.LoanResponseDto;
import com.lendingApp.main.entity.Application;
import com.lendingApp.main.entity.Document;
import com.lendingApp.main.entity.Employee;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.repository.ApplicationRepository;
import com.lendingApp.main.repository.EmployeeRepository;
import com.lendingApp.main.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Employee assignManagerToApplication(User user) {
		String userCity = user.getAddress().getCity();
        String userPincode = user.getAddress().getPincode();

        // Step 1: Try to find manager by city
        Optional<Employee> cityMatch = employeeRepository.findFirstByCityIgnoreCaseAndDesignation(userCity, "Loan Manager");
        if (cityMatch.isPresent()) {
            return cityMatch.get();
        }

        // Step 2: If city not matched, find manager by pincode
        List<Employee> allManagers = employeeRepository.findByDesignation("Loan Manager");
        Employee nearestManager = findNearestManagerByPincode(userPincode, allManagers);

        if (nearestManager != null) {
            return nearestManager;
        }

        throw new RuntimeException("No manager found for the user's location.");
    }

    private Employee findNearestManagerByPincode(String userPincode, List<Employee> managers) {
        int userPin = Integer.parseInt(userPincode);
        Employee closestManager = null;
        int minDifference = Integer.MAX_VALUE;

        for (Employee manager : managers) {
            String managerPin = manager.getPincode();
                int managerPinInt = Integer.parseInt(managerPin);
                int diff = Math.abs(userPin - managerPinInt);
                if (diff < minDifference) {
                    minDifference = diff;
                    closestManager = manager;
            }
        }

        return closestManager;
    }

	@Override
	public List<AppliedLoanApplications> getAllLoansAssignToManager(UUID managerId) {
		List<Application> applications = applicationRepository.findByAssignedManager_eId(managerId);
		List<AppliedLoanApplications> allLoanApplication = new ArrayList<>();
		for(Application application:applications) {
			AppliedLoanApplications appliedApplication = mapper.map(application,AppliedLoanApplications.class);
			appliedApplication.setDocumentResponseDto(mapDocument(application.getDocuments()));
			appliedApplication.setLoanResponse(mapper.map(application.getLoanRequirement(),LoanResponseDto.class));
			allLoanApplication.add(appliedApplication);
		}
		return allLoanApplication;
	}
	private List<DocumentResponseDto> mapDocument(List<Document>docs){
		List<DocumentResponseDto> documentResponseDtos = new ArrayList<>();
		for(Document doc:docs) {
			documentResponseDtos.add(mapper.map(doc, DocumentResponseDto.class));
		}
		return documentResponseDtos;
	}

}

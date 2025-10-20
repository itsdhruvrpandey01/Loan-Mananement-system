package com.lendingApp.main.service;

import java.util.List;
import java.util.UUID;

import com.lendingApp.main.dto.AppliedLoanApplications;
import com.lendingApp.main.entity.Employee;
import com.lendingApp.main.entity.User;

public interface ManagerService {
	Employee assignManagerToApplication(User user);
	List<AppliedLoanApplications> getAllLoansAssignToManager(UUID managerId);
}
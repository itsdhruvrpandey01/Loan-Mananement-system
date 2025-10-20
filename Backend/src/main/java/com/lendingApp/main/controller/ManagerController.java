package com.lendingApp.main.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lendingApp.main.dto.AddressDto;
import com.lendingApp.main.dto.AddressResponseDto;
import com.lendingApp.main.dto.ApplicationResponse;
import com.lendingApp.main.dto.AppliedLoanApplications;
import com.lendingApp.main.dto.EmployeeResponseDto;
import com.lendingApp.main.dto.UpdateEmployeeDetailsDto;
import com.lendingApp.main.dto.UpdatedEmployeeResponseDto;
import com.lendingApp.main.entity.Application;
import com.lendingApp.main.entity.Employee;
import com.lendingApp.main.service.ApplicationService;
import com.lendingApp.main.service.EmployeeService;
import com.lendingApp.main.service.ManagerService;

@RestController
@RequestMapping("/loan-app/manager")
@CrossOrigin(origins = "*")
public class ManagerController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ManagerService managerService;

    // // 1. View All Requested Loans
    // @GetMapping("/loans/requested")
    // public List<Application> getRequestedLoans(@RequestParam(required = false)
    // String phone) {
    // if (phone != null) {
    // return applicationService.getRequestedLoansByPhone(phone);
    // }
    // return applicationService.getAllRequestedLoans();
    // }

    // 2. Approve Loan
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PutMapping("/loans/{applicationId}/approve")
    public ResponseEntity<ApplicationResponse> approveLoan(@PathVariable UUID applicationId) {
        return ResponseEntity.ok(applicationService.approveLoan(applicationId));
    }

    // 3. Reject Loan
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PutMapping("/loans/{loanId}/reject")
    public ResponseEntity<ApplicationResponse> rejectLoan(@PathVariable UUID loanId,
            @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(applicationService.rejectLoan(loanId, reason != null ? reason : "No reason provided"));
    }

    // 4. View Manager (Employee) Profile
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/profile/{managerId}")
    public EmployeeResponseDto getManagerProfile(@PathVariable UUID managerId) {
        return employeeService.getEmployeeById(managerId);
    }

    // 5. Approve Customer KYC
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/customers/{customerId}/kyc/approve")
    public String approveCustomerKyc(@PathVariable UUID customerId) {
        return "Customer KYC approved for ID: " + customerId;
    }

    // 6. Reject Customer KYC
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/customers/{customerId}/kyc/reject")
    public String rejectCustomerKyc(@PathVariable UUID customerId) {
        return "Customer KYC rejected for ID: " + customerId;
    }

    @PostMapping("/{employeeId}/details")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<UpdatedEmployeeResponseDto> updateEmployeeDetails(@PathVariable UUID employeeId,
            @RequestBody UpdateEmployeeDetailsDto employeeDetailsDto) {
        return ResponseEntity.ok(employeeService.updateEmployeeUserDetails(employeeId, employeeDetailsDto));
    }

    @PostMapping("/{employeeId}/address")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<AddressResponseDto> postMethodName(@PathVariable UUID employeeId,
            @RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(employeeService.updateAddress(employeeId, addressDto));
    }

    @GetMapping("/{managerId}/loans/applications")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<AppliedLoanApplications>> postMethodName(@PathVariable UUID managerId) {

        return ResponseEntity.ok(managerService.getAllLoansAssignToManager(managerId));
    }

    @GetMapping("/applications/{applicationId}/missing-documents")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<String>> getMissingDocuments(@PathVariable UUID applicationId) {
        List<String> missingDocuments = applicationService.getMissingDocuments(applicationId);
        return ResponseEntity.ok(missingDocuments);
    }

}

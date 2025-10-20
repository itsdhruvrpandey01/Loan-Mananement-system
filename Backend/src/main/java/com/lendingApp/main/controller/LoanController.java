package com.lendingApp.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lendingApp.main.dto.LoanResponseDto;
import com.lendingApp.main.service.LoanSchemeService;

@RestController
@RequestMapping("/loan-app")
@CrossOrigin(origins="*")
public class LoanController {
	@Autowired
	private LoanSchemeService loanSchemeService;

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
	@GetMapping("/loans/{loanId}")
	public ResponseEntity<LoanResponseDto> getLoandByID(@PathVariable Long loanSchemeId) {
		return ResponseEntity.ok(loanSchemeService.findLoandById(loanSchemeId));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
	@GetMapping("/loans")
	public ResponseEntity<List<LoanResponseDto>> getLoanSchemes(@RequestParam(required = false) String loanType) {
		if (loanType == null) {
			return ResponseEntity.ok(loanSchemeService.getAllLoans());
		} else {
			return ResponseEntity.ok(loanSchemeService.findLoanByLoanType(loanType));
		}
	}
}

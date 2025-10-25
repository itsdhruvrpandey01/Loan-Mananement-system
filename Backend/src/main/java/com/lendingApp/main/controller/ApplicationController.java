package com.lendingApp.main.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendingApp.main.dto.AppliedLoanApplications;
import com.lendingApp.main.service.ApplicationService;


@RestController
@RequestMapping("loan-app/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {
	@Autowired
	private ApplicationService applicationService;
	
	@GetMapping("/{applicationID}")
	public ResponseEntity<AppliedLoanApplications> getMethodName(@PathVariable UUID applicationID) {
		return ResponseEntity.ok(applicationService.getAppliedLoanById(applicationID));
	}
	
}

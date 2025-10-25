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

import com.lendingApp.main.dto.CollateralTypeDto;
import com.lendingApp.main.dto.CollatoralTypeResponseDto;
import com.lendingApp.main.dto.EmployeeRequestDto;
import com.lendingApp.main.dto.EmployeeResponseDto;
import com.lendingApp.main.dto.LoanResponseDto;
import com.lendingApp.main.dto.LoanSchemeDto;
import com.lendingApp.main.dto.LoanTypeDto;
import com.lendingApp.main.dto.LoanTypeResponseDto;
import com.lendingApp.main.dto.PageResponseDto;
import com.lendingApp.main.dto.RoleDto;
import com.lendingApp.main.dto.RoleResponseDto;
import com.lendingApp.main.dto.UpdateLoanSchemeDto;
import com.lendingApp.main.service.CollateralService;
import com.lendingApp.main.service.EmployeeService;
import com.lendingApp.main.service.LoanSchemeService;
import com.lendingApp.main.service.LoanTypeService;
import com.lendingApp.main.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("loan-app/admin")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private LoanSchemeService loanSchemeService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private LoanTypeService loanTypeService;

	@Autowired
	private CollateralService collateralService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/role")
	public ResponseEntity<RoleResponseDto> postMethodName(@Valid @RequestBody RoleDto roleDto) {
		return ResponseEntity.ok(roleService.addRole(roleDto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/role")
	public ResponseEntity<List<RoleResponseDto>> getRoles() {
		List<RoleResponseDto> roles = roleService.getAllRoles();
		return ResponseEntity.ok(roles);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/loans")
	public ResponseEntity<LoanResponseDto> addLoanScheme(@Valid @RequestBody LoanSchemeDto loanSchemeDto) {
		return ResponseEntity.ok(loanSchemeService.addLoanScheme(loanSchemeDto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/loans/{loanSchemeId}")
	public ResponseEntity<LoanResponseDto> getLoandByID(@PathVariable Long loanSchemeId) {
		return ResponseEntity.ok(loanSchemeService.findLoandById(loanSchemeId));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/loans")
	public ResponseEntity<List<LoanResponseDto>> getLoanSchemes(@Valid @RequestParam(required = false) String loanType) {
		if (loanType == null) {
			return ResponseEntity.ok(loanSchemeService.getAllLoans());
		} else {
			return ResponseEntity.ok(loanSchemeService.findLoanByLoanType(loanType));
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/loans/{loanId}/deactive")
	public ResponseEntity<LoanResponseDto> deActivateLoan(@PathVariable Long loanId) {
		return ResponseEntity.ok(loanSchemeService.deactiveLoanScheme(loanId));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/loans/{loanId}/active")
	public ResponseEntity<LoanResponseDto> activateLoan(@PathVariable Long loanId) {
		return ResponseEntity.ok(loanSchemeService.activateLoanScheme(loanId));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/manager/{userId}")
	public ResponseEntity<EmployeeResponseDto> updateManagerDetails(@PathVariable UUID userId,
			@RequestBody EmployeeRequestDto employeeRequestDto) {
		return ResponseEntity.ok(this.employeeService.addEmployee(employeeRequestDto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/managers")
	public ResponseEntity<EmployeeResponseDto> updateManagerDetails(
			@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
		return ResponseEntity.ok(this.employeeService.addEmployee(employeeRequestDto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/manager/{employeeID}")
	public ResponseEntity<EmployeeResponseDto> addCityToManager(@PathVariable UUID employeeId,
			@Valid @RequestBody String city) {
		return ResponseEntity.ok(this.employeeService.updateCity(employeeId, city));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/loans/loantype")
	public ResponseEntity<LoanTypeResponseDto> addLoanType(@Valid @RequestBody LoanTypeDto loanTypeDto) {
		return ResponseEntity.ok(this.loanTypeService.addLoanType(loanTypeDto));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/loans/loantype")
	public ResponseEntity<List<LoanTypeResponseDto>> getAllLoanTypes() {
		return ResponseEntity.ok(this.loanTypeService.getAllLoanTypes());
	}

	@PostMapping("/loans/collaterals")
	public ResponseEntity<CollatoralTypeResponseDto> addCollaterals(@Valid @RequestBody CollateralTypeDto collateralTypeDto) {
		return ResponseEntity.ok(this.collateralService.addCollatoralType(collateralTypeDto));
	}

	@GetMapping("/loans/collaterals")
	public ResponseEntity<List<CollatoralTypeResponseDto>> getAllCollaterals() {
		return ResponseEntity.ok(this.collateralService.getAllColatorals());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/loans/{loanId}")
	public ResponseEntity<LoanResponseDto> updateLoanScheme(
	        @PathVariable Long loanId,
	        @Valid @RequestBody UpdateLoanSchemeDto dto) {
	    return ResponseEntity.ok(loanSchemeService.updateLoanScheme(loanId, dto));
	}
	
	@GetMapping("/managers")
	public ResponseEntity<PageResponseDto<EmployeeResponseDto>>find(@RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
		return ResponseEntity.ok(employeeService.getAllEmployees(page,size));
	}
	
	
}
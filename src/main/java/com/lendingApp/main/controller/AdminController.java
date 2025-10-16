package com.lendingApp.main.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.lendingApp.main.dto.RoleDto;
import com.lendingApp.main.dto.RoleResponseDto;
import com.lendingApp.main.service.CollateralService;
import com.lendingApp.main.service.EmployeeService;
import com.lendingApp.main.service.LoanSchemeService;
import com.lendingApp.main.service.LoanTypeService;
import com.lendingApp.main.service.RoleService;

@RestController
@RequestMapping("loan-app/admin")
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

	@PostMapping("/role")
	public ResponseEntity<RoleResponseDto> postMethodName(@RequestBody RoleDto roleDto) {
		return ResponseEntity.ok(roleService.addRole(roleDto));
	}

	@GetMapping("/role")
	public ResponseEntity<List<RoleResponseDto>> getRoles() {
		List<RoleResponseDto> roles = roleService.getAllRoles();
		return ResponseEntity.ok(roles);
	}

	@PostMapping("/loans")
	public ResponseEntity<LoanResponseDto> addLoanScheme(@RequestBody LoanSchemeDto loanSchemeDto) {
		return ResponseEntity.ok(loanSchemeService.addLoanScheme(loanSchemeDto));
	}

	@GetMapping("/loans/{loanId}")
	public ResponseEntity<LoanResponseDto> getLoandByID(@PathVariable Long loanSchemeId) {
		return ResponseEntity.ok(loanSchemeService.findLoandById(loanSchemeId));
	}

	@GetMapping("/loans")
	public ResponseEntity<List<LoanResponseDto>> getLoanSchemes(@RequestParam(required = false) String loanType) {
		if (loanType == null) {
			return ResponseEntity.ok(loanSchemeService.getAllLoans());
		} else {
			return ResponseEntity.ok(loanSchemeService.findLoanByLoanType(loanType));
		}
	}

	@PutMapping("/loans/{loanId}/deactive")
	public ResponseEntity<LoanResponseDto> deActivateLoan(@PathVariable Long loanId) {
		return ResponseEntity.ok(loanSchemeService.deactiveLoanScheme(loanId));
	}

	@PutMapping("/loans/{loanId}/active")
	public ResponseEntity<LoanResponseDto> activateLoan(@PathVariable Long loanId) {
		return ResponseEntity.ok(loanSchemeService.activateLoanScheme(loanId));
	}

	@PostMapping("/manager/{userId}")
	public ResponseEntity<EmployeeResponseDto> updateManagerDetails(@PathVariable UUID userId,
			@RequestBody EmployeeRequestDto employeeRequestDto) {
		return ResponseEntity.ok(this.employeeService.addEmployee(employeeRequestDto));
	}
	
	@PostMapping("/managers")
	public ResponseEntity<EmployeeResponseDto> updateManagerDetails(@RequestBody EmployeeRequestDto employeeRequestDto) {
		return ResponseEntity.ok(this.employeeService.addEmployee(employeeRequestDto));
	}

	@PutMapping("/manager/{employeeID}")
	public ResponseEntity<EmployeeResponseDto> addCityToManager(@PathVariable UUID employeeId,
			@RequestBody String city) {
		return ResponseEntity.ok(this.employeeService.updateCity(employeeId, city));
	}

	@PostMapping("/loans/loantype")
	public ResponseEntity<LoanTypeResponseDto> addLoanType(@RequestBody LoanTypeDto loanTypeDto) {
		return ResponseEntity.ok(this.loanTypeService.addLoanType(loanTypeDto));
	}

	@GetMapping("/loans/loantype")
	public ResponseEntity<List<LoanTypeResponseDto>> getAllLoanTypes() {
		return ResponseEntity.ok(this.loanTypeService.getAllLoanTypes());
	}

	@PostMapping("/loans/collaterals")
	public ResponseEntity<CollatoralTypeResponseDto> addCollaterals(@RequestBody CollateralTypeDto collateralTypeDto) {
		return ResponseEntity.ok(this.collateralService.addCollatoralType(collateralTypeDto));
	}

	@GetMapping("/loans/collaterals")
	public ResponseEntity<List<CollatoralTypeResponseDto>> getAllCollaterals() {
		return ResponseEntity.ok(this.collateralService.getAllColatorals());
	}

}
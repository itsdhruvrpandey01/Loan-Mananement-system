package com.lendingApp.main.service;

import java.util.List;
import java.util.UUID;

import com.lendingApp.main.dto.AddressDto;
import com.lendingApp.main.dto.AddressResponseDto;
import com.lendingApp.main.dto.EmployeeRequestDto;
import com.lendingApp.main.dto.EmployeeResponseDto;
import com.lendingApp.main.dto.PageResponseDto;
import com.lendingApp.main.dto.UpdateEmployeeDetailsDto;
import com.lendingApp.main.dto.UpdatedEmployeeResponseDto;

public interface EmployeeService {
	PageResponseDto<EmployeeResponseDto> getAllEmployees(int page,int size);
	EmployeeResponseDto getEmployeeById(UUID managerId);
    EmployeeResponseDto addEmployee(EmployeeRequestDto employeeRequestDto);
    EmployeeResponseDto updateCity(UUID employeeId,String city);
    UpdatedEmployeeResponseDto updateEmployeeUserDetails(UUID employeeId, UpdateEmployeeDetailsDto dto);
    AddressResponseDto updateAddress(UUID employeeId,AddressDto addressDto);
}
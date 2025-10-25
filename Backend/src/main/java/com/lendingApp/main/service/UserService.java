package com.lendingApp.main.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.lendingApp.main.dto.AddressDto;
import com.lendingApp.main.dto.AddressResponseDto;
import com.lendingApp.main.dto.ApplicationDto;
import com.lendingApp.main.dto.ApplicationResponse;
import com.lendingApp.main.dto.DocumentResponseDto;
import com.lendingApp.main.dto.PageResponseDto;
import com.lendingApp.main.dto.UserDetailsResponseDto;
import com.lendingApp.main.dto.UserDetailsUpdateDto;
import com.lendingApp.main.entity.Customer;
import com.lendingApp.main.entity.User;

public interface UserService {
    ApplicationResponse applyLoan(ApplicationDto applicationDto,UUID customerId);
    Customer findCustomerById(UUID customerId);
    User findUserById(UUID userId);
    DocumentResponseDto uploadDocuments(UUID applicationId,List<MultipartFile> files,List<String>docTypes)throws IOException;
    UserDetailsResponseDto findUserProfileById(UUID userId);
    UserDetailsResponseDto udpdateUserProfile(UUID userId,UserDetailsUpdateDto userDetailsUpdateDto,MultipartFile file)throws IOException;
    AddressResponseDto updateAddress(UUID userId,AddressDto addressDto);
    AddressResponseDto getUserAddress(UUID userId);
    PageResponseDto<ApplicationResponse> getAllAppliedLoans(UUID customerId,String status,int page,int size);
    UUID getCustomerID(UUID userID);
    UUID getManagerID(UUID userID);
}

package com.lendingApp.main.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lendingApp.main.dto.AddressDto;
import com.lendingApp.main.dto.AddressResponseDto;
import com.lendingApp.main.dto.ApplicationDto;
import com.lendingApp.main.dto.ApplicationResponse;
import com.lendingApp.main.dto.DocumentResponseDto;
import com.lendingApp.main.dto.InstallmentDto;
import com.lendingApp.main.dto.LoanResponseDto;
import com.lendingApp.main.dto.PageResponseDto;
import com.lendingApp.main.dto.ProfilePictureResponseDto;
import com.lendingApp.main.dto.UserDetailsResponseDto;
import com.lendingApp.main.dto.UserDetailsUpdateDto;
import com.lendingApp.main.entity.Address;
import com.lendingApp.main.entity.Application;
import com.lendingApp.main.entity.Customer;
import com.lendingApp.main.entity.Document;
import com.lendingApp.main.entity.Employee;
import com.lendingApp.main.entity.LoanScheme;
import com.lendingApp.main.entity.ProfilePicture;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.exception.LoanELigibiltyException;
import com.lendingApp.main.exception.UserNotFoundException;
import com.lendingApp.main.helper.LoanCalculator;
import com.lendingApp.main.helper.PaginationUtils;
import com.lendingApp.main.repository.ApplicationRepository;
import com.lendingApp.main.repository.CustomerRepository;
import com.lendingApp.main.repository.EmployeeRepository;
import com.lendingApp.main.repository.LoanSchemeRepository;
import com.lendingApp.main.repository.UserRepository;
import com.lendingApp.main.service.CloudinaryService;
import com.lendingApp.main.service.EmployeeService;
import com.lendingApp.main.service.LoanSchemeService;
import com.lendingApp.main.service.ManagerService;
import com.lendingApp.main.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private LoanSchemeService loanSchemeService;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private LoanCalculator loanCalculator;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private CloudinaryService cloudinaryService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private LoanSchemeRepository loanschemeRepository;	

	@Override
	public ApplicationResponse applyLoan(ApplicationDto applicationDto, UUID customerId) {

		// LoanScheme loanScheme = mapper.map(loanSchemeService.findLoandById(applicationDto.getLoanSchemeId()),
				// LoanScheme.class);
		LoanScheme loanScheme = loanschemeRepository.findById(applicationDto.getLoanSchemeId())
				.orElseThrow(() -> new RuntimeException("Loan Scheme not found with id: " + applicationDto.getLoanSchemeId()));
		if (loanScheme.getMaxTenure() < applicationDto.getTenure()) {
			throw new LoanELigibiltyException("Tenure is greater than maximum tenure or the loan scheme");
		}
		if (applicationDto.getApplicantAge() > loanScheme.getMaxAge()
				|| applicationDto.getApplicantAge() < loanScheme.getMinAge()) {
			throw new LoanELigibiltyException(
					"Applicant age must be between " + loanScheme.getMaxAge() + " - " + loanScheme.getMinAge());
		}
		if (applicationDto.getLoanAmount() > loanScheme.getMaxLoanAmount()
				|| applicationDto.getLoanAmount() < loanScheme.getMinLoanAmount()) {
			throw new LoanELigibiltyException("Requested loan amount must be between " + loanScheme.getMaxLoanAmount()
					+ " " + loanScheme.getMinLoanAmount());
		}
		if (applicationDto.getMonthlyIncome() < loanScheme.getMinIncome()) {
			throw new LoanELigibiltyException(
					"Applicants Income is lesser than required income " + loanScheme.getMinIncome());
		}
		Double totalLoanAmount = loanCalculator.calculateTotalAmount(applicationDto.getLoanAmount(),
				loanScheme.getInterestRate(), applicationDto.getTenure());
		Application application = new Application();

		application.setLoanRequirement(loanScheme);

		application.setApplicantAge(applicationDto.getApplicantAge());

		application.setRequestedAmount(applicationDto.getLoanAmount());

		application.setRequestedTenure(applicationDto.getTenure());

		application.setMonthlyIncome(applicationDto.getMonthlyIncome());

		application.setOccupation(applicationDto.getOccupation());

		application.setCreatedAt(LocalDateTime.now());

		application.setUpdatedAt(LocalDateTime.now());

		application.setStatus("PENDING");

		Customer customer = findCustomerById(customerId);

		application.setCustomer(customer);

		application.setTotalLoanAmount(totalLoanAmount);

		// List<Document> docs = new ArrayList<>();
		// for(DocsDto docsDto:applicationDto.getDocuments()){
		// Document doc =mapper.map(docsDto, Document.class);
		// doc.setApplication(application);
		// doc.setCustomer(customer);
		// docs.add(doc);
		// }
		// application.setDocuments(docs);
		Employee manager = managerService.assignManagerToApplication(customer.getUser());
		application.setAssignedManager(manager);

		Application submittedApplication = applicationRepository.save(application);

		ApplicationResponse response = mapper.map(submittedApplication, ApplicationResponse.class);
		response.setManagerName(manager.getUser().getFirstName() + " " + manager.getUser().getLastName());
		response.setLoanResponseDto(mapper.map(submittedApplication.getLoanRequirement(), LoanResponseDto.class));
		return response;
	}

	@Override
	public Customer findCustomerById(UUID customerId) {
		return this.customerRepository.findById(customerId)
				.orElseThrow(() -> new UserNotFoundException("No customer with id " + customerId));
	}

	@Override
	public User findUserById(UUID userId) {
		return this.userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("No customer with id " + userId));
	}

	@Override
	public DocumentResponseDto uploadDocuments(UUID applicationId, List<MultipartFile> files, List<String> docTypes)
			throws IOException {
		Application application = applicationRepository.findById(applicationId)
				.orElseThrow(() -> new UserNotFoundException("No Application with id " + applicationId));
		if (files.size() != docTypes.size()) {
			throw new RuntimeException("Mismatch in number of files and doc types");
		}
		List<Document> documents = new ArrayList<>();
		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			String docType = docTypes.get(i);

			String url = cloudinaryService.uploadFile(file);

			Document doc = new Document();
			doc.setDocName(file.getOriginalFilename());
			doc.setDocumentType(docType);
			doc.setDocURL(url);
			doc.setDocUploadedAt(LocalDateTime.now());
			doc.setApplication(application);
			doc.setCustomer(application.getCustomer());
			documents.add(doc);
		}
		application.getDocuments().addAll(documents);
		applicationRepository.save(application);
		return mapper.map(documents, DocumentResponseDto.class);
	}

	@Override
	public UserDetailsResponseDto findUserProfileById(UUID userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("No user with id " + userId));
		UserDetailsResponseDto userDetail = mapper.map(user, UserDetailsResponseDto.class);
		if (user.getProfilePicture() != null)
			userDetail.setProfilePictureResponseDto(
					mapper.map(user.getProfilePicture(), ProfilePictureResponseDto.class));
		return userDetail;
	}

	@Override
	public UserDetailsResponseDto udpdateUserProfile(UUID userId, UserDetailsUpdateDto userDetailsUpdateDto,
			MultipartFile file) throws IOException {
		User user = findUserById(userId); // Throws if not found

		// Update fields only if they are not null or blank
		if (userDetailsUpdateDto.getFirstName() != null && !userDetailsUpdateDto.getFirstName().isBlank()) {
			user.setFirstName(userDetailsUpdateDto.getFirstName());
		}

		if (userDetailsUpdateDto.getMiddleName() != null && !userDetailsUpdateDto.getMiddleName().isBlank()) {
			user.setMiddleName(userDetailsUpdateDto.getMiddleName());
		}

		if (userDetailsUpdateDto.getLastName() != null && !userDetailsUpdateDto.getLastName().isBlank()) {
			user.setLastName(userDetailsUpdateDto.getLastName());
		}

		if (userDetailsUpdateDto.getMobile() != null && !userDetailsUpdateDto.getMobile().isBlank()) {
			user.setMobile(userDetailsUpdateDto.getMobile());
		}

		if (userDetailsUpdateDto.getGender() != null && !userDetailsUpdateDto.getGender().isBlank()) {
			user.setGender(userDetailsUpdateDto.getGender());
		}

		if (userDetailsUpdateDto.getCreatedAt() != null) {
			user.setCreatedAt(userDetailsUpdateDto.getCreatedAt());
		}

		// Handle profile picture upload
		if (file != null && !file.isEmpty()) {
			String url = cloudinaryService.uploadFile(file);

			ProfilePicture profilePicture = new ProfilePicture();
			profilePicture.setPicName(file.getOriginalFilename());
			profilePicture.setPicURL(url);
			profilePicture.setPicUploadedAt(LocalDateTime.now());
			profilePicture.setUser(user); // bidirectional setup, optional

			user.setProfilePicture(profilePicture); // will update the profile picture in the user
		}

		// Persist user
		user = userRepository.save(user);

		// Convert to response DTO (assuming a mapper exists)
		UserDetailsResponseDto userDetail = mapper.map(user, UserDetailsResponseDto.class);

		if (user.getProfilePicture() != null)
			userDetail.setProfilePictureResponseDto(
					mapper.map(user.getProfilePicture(), ProfilePictureResponseDto.class));
		return userDetail;
	}

	@Override
	public AddressResponseDto updateAddress(UUID userId, AddressDto addressDto) {
		User user = findUserById(userId);
		Address address = user.getAddress();

		if (address == null) {
			// If no address exists, create a new one
			address = new Address();
			user.setAddress(address);
		}

		// 2. Map non-null fields from AddressDto to Address entity
		if (addressDto.getStreet() != null && !addressDto.getStreet().isBlank()) {
			address.setStreet(addressDto.getStreet());
		}
		if (addressDto.getArea() != null && !addressDto.getArea().isBlank()) {
			address.setArea(addressDto.getArea());
		}
		if (addressDto.getCity() != null && !addressDto.getCity().isBlank()) {
			address.setCity(addressDto.getCity());
		}
		if (addressDto.getPincode() != null && !addressDto.getPincode().isBlank()) {
			address.setPincode(addressDto.getPincode());
		}

		// 3. Save user (cascade should save address if configured)
		User savedUser = userRepository.save(user);
		return mapper.map(savedUser.getAddress(), AddressResponseDto.class);
	}

	@Override
	public AddressResponseDto getUserAddress(UUID userId) {
		User user = findUserById(userId);
		Address address = user.getAddress();
		return mapper.map(address, AddressResponseDto.class);
	}

	@Override
	public PageResponseDto<ApplicationResponse> getAllAppliedLoans(UUID customerId, String status, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Application> applications;

		if (status == null || status.isBlank()) {
			applications = applicationRepository.findByCustomerCustomerId(customerId, pageable);
		} else {
			applications = applicationRepository.findByCustomerCustomerIdAndStatus(
					customerId, status.trim().toUpperCase(), pageable);
		}

		List<ApplicationResponse> applicationResponses = applications.stream()
				.map(this::mapToApplicationResponse)
				.collect(Collectors.toList());

		return PaginationUtils.buildPageResponse(applications, applicationResponses);
	}

	private ApplicationResponse mapToApplicationResponse(Application application) {
		ApplicationResponse response = mapper.map(application, ApplicationResponse.class);
		response.setManagerName(application.getAssignedManager().getUser().getFirstName()
				+ " " + application.getAssignedManager().getUser().getLastName());
		response.setLoanResponseDto(mapper.map(application.getLoanRequirement(), LoanResponseDto.class));

		if (application.getInstallments() != null) {
			List<InstallmentDto> installmentDtos = application.getInstallments().stream()
					.map(installment -> mapper.map(installment, InstallmentDto.class))
					.collect(Collectors.toList());
			response.setInstallmentDtos(installmentDtos);
		}

		return response;
	}

	@Override
	public UUID getCustomerID(UUID userID) {
		Customer customer = this.customerRepository.findByUser_UserId(userID).get();
		return customer.getCustomerId();
	}

	@Override
	public UUID getManagerID(UUID userID) {
		UUID userId = this.employeeRepository.findByUser_UserId(userID).get().getEId();
		System.out.println(userId);
		return userId;
	}

}
package com.lendingApp.main.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lendingApp.main.dto.AddressDto;
import com.lendingApp.main.dto.AddressResponseDto;
import com.lendingApp.main.dto.EmployeeRequestDto;
import com.lendingApp.main.dto.EmployeeResponseDto;
import com.lendingApp.main.dto.PageResponseDto;
import com.lendingApp.main.dto.UpdateEmployeeDetailsDto;
import com.lendingApp.main.dto.UpdatedEmployeeResponseDto;
import com.lendingApp.main.entity.Address;
import com.lendingApp.main.entity.Employee;
import com.lendingApp.main.entity.Role;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.exception.ResourceNotFoundException;
import com.lendingApp.main.exception.UserNotFoundException;
import com.lendingApp.main.helper.PaginationUtils;
import com.lendingApp.main.repository.EmployeeRepository;
import com.lendingApp.main.repository.RoleRepository;
import com.lendingApp.main.repository.UserRepository;
import com.lendingApp.main.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @Override
    public EmployeeResponseDto getEmployeeById(UUID managerId) {
    	Employee employee = employeeRepository.findById(managerId).orElseThrow(()->new UserNotFoundException("Employee not found"));
        return mapper.map(employee,EmployeeResponseDto.class);
    }

	@Override
	public EmployeeResponseDto addEmployee(EmployeeRequestDto employeeRequestDto) {
		User user = mapper.map(employeeRequestDto, User.class);
		user.setPassword(passwordEncoder.encode(employeeRequestDto.getPassword()));
		Role managerRole = roleRepository.findByRoleName("ROLE_MANAGER")
		        .orElseThrow(() -> new ResourceNotFoundException("No role with name ROLE_MANAGER"));
		    user.setRole(managerRole);
		user.setIsActive(true);
		User savedUser = this.userRepository.save(user);
		Employee employee = mapper.map(employeeRequestDto, Employee.class);
		employee.setUser(savedUser);
		
		Employee savedEmployee = this.employeeRepository.save(employee);
		return mapper.map(savedEmployee, EmployeeResponseDto.class);
	}

	@Override
	public EmployeeResponseDto updateCity(UUID employeeId, String city) {
		Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(() -> new UserNotFoundException("No Employee with id " + employeeId));
		employee.setCity(city);
		Employee updatedEmployee = this.employeeRepository.save(employee);
		return mapper.map(updatedEmployee, EmployeeResponseDto.class);
	}

	@Override
	public UpdatedEmployeeResponseDto updateEmployeeUserDetails(UUID employeeId, UpdateEmployeeDetailsDto dto) {
		Employee employee = employeeRepository.findById(employeeId)
		        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

		    User user = employee.getUser();

		    // 2. Update fields only if not null/blank
		    if (dto.getFirstName() != null && !dto.getFirstName().isBlank()) {
		        user.setFirstName(dto.getFirstName());
		    }
		    if (dto.getMiddleName() != null && !dto.getMiddleName().isBlank()) {
		        user.setMiddleName(dto.getMiddleName());
		    }
		    if (dto.getLastName() != null && !dto.getLastName().isBlank()) {
		        user.setLastName(dto.getLastName());
		    }
		    if (dto.getMobile() != null && !dto.getMobile().isBlank()) {
		        user.setMobile(dto.getMobile());
		    }
		    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
		        user.setPassword(passwordEncoder.encode(dto.getPassword()));  // encode password!
		    }
		    if (dto.getGender() != null && !dto.getGender().isBlank()) {
		        user.setGender(dto.getGender());
		    }

		    // 3. Save User (assuming cascade doesn't cover this)
		    User savedUser = userRepository.save(user);
		return mapper.map(savedUser,UpdatedEmployeeResponseDto.class);
	}

	@Override
	public AddressResponseDto updateAddress(UUID employeeId, AddressDto addressDto) {
		
		Employee employee = employeeRepository.findById(employeeId)
		        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

		    User user = employee.getUser();
		
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
		return mapper.map(savedUser.getAddress(),AddressResponseDto.class);
	}

	@Override
	public PageResponseDto<EmployeeResponseDto> getAllEmployees(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    Page<Employee> pageEmployee = employeeRepository.findAll(pageable);

	    List<EmployeeResponseDto> employees = pageEmployee
	        .stream()
	        .map(employee -> mapper.map(employee, EmployeeResponseDto.class)) // ✅ Correct mapping
	        .collect(Collectors.toList());

	    return PaginationUtils.buildPageResponse(pageEmployee, employees);
	}


	
	
}

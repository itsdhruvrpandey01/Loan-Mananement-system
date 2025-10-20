package com.lendingApp.main.service.impl;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lendingApp.main.dto.LoginRequestDto;
import com.lendingApp.main.dto.LoginResponseDto;
import com.lendingApp.main.dto.RegisterRequestDto;
import com.lendingApp.main.entity.Customer;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.exception.UserNotFoundException;
import com.lendingApp.main.repository.CustomerRepository;
import com.lendingApp.main.repository.UserRepository;
import com.lendingApp.main.security.JwtTokenProvider;
import com.lendingApp.main.service.AuthService;
import com.lendingApp.main.service.RoleService;
import com.lendingApp.main.service.EmailService;
import com.lendingApp.main.service.NotificationService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepo;
    
    @Autowired
    ModelMapper mapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    // ========== EMAIL INTEGRATION - ADD THESE TWO ==========
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private NotificationService notificationService;
    // =======================================================

    @Override
    public void register(RegisterRequestDto registerReq) {
        try {
            // Example business check
            if (userRepo.existsByEmail(registerReq.getEmail())) {
                throw new UserNotFoundException("User with this email already exists");
            }

            User user = mapper.map(registerReq, User.class);
            user.setPassword(passwordEncoder.encode(registerReq.getPassword()));
            user.setIsActive(true);
            user.setCreatedAt(LocalDate.now());
            user.setRole(roleService.getRoleByName("ROLE_CUSTOMER"));
            User saveduser = userRepo.save(user);
            
            Customer customer = new Customer();
            customer.setUser(saveduser);
            this.customerRepository.save(customer);

            // ========== EMAIL INTEGRATION START ==========
            try {
                // Send welcome email
                emailService.sendWelcomeEmail(saveduser);
                System.out.println("✅ Welcome email sent to: " + saveduser.getEmail());

                // Create in-app notification
                notificationService.createNotification(
                    saveduser.getUserId(),
                    "Welcome to LendingApp! Your account has been created successfully.",
                    "ACCOUNT_CREATED"
                );
                System.out.println("✅ Welcome notification created for: " + saveduser.getEmail());

            } catch (Exception emailException) {
                // Log error but don't fail registration if email fails
                System.err.println("⚠️ Failed to send welcome email to: " + saveduser.getEmail());
                emailException.printStackTrace();
                // Registration still succeeds even if email fails
            }
            // ========== EMAIL INTEGRATION END ==========

        } catch (UserNotFoundException e) {
            // Handle or log your custom error here
            System.out.println("Registration failed: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginReq) {
        try {
            User user = userRepo.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication);
            
            LoginResponseDto response = new LoginResponseDto();
            response.setToken(token);
            response.setRole(user.getRole().getRoleName());
            response.setUserId(user.getUserId());

            // ========== EMAIL INTEGRATION START ==========
            try {
                // Create login notification (security feature)
                notificationService.createNotification(
                    user.getUserId(),
                    "New login detected on your account at " + LocalDate.now(),
                    "LOGIN_ALERT"
                );
                System.out.println("✅ Login notification created for: " + user.getEmail());

                // OPTIONAL: Uncomment below to send login alert email
                // emailService.sendLoginAlertEmail(user);

            } catch (Exception notificationException) {
                // Log error but don't fail login if notification fails
                System.err.println("⚠️ Failed to create login notification");
                notificationException.printStackTrace();
                // Login still succeeds
            }
            // ========== EMAIL INTEGRATION END ==========
            
            return response;
            
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException("Username or password not found");
        }
    }
}


// package com.lendingApp.main.service.impl;

// import java.time.LocalDate;

// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.cloudinary.provisioning.Account.Role;
// import com.lendingApp.main.dto.LoginRequestDto;
// import com.lendingApp.main.dto.LoginResponseDto;
// import com.lendingApp.main.dto.RegisterRequestDto;
// import com.lendingApp.main.entity.Customer;
// import com.lendingApp.main.entity.User;
// import com.lendingApp.main.exception.UserNotFoundException;
// import com.lendingApp.main.repository.CustomerRepository;
// import com.lendingApp.main.repository.UserRepository;
// import com.lendingApp.main.security.JwtTokenProvider;
// import com.lendingApp.main.service.AuthService;
// import com.lendingApp.main.service.RoleService;

// @Service
// public class AuthServiceImpl implements AuthService {

//     @Autowired
//     UserRepository userRepo;
//     @Autowired
//     ModelMapper mapper;
//     @Autowired
// 	private PasswordEncoder passwordEncoder;
//     @Autowired
// 	private AuthenticationManager authenticationManager;
//     @Autowired
// 	private JwtTokenProvider tokenProvider;
//     @Autowired
//     private RoleService roleService;
//     @Autowired
//     private CustomerRepository customerRepository;

//     @Override
//     public void register(RegisterRequestDto registerReq) {
//         try {
// 			// Example business check
// 			if (userRepo.existsByEmail(registerReq.getEmail())) {
// 				throw new UserNotFoundException("User with this email already exists");
// 			}

// 			User user = mapper.map(registerReq, User.class);
// 			user.setPassword(passwordEncoder.encode(registerReq.getPassword()));
// 			user.setIsActive(true);
// 			user.setCreatedAt(LocalDate.now());
// 			user.setRole(roleService.getRoleByName("ROLE_CUSTOMER"));
// 			User saveduser = userRepo.save(user);
// 			Customer customer = new Customer();
// 			customer.setUser(saveduser);
// 			this.customerRepository.save(customer);

// 		} catch (UserNotFoundException e) {
// 			// Handle or log your custom error here, e.g.:
// 			System.out.println("Registration failed: " + e.getMessage());
// 			throw e;
// 		}
//     }

//     @Override
//     public LoginResponseDto login(LoginRequestDto loginReq) {
//         	try {
//             User user = userRepo.findByEmail(loginReq.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
// 			Authentication authentication = authenticationManager
// 					.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));

// 			SecurityContextHolder.getContext().setAuthentication(authentication);
// 			String token = tokenProvider.generateToken(authentication);
// 			LoginResponseDto response = new LoginResponseDto();
//             response.setToken(token);
//             response.setRole(user.getRole().getRoleName());
//             response.setUserId(user.getUserId());
//             return response;
// 		} catch (BadCredentialsException e) {
// 			throw new UserNotFoundException("Username or password not found");
// 		}
        
//     }

// }

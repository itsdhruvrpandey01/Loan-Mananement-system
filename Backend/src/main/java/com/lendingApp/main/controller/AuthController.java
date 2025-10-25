package com.lendingApp.main.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendingApp.main.dto.LoginRequestDto;
import com.lendingApp.main.dto.LoginResponseDto;
import com.lendingApp.main.dto.RegisterRequestDto;
import com.lendingApp.main.service.AuthService;
import com.lendingApp.main.service.PasswordResetService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterRequestDto registerDto) {
        authService.register(registerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginDto) {
        LoginResponseDto response = authService.login(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        passwordResetService.initiatePasswordReset(email);
        return ResponseEntity.ok(Map.of("message", "If your email exists, an OTP has been sent."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        boolean success = passwordResetService.resetPassword(email, otp, newPassword);
        if (!success) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid or expired OTP."));
        }

        return ResponseEntity.ok(Map.of("message", "Password reset successful."));
    }
}

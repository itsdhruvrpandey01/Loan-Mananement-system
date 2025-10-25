package com.lendingApp.main.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lendingApp.main.entity.PasswordResetToken;
import com.lendingApp.main.entity.User;
import com.lendingApp.main.repository.PasswordResetRepository;
import com.lendingApp.main.repository.UserRepository;
import com.lendingApp.main.service.PasswordResetService;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordResetRepository tokenRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void initiatePasswordReset(String email) {
		Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty()) return; // silently ignore if no user to prevent user enumeration

        String otp = String.format("%06d", new Random().nextInt(999999));
        System.out.println(otp);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(otp);
        resetToken.setEmail(email);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        tokenRepo.save(resetToken);
        System.out.println();
	}

	@Override
	public boolean resetPassword(String email,String token, String newPassword) {
		Optional<PasswordResetToken> otpOpt = tokenRepo.findTopByEmailOrderByExpiryDateDesc(email);
        if (otpOpt.isEmpty()) return false;

        PasswordResetToken storedOtp = otpOpt.get();

        if (storedOtp.isUsed()
                || !storedOtp.getToken().equals(token)
                || storedOtp.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty()) return false;

        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        storedOtp.setUsed(true);
        tokenRepo.save(storedOtp);

        return true;
    }

}

package com.lendingApp.main.service;

public interface PasswordResetService {
	public void initiatePasswordReset(String email);
	public boolean resetPassword(String email,String token, String newPassword);
}

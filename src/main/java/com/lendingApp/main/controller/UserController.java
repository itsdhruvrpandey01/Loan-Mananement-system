package com.lendingApp.main.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lendingApp.main.dto.AddressDto;
import com.lendingApp.main.dto.AddressResponseDto;
import com.lendingApp.main.dto.UserDetailsResponseDto;
import com.lendingApp.main.dto.UserDetailsUpdateDto;
import com.lendingApp.main.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/loan-app/user")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/profile/{userId}")
	public ResponseEntity<UserDetailsResponseDto> getUser(@PathVariable UUID userId) {
		return ResponseEntity.ok(userService.findUserProfileById(userId));
	}
	
	@PutMapping("profile/{userId}")
	public ResponseEntity<UserDetailsResponseDto> updateUser(@PathVariable UUID userId, @ModelAttribute UserDetailsUpdateDto userDetailsUpdateDto,@RequestParam(value="file",required = false)MultipartFile file)throws IOException {
		return ResponseEntity.ok(userService.udpdateUserProfile(userId, userDetailsUpdateDto, file));
	}
	@PutMapping("profile/address/{userId}")
	public ResponseEntity<AddressResponseDto> updateUserAddress(@PathVariable UUID userId, @RequestBody AddressDto addressDto) {
		return ResponseEntity.ok(userService.updateAddress(userId, addressDto));
	}
	@GetMapping("profile/address/{userId}")
	public ResponseEntity<AddressResponseDto> getUserAddress(@PathVariable UUID userId) {
		return ResponseEntity.ok(userService.getUserAddress(userId));
	}

}

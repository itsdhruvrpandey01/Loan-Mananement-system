package com.lendingApp.main.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
	public String uploadFile(MultipartFile file) throws IOException;
}

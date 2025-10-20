package com.lendingApp.main.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lendingApp.main.dto.DocsDto;

public interface DocService {
	List<DocsDto> UploadDocs(List<MultipartFile> files) throws IOException;
}

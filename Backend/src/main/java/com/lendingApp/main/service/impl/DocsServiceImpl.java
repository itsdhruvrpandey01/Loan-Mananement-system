package com.lendingApp.main.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lendingApp.main.dto.DocsDto;
import com.lendingApp.main.service.CloudinaryService;
import com.lendingApp.main.service.DocService;

@Service
public class DocsServiceImpl implements DocService{
	
	@Autowired
	private CloudinaryService cloudinaryService;
	
	@Override
	public List<DocsDto> UploadDocs(List<MultipartFile> files) throws IOException {
		List<DocsDto> docsDtos = new ArrayList<>();
		for(MultipartFile file:files) {
			DocsDto doc = new DocsDto();
			doc.setDocName(file.getName());
			doc.setDocUploadedAt(LocalDateTime.now());
			doc.setDocURL(cloudinaryService.uploadFile(file));
			docsDtos.add(doc);
		}
		return docsDtos;
	}

}

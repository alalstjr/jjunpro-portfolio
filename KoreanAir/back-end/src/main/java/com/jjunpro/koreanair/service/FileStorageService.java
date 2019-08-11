package com.jjunpro.koreanair.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.jjunpro.koreanair.domain.StoreFile;
import com.jjunpro.koreanair.property.FileStorageProperties;

public interface FileStorageService {
	
	public String storeFile(MultipartFile file, long num);
	
	public Resource loadFileAsResource(String fileName);
	
	public void thumbUpdate(long num);
	
	public StoreFile[] findByFile(long bo_num);
	
	public StoreFile[] findByImgFile(long bo_num);
	
	public void removeFile(long num, String file);
}

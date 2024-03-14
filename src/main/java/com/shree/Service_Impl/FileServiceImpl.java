package com.shree.Service_Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shree.Exception.BadApiRequest;
import com.shree.Service.FileService;

@Service
public class FileServiceImpl implements FileService {

	
	private Logger logger =LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		
		String originalFilename = file.getOriginalFilename();
		logger.info("Filename: {}",originalFilename);
		String fileName=UUID.randomUUID().toString();
		String extension=originalFilename.substring(originalFilename.lastIndexOf("."));
		String fileNameWithExtension= fileName.concat(extension);
		String fullPathWithFileName= path+File.separator+fileNameWithExtension;
		
		if(extension.equalsIgnoreCase(".png")|| extension.equalsIgnoreCase(".jpg")|| extension.equalsIgnoreCase(".jpeg"))
		{
			//give path where you want to save is available
			
		   // Create a File object representing the directory(folder) mentioned in path
			File folder= new File(path);
			  
			// Check if the directory already exists
			if(!folder.exists()) {
				 
				folder.mkdirs();
			}
		 
			//save file
			//file.getInputStream() it reads the data in byte 
			//now this readed data need to copy to the path which is mentioned in path 
			// so as targetPath you given the full path where the data need to store;
			Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
			
			return fileNameWithExtension;
		}
	
		else {
			
			throw new BadApiRequest("File with this extension "+extension+" not allowed");
		}
			
		
		
	}

	@Override
	public InputStream getResource(String path, String imageName) throws FileNotFoundException {
	      
		logger.info("Image name:",imageName);
		logger.info("Path: {}",path+File.separator+imageName);
		
		String fullPath=path+File.separator+imageName;
		
		
		InputStream inputStream= new FileInputStream(fullPath);
		  
		return inputStream;
	}


}

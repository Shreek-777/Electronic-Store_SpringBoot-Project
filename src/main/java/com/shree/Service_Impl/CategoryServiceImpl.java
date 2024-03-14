package com.shree.Service_Impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shree.Dto.CategoryDto;
import com.shree.Dto.PageableResponse;
import com.shree.Entities.Category;
import com.shree.Exception.ResourceNotFoundException;
import com.shree.Helper.Helper;
import com.shree.Repository.CategoryRepo;
import com.shree.Service.CategoryService;




@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo; 
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${category.profile.image.path}")
    private String imagePath;	
	
	
	Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	private  CategoryDto entityToDto(Category category) {
		
		 return mapper.map(category, CategoryDto.class);	 
	 };
	 
	 private Category dtoToEntity(CategoryDto categoryDto) {
		 
		 return mapper.map(categoryDto,Category.class);
	 }
	
	@Override
	public CategoryDto create(CategoryDto categoryDto) {
     

		// generete unique id in string format
		String userId = UUID.randomUUID().toString();
		categoryDto.setCategoryId(userId);
		
		Category categoryEntity = dtoToEntity(categoryDto);

		Category savedEntity = categoryRepo.save(categoryEntity);

		return entityToDto(savedEntity);
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto, String categoryId) {

		Category existingUser = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with this id " + categoryId + "Not Found"));
		existingUser.setCoverImage(categoryDto.getCoverImage());
		existingUser.setDescription(categoryDto.getDescription());
		existingUser.setTitle(categoryDto.getTitle());

		// save data
		Category save = categoryRepo.save(existingUser);

		return entityToDto(save);
	}

	@Override
	public String deleteCategory(String categoryId) {
		Category existingUser = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with this id not available"));

		String coverImage = existingUser.getCoverImage();
		String fullpath = imagePath + File.separator + coverImage;

		Path path = Paths.get(fullpath);

		try {
			Files.delete(path);
			logger.info("Imagen associated with user also deleted succefully...");
		} catch (NoSuchFileException ex) {
			logger.info("No such file found");
			ex.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		//delete the category
		categoryRepo.delete(existingUser);
		logger.info("Category deleted with : {}",categoryId);
		
		return "Category Deleted Succefully !!";
	}

	

	@Override
	public CategoryDto getByid(String catogoryId) {
		
		Category existingUser = categoryRepo.findById(catogoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with this id " + catogoryId + "Not Found"));
		
		return entityToDto(existingUser);
	}

	//get all
	@Override
	public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortDir, String sortBy) {
		
		//org.springframework.data.domain.Sort sort = Helper.sortDir(sortDir, sortBy);
		
		Pageable pageable = Helper.createPageRequest(pageNumber, pageSize, sortDir, sortBy);
		
		//Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Category> page = categoryRepo.findAll(pageable);
		
		PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);
		
		return response;
		
	}

	

}

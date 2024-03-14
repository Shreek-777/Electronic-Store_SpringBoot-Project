package com.shree.Service;

import com.shree.Dto.ApiResPonseMessage;
import com.shree.Dto.CategoryDto;
import com.shree.Dto.PageableResponse;

public interface CategoryService {

	//create
	CategoryDto create(CategoryDto categoryDto);
	
	//update 
	CategoryDto update(CategoryDto categoryDto, String categoryId);
	
	//delete
	String deleteCategory(String categoryId);

	
	//get single category detail
	CategoryDto getByid(String catogoryId);
     
	//get all
	PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortDir, String sortBy);
	
	
	
	
	//search
	
	
	
}

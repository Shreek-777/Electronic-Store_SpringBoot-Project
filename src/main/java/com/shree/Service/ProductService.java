package com.shree.Service;

import java.util.List;

import com.shree.Dto.PageableResponse;
import com.shree.Dto.ProductDto;
import com.shree.Entities.Category;

public interface ProductService {

	
	//create 
	ProductDto createProduct(ProductDto productDto);
	
	//update 
	ProductDto updateProduct(ProductDto productDto,String productId);
	
	//delete
	String deleteProduct(String productId);
	
	
	//get single
	ProductDto getProductById(String productId);
	
	//get all
	PageableResponse<ProductDto>getAll(int pageNumber, int pageSize,String sortBy,String sortDir);
	
	//get all live
	PageableResponse<ProductDto>getAllLive(int pageNumber, int pageSize,String sortBy,String sortDir);
	
	//search product
	//PageableResponse<ProductDto>searchByTitle(String subTitle,int pageNumber, int pageSize,String sortBy,String sortDir);

	PageableResponse<ProductDto> searchByTitle(int pageNumber, int pageSize, String sortBy, String sortDir,
			String subTitle);

	// create product with category
	ProductDto createProductWithCategory(String CategoryId, ProductDto productDto);
	
	
	//update Category of product
	ProductDto updateProductCategory(String CategoryId,String productId );
	
	//get product by category
	PageableResponse<ProductDto>getAllByCategory(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir);
	
}
	
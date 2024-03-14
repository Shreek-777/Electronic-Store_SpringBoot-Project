package com.shree.Service_Impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shree.Controller.UserController;
import com.shree.Dto.CategoryDto;
import com.shree.Dto.PageableResponse;
import com.shree.Dto.ProductDto;
import com.shree.Entities.Category;
import com.shree.Entities.Product;
import com.shree.Exception.ResourceNotFoundException;
import com.shree.Helper.Helper;
import com.shree.Repository.CategoryRepo;
import com.shree.Repository.ProductRepository;
import com.shree.Service.CategoryService;
import com.shree.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${product.profile.image.path}")
	private String imageUploadPath;
	
	Logger logger=LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Override
	public ProductDto createProduct(ProductDto productDto) {

		Product product = mapper.map(productDto, Product.class);

		String userId = UUID.randomUUID().toString();
		product.setProductId(userId);
		product.setAddedDate(new Date());

		Product save = productRepo.save(product);
		return mapper.map(save, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto, String productId) {
		
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product with this id not Found"));

		product.setDescription(productDto.getDescription());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setLive(productDto.isLive());
		product.setPrice(productDto.getPrice());
		product.setQuantityInStock(productDto.getQuantityInStock());
		product.setStock(productDto.isStock());
		product.setTitle(productDto.getTitle());
		product.setImageName(productDto.getImageName());

		Product save = productRepo.save(product);

		return mapper.map(save, ProductDto.class);
	}

	@Override
	public String deleteProduct(String productId) {
		
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product with this id not Available"));
		
		String fullImagePath=imageUploadPath+File.separator+product.getImageName();
		
		logger.info("Image path : {}",fullImagePath);
		
		Path path=Paths.get(fullImagePath);
		try {
			Files.delete(path);
			logger.info("Imagen associated with user also deleted succefully...");
		} catch (NoSuchFileException ex) {
			logger.info("No such file found");
			ex.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		productRepo.delete(product);
		
		return "Product deleted succefully !!";
	}

	@Override
	public ProductDto getProductById(String productId) {
		
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product with this id not Available"));
		
		
		return mapper.map(product, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort= (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		
		Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepo.findAll(pageable);
		PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
		return response;
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepo.findByLiveTrue(pageable);
		PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
		return response;
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(int pageNumber, int pageSize, String sortBy,
			String sortDir, String subTitle) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepo.findByTitleContaining(subTitle,pageable);
		PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
		return response;
		
	
	}

	@Override
	public ProductDto createProductWithCategory(String CategoryId, ProductDto productDto) {
		
		CategoryDto categoryDto= categoryService.getByid(CategoryId);
	    Category category = mapper.map(categoryDto, Category.class);
	   
		Product product = mapper.map(productDto, Product.class);

		String userId = UUID.randomUUID().toString();
		product.setProductId(userId);
		product.setAddedDate(new Date());
		product.setCategory(category);

		Product save = productRepo.save(product);
		return mapper.map(save, ProductDto.class);
		
	}

	@Override
	public ProductDto updateProductCategory(String CategoryId, String productId) {

		CategoryDto categoryDto = categoryService.getByid(CategoryId);
		Category category = mapper.map(categoryDto, Category.class);

		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product with this id not Available"));

		product.setCategory(category);
		Product save = productRepo.save(product);

		return mapper.map(save, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllByCategory(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		CategoryDto categoryDto = categoryService.getByid(categoryId);
		Category category = mapper.map(categoryDto, Category.class);
		
		Sort sort= (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Product> findByCategory = productRepo.findByCategory(category, pageable);
		
		PageableResponse<ProductDto> response = Helper.getPageableResponse(findByCategory, ProductDto.class);
		return response;
	}

}

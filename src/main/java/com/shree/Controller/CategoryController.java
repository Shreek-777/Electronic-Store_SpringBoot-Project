package com.shree.Controller;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shree.Dto.ApiResPonseMessage;
import com.shree.Dto.CategoryDto;
import com.shree.Dto.ImageResponse;
import com.shree.Dto.PageableResponse;
import com.shree.Dto.ProductDto;
import com.shree.Dto.UserDto;
import com.shree.Service.CategoryService;
import com.shree.Service.FileService;
import com.shree.Service.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired 
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileService fileService;
	
	Logger logger=LoggerFactory.getLogger(UserController.class);
    
	@Value("${category.profile.image.path}")
	private String imageUploadPath;

	@PostMapping
	public ResponseEntity<CategoryDto> createUser( @Valid @RequestBody CategoryDto categoryDto) {

		CategoryDto  createCategory= categoryService.create(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateUser(@PathVariable String categoryId, @Valid @RequestBody CategoryDto categoryDto) {

		CategoryDto updatedUser =categoryService.update(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updatedUser, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResPonseMessage> deleteUser(@PathVariable String categoryId) {

		String deleteUser = categoryService.deleteCategory(categoryId);
		ApiResPonseMessage message=ApiResPonseMessage.builder().message(deleteUser).status(HttpStatus.OK).success(true).build();
		
		return new ResponseEntity<ApiResPonseMessage>(message, HttpStatus.OK);
	}

	// getall
	@GetMapping
	public ResponseEntity<PageableResponse<CategoryDto>>getAllUser(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "10",required=false ) int pageSize, 
			@RequestParam(value = "sortBy", defaultValue = "title",required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
	)
	{
		return new ResponseEntity<PageableResponse<CategoryDto>>(categoryService.getAll(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
	}

	// get by id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto>getById(@PathVariable String categoryId){
		CategoryDto userById = categoryService.getByid(categoryId);
		return new  ResponseEntity<CategoryDto>(userById, HttpStatus.OK);
	}
	
	
	//upload image
	@PostMapping("/image/{categoryId}")
	public ResponseEntity<ImageResponse>uploadImage(@RequestParam("categoryImage") MultipartFile file, @PathVariable String categoryId ) throws IOException{
		
		CategoryDto category = categoryService.getByid(categoryId);
		
		String imageName = fileService.uploadFile(file, imageUploadPath);
		category.setCoverImage(imageName);
		logger.info("User image name upload: {}",category.getCoverImage());
		CategoryDto update = categoryService.update(category, categoryId);
		
		ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).status(HttpStatus.CREATED).success(true).message("Image uploaded sucessfully").build();
		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}
	
	//serve image
	
	@GetMapping("/image/{categoryId}")
	public void serveUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
		
		
		 CategoryDto category = categoryService.getByid(categoryId);
		 String imageName = category.getCoverImage();
		 logger.info("User image name serve: {}",imageName);
		 InputStream resource = fileService.getResource(imageUploadPath, imageName);
		
		 // Set the content type in the HTTP response to indicate it's an image (JPEG in this case)
		 response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		 
		  // Copy the contents of the image InputStream to the response's output stream
		 StreamUtils.copy(resource, response.getOutputStream()); 
		
	}
	
	@PostMapping("/{categoryId}/products")
	public ResponseEntity<ProductDto>createCategoryWithProduct(@PathVariable String categoryId, @RequestBody ProductDto productDto){
		
		ProductDto productWithCategory = productService.createProductWithCategory(categoryId, productDto);
		
		return new ResponseEntity<ProductDto>(productWithCategory, HttpStatus.OK);
		
	}
	
	@PutMapping("/{categoryId}/product/{productId}")
	public ResponseEntity<ProductDto>updateProductCategory(@PathVariable String categoryId, @PathVariable String productId ){
		
		ProductDto updateProductCategory = productService.updateProductCategory(categoryId, productId);
		
		return new ResponseEntity<ProductDto>(updateProductCategory ,HttpStatus.OK );
	}
	
	@GetMapping("/products/{categoryId}")
	ResponseEntity<PageableResponse<ProductDto>>getProductByCategory(@PathVariable String categoryId,
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "10",required=false ) int pageSize, 
			@RequestParam(value = "sortBy", defaultValue = "title",required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
			){
		
		PageableResponse<ProductDto> response = productService.getAllByCategory(categoryId,pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<PageableResponse<ProductDto>>(response, HttpStatus.OK);
		
	}
	
	
}

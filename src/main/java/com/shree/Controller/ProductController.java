package com.shree.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.shree.Service.FileService;
import com.shree.Service.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${product.profile.image.path}")
	private String imageUploadPath;
	
	Logger logger=LoggerFactory.getLogger(ProductController.class);

	// create
	@PostMapping
	public ResponseEntity<ProductDto> createUser(@Valid @RequestBody ProductDto productDto) {

		ProductDto product = productService.createProduct(productDto);
		return new ResponseEntity<ProductDto>(product, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{userId}")
	public ResponseEntity<ProductDto> updateUser(@PathVariable String userId,
			@Valid @RequestBody ProductDto productDto) {

		ProductDto updatedUser = productService.updateProduct(productDto, userId);
		return new ResponseEntity<ProductDto>(updatedUser, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResPonseMessage> deleteUser(@PathVariable String productId) {

		String deleteUser = productService.deleteProduct(productId);
		ApiResPonseMessage message = ApiResPonseMessage.builder().message(deleteUser).status(HttpStatus.OK)
				.success(true).build();

		return new ResponseEntity<ApiResPonseMessage>(message, HttpStatus.OK);
	}

	// getall
	@GetMapping
	public ResponseEntity<PageableResponse<ProductDto>> getAllUser(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		return new ResponseEntity<PageableResponse<ProductDto>>(
				productService.getAll(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
	}

	// get by id
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getById(@PathVariable String productId) {
		ProductDto productById = productService.getProductById(productId);
		return new ResponseEntity<ProductDto>(productById, HttpStatus.OK);
	}

	@GetMapping("/live")
	public ResponseEntity<PageableResponse<ProductDto>>getAllLive(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
			
		return new ResponseEntity<PageableResponse<ProductDto>>(productService.getAllLive(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
	}
	
	@GetMapping("/search/{query}")
	public ResponseEntity<PageableResponse<ProductDto>>getAllLive(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir ,
			@PathVariable String query){
			
		return new ResponseEntity<PageableResponse<ProductDto>>(productService.searchByTitle(pageNumber, pageSize, sortBy, sortDir,query), HttpStatus.OK);
	}
	
	
	//upload Image
	@PostMapping("/image/{productId}")
	public ResponseEntity<ImageResponse> uploadImage(@RequestParam("productImage") MultipartFile image, @PathVariable String productId)
			throws IOException {
		
       ProductDto product = productService.getProductById(productId);
		
		String imageName = fileservice.uploadFile( image, imageUploadPath);
		product.setImageName(imageName);
		logger.info("product image name: {}",product.getImageName());
		logger.info("User image name upload: {}",product.getImageName());
		ProductDto updateProduct = productService.updateProduct(product,productId);
		
		logger.info("updated Product final name:{}",updateProduct.getImageName());
	
		
		ImageResponse response = ImageResponse.builder().imageName(imageName).status(HttpStatus.CREATED).success(true)
				.message("Image uploaded successfully").build();

		return new ResponseEntity<ImageResponse>(response, HttpStatus.OK);
	}
	
	//serve Image
	@GetMapping("/image/{productId}")
	public void serveImage(@PathVariable String productId,HttpServletResponse response) throws IOException {

		ProductDto productById = productService.getProductById(productId);
		String imageName = productById.getImageName();
		
		logger.info("Product image name serve: {}", imageName);
		
		InputStream resource = fileservice.getResource(imageUploadPath, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		 // Copy the contents of the image InputStream to the response's output stream
		StreamUtils.copy(resource, response.getOutputStream()); 

	}
	
}

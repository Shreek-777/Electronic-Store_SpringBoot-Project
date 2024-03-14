package com.shree.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.shree.Dto.ImageResponse;
import com.shree.Dto.PageableResponse;
import com.shree.Dto.UserDto;
import com.shree.Service.FileService;
import com.shree.Service.UserService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")

public class UserController {

	@Autowired 
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	Logger logger=LoggerFactory.getLogger(UserController.class);
    
	@Value("${user.profile.image.path}")
	private String imageUploadPath;

	@PostMapping
	public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto) {

		UserDto createuser = userService.createuser(userDto);
		return new ResponseEntity<UserDto>(createuser, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @Valid @RequestBody UserDto userdto) {

		UserDto updatedUser = userService.updateUser(userdto, userId);
		return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResPonseMessage> deleteUser(@PathVariable String userId) {

		String deleteUser = userService.deleteUser(userId);
		ApiResPonseMessage message=ApiResPonseMessage.builder().message(deleteUser).status(HttpStatus.OK).success(true).build();
		
		return new ResponseEntity<ApiResPonseMessage>(message, HttpStatus.OK);
	}

	// getall
	@GetMapping
	public ResponseEntity<PageableResponse<UserDto>>getAllUser(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "10",required=false ) int pageSize, 
			@RequestParam(value = "sortBy", defaultValue = "name",required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
	){
		return new ResponseEntity<PageableResponse<UserDto>>(userService.getallUser(pageNumber, pageSize,sortBy,sortDir), HttpStatus.OK);
	}

	// get by id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto>getById(@PathVariable String userId){
		UserDto userById = userService.getUserById(userId);
		return new  ResponseEntity<UserDto>(userById, HttpStatus.OK);
	}
	

	// get by email
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto>getByEmail(@PathVariable String email){
		UserDto userByEmail= userService.getUserByEmail(email);
		return new  ResponseEntity<UserDto>(userByEmail, HttpStatus.OK);
	}
	

	// search user by keyword
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<UserDto>>searchUser(@PathVariable String keyword){
		 List<UserDto> searchUser = userService.searchUser(keyword);
		 return new ResponseEntity<List<UserDto>>(searchUser, HttpStatus.OK);
	}
	
	//upload user image
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse>uploadImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException{
		
		UserDto user = userService.getUserById(userId);
		
		String imageName = fileService.uploadFile(image, imageUploadPath);
		user.setImageName(imageName);
		logger.info("User image name upload: {}",user.getImageName());
		UserDto updateUser = userService.updateUser(user, userId);
		
		ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).status(HttpStatus.CREATED).success(true).message("Image uploaded sucessfully").build();
		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}
	
	
	//serve user image
	
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
		
		 UserDto user = userService.getUserById(userId);
		 String imageName = user.getImageName();
		 logger.info("User image name serve: {}",imageName);
		 InputStream resource = fileService.getResource(imageUploadPath, imageName);
		
		 // Set the content type in the HTTP response to indicate it's an image (JPEG in this case)
		 response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		 
		  // Copy the contents of the image InputStream to the response's output stream
		 StreamUtils.copy(resource, response.getOutputStream()); 
		
	}
	
}

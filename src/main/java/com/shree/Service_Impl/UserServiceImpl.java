package com.shree.Service_Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shree.Dto.PageableResponse;
import com.shree.Dto.UserDto;
import com.shree.Entities.UserDetails;
import com.shree.Exception.ResourceNotFoundException;
import com.shree.Helper.Helper;
import com.shree.Repository.UserRepo;
import com.shree.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper mapper;

	@Value("${user.profile.image.path}")
    private String imagePath;	
	
	Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
	

	@Override
	public UserDto createuser(UserDto userDto) {

		// generete unique id in string format
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);

		// Dto to entity
		UserDetails userDetails = dtoToEntity(userDto);

		// Perform necessary operations with userDetails
		UserDetails savedUser = userRepo.save(userDetails);

		// Entity to dto
		UserDto resultDto = entityToDto(savedUser);

		return resultDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		// find user by id and save
		UserDetails existingUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given id not found"));

		// upadte info in existing data

		existingUser.setName(userDto.getName());
		// update email if u want
		existingUser.setPassword(userDto.getPassword());
		existingUser.setGender(userDto.getGender());
		existingUser.setAbout(userDto.getAbout());
		existingUser.setImageName(userDto.getImageName());

		// save data
		UserDetails updatedUser = userRepo.save(existingUser);

		UserDto updatedDto = entityToDto(updatedUser);

		return updatedDto;
	}

	@Override
	public PageableResponse<UserDto> getallUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

		// create sort object and pass to pageable page req ,ethod so it gives sorting
		// Create Sort object and pass it to PageRequest for sorting
		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		// pagination create pageble object
		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		// find all users list page
		Page<UserDetails> page = userRepo.findAll(pageable);

//		// now from this page to convert into
//		List<UserDetails> allUsers = page.getContent();
//
//		// now stream and map on userlist on by one and save to dtoList objet and return
//		List<UserDto> dtoList = allUsers.stream()
//				       .map((user) -> entityToDto(user))
//				       .collect(Collectors.toList());
//		PageableResponse<UserDto> response= new PageableResponse<>();
//		 
//		response.setContent(dtoList);
//		response.setPageNumber(page.getNumber());
//		response.setLastPage(page.isLast());
//		response.setPageSize(page.getSize());
//		response.setTotalElement(page.getTotalElements());
//		response.setTotalPages(page.getTotalPages());
		
		PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
		
		return response;
	}

	@Override
	public UserDto getUserById(String userId) {

		UserDetails useById = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given id not found"));
		return entityToDto(useById);
	}

	@Override
	public UserDto getUserByEmail(String email) {

		UserDetails userByEmail = userRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User with given Email id not found"));

		return entityToDto(userByEmail);
	}

	@Override
	public List<UserDto> searchUser(String keyword) {

		List<UserDetails> userList = userRepo.findByNameContaining(keyword);

		List<UserDto> userListDto = userList.stream().map((user) -> entityToDto(user)).collect(Collectors.toList());

		return userListDto;
	}

	private UserDetails dtoToEntity(UserDto userDto) {

//		 UserDetails userdetails = UserDetails.builder()
//			       .userId(userDto.getUserId())
//			       .name(userDto.getName())
//			       .email(userDto.getEmail())
//			       .password(userDto.getPassword())
//			       .about(userDto.getAbout())
//			       .imageName(userDto.getImageName())
//			       .gender(userDto.getGender()).build();
//           					         
		return mapper.map(userDto, UserDetails.class);
	}

	private UserDto entityToDto(UserDetails savedUser) {

//	        UserDto resultDto= UserDto.builder()
//			                    .userId(savedUser.getUserId())
//			                    .name(savedUser.getName())
//			                    .email(savedUser.getEmail())
//			                    .password(savedUser.getPassword())
//			                    .about(savedUser.getAbout())
//			                    .imageName(savedUser.getImageName())
//			                    .gender(savedUser.getGender()).build();
//			
//			return resultdto;
		return mapper.map(savedUser, UserDto.class);
	}

	@Override
	public String deleteUser(String userId) {
		// find user by id and save
		UserDetails existingUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given id not found"));
		
		//delete image with user
		String imageName = existingUser.getImageName();
		String fullPath=imagePath+File.separator+imageName;
		
		//Path path=Paths.get(fullPath);
  
		try {
			Path path=Paths.get(fullPath);
			
			Files.delete(path);
			logger.info("Imagen associated with user also deleted succefully...");
			
		}catch(NoSuchFileException ex) {
			logger.info("No such file found");
			ex.printStackTrace();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// delete user
		userRepo.delete(existingUser);

		return "User delated succefully by given id:" + existingUser.getUserId();
	}

}

package com.shree.Service;

import java.io.IOException;
import java.util.List;

import com.shree.Dto.PageableResponse;
import com.shree.Dto.UserDto;

public interface UserService {

	//create
	UserDto createuser(UserDto userDto);
	
	
	//update
	UserDto updateUser(UserDto userDto, String userId);
	
	
	//delete
	String deleteUser(String userId) ;
	
	
	//getallUser
	 PageableResponse<UserDto> getallUser(int pageNumber, int pageSize,String sortBy,String sortDir);
	
	
	//get single user by id
	UserDto getUserById(String userId);
	
	
	//get single user by email
	UserDto getUserByEmail(String email);
	
	
	
	//search user
	List<UserDto>searchUser(String keyword);
}

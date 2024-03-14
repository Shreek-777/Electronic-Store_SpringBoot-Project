package com.shree.Dto;


import java.util.ArrayList;

import org.hibernate.validator.constraints.Email;

import com.shree.CustomValidation.ImageNameValidation;
import com.shree.Entities.Orders;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data@NoArgsConstructor@AllArgsConstructor@ToString
@Builder
public class UserDto {

    private String userId;
	
	@Size(min = 5,max = 16, message = "Invalid name !!")
	private String name;
	
	@Email(message = "Please provide a valid email address")
	@Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
     message = "Please provide a valid email address")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;
	
	@Size(min = 4,max = 6,message = "Invalid gender")
	private String gender;
	
    @NotBlank(message = "Write something about yourself ")
	private String about;
	
	@ImageNameValidation
	private String imageName;
	
	//private java.util.List<OrdersDto> orders = new ArrayList<>();
}

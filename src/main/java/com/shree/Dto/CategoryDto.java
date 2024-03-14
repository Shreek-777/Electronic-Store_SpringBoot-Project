package com.shree.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder@AllArgsConstructor@NoArgsConstructor
public class CategoryDto {
   
	private String categoryId;

	@NotBlank
	@Size(min = 10 ,message = "Title must be of minimum 4 character !! ")
	private String title;

	@NotBlank(message = "Description Required !!")
	private String description;

	
	private String coverImage;
}

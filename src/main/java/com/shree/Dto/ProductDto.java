package com.shree.Dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shree.Entities.Category;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data@AllArgsConstructor@NoArgsConstructor@ToString
@Builder
public class ProductDto {

	private String productId;

	private String title;

	private String description;

	private int price;

	private int discountedPrice;

	private int quantityInStock;

	private Date addedDate;

	private boolean live;

	private boolean stock;
	
	private String imageName;
	
	@JsonIgnore
	private CategoryDto category;

	
}

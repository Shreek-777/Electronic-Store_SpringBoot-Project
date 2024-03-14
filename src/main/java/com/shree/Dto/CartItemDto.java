package com.shree.Dto;

import com.shree.Entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
public class CartItemDto {

	private int cartItemId;

	private ProductDto product;
	private int quantity;
	private int totalPrice;
	
	//mapping cart
	
}

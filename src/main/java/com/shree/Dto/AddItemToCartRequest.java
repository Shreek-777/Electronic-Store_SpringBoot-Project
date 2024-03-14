package com.shree.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddItemToCartRequest {

	private String productId;
	
	private int quantity;


}

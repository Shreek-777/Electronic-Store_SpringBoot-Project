package com.shree.Dto;

import com.shree.Entities.Orders;
import com.shree.Entities.Product;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor@AllArgsConstructor
@Builder
public class OrderItemDto {

	 private int orderItemId;
	 
	 private int quantity;
	 
	 private int totalPrice;
	
	 private ProductDto product;
	 
	 //private OrdersDto order;
	   
}

package com.shree.Dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shree.Entities.OrderItem;
import com.shree.Entities.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor@ToString@NoArgsConstructor
@Builder
public class OrdersDto {

    private String orderId;
	
    private String orderStatus="PENDING";
    
    private String payementStatus="NOTPAID";
    
    private int orderAmount;
   
	private String billingAddress;
	
	private String contactNumber;
	
	private String billingName;
	
	private Date orderDate= new Date();
	
	private Date deliveryDate;
	
	//private UserDto user;
	
	private List<OrderItemDto> orderItems= new ArrayList<>();
}

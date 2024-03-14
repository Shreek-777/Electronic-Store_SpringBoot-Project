package com.shree.Service;

import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.shree.Dto.CreateOrderRequest;
import com.shree.Dto.OrdersDto;
import com.shree.Dto.PageableResponse;

public interface OrderService {

	//create order
	OrdersDto createOrder(CreateOrderRequest request);
	
	//remove order
	void removeOrder(String orderId);
	
	//get order by user
	List<OrdersDto>getOrdersOfUser(String userId);
	
	//get orders
	PageableResponse<OrdersDto>getOrders(int pageNumber, int pageSize, String SortDir, String  SortBy);
}

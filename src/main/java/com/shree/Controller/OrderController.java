 package com.shree.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shree.Dto.ApiResPonseMessage;
import com.shree.Dto.ApiResPonseMessage.ApiResPonseMessageBuilder;
import com.shree.Dto.CreateOrderRequest;
import com.shree.Dto.OrdersDto;
import com.shree.Dto.PageableResponse;
import com.shree.Service.OrderService;

@RestController
@RequestMapping("orders")
public class OrderController {
    
	@Autowired
	private OrderService orderService;
	
	//create
	@PostMapping
	public ResponseEntity<OrdersDto> createOrder(@RequestBody CreateOrderRequest request){
		
		OrdersDto createOrder = orderService.createOrder(request);
		return new ResponseEntity<OrdersDto>(createOrder, HttpStatus.OK);
	}
	
	//delete order
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResPonseMessage>removeOrder(@PathVariable String orderId){
		orderService.removeOrder(orderId);
		ApiResPonseMessage message = ApiResPonseMessage.builder().message("Order deleted succefully").status(HttpStatus.OK)
				.success(true).build();
	  return new ResponseEntity<ApiResPonseMessage>(message, HttpStatus.OK);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<OrdersDto>>getallOrder(@PathVariable String userId){
		List<OrdersDto> ordersOfUser = orderService.getOrdersOfUser(userId);
		return new ResponseEntity<List<OrdersDto>>(ordersOfUser, HttpStatus.OK);
	} 
	
	@GetMapping("/users")
	public ResponseEntity<PageableResponse<OrdersDto>>getOrders( 
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "10",required=false ) int pageSize, 
			@RequestParam(value = "sortBy", defaultValue = "orderDate",required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
		PageableResponse<OrdersDto> orders = orderService.getOrders(pageNumber, pageSize, sortDir, sortBy);
		return new ResponseEntity<PageableResponse<OrdersDto>>(orders, HttpStatus.OK);
	}
}

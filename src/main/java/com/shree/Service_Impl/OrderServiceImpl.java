package com.shree.Service_Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;

import com.shree.Dto.CreateOrderRequest;
import com.shree.Dto.OrdersDto;
import com.shree.Dto.PageableResponse;
import com.shree.Entities.Cart;
import com.shree.Entities.CartItem;
import com.shree.Entities.OrderItem;
import com.shree.Entities.Orders;
import com.shree.Entities.UserDetails;
import com.shree.Exception.BadApiRequest;
import com.shree.Exception.ResourceNotFoundException;
import com.shree.Helper.Helper;
import com.shree.Repository.CartRepo;
import com.shree.Repository.OrderRepo;
import com.shree.Repository.UserRepo;
import com.shree.Service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public OrdersDto createOrder(CreateOrderRequest request) {
		
		String userId=request.getUserId();
		String cartId=request.getCartId();
	
		//fetch user
		UserDetails user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
		
		//fetch cart
		Cart cart = cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart not availble with this id"));
		Optional<Cart> cart1 = cartRepo.findByUser(user);
		//from cart take cartItems 
		List<CartItem> cartItems = cart.getItems();
		
		//check cart is empty or not
		if(cartItems.size()<=0) {
			throw new BadApiRequest("Cart is empty..");
		}
		
		
		 Orders orders = Orders.builder()
		.billingAddress(request.getBillingAddress())
		.billingName(request.getBillingName())
		.contactNumber(request.getContactNumber())
		.orderDate(new Date())
		.deliveryDate(null)
		.payementStatus(request.getPayementStatus())
		.orderStatus(request.getOrderStatus())
		.orderId(UUID.randomUUID().toString())
		.user(user)
		.build();
		
		
		//order items 
		 AtomicReference<Integer> orderAmount= new AtomicReference<>(0);
       List<OrderItem>orderItems = cartItems.stream().map(cartItem -> {
			//cartItem -> orderitem
			OrderItem orderItem= OrderItem.builder()
			.quantity(cartItem.getQuantity())
			.product(cartItem.getProduct())
			.totalPrice(cartItem.getTotalPrice())
			.order(orders)
			.build();
			orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());
			return orderItem;
		}).collect(Collectors.toList());
		
        //now set this orderitem inside order
        orders.setOrderItems(orderItems);
        //order amount
        orders.setOrderAmount(orderAmount.get());
        
        // clear the cart items after order placed
        cart.getItems().clear();
        cartRepo.save(cart);
        Orders saveOrder = orderRepo.save(orders);
		
		return mapper.map(saveOrder, OrdersDto.class);
	}

	@Override
	public void removeOrder(String orderId) {
		Orders orders = orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order with this id not present"));
		orderRepo.delete(orders);
	}

	@Override
	public List<OrdersDto> getOrdersOfUser(String userId) {
		UserDetails user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
		List<Orders> orders = orderRepo.findByUser(user);
		List<OrdersDto> orderDtos = orders.stream().map((order)->{
		    	return mapper.map(order, OrdersDto.class);
		    }).collect(Collectors.toList());
		
		return orderDtos;
	}
	
//	public List<OrderDto> getOrdersOfUser(String userId) {
//	    UserDetails user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
//	    
//	    List<OrderDto> orderDtos = orderRepo.findByUser(user)
//	        .stream()
//	        .map(order -> mapper.map(order, OrderDto.class))
//	        .collect(Collectors.toList());
//	    
//	    return orderDtos;
//	}


	@Override
	public PageableResponse<OrdersDto> getOrders(int pageNumber, int pageSize, String SortDir, String  SortBy) {
	
		Sort sort = SortDir.equalsIgnoreCase("desc")?(Sort.by(SortBy).descending()):(Sort.by(SortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		Page<Orders> pageOrders = orderRepo.findAll(pageable);
		//System.out.println(pageOrders.getContent());
		PageableResponse<OrdersDto> response = Helper.getPageableResponse(pageOrders, OrdersDto.class);
		
		return response;
	}

}

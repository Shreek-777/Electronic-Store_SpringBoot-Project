package com.shree.Service;

import com.shree.Dto.AddItemToCartRequest;
import com.shree.Dto.CartDto;

public interface CartService {
  
	//add item to cart:
	//case 1: cart for user is not available : we will create the cart 
	//case 2: cart is available add item to cart
	
	CartDto addItemToCart(String userId, AddItemToCartRequest request);
	
	//remove cart Item
	void removeItemFromCart(String userId, int cartItem);
	
	//clear cart
	void clearCart(String userId);
	
	CartDto getCartByUser(String UserId);
}

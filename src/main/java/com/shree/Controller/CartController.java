package com.shree.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shree.Dto.AddItemToCartRequest;
import com.shree.Dto.ApiResPonseMessage;
import com.shree.Dto.CartDto;
import com.shree.Service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {
     
	 @Autowired
	 private CartService cartService;
	 
	 //add item to cart
	 @PostMapping("/{userId}")
	 public ResponseEntity<CartDto>addItemToCart(@PathVariable String userId,  @RequestBody AddItemToCartRequest request){
		 CartDto cart = cartService.addItemToCart(userId, request);
		 return new ResponseEntity<CartDto>(cart, HttpStatus.OK);
		 
	 }
	 
	 //remove cart Item
	 @DeleteMapping("/{userId}/item/{itemId}")
	 public ResponseEntity<ApiResPonseMessage>deleteCartItem(@PathVariable String userId, @PathVariable int itemId){
		 cartService.removeItemFromCart(userId, itemId);
		 ApiResPonseMessage response = ApiResPonseMessage.builder().message("Cart item removed succefully").success(true).status(HttpStatus.OK).build();
	    return new  ResponseEntity<ApiResPonseMessage>(response, HttpStatus.OK);
	 }
	 
	
	 //clear cart
	 @DeleteMapping("/{userId}")
	 public ResponseEntity<ApiResPonseMessage>deleteCartItem(@PathVariable String userId){
		 cartService.clearCart(userId);
		 ApiResPonseMessage response = ApiResPonseMessage.builder().message("Cart is cleared succefully").success(true).status(HttpStatus.OK).build();
	    return new  ResponseEntity<ApiResPonseMessage>(response, HttpStatus.OK);
	 }
	 
	
	 //get cart by user
	 @GetMapping("/{userId}")
	 public ResponseEntity<CartDto>getCartByUser(@PathVariable String userId){
		 CartDto cart = cartService.getCartByUser(userId);
		 return new ResponseEntity<CartDto>(cart,HttpStatus.OK);
	 }
	
}

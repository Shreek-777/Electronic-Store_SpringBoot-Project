package com.shree.Service_Impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shree.Dto.AddItemToCartRequest;
import com.shree.Dto.CartDto;
import com.shree.Dto.ProductDto;
import com.shree.Entities.Cart;
import com.shree.Entities.CartItem;
import com.shree.Entities.Product;
import com.shree.Entities.UserDetails;
import com.shree.Exception.BadApiRequest;
import com.shree.Exception.ResourceNotFoundException;
import com.shree.Repository.CartItemRepo;
import com.shree.Repository.CartRepo;
import com.shree.Repository.ProductRepository;
import com.shree.Repository.UserRepo;
import com.shree.Service.CartService;
import com.shree.Service.ProductService;
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private UserRepo userrepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private CartItemRepo cartItemRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

		// fetch user from db
		UserDetails user = userrepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User is no present !!"));

		String productId = request.getProductId();
		int quantity = request.getQuantity();
		
		if(quantity <=0) {
			throw new BadApiRequest("Requested quantity is not valid !!");
		}
			
				// fetch product from db
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product is no present !!"));

		// fetch the cart object if the user has already cart by findcartbyuser;
		Cart cart = null;
		try {
			cart = cartRepo.findByUser(user).get();
		} catch (NoSuchElementException e) {
			cart = new Cart();
			cart.setCartId(UUID.randomUUID().toString());
			cart.setCreatedDate(new Date());
		}

		// now cart is available
		// perform cart operation

		// if the data is available in cart this will fot here
		// if cart item alraedy present then update;
	AtomicReference<Boolean>updated=new AtomicReference<>(false);
		
	    List<CartItem> items = cart.getItems();
		//List<CartItem> updatedItems = items.stream().map(item -> {
		   items = items.stream().map(item -> {
			if (item.getProduct().getProductId().equals(productId)) {
				// item already present in cart
				item.setQuantity(quantity);
				item.setTotalPrice(quantity * product.getDiscountedPrice());
				updated.set(true);
			}
			return item;
		}).collect(Collectors.toList());
		
		//cart.setItems(items);

		// now set data in cart item and give this cartItem to cart
		// so data can be added to cart

		if (!updated.get()) {
			CartItem cartItem = CartItem.builder().quantity(quantity).product(product)
					.totalPrice(quantity * product.getDiscountedPrice()).cart(cart).build();
			cart.getItems().add(cartItem);
		}

		cart.setUser(user);
		Cart updatedCart = cartRepo.save(cart);

		return mapper.map(updatedCart, CartDto.class);
	}

	
	
	
	@Override
	public void removeItemFromCart(String userId, int cartItemId) {
		
		CartItem cartItem = cartItemRepo.findById(cartItemId)
		  .orElseThrow(()-> new ResourceNotFoundException("Cart Item not Found"));
         cartItemRepo.delete(cartItem);
	}

	@Override
	public void clearCart(String userId) {
		
		UserDetails userDetails = userrepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not Found"));
		Cart cart = cartRepo.findByUser(userDetails).orElseThrow(()-> new ResourceNotFoundException("Cart is not available for the user"));
		cart.getItems().clear();
		cartRepo.save(cart);
	}


	@Override
	public CartDto getCartByUser(String UserId) {
		
		UserDetails userDetails = userrepo.findById(UserId).orElseThrow(()-> new ResourceNotFoundException("user not Found"));
		Cart cart = cartRepo.findByUser(userDetails).orElseThrow(()-> new ResourceNotFoundException("Cart is not available for the user"));
		
		return mapper.map(cart, CartDto.class);
	}

}

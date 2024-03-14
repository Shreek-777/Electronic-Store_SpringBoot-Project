package com.shree.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shree.Entities.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Integer>{

	
}

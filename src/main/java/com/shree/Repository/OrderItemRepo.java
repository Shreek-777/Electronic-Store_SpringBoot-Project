package com.shree.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shree.Entities.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {

	
}

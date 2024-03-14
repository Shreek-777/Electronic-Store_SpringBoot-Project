package com.shree.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int orderItemId;
	 
	 private int quantity;
	 
	 private int totalPrice;
	 
	 @OneToOne
	 private Product product;
	 
	 @ManyToOne
	 private Orders order;
	 
}

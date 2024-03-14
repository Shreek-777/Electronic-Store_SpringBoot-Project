package com.shree.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data@NoArgsConstructor@AllArgsConstructor@ToString
@Builder
@Table(name = "cart_Items")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartItemId;
	
	@OneToOne
	@JoinColumn(name="product_id")
	private Product product;
	private int quantity;
	private int totalPrice;
	
	//mapping cart
	@JsonIgnore 
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private Cart cart;
}

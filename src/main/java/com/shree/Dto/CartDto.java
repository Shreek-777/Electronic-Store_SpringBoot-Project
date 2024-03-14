package com.shree.Dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shree.Entities.Cart;
import com.shree.Entities.CartItem;
import com.shree.Entities.Product;
import com.shree.Entities.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
@Builder
public class CartDto {

	
	private String cartId;
	
	private Date createdDate;
	
    private UserDto user;
	
	private List<CartItem> items = new ArrayList<>();
	
	
}

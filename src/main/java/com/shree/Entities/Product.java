package com.shree.Entities;

import java.util.Date;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor@AllArgsConstructor@ToString
@Builder
@Table(name = "products")
public class Product {

	@Id
	@Column(name = "product_id")
	private String productId;
	
	@Column(name = "product_name", nullable = false)
	private String title;
	
	@Column(name = "description", length = 1000)
	private String description;
	
	@Column(name = "price", nullable = false)
	private int price;
	
	private int discountedPrice;
	
	@Column(name = "quantity_in_stock", nullable = false)
	private int quantityInStock;
	
	private Date addedDate;
	
	private boolean live;
	
	private boolean stock;
	
	private String imageName;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "category_id")
	private Category category;
}

package com.shree.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data@NoArgsConstructor@AllArgsConstructor@ToString
@Builder
@Table(name = "categories")
public class Category {
   
	@Id
	@Column(name  ="id")
	private String categoryId;
	
	@Column(name = "category_title", length = 50,nullable = false)
	private String title;
	
	@Column(name = "category_desc" ,length = 500)
	private String description;
	
	
	private String coverImage;
	
	//Other attrubutes
	@JsonIgnore
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Product> products = new ArrayList<>();
	
	
}
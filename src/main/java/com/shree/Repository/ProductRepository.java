package com.shree.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shree.Dto.ProductDto;
import com.shree.Entities.Category;
import com.shree.Entities.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	
	// search
	Page<Product>findByTitleContaining(String subTitle, Pageable pageable);
	
	Page<Product> findByLiveTrue( Pageable pageable);
	
	//native method
	Page<Product>findByCategory(Category category, Pageable pageable);
	
	// other methods
	//custom finder method
	//query method
	
}

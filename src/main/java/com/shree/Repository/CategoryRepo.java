package com.shree.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shree.Entities.Category;

public interface CategoryRepo extends JpaRepository<Category, String> {

	
}

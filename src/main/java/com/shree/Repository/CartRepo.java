package com.shree.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shree.Entities.Cart;
import com.shree.Entities.UserDetails;

public interface CartRepo extends JpaRepository<Cart, String> {

   Optional<Cart> findByUser(UserDetails user);
}

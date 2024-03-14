package com.shree.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shree.Entities.Orders;
import com.shree.Entities.UserDetails;

public interface OrderRepo extends JpaRepository<Orders, String>{

	List<Orders>findByUser(UserDetails user);
}

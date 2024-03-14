package com.shree.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shree.Entities.UserDetails;

public interface UserRepo extends JpaRepository<UserDetails, String> {

	Optional<UserDetails> findByEmail(String email);

	Optional<UserDetails> findByEmailAndPassword(String email, String password);

	List<UserDetails> findByNameContaining(String keyword);
}

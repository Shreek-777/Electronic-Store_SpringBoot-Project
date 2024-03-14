package com.shree.Entities;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.AssertFalse.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data@NoArgsConstructor@AllArgsConstructor@ToString
@Builder
public class UserDetails {
    
	@Id
	private String userId;
	
	@Column(name = "user_name")
	private String name;
	
	@Column(name = "user_email", unique = true)
	private String email;
	
	@Column(name = "user_password",length = 8)
	private String password;
	
	
	private String gender;
	
	@Column(length = 500)
	private String about;
	
	@Column(name = "user_image_name")
	private String imageName;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	@Column(name = "order_list")
	private java.util.List<Orders> orders = new ArrayList<>();
	
}

package com.shree.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.shree.Entities.UserDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "cart")
public class Cart {

	@Id
	private String cartId;
	
	private Date createdDate;
	@OneToOne
    private UserDetails user;
	
	@OneToMany(mappedBy = "cart",fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CartItem> items = new ArrayList<>();
	
}

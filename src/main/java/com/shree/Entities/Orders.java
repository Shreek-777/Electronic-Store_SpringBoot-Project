package com.shree.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class Orders {
	
    @Id
	private String orderId;
	
    //PENDING  DISPATCHED  DELIVERED
    //ENUM
    private String orderStatus;
    
    //NOT PAID  PAID 
    private String payementStatus;
    
    @Column(nullable = false)
    private int orderAmount;
    
    @Column(length = 1000)
	private String billingAddress;
	
	private String contactNumber;
	
	private String billingName;
	
	private Date orderDate;
	
	private Date deliveryDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserDetails user;
	
	@OneToMany(mappedBy ="order", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	private List<OrderItem> orderItems= new ArrayList<>();
}

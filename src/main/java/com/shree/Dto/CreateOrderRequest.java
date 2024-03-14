package com.shree.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

	@NotBlank(message = "Cart id is required")
	private String cartId;
	@NotBlank(message = "User id is required")
	private String userId;
	
    private String orderStatus="PENDING";
    private String payementStatus="NOTPAID";
    
    @NotBlank(message = "BillingAddress id is required")
	private String billingAddress;
    @NotBlank(message = "ContactNumber id is required")
	private String contactNumber;
    @NotBlank(message = "BillingName id is required")
	private String billingName;
}

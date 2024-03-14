package com.shree.Exception;

import lombok.Builder;



@Builder
public class BadApiRequest extends RuntimeException {

	public BadApiRequest() {
		super("Bad Api Request !!");
	}
	
    public BadApiRequest(String message) {
		super(message);
	}
}

	

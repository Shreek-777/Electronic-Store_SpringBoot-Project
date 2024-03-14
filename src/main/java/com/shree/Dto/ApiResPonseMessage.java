package com.shree.Dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResPonseMessage {

	private String message;
	private boolean success;
	private HttpStatus status;
}

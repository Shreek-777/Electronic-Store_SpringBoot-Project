package com.shree.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shree.Dto.ApiResPonseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	//handler resource not found exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResPonseMessage>resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		
		logger.info("Global Exception invoked rnf !!");
		
	    ApiResPonseMessage responseMessage = ApiResPonseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
	    
	    return new ResponseEntity<ApiResPonseMessage>(responseMessage, HttpStatus.OK);
	}
	
	@ExceptionHandler(BadApiRequest.class)
	public ResponseEntity<ApiResPonseMessage>badApiRequestHandler(BadApiRequest ex){
		
		logger.info("Global Exception invoked (bad api) !!");
		  
        ApiResPonseMessage responseMessage = ApiResPonseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();

		return new ResponseEntity<ApiResPonseMessage>(responseMessage,HttpStatus.BAD_REQUEST);
		
	}
	
}

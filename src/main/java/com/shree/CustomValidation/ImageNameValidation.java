package com.shree.CustomValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface  ImageNameValidation {
  
	//error message
	String message() default "Invalid image Name !!";
    
	//represent group of constraints
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}

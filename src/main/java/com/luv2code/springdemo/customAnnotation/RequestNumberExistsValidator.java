package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.entity.Request;
import com.luv2code.springdemo.service.RequestService;

// Custom validator for Request Number, checks if request with the desired number exists
public class RequestNumberExistsValidator implements ConstraintValidator<RequestNumberExists, Integer> {

	private String messageExists;

	
	@Autowired
	private RequestService requestService;
	
	@Override
	public void initialize(RequestNumberExists constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(Integer number, ConstraintValidatorContext context) {				
		
		context.disableDefaultConstraintViolation();
		
		Request lastRequest = requestService.getLastRequest();
		int lastRequestId = 0;
		
		if(lastRequest != null) {
			lastRequestId = lastRequest.getId();
		}
		
		// if number is null validation passes, but not for @NotNull
		if(number == null) {
			return true;
		}else {
			if (number > lastRequestId) {
				
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Request dose not exists);
				context.buildConstraintViolationWithTemplate(messageExists).addConstraintViolation();
				return false;
			}else {
				return true;
			}
		}		
		
	}

}

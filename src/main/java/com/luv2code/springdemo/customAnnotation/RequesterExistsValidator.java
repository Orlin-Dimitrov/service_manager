package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.service.RequesterService;

// Custom validator for Requester, checks if Requester already exists
public class RequesterExistsValidator implements ConstraintValidator<RequesterExists, String> {

	private String messageExists;

	
	@Autowired
	private RequesterService requesterService;
	
	@Override
	public void initialize(RequesterExists constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {				
		
		context.disableDefaultConstraintViolation();
		
		// Validate only if name is not null, else validation will pass, but will stop at @NotNull
		if (name != null) {
	
			boolean exists = requesterService.requesterExists(name);
			
			if(exists) {
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Requester exists);
				context.buildConstraintViolationWithTemplate(messageExists).addConstraintViolation();
				return false;
			}else {
				return true;
			}
			
		}else {
			return true;
		}						
	}

}

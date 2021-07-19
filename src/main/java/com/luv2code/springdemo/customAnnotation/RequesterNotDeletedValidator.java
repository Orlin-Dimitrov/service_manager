package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.entity.Requester;
import com.luv2code.springdemo.service.RequesterService;

// Custom validator for Requester, checks if Requester is Not deleted
public class RequesterNotDeletedValidator implements ConstraintValidator<RequesterNotDeleted, Integer> {

	private String messageExists;

	
	@Autowired
	private RequesterService requesterService;
	
	@Override
	public void initialize(RequesterNotDeleted constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(Integer theId, ConstraintValidatorContext context) {						
		
		context.disableDefaultConstraintViolation();
		
		// Validate only if theId is more than 0, else validation will pass, but will stop at @Min=1.
		if (theId > 0) {
	
			Requester requester = requesterService.getRequester(theId);
			
			// If Requester is null, it has been deleted
			if(requester == null) {
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Requester deleted);
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

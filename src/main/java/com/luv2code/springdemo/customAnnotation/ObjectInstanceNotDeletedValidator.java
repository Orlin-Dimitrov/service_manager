package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.entity.ObjectInstance;
import com.luv2code.springdemo.service.ObjectInstanceService;

// Custom validator for Object Instance, checks if Object Instance is Not deleted
public class ObjectInstanceNotDeletedValidator implements ConstraintValidator<ObjectInstanceNotDeleted, Integer> {

	private String messageExists;

	
	@Autowired
	private ObjectInstanceService objectInstanceService;
	
	@Override
	public void initialize(ObjectInstanceNotDeleted constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(Integer theId, ConstraintValidatorContext context) {						
		
		context.disableDefaultConstraintViolation();
		
		// Validate only if theId is more than 0, else validation will pass, but will stop at @Min=1.
		if (theId > 0) {
	
			ObjectInstance objectInstance = objectInstanceService.getObjectInstanceById(theId);
			
			// If ObjectInstance is null, it has been deleted
			if(objectInstance == null) {
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Instance deleted);
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

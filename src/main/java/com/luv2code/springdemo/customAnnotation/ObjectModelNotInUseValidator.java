package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.service.ObjectModelService;

// Custom validator for Object Model, checks if Object Model has associated Object Instances
public class ObjectModelNotInUseValidator implements ConstraintValidator<ObjectModelNotInUse, Integer> {

	private String messageExists;

	
	@Autowired
	private ObjectModelService objectModelService;
	
	@Override
	public void initialize(ObjectModelNotInUse constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(Integer theId, ConstraintValidatorContext context) {				
		
		context.disableDefaultConstraintViolation();
		

		// Validate only if theId is more than 0, else validation will pass, but will stop at @Min=1
		if (theId > 0) {
	
			// If ObjectModel is deleted inUse will be false. Validation will stop at @ObjectModelNotDeleted
			boolean inUse = objectModelService.objectModelInUse(theId);
			
			if(inUse) {
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Model inUse);
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

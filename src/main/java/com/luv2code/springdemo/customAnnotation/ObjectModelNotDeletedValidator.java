package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.entity.ObjectModel;
import com.luv2code.springdemo.service.ObjectModelService;

// Custom validator for Object Model, checks if Object Model is Not deleted
public class ObjectModelNotDeletedValidator implements ConstraintValidator<ObjectModelNotDeleted, Integer> {

	private String messageExists;

	
	@Autowired
	private ObjectModelService objectModelService;
	
	@Override
	public void initialize(ObjectModelNotDeleted constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(Integer theId, ConstraintValidatorContext context) {						
		
		context.disableDefaultConstraintViolation();
		
		// Validate only if theId is more than 0, else validation will pass, but will stop at @Min=1.
		if (theId > 0) {
	
			ObjectModel objectModel = objectModelService.getObjectModel(theId);
			
			// If ObjectModel is null, it has been deleted
			if(objectModel == null) {
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Model deleted);
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

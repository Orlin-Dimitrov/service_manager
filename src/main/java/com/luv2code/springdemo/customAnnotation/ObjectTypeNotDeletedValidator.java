package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.entity.ObjectType;
import com.luv2code.springdemo.service.ObjectTypeService;

// Custom validator for Object Type, checks if Object Type is Not deleted
public class ObjectTypeNotDeletedValidator implements ConstraintValidator<ObjectTypeNotDeleted, Integer> {

	private String messageExists;

	
	@Autowired
	private ObjectTypeService objectTypeService;
	
	@Override
	public void initialize(ObjectTypeNotDeleted constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(Integer theId, ConstraintValidatorContext context) {						
		
		context.disableDefaultConstraintViolation();
		
		// Validate only if theId is more than 0, else validation will pass, but will stop at @Min=1.
		if (theId > 0) {
	
			ObjectType objectType = objectTypeService.getObjectType(theId);
			
			// If ObjectType is null, it has been deleted
			if(objectType == null) {
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Type deleted);
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

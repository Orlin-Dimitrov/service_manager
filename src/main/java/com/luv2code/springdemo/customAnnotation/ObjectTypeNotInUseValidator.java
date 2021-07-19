package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.service.ObjectTypeService;

// Custom validator for Object Type, checks if Object Type has associated Object Models
public class ObjectTypeNotInUseValidator implements ConstraintValidator<ObjectTypeNotInUse, Integer> {

	private String messageExists;

	
	@Autowired
	private ObjectTypeService objectTypeService;
	
	@Override
	public void initialize(ObjectTypeNotInUse constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(Integer theId, ConstraintValidatorContext context) {				
		
		context.disableDefaultConstraintViolation();
		

		// Validate only if theId is more than 0, else validation will pass, but will stop at @Min=1
		if (theId > 0) {
	
			// If ObjectType is deleted inUse will be false. Validation will stop at @ObjectTypeNotDeleted
			boolean inUse = objectTypeService.objectTypeInUse(theId);
			
			if(inUse) {
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Type inUse);
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

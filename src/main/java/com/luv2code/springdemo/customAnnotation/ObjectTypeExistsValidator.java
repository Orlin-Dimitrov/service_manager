package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.service.ObjectTypeService;

// Custom validator for Object Type, checks if Object Type already exists
public class ObjectTypeExistsValidator implements ConstraintValidator<ObjectTypeExists, String> {

	private String messageExists;

	
	@Autowired
	private ObjectTypeService objectTypeService;
	
	@Override
	public void initialize(ObjectTypeExists constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(String type, ConstraintValidatorContext context) {				
		
		context.disableDefaultConstraintViolation();
		
		// Validate only if type is not null, else validation will pass, but will stop at @NotNull
		if (type != null) {
	
			boolean exists = objectTypeService.typeExists(type);
			
			if(exists) {
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Type exists);
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

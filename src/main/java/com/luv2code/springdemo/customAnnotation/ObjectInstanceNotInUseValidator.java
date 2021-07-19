package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.service.ObjectInstanceService;

// Custom validator for Object Instance, checks if Object Instance in NOT used in a Request
public class ObjectInstanceNotInUseValidator implements ConstraintValidator<ObjectInstanceNotInUse, Integer> {

	private String messageExists;

	
	@Autowired
	private ObjectInstanceService objectInstanceService;
	
	@Override
	public void initialize(ObjectInstanceNotInUse constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(Integer theId, ConstraintValidatorContext context) {				
		
		context.disableDefaultConstraintViolation();
		

		// Validate only if theId is more than 0, else validation will pass, but will stop at @Min=1
		if (theId > 0) {
	
			// If ObjectInstance is deleted inUse will be false. Validation will stop at @ObjectInstanceNotDeleted
			boolean inUse = objectInstanceService.objectInstanceInUse(theId);
			
			if(inUse) {
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Instance inUse);
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

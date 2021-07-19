package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.helperObjects.AddObjectInstance;
import com.luv2code.springdemo.helperObjects.AdminEditObjectInstance;
import com.luv2code.springdemo.service.ObjectInstanceService;

// Custom validator for Object Instance, checks if Object Instance (Serial number) already exists
public class ObjectInstanceExistsValidator implements ConstraintValidator<ObjectInstanceExists, Object> {

	private String messageExists;

	
	@Autowired
	private ObjectInstanceService objectInstanceService;
	
	@Override
	public void initialize(ObjectInstanceExists constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {				
		
		context.disableDefaultConstraintViolation();
		
		String instanceName = null;
		int parentModelId = 0;
		
		// Validation is used for AddObjectInstance and AdminEditObject|Instance. Both classes have the same methods but have differences.
		// Not sure with type casting
		if(value instanceof AddObjectInstance) {
			AddObjectInstance instance = (AddObjectInstance) value;
			
			instanceName = instance.getInstance();
			parentModelId = instance.getModelId();
		}
		else if(value instanceof AdminEditObjectInstance) {
			AdminEditObjectInstance instance = (AdminEditObjectInstance) value;
			
			instanceName = instance.getInstance();
			parentModelId = instance.getModelId();
		}else {
			
		}
		
		
		// Validate only if instanceName is not null and parentModelId is > 0, else validation will pass, but will stop at @NotNull
		if (instanceName != null && parentModelId > 0) {
	
			
//			System.out.println(">>> INSIDE CUSTOM ANOTATION");
//			System.out.println(">>> Serial number: " + instanceName);
//			System.out.println(">>> Model id: " + parentModelId);
			
			boolean exists = objectInstanceService.instanceExists(instanceName, parentModelId);
			
			if(exists) {
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Serial number exists");
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

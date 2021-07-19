package com.luv2code.springdemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.helperObjects.AddObjectModel;
import com.luv2code.springdemo.helperObjects.AdminEditObjectModel;
import com.luv2code.springdemo.service.ObjectModelService;

// Custom validator for Object Model, checks if Object Model already exists
public class ObjectModelExistsValidator implements ConstraintValidator<ObjectModelExists, Object> {

	private String messageExists;

	
	@Autowired
	private ObjectModelService objectModelService;
	
	@Override
	public void initialize(ObjectModelExists constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageExists = constraintAnnotation.message();

	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {				
		
		context.disableDefaultConstraintViolation();
		
		String modelName = null;
		int parentTypeId = 0;
		
		// Validation is used for AddObjectModel and AdminEditObjectModel. Both classes have the same methods but have differences.
		// Not sure with type casting
		if(value instanceof AddObjectModel) {
			AddObjectModel model = (AddObjectModel) value;
			
			modelName = model.getModel();
			parentTypeId = model.getTypeId();
		}
		else if(value instanceof AdminEditObjectModel) {
			AdminEditObjectModel model = (AdminEditObjectModel) value;
			
			modelName = model.getModel();
			parentTypeId = model.getTypeId();
		}else {
			
		}				
		
		// Validate only if model is not null and typeId is > 0, else validation will pass, but will stop at @NotNull
		if (modelName != null && parentTypeId > 0) {

			
//			System.out.println(">>> INSIDE CUSTOM ANOTATION");
//			System.out.println(">>> Model name: " + modelName);
//			System.out.println(">>> Type id: " + parentTypeId);
			
			boolean exists = objectModelService.modelExists(modelName, parentTypeId);
			
			if(exists) {
//				System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, Model exists");
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

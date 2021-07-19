package com.luv2code.springdemo.customAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ObjectModelExistsValidator.class)
//@Target({ElementType.METHOD, ElementType.FIELD})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface ObjectModelExists {
	

	
	String message() default "{addObjectModel.exists}";
	
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};

}

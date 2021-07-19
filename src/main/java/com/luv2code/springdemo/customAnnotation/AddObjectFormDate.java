package com.luv2code.springdemo.customAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = AddObjectFormDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface AddObjectFormDate {
	
	String message() default "{helperForm.dateSelected.invalidDate}";
	
	String messageNotInRange() default "{helperForm.dateSelected.notInRange}";
	
	String messageNotBeforeLastRequest() default "{helperForm.dateSelected.notBeforeLastRequest}";
	
	String messageNotInTheFuture() default "{helperForm.dateSelected.notInTheFuture}";
	
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};

}

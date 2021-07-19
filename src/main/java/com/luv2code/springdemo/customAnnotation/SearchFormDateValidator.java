package com.luv2code.springdemo.customAnnotation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

// Custom validator for Updating Request's Date By ADMIN !!! NO PAST DATE RESTRICTION !!! <--- !!! THIS IS REMOVED DATE CHANGE IS DISABLED
public class SearchFormDateValidator implements ConstraintValidator<SearchFormDate, String> {

	private String messageDateFormated;
	private String messageDateInRange;
	private String messageDateInTheFuture;
	
	// autowired custom date formater of pattern "dd-MM-yyyy"
	@Autowired
	private DateTimeFormatter customDateFormatter;
	
	@Override
	public void initialize(SearchFormDate constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageDateFormated = constraintAnnotation.message();
		messageDateInRange = constraintAnnotation.messageNotInRange();
		messageDateInTheFuture = constraintAnnotation.messageNotInTheFuture();
	}
	
	@Override
	public boolean isValid(String date, ConstraintValidatorContext context) {				
		
		context.disableDefaultConstraintViolation();
		
		// If date form field is empty, validation passes, but validation is not passed for @NotNull.
		if(date == null) {
			return true;
		}else{
						
			boolean thrownEx = false;
			LocalDate dateAfterFormat = null;
			
			try {
				// trying to parse the String date(selectedDate) to a Date object
				dateAfterFormat = LocalDate.parse(date, customDateFormatter);
//				System.out.println("Date after format: " + dateAfterFormat);
											
				LocalDate dateStart = LocalDate.parse("01-01-2016", customDateFormatter);
				LocalDate dateEnd = LocalDate.parse("31-12-2026", customDateFormatter);
				
				// if date is not in range from 2016-01-01 to 2026-12-31 validation is not passed
				if(dateAfterFormat.isBefore(dateStart) || dateAfterFormat.isAfter(dateEnd)) {
					context.buildConstraintViolationWithTemplate(messageDateInRange).addConstraintViolation();
					return false;
				}else{
									
					LocalDate dateNow = LocalDate.now();
					
					//If date is not in the future validation will pass. Date can be any in the past after 2016-01-01. 
					if(dateAfterFormat.isAfter(dateNow)){
						context.buildConstraintViolationWithTemplate(messageDateInTheFuture).addConstraintViolation();
						return false;
					}else{
						// date is properly formated and not in the future, and validation passes
						return true;
					}
				}					
			}			
			
			/* If the parse throws exception - the selectedDate is not in the format dd-MM-yyyy, exception is caught.
			   Boolean value thrownEx becomes true.
			*/
			catch (DateTimeParseException exc) {
			    thrownEx = true;		   
			}
			
			//Finally, if exception is thrown ( date is not formated ), validation will not pass.
			finally {

				if(thrownEx) {
//					System.out.println(">>> THROWN EXCEPTION INSIDE ANTOTATION, DATE NOT PROPERLY FORMATED");
					context.buildConstraintViolationWithTemplate(messageDateFormated).addConstraintViolation();
					return false;
				}										
			}
		}
		// change to true if error
		return false;			
	}

}

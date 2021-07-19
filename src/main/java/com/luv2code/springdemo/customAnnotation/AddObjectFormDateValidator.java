package com.luv2code.springdemo.customAnnotation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.luv2code.springdemo.entity.Request;
import com.luv2code.springdemo.service.RequestService;

// Custom validator for new Request's Date
public class AddObjectFormDateValidator implements ConstraintValidator<AddObjectFormDate, String> {

	private String messageDateFormated;
	private String messageDateInRange;
	private String messageDateBeforeLastRequest;
	private String messageDateInTheFuture;
	
	// autowired custom date formater of pattern "dd-MM-yyyy"
	@Autowired
	private DateTimeFormatter customDateFormatter;
	
	@Autowired
	private RequestService requestService;
	
	@Override
	public void initialize(AddObjectFormDate constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageDateFormated = constraintAnnotation.message();
		messageDateInRange = constraintAnnotation.messageNotInRange();
		messageDateBeforeLastRequest = constraintAnnotation.messageNotBeforeLastRequest();
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
					
					// retrieving the last request date, necessary for date validation
					Request lastRequest = requestService.getLastRequest();
					LocalDate dateLastRequest = null;
					
					if(lastRequest != null) {
						dateLastRequest = lastRequest.getDate();
					}else {
						dateLastRequest = LocalDate.parse("01-01-2016", customDateFormatter);
					}
//					System.out.println(">>> Last Request Date: " + dateLastRequest);	
					
					LocalDate dateNow = LocalDate.now();
					
					//If date is greater or equal to the last Request's date or not in the future validation will pass
					if(dateAfterFormat.isBefore(dateLastRequest)) {
						context.buildConstraintViolationWithTemplate(messageDateBeforeLastRequest).addConstraintViolation();
						return false;
					}else if(dateAfterFormat.isAfter(dateNow)){
						context.buildConstraintViolationWithTemplate(messageDateInTheFuture).addConstraintViolation();
						return false;
					}else{
						// date is properly formated and not before the date of the last request or in the future, and validation passes
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

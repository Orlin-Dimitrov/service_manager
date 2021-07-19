package com.luv2code.springdemo.customAnnotation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.AllowedCharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

// Custom validator to check if entry uses English characters, symbols and no whitespace are used
public class AllowedCharactersValidator implements ConstraintValidator<AllowedCharacters, String> {

	private String messageInValid;
	
	@Override
	public void initialize(AllowedCharacters constraintAnnotation) {
		
		// messages defined in messages.properties file
		messageInValid = constraintAnnotation.message();
	}
	
	@Override
	public boolean isValid(String input, ConstraintValidatorContext context) {				
		
		context.disableDefaultConstraintViolation();
		
		// Check if input field is not empty
		if(input != null) {
						
//			System.out.println("Input Value: " + input); 
			
			String letters = EnglishCharacterData.Alphabetical.getCharacters();
			String numbers = EnglishCharacterData.Digit.getCharacters();
			String special = EnglishCharacterData.Special.getCharacters();
			
			StringBuilder sb = new StringBuilder();
			sb.append(letters);
			sb.append(numbers);
			sb.append(special);
			
			char allowed[] = sb.toString().toCharArray();
			
	        PasswordValidator validator = new PasswordValidator(Arrays.asList(
	                
	        		// at least 8 characters
//	                new LengthRule(8, 30),

//	                // English upper-case character
//	                new CharacterRule(EnglishCharacterData.UpperCase),
//
//	                // English lower-case character
//	                new CharacterRule(EnglishCharacterData.LowerCase),
	        		
//	                new CharacterRule(EnglishCharacterData.Alphabetical,0),
	                	        			        		
	                new AllowedCharacterRule(allowed),

	                // no whitespace
	                new WhitespaceRule()

	            ));
			
	        RuleResult result = validator.validate(new PasswordData(input));
	        
	        if (result.isValid()) {
	            return true;
	        }else {
//	        		System.out.println(">>> INVALID CHARACTERS: " + result.getDetails());		

					context.buildConstraintViolationWithTemplate(messageInValid).addConstraintViolation();
					return false;
			}			
		}else {
			return true;
		}
	
	}

}

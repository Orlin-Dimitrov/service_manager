package com.luv2code.springdemo.helperObjects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class AddObjectFormBACKUP {

	private int objectTypeIdInService;	
	private int objectModelIdInService;	
	private int objectInstanceIdInService;
	
	private int objectTypeIdInOperation;
	private int objectModelIdInOperation;
	private int objectInstanceIdInOperation;

	@Min(value = 1, message="{helperForm.requesterId.min}")	
	private int requesterId;
	
	private boolean checkBoxInService;
	private boolean checkBoxInOperation;
	
	@NotNull(message="{helperForm.selectedDate.notSelected}")
	private String selectedDate;
	
	private boolean serviceSelectedValidation;
	private boolean operationSelectedValidation;
	
	private boolean itemSelectedValidation;
	
	private boolean objectTypesMatchValidation;
	
	private boolean dateFormated;
	
	private String lastRequestDate;
	
	private boolean dateValid;
	
	public AddObjectFormBACKUP() {
	}

	
	/* Check if selectedDate is properly formated YYYY-MM-DD and in the range from 2016-01-01 to 2026-12-31.
	 * If selectedDate is null validation is passed and error message won't be displayed.
	 * But error is displayed for - selectedDate @NotNull
	*/
	@AssertTrue(message="{helperForm.dateFormated.invalidDate}")
	public boolean isDateFormated() {
		
		dateFormated = false;
		boolean dateIsInRange = false;
		boolean thrownEx = false;
		
		//Only check for proper Date format if selectedDate is not null, or else InvocationTargetException is thrown and the page won't load.
		if (selectedDate != null) {
			try {
				
				// trying to parse the String selectedDate to a Date object
			    LocalDate date = LocalDate.parse(selectedDate, DateTimeFormatter.ISO_LOCAL_DATE);
			    //System.out.printf("%s%n", date);
			    
			    // Limiting the Date range from 2016-01-01 to 2026-12-31
			    LocalDate dateStart = LocalDate.parse("2015-12-31", DateTimeFormatter.ISO_LOCAL_DATE);
			    LocalDate dateEnd = LocalDate.parse("2027-01-01", DateTimeFormatter.ISO_LOCAL_DATE);

			    if(date.isAfter(dateStart) && date.isBefore(dateEnd)) {
			    	dateIsInRange = true;
			    }
			}
			
			/* If the parse throws exception - the selectedDate is not in the format YYYY-MM-DD, exception is caught.
			   Boolean value thrownEx becomes true.
			*/
			catch (DateTimeParseException exc) {
			    //System.out.printf("%s is not parsable!%n", selectedDate);
			    thrownEx = true;		   
			}
			
			//Finally, if exception is not thrown and the selectedDate is in desired range, validation passes.
			finally {
				if(!thrownEx && dateIsInRange) {
					dateFormated = true;
				}
			}
		
		//Validation passes when selectedDate is null and error message is not displayed. But error is displayed for - selectedDate @NotNull	
		}else {
			dateFormated = true;
		}
	
		return dateFormated;
	}


	public void setDateFormated(boolean dateFormated) {
		this.dateFormated = dateFormated;
	}
	
		
    // model attribute ${lastRequestDate} required for form validation of Date
	public String getLastRequestDate() {
		return lastRequestDate;
	}

	public void setLastRequestDate(String lastRequestDate) {
		this.lastRequestDate = lastRequestDate;
	}


	/* Check if selectedDate is not before the date of the last Request entry and not after today.
	 * Validation is made if selectedDate is not null and selectedDate is properly formated.
	 * 
	 * If selectedDate is null validation is passed and error message won't be displayed.
	 * But error is displayed for - selectedDate @NotNull
	 * 
	 * If selectedDate is Not null and isDateFormated() == FALSE, validation is passed and error message won't be displayed.
	 * But error is displayed for - isDateFormated() == FALSE
	*/
	@AssertTrue(message="{helperForm.dateValid.notInRange}")
	public boolean isDateValid() {
		
		dateValid = false;
	
		// If selectedDate is not null and date is properly formated proceed
		if(selectedDate != null && this.isDateFormated() == true) {
								 
			// Tested with no entries in DB !!!!!			
			System.out.println("INSIDE isDateInRange validation: ");
			
			// Limiting the Date range from the date of the last Request to now (DateTimeFormatter.ISO_LOCAL_DATE))
			LocalDate dateStart = LocalDate.parse(lastRequestDate, DateTimeFormatter.ISO_LOCAL_DATE);
			LocalDate dateEnd = LocalDate.now();
			
			// No try/catch block because selectedDate is not null and date must be properly formated
			LocalDate date = LocalDate.parse(selectedDate, DateTimeFormatter.ISO_LOCAL_DATE);
			
			//If date is greater or equal to the last Request's date or not in the future validation will pass
			if(date.isBefore(dateStart) || date.isAfter(dateEnd)) {
				dateValid = false;
			}else{
				dateValid = true;
			}
			System.out.println("END");		
		
		}else {
			dateValid = true;
		}
	
		return dateValid;
	}


	public void setDateValid(boolean dateValid) {
		this.dateValid = dateValid;
	}


	
	
	
	
	
	@AssertTrue(message="{helperForm.itemSelectedValidation.notSelected}")
	public boolean isItemSelectedValidation() {
		
		//if none of the options checkBoxInService and checkBoxInOperation is selected 
		// validation is NOT passed
		if(checkBoxInService == false && checkBoxInOperation == false) {
			itemSelectedValidation = false;
		}else {
			itemSelectedValidation = true;
		}		
		return itemSelectedValidation;
	}

	public void setItemSelectedValidation(boolean itemSelectedValidation) {
		this.itemSelectedValidation = itemSelectedValidation;
	}
	
	
	@AssertTrue(message="{helperForm.objectInstanceIdInService.notSelected}")
	public boolean isServiceSelectedValidation() {
		
		//only if checkBoxInService is selected 
		//and the objectInstanceIdInService is different from 0 (is selected from the list)
		// validation is passed
		if(checkBoxInService == true && objectInstanceIdInService == 0) {

			serviceSelectedValidation = false;
		
		// if checkBoxInService is NOT selected, select list are Disabled but validation will still pass	
		//Objects "ObjectInstance" are created in "/review"
		}else {
			serviceSelectedValidation = true;
		}
		
		return serviceSelectedValidation;
	}
	
	public void setServiceSelectedValidation(boolean serviceSelectedValidation) {
		this.serviceSelectedValidation = serviceSelectedValidation;
	}

	
	@AssertTrue(message="{helperForm.objectInstanceIdInOperation.notSelected}")
	public boolean isOperationSelectedValidation() {
		
		//only if checkBoxInOperation is selected 
		//and the objectInstanceIdInOperation is different from 0 (is selected from the list)
		// validation is passed
		if(checkBoxInOperation == true && objectInstanceIdInOperation == 0) {

			operationSelectedValidation = false;
		
		// if checkBoxInOperation is NOT selected, select list are Disabled but validation will still pass	
		//Objects "ObjectInstance" are created in "/review"
		}else {
			operationSelectedValidation = true;
		}
		
		return operationSelectedValidation;
	}
	
	public void setOperationSelectedValidation(boolean operationSelectedValidation) {
		this.operationSelectedValidation = operationSelectedValidation;
	}
	
	
	@AssertTrue(message="{helperForm.objectTypesMatchValidation.doNotMatch}")
	public boolean isObjectTypesMatchValidation() {
		
		//if objectTypeIdInService and objectTypeIdInOperation do not match 
		// validation is NOT passed
		if(checkBoxInService == true && checkBoxInOperation == true && objectTypeIdInService != objectTypeIdInOperation) {
			objectTypesMatchValidation = false;
		}else {
			objectTypesMatchValidation = true;
		}		
		return objectTypesMatchValidation;
	}
	
	public void setObjectTypesMatchValidation(boolean objectTypesMatchValidation) {
		this.objectTypesMatchValidation = objectTypesMatchValidation;
	}
	
	
	public int getObjectTypeIdInService() {
		return objectTypeIdInService;
	}

	public void setObjectTypeIdInService(int objectTypeIdInService) {
		this.objectTypeIdInService = objectTypeIdInService;
	}

	public int getObjectModelIdInService() {
		return objectModelIdInService;
	}

	public void setObjectModelIdInService(int objectModelIdInService) {
		this.objectModelIdInService = objectModelIdInService;
	}

	public int getObjectInstanceIdInService() {
		return objectInstanceIdInService;
	}

	public void setObjectInstanceIdInService(int objectInstanceIdInService) {
		this.objectInstanceIdInService = objectInstanceIdInService;
	}

	public int getObjectTypeIdInOperation() {
		return objectTypeIdInOperation;
	}

	public void setObjectTypeIdInOperation(int objectTypeIdInOperation) {
		this.objectTypeIdInOperation = objectTypeIdInOperation;
	}

	public int getObjectModelIdInOperation() {
		return objectModelIdInOperation;
	}

	public void setObjectModelIdInOperation(int objectModelIdInOperation) {
		this.objectModelIdInOperation = objectModelIdInOperation;
	}

	public int getObjectInstanceIdInOperation() {
		return objectInstanceIdInOperation;
	}

	public void setObjectInstanceIdInOperation(int objectInstanceIdInOperation) {
		this.objectInstanceIdInOperation = objectInstanceIdInOperation;
	}

	public int getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(int requesterId) {
		this.requesterId = requesterId;
	}
	

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public boolean isCheckBoxInService() {
		return checkBoxInService;
	}

	public void setCheckBoxInService(boolean checkBoxInService) {
		this.checkBoxInService = checkBoxInService;
	}

	public boolean isCheckBoxInOperation() {
		return checkBoxInOperation;
	}

	public void setCheckBoxInOperation(boolean checkBoxInOperation) {
		this.checkBoxInOperation = checkBoxInOperation;
	}

	
	
}

package com.luv2code.springdemo.helperObjects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class AddObjectFormBACKUP2working {

	@Min(value = 1, message="{helperForm.requesterId.min}")	
	private int requesterId;	

	@NotNull(message="{helperForm.dateSelected.notSelected}")
	private String dateSelected;		
	private boolean dateFormated;
	private boolean dateInRange;	
	private boolean dateValid;
	private LocalDate dateAfterFormat;	
	private String lastRequestDate;		
	
	private boolean checkBoxInService;
	private int objectTypeIdInService;	
	private int objectModelIdInService;	
	private int objectInstanceIdInService;
	
	private boolean checkBoxInOperation;	
	private int objectTypeIdInOperation;
	private int objectModelIdInOperation;
	private int objectInstanceIdInOperation;

	private boolean itemSelectedValidation;
	private boolean serviceSelectedValidation;
	private boolean operationSelectedValidation;
	private boolean objectTypesMatchValidation;
		
	public AddObjectFormBACKUP2working() {
	}

	// DATE VALIDATION START!!!
	
	public String getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(String dateSelected) {
		this.dateSelected = dateSelected;
	}	
		
	
	/* Check if selectedDate is properly formated YYYY-MM-DD.
	 * If selectedDate is null validation is passed and error message won't be displayed.
	 * But error is displayed for - dateSelected @NotNull
	*/
	@AssertTrue(message="{helperForm.dateFormated.invalidDate}")
	public boolean isDateFormated() {
	    System.out.println(">>> Inside isDateFormated()");
	    
		dateFormated = false;
		boolean thrownEx = false;
		
		//Only check for proper Date format if selectedDate is not null, or else InvocationTargetException is thrown and the page won't load.
		if (dateSelected != null) {
			try {
				// trying to parse the String selectedDate to a Date object
			    this.dateAfterFormat = LocalDate.parse(dateSelected, DateTimeFormatter.ISO_LOCAL_DATE);
			}
			
			/* If the parse throws exception - the selectedDate is not in the format YYYY-MM-DD, exception is caught.
			   Boolean value thrownEx becomes true.
			*/
			catch (DateTimeParseException exc) {
			    thrownEx = true;		   
			}
			
			//Finally, if exception is not thrown, validation passes.
			finally {
				if(!thrownEx) {
					dateFormated = true;
				}
			}		
		//Validation passes when selectedDate is null and error message is not displayed. But error is displayed for - dateSelected @NotNull	
		}else {
			dateFormated = true;
		}	
		return dateFormated;
	}

	public void setDateFormated(boolean dateFormated) {
		this.dateFormated = dateFormated;
	}
		
	
	/* Check if dateSelected is in range 2016-01-01 to 2026-12-31
	*  
	*  If selectedDate is null or date is not properly formated validation passes
	*  and error message is not displayed. But error is displayed for - selectedDate @NotNull or isDateFormated() == FALSE	
	*/
	@AssertTrue(message="{helperForm.dateInRange.notInRange}")
	public boolean isDateInRange() {

    	System.out.println(">>> Inside isDateInRange()");
    	
		//Only check for proper Date range if selectedDate is not null and Date is properly formated, or else InvocationTargetException is thrown and the page won't load.
		if (dateSelected != null && this.isDateFormated() == true) {
//    	if (dateSelected != null && this.dateFormated == true) {		    
			// Limiting the Date range from 2016-01-01 to 2026-12-31
			LocalDate dateStart = LocalDate.parse("2015-12-31", DateTimeFormatter.ISO_LOCAL_DATE);
			LocalDate dateEnd = LocalDate.parse("2027-01-01", DateTimeFormatter.ISO_LOCAL_DATE);

			if(this.dateAfterFormat.isAfter(dateStart) && this.dateAfterFormat.isBefore(dateEnd)) {
			   	dateInRange = true;
			}else {
			   	dateInRange = false;
			}
		}
		else {
			dateInRange = true;
		}		
		return dateInRange;
	}

	public void setDateInRange(boolean dateInRange) {
		this.dateInRange = dateInRange;
	}

	
	// model attribute ${lastRequestDate} required for form validation of Date
	public String getLastRequestDate() {
		return lastRequestDate;
	}

	public void setLastRequestDate(String lastRequestDate) {
		this.lastRequestDate = lastRequestDate;
	}


	/* Check if dateSelected is not before the date of the last Request entry and not after today.
	 * Validation is made if dateSelected is not null and selectedDate is properly formated and in range.
	 * 
	 * If selectedDate is null, not formated or not in range validation is passed and error message won't be displayed.
	 * But error is displayed for the according validation
	*/
	@AssertTrue(message="{helperForm.dateValid.notBeforeOrAfter}")
	public boolean isDateValid() {
		
		System.out.println(">>> Inside isDateValid()");
		
		// If dateSelected is not null and date is properly formated and in range proceed
		if(dateSelected != null && this.isDateFormated() == true && this.isDateInRange() == true) {
//		if(dateSelected != null && this.dateFormated == true && this.dateInRange == true) {
			// Limiting the Date range from the date of the last Request to now (DateTimeFormatter.ISO_LOCAL_DATE))
			LocalDate dateStart = LocalDate.parse(lastRequestDate, DateTimeFormatter.ISO_LOCAL_DATE);
			LocalDate dateEnd = LocalDate.now();
			
			//If date is greater or equal to the last Request's date or not in the future validation will pass
			if(this.dateAfterFormat.isBefore(dateStart) || this.dateAfterFormat.isAfter(dateEnd)) {
				dateValid = false;
			}else{
				dateValid = true;
			}	
		}else {
			dateValid = true;
		}	
		return dateValid;
	}

	public void setDateValid(boolean dateValid) {
		this.dateValid = dateValid;
	}

	//DATE VALIDATION END
	
	
	
	// ITEM VALIDATION START
	
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
	
	// ITEM VALIDATION END
	
	
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

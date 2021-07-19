package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import com.luv2code.springdemo.customAnnotation.ObjectInstanceNotDeleted;
import com.luv2code.springdemo.customAnnotation.ObjectModelNotDeleted;
import com.luv2code.springdemo.customAnnotation.ObjectTypeNotDeleted;


public class UpdateObjectForm {
	

	private int requestId;

	// NO validation, existing request, requester is valid. It can't be deleted only modified because updated request must always have requester
	private String requesterName;	

	// NO validation, existing request, date is valid
	private String dateSelected;		
	
	@Size(max = 75, message="{helperForm.requesterComment.max}")
	private String requesterComment;
	
	private boolean checkBoxInService;
	
	@ObjectTypeNotDeleted
	private int objectTypeIdInService;
	
	@ObjectModelNotDeleted
	private int objectModelIdInService;
	
	@ObjectInstanceNotDeleted
	private int objectInstanceIdInService;
	
	
	private boolean checkBoxInOperation;
	
	@ObjectTypeNotDeleted
	private int objectTypeIdInOperation;
	
	@ObjectModelNotDeleted
	private int objectModelIdInOperation;
	
	@ObjectInstanceNotDeleted
	private int objectInstanceIdInOperation;

	private boolean itemSelectedValidation;
	private boolean serviceSelectedValidation;
	private boolean operationSelectedValidation;
	private boolean objectTypesMatchValidation;
	
	// Testing disable selectpicker inputs on update form
	private boolean savedCheckBoxInService;
	private boolean savedCheckBoxInOperation;
	
	@Size(max = 250, message="{helperForm.itemComment.max}")
	private String itemComment;
	
	private String userName;
	
	public UpdateObjectForm() {
	}
	
	
	// ITEM VALIDATION START
	
	/* If none of the options checkBoxInService and checkBoxInOperation is selected validation is NOT passed.
	 * At least one option must be selected - Item in Service, Item in Operation or both.
	 */
	@AssertTrue(message="{helperForm.itemSelectedValidation.notSelected}")
	public boolean isItemSelectedValidation() {
		
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
	
	
	/* If checkBoxInService is selected and the objectInstanceIdInService is 0 (is not selected from the list),
	   validation will not pass. If checkBoxInService is NOT selected, select list are Disabled but validation will still pass.
	   Objects "ObjectInstance" are created in "/review"
	*/
	@AssertTrue(message="{helperForm.objectInstanceIdInService.notSelected}")
	public boolean isServiceSelectedValidation() {
		
		if(checkBoxInService == true && objectInstanceIdInService == 0) {

			serviceSelectedValidation = false;

		}else {
			serviceSelectedValidation = true;
		}
		
		return serviceSelectedValidation;
	}
	
	public void setServiceSelectedValidation(boolean serviceSelectedValidation) {
		this.serviceSelectedValidation = serviceSelectedValidation;
	}

	
	/* If checkBoxInOperation is selected and the objectInstanceIdInOperation is 0 (is not selected from the list),
	   validation will not pass. If checkBoxInOperation is NOT selected, select list are Disabled but validation will still pass.
	   Objects "ObjectInstance" are created in "/review"
	*/
	@AssertTrue(message="{helperForm.objectInstanceIdInOperation.notSelected}")
	public boolean isOperationSelectedValidation() {
		
		if(checkBoxInOperation == true && objectInstanceIdInOperation == 0) {

			operationSelectedValidation = false;
		
		}else {
			operationSelectedValidation = true;
		}
		
		return operationSelectedValidation;
	}
	
	public void setOperationSelectedValidation(boolean operationSelectedValidation) {
		this.operationSelectedValidation = operationSelectedValidation;
	}
	
	
	// If objectTypeIdInService and objectTypeIdInOperation do not match, validation is NOT passed.
	@AssertTrue(message="{helperForm.objectTypesMatchValidation.doNotMatch}")
	public boolean isObjectTypesMatchValidation() {
		
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
	
	
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}	
		
	public String getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(String dateSelected) {
		this.dateSelected = dateSelected;
	}	
	
	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	
	public String getRequesterComment() {
		return requesterComment;
	}

	public void setRequesterComment(String requesterComment) {
		this.requesterComment = requesterComment;
	}


	public boolean isCheckBoxInService() {
		return checkBoxInService;
	}

	public void setCheckBoxInService(boolean checkBoxInService) {
		this.checkBoxInService = checkBoxInService;
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

	
	public boolean isCheckBoxInOperation() {
		return checkBoxInOperation;
	}

	public void setCheckBoxInOperation(boolean checkBoxInOperation) {
		this.checkBoxInOperation = checkBoxInOperation;
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


	public String getItemComment() {
		return itemComment;
	}

	public void setItemComment(String itemComment) {
		this.itemComment = itemComment;
	}

	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	// Getter Setters for saved value of checkedBox, necessary for disabling input selectpickers
	public boolean isSavedCheckBoxInService() {
		return savedCheckBoxInService;
	}


	public void setSavedCheckBoxInService(boolean savedCheckBoxInService) {
		this.savedCheckBoxInService = savedCheckBoxInService;
	}


	public boolean isSavedCheckBoxInOperation() {
		return savedCheckBoxInOperation;
	}


	public void setSavedCheckBoxInOperation(boolean savedCheckBoxInOperation) {
		this.savedCheckBoxInOperation = savedCheckBoxInOperation;
	}
	
	
	
}

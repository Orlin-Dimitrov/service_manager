// Object for storing user data when submitting form 

package com.luv2code.springdemo.user;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

public class FormUserDelete {

	private String userName;

	@NotNull(message="{formUser.userName.empty}")
	private String confirmationUserName;
	
	private boolean userNamesMatch;
	
	private String dataTablesUniqueIdFormUser;
	
	public FormUserDelete() {
		
	}
	
	// Checking if usernames are the same
	@AssertTrue(message="{formUser.userNames.dontMatch}")
	public boolean isUserNamesMatch() {
		
		if(userName != null && confirmationUserName != null) {
			
			if(userName.equals(confirmationUserName)) {
				userNamesMatch = true;
			}else {
				userNamesMatch = false;
			}
		}
		
		// Validation will stop at not null
		else {
			userNamesMatch = true;
		}		
		return userNamesMatch;
	}
	
	public void setUserNamesMatch(boolean userNamesMatch) {
		this.userNamesMatch = userNamesMatch;
	}
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getConfirmationUserName() {
		return confirmationUserName;
	}

	public void setConfirmationUserName(String confirmationUserName) {
		this.confirmationUserName = confirmationUserName;
	}

	public String getDataTablesUniqueIdFormUser() {
		return dataTablesUniqueIdFormUser;
	}

	public void setDataTablesUniqueIdFormUser(String dataTablesUniqueIdFormUser) {
		this.dataTablesUniqueIdFormUser = dataTablesUniqueIdFormUser;
	}
	
	
}

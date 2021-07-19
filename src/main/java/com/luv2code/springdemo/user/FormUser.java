// Object for storing user data when submitting form 

package com.luv2code.springdemo.user;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.luv2code.springdemo.customAnnotation.AllowedCharacters;
import com.luv2code.springdemo.customAnnotation.AllowedCharactersPassword;

public class FormUser {

	@NotNull(message="{formUser.userName.empty}")
	@Size(min=3, message="{formUser.userName.min}")
	@Size(max = 25, message="{formUser.userName.max}")
	@AllowedCharacters
	private String userName;

	@NotNull(message="{formUser.userName.empty}")
	@Size(min=3, message="{formUser.userName.min}")
	@Size(max = 25, message="{formUser.userName.max}")
	@AllowedCharacters
	private String confirmationUserName;
	
	@NotNull(message="{formUser.password.empty}")
	@Size(min=10, message="{formUser.password.min}")
	@Size(max = 50, message="{formUser.password.max}")
	@AllowedCharacters
	@AllowedCharactersPassword
	private String password;

	@NotNull(message="{formUser.password.empty}")
	@Size(min=10, message="{formUser.password.min}")
	@Size(max = 50, message="{formUser.password.max}")
	@AllowedCharacters
	@AllowedCharactersPassword
	private String confirmationPassword;
	
	private boolean userNamesMatch;
	private boolean passwordsMatch;
	
	@NotNull(message="{formUser.level.notSelected}")
	private String accessLevel;
	
	private String dataTablesUniqueIdFormUser;
	
	public FormUser() {
		
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
	
	// Checking if passwords are the same
	@AssertTrue(message="{formUser.passwords.dontMatch}")
	public boolean isPasswordsMatch() {
		
		if(password != null && confirmationPassword != null) {
			
			if(password.equals(confirmationPassword)) {
				passwordsMatch = true;
			}else {
				passwordsMatch = false;
			}
		}
		
		// Validation will stop at not null
		else {
			passwordsMatch = true;
		}		
		return passwordsMatch;
	}
	
	public void setPasswordsMatch(boolean passwordsMatch) {
		this.passwordsMatch = passwordsMatch;
	}
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	public String getConfirmationUserName() {
		return confirmationUserName;
	}

	public void setConfirmationUserName(String confirmationUserName) {
		this.confirmationUserName = confirmationUserName;
	}

	
	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getDataTablesUniqueIdFormUser() {
		return dataTablesUniqueIdFormUser;
	}

	public void setDataTablesUniqueIdFormUser(String dataTablesUniqueIdFormUser) {
		this.dataTablesUniqueIdFormUser = dataTablesUniqueIdFormUser;
	}
	
	
}

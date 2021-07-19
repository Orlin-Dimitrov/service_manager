// Object for storing user names and authority levels AFTER Format

package com.luv2code.springdemo.helperObjects;

public class SimpleUserFormated {


	private String userName;

	private String formatedAuthority;
	
	private String dataTablesUniqueId;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFormatedAuthority() {
		return formatedAuthority;
	}

	public void setFormatedAuthority(String formatedAuthority) {
		this.formatedAuthority = formatedAuthority;
	}

	public String getDataTablesUniqueId() {
		return dataTablesUniqueId;
	}

	public void setDataTablesUniqueId(String dataTablesUniqueId) {
		this.dataTablesUniqueId = dataTablesUniqueId;
	}

	
}

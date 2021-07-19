package com.luv2code.springdemo.service;

import java.util.List;

import com.luv2code.springdemo.user.SimpleUser;

public interface UserService {
	
	// Get list of users with authority ROLE_EMPLOYEE, ROLE_MANAGER
	public List<SimpleUser> getSimpleUsers();
	
	// Get list of users with authority ROLE_EMPLOYEE, ROLE_MANAGER, ROLE_ADMIN
	public List<SimpleUser> getSimpleUsersSu();
	
	// NEW Saving login date and time for user
	public void saveLoginDateTime(String userName, String dateTime);
	
	// NEW Getting last login date and time	for user
	public String getLastLoginDateTime(String userName);
	
}

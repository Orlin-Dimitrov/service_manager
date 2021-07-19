package com.luv2code.springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.UserDAO;
import com.luv2code.springdemo.user.SimpleUser;

@Service
public class UserServiceImpl implements UserService {

	
	// need to inject user dao
	@Autowired
	private UserDAO userDAO;
	

	// Get list of users with authority ROLE_EMPLOYEE, ROLE_MANAGER
	@Override
	@Transactional(value="transactionSecurityManager")
	public List<SimpleUser> getSimpleUsers(){
		
		return userDAO.getSimpleUsers();
	}

	
	// Get list of users with authority ROLE_EMPLOYEE, ROLE_MANAGER, ROLE_ADMIN
	@Override
	@Transactional(value="transactionSecurityManager")
	public List<SimpleUser> getSimpleUsersSu(){
		
		return userDAO.getSimpleUsersSu();
	}

	
	// NEW Saving login date and time for user
	@Override
	@Transactional(value="transactionSecurityManager")
	public void saveLoginDateTime(String userName, String dateTime) {
		
		userDAO.saveLoginDateTime(userName, dateTime);
	}
	
	
	// NEW Getting last login date and time	for user
	@Override
	@Transactional(value="transactionSecurityManager")
	public String getLastLoginDateTime(String userName) {
		
		return userDAO.getLastLoginDateTime(userName);
	}
	
}






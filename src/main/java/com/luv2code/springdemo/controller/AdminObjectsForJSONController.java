package com.luv2code.springdemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luv2code.springdemo.customExceptions.JsonNoDbConnectionException;
import com.luv2code.springdemo.helperObjects.SimpleUserFormated;
import com.luv2code.springdemo.service.UserService;
import com.luv2code.springdemo.user.SimpleUser;

// Controller for getting JSON Data for dropdown select In JSPs for adding and editing Request.

@Controller
@RequestMapping("/admin-json")
public class AdminObjectsForJSONController {

	private static final Logger logger = LoggerFactory.getLogger(AdminObjectsForJSONController.class);

	
	// need to inject our user Security service
	@Autowired
	private UserService userService;
	
	
	/* Generating list for Users (Name, authority)
	*/
	
	@GetMapping("/listUsers")
	@ResponseBody
	public List<SimpleUserFormated> listUsers()  throws JsonNoDbConnectionException {
		
		try {
			
			// get user names from the service
			List<SimpleUser> theUsers = userService.getSimpleUsers();
			
			List<SimpleUserFormated> formatedUsers = new ArrayList<SimpleUserFormated>();
			
			for (SimpleUser entry : theUsers) {
				
				SimpleUserFormated tempSimpleUserFormated = new SimpleUserFormated();
				
				tempSimpleUserFormated.setUserName(entry.getUserName());
				
				
				String authority = entry.getAuthority();
				
				if(authority.equals("ROLE_EMPLOYEE")){
					
					tempSimpleUserFormated.setFormatedAuthority("User");
					
				}else if(authority.equals("ROLE_MANAGER")) {
					
					tempSimpleUserFormated.setFormatedAuthority("Manager");
				}else {
					// Code shod not come here
				}
				
				formatedUsers.add(tempSimpleUserFormated);
				
			}
			return formatedUsers;
		}

		//No connection to the DataBase
		catch(CannotCreateTransactionException ex) {
			
			logger.warn("!NO CONNECTION to DB! AdminObjectsForJSONController(/admin-json/listUsers) > Exception: " + ex.getClass() + " > Cause: " + ex.getCause() + " > Root cause: " + ExceptionUtils.getRootCause(ex));

			// DO NOT RENAME Error Message "DataBase error". It is used in handle-dberror-sessionexpired.js
			throw new JsonNoDbConnectionException("DataBase error");
		}

	}
	
	
	
	/* Generating list for Users (Name, authority) for SuperAdmin
	*/	
	@GetMapping("/listUsersSu")
	@ResponseBody
	public List<SimpleUserFormated> listUsersSu()  throws JsonNoDbConnectionException {
		
		try {
			
			// get user names from the service
			List<SimpleUser> theUsers = userService.getSimpleUsersSu();
			
			List<SimpleUserFormated> formatedUsers = new ArrayList<SimpleUserFormated>();
			
			for (SimpleUser entry : theUsers) {
				
				SimpleUserFormated tempSimpleUserFormated = new SimpleUserFormated();
				
				tempSimpleUserFormated.setUserName(entry.getUserName());
				
				
				String authority = entry.getAuthority();
				
				if(authority.equals("ROLE_EMPLOYEE")){
					
					tempSimpleUserFormated.setFormatedAuthority("User");				
				}
				else if(authority.equals("ROLE_MANAGER")) {
					
					tempSimpleUserFormated.setFormatedAuthority("Manager");
				}
				else if(authority.equals("ROLE_ADMIN")) {
					
					tempSimpleUserFormated.setFormatedAuthority("Administrator");
				}
				else {
					// Code shod not come here
				}
				
				formatedUsers.add(tempSimpleUserFormated);
				
			}
			return formatedUsers;			
		}

		//No connection to the DataBase
		catch(CannotCreateTransactionException ex) {
			
			logger.warn("!NO CONNECTION to DB! AdminObjectsForJSONController(/admin-json/listUsersSu) > Exception: " + ex.getClass() + " > Cause: " + ex.getCause() + " > Root cause: " + ExceptionUtils.getRootCause(ex));

			// DO NOT RENAME Error Message "DataBase error". It is used in handle-dberror-sessionexpired.js
			throw new JsonNoDbConnectionException("DataBase error");
		}
		
	}	
}
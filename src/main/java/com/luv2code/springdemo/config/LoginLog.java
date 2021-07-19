package com.luv2code.springdemo.config;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.luv2code.springdemo.service.UserService;

@Component
@Scope("singleton")
public class LoginLog implements ApplicationListener<AuthenticationSuccessEvent>{

	// need to inject our user Security service
	@Autowired
	private UserService userService;
	
//	private static final Logger logger = LoggerFactory.getLogger(LoginLog.class);
	
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		
		// Retrieving the user details
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        
        //Logged in user name
        String loggedUser = userDetails.getUsername();

        // Getting todays date and formating it
        LocalDate today = LocalDate.now();
        String formatedDate =  today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        
        // Getting current time and formating it
        LocalTime time = LocalTime.now();
        String formatedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        
        // String date - time to be saved to DB
        String dateTime = formatedDate + " " + formatedTime;
        
        // Saving the "logging in" date - time for the user
        userService.saveLoginDateTime(loggedUser, dateTime);
        
//        logger.info("User: " + loggedUser + " >>> Date: " + formatedDate + " Time: " + formatedTime);
		
	}

}

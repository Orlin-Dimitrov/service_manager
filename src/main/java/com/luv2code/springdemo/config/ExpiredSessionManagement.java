package com.luv2code.springdemo.config;


import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ExpiredSessionManagement implements ApplicationListener<SessionDestroyedEvent>{

	@Autowired
	private SessionRegistry sessionRegistry;
	
	private static final Logger logger = LoggerFactory.getLogger(ExpiredSessionManagement.class);
	
	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		
//		List<SecurityContext> lstSecurityContext = event.getSecurityContexts();
		String sessionId = event.getId();
	
		SessionInformation destroyedSession = sessionRegistry.getSessionInformation(sessionId);
		
		if(destroyedSession == null) {
			
			LocalTime time = LocalTime.now();
			
			logger.info("Session is destroyed - Time: " + time);
//			System.out.println("Session is destroyed - Time: " + time);
		}else {
			
			System.out.println(">>>> Removing session from session registry : " + sessionId);
			
			destroyedSession.expireNow();
			
		} 
		 
//		System.out.println(destroyedSession.toString());
		
//		if (destroyedSession.isExpired() == false) {
//			
//			System.out.println(">>>> Removing session from session registry : " + sessionId);
//			
//			destroyedSession.expireNow();
//		}
		
//		UserDetails tempUserDetails = (UserDetails)destroyedSession.getPrincipal();

		
	}

}

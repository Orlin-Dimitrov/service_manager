package com.luv2code.springdemo.customHandlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

//Custom AccessDeniedHandler when accessing page without necessary access level, trying to login when already logged in & everything else 
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		try {
	
			logger.warn("Custom Access Denied Handler in Operation > LocalizedMessage : " + ExceptionUtils.getRootCause(accessDeniedException).getLocalizedMessage());
			
			if(ExceptionUtils.getRootCause(accessDeniedException).getLocalizedMessage()
					.matches("Access is denied")) {
				
				response.sendRedirect(request.getContextPath() + "/access-denied");
				
			}else {
				
				response.sendRedirect(request.getContextPath() + "/");
			}
			
			
		} catch (IOException e) {
			logger.warn("IOException in >>> CustomAccessDeniedHandler");
			
		}

	}

}

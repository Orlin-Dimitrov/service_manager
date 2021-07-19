package com.luv2code.springdemo.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class GlobalFilterExceptionHandler extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(GlobalFilterExceptionHandler.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
	
		try {
			
			 filterChain.doFilter(request, response);
			 
			 
		} catch(IOException ioex) {
						
			logger.warn("GlobalFilterExceptionHandler filtering IOException");
			
		} 
		
		// TEST WITH  json/searchItem?draw=1&start=1&length=20&order[0][column]=1&order[0][dir]=1&requesterId=2&objectTypeId=2&objectModelId=1&objectInstanceId=1&startDate=%27null%27&endDate=22
		catch (ServletException sex) {
			
			logger.warn("Filter ServletException - " + ExceptionUtils.getRootCause(sex) + ">>> Location - " + request.getRequestURI());
			
		/*	If This is better, use this instead !!!
		 * try {
				response.sendRedirect(request.getContextPath() + "/");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.warn("Filter IOException, Redirect to /");
			}
		*/
			
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/default-error-no-menu.jsp");
	              
	        	try {
	            	  
					dispatcher.forward(request, response);
					
				} catch (ServletException ex) {

					logger.warn("Filter ServletException - SERIOUS ServletException in forwarding" + ExceptionUtils.getRootCause(ex));
					
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					logger.warn("Filter ServletException - SERIOUS IOException in forwarding" + ExceptionUtils.getRootCause(ex));
				}			
		}
	}

}

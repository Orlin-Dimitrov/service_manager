package com.luv2code.springdemo.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.filter.OncePerRequestFilter;


// THIS CLASS IS NOT USED IN THE PROJECT , KEPT FOR FUTURE REFERENCE
public class FilterChainExceptionHandler extends OncePerRequestFilter {

	
	
	private static final Logger logger = LoggerFactory.getLogger(FilterChainExceptionHandler.class);
	
/*	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

        try {
            filterChain.doFilter(request, response);
        } catch (Throwable ex) {
        	logger.warn("Spring Security Filter Chain Exception. No connection to DB:");
            
      	  logger.warn(">>>SS>>> Exception: " + ex.getClass());
    	  logger.warn(">>>SS>>> Cause: " +  ex.getCause());
    	  logger.warn(">>>SS>>> Root cause: " + ExceptionUtils.getRootCause(ex));
        }
	}*/
 
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	    try {
	        filterChain.doFilter(request, response);
	        logger.info("!!! Custom SS FILTER");
	    } catch (InternalAuthenticationServiceException ex) {
	    	logger.warn("Spring Security Filter Chain Exception. No connection to DB:");
	        
	  	  logger.warn(">>>SS>>> Exception: " + ex.getClass());
		  logger.warn(">>>SS>>> Cause: " +  ex.getCause());
		  logger.warn(">>>SS>>> Root cause: " + ExceptionUtils.getRootCause(ex));
	    }
	}


	
	
	

}

package com.luv2code.springdemo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.firewall.RequestRejectedException;

public class FirewallExceptionHandlingFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(FirewallExceptionHandlingFilter.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
        try {
        	
            chain.doFilter(request, response);
            
        } catch (RequestRejectedException ex) {
        	
        	logger.warn(">>> Spring StrictHttpFirewall Exception - RequestRejectedException");
        	logger.warn(">>> RequestRejectedException>>> Root cause: " + ExceptionUtils.getRootCause(ex));
        	
        	HttpServletRequest requestMod = (HttpServletRequest) request;
        	HttpServletResponse responseMod = (HttpServletResponse) response;

//        	logger.warn("Spring Security Filter Chain Exception. RequestRejectedException. Remote IP: " + requestMod.getRemoteAddr());
                  	
        	responseMod.sendRedirect(requestMod.getContextPath() + "/");
        }

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	
/*	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		
        try {
        	
            chain.doFilter(request, response);
            
        } catch (RequestRejectedException ex) {
        	
        	logger.warn(">>> Spring StrictHttpFirewall Exception - RequestRejectedException");
        	logger.warn(">>> RequestRejectedException>>> Root cause: " + ExceptionUtils.getRootCause(ex));
        	
        	HttpServletRequest requestMod = (HttpServletRequest) request;
        	HttpServletResponse responseMod = (HttpServletResponse) response;

//        	logger.warn("Spring Security Filter Chain Exception. RequestRejectedException. Remote IP: " + requestMod.getRemoteAddr());
            
//        	throw new IOException();
        	
        	try {
        		
        		responseMod.sendRedirect(requestMod.getContextPath() + "/");
        		
        	} catch (IOException ioex) {
        		
        		logger.warn(">>> Spring StrictHttpFirewall Exception - IO Exception");
        		
        	}
  	
        } catch (ServletException ex) {
        	
        	logger.warn(">>> Spring StrictHttpFirewall Exception - Servlet Exception");
        	
        } catch (IOException ex) {
        	
        	logger.warn(">>> Spring StrictHttpFirewall Exception - IO Exception");
        	
        }

	}
*/	
	
}

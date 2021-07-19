package com.luv2code.springdemo.config;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import com.luv2code.springdemo.filters.FirewallExceptionHandlingFilter;
import com.luv2code.springdemo.filters.GlobalFilterExceptionHandler;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	private static final Logger logger = LoggerFactory.getLogger(SecurityWebApplicationInitializer.class);
	
	// use this to enable proper session management when max sessions per user is configured
	@Override
	protected boolean enableHttpSessionEventPublisher() {
		
		return true;
	}

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		
		super.beforeSpringSecurityFilterChain(servletContext);
		
		logger.info(">>> Before Spring Security Filter Chain - Register GlobalFilterExceptionHandle");		
		super.insertFilters(servletContext, new GlobalFilterExceptionHandler());
		
		logger.info(">>> Before Spring Security Filter Chain - Register FirewallExceptionHandlingFilter (catch RequestRejectedException)");
		super.insertFilters(servletContext, new FirewallExceptionHandlingFilter());
	}
	
	
}

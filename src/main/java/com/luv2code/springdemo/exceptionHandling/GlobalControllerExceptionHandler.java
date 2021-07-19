package com.luv2code.springdemo.exceptionHandling;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@ControllerAdvice
public class GlobalControllerExceptionHandler {

	
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
	
/*	// No test found, leave it
    @ResponseStatus(code = HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(AccessDeniedException.class)
    public void handleConflict403(HttpServletRequest req, HttpServletResponse resp) {
    	
    	logger.warn("HTTP error 403. AccessDeniedException");
    	
    	redirectToHome(req, resp, "AccessDeniedException");
    }  */
    
	// No test found, leave it
    @ResponseStatus(code = HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(AccessDeniedException.class)
    public String handleConflict403(HttpServletRequest req, HttpServletResponse resp) {
    	
    	logger.warn("HTTP error 403. AccessDeniedException");
    	
    	return "access-denied";
    }   
    

    
    // TEST with /json/db-error or /component/saveType
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)  // 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handleConflict(HttpServletRequest req, HttpServletResponse resp) {
    	
    	logger.warn("HTTP error 405. HttpRequestMethodNotSupportedException");   	
    	
    	redirectToHome(req, resp, "HttpRequestMethodNotSupportedException"); 
    		   	
    }
    
    
/*    // TEST with /json/db-error or /component/saveType
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)  // 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handleConflict(HttpServletRequest req, HttpServletResponse resp) {
    	
    	logger.warn("HTTP error 405. HttpRequestMethodNotSupportedException");   	
    	
    	redirectToHome(req, resp, "HttpRequestMethodNotSupportedException");   	
    }
    */
    
    //TEST with accessing /json/objectModelList
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleConflict400(HttpServletRequest req, HttpServletResponse resp){
    	
    	logger.warn("HTTP error 400. MissingServletRequestParameterException");
    	
    	redirectToHome(req, resp, "MissingServletRequestParameterException");
    }
 
    
    //TEST with accessing /requests/1a
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)  // 400 string instead of number
    @ExceptionHandler(NumberFormatException.class)
    public void handleConflict400NumberFormat(HttpServletRequest req, HttpServletResponse resp) {
    	
    	logger.warn("HTTP error 400. NumberFormatException");
    	
    	redirectToHome(req, resp, "NumberFormatException");
    }
    
    
    //TEST access invalid url 
	@ResponseStatus(code = HttpStatus.NOT_FOUND) // 404
	@ExceptionHandler(NoHandlerFoundException.class)    
	public String handleConflict404() {
		  
		logger.warn("HTTP error 404. NoHandlerFoundException");
	  	
		return "page-not-found";
	 }
	 
	 
	 //TEST with stopping mysql. Exception handling when NO CONNECTION to the DB
	 @ExceptionHandler({CannotCreateTransactionException.class, CannotGetJdbcConnectionException.class})    
	 public String noConnectionToDbException(Exception ex, RedirectAttributes redirectAttributes) {

		 logger.warn("!NO CONNECTION to DB! Global exception handler > Exception: " + ex.getClass() + " > Cause: " + ex.getCause() + " > Root cause: " + ExceptionUtils.getRootCause(ex));
//	  logger.warn("!NO CONNECTION to DB! > Cause: " +  ex.getCause());
//	  logger.warn("!NO CONNECTION to DB! > Root cause: " + ExceptionUtils.getRootCause(ex));
	  
	  	String dbErrorFlashAttribute = "dbError";
	  	redirectAttributes.addFlashAttribute("db-error", dbErrorFlashAttribute);
	  	
		return "redirect:/db-error";
	 }

	 
	 // TESTING TESTING
	 @ExceptionHandler(DataAccessException.class)    
	 public String dataAccessException(Exception ex, RedirectAttributes redirectAttributes) {

		 logger.warn("!Data Access Exception! Global exception handler > Exception: " + ex.getClass() + " > Cause: " + ex.getCause() + " > Root cause: " + ExceptionUtils.getRootCause(ex));	  
		 String dbErrorFlashAttribute = "dbError";
		 redirectAttributes.addFlashAttribute("db-error", dbErrorFlashAttribute);
	  	
		return "redirect:/db-error";
	 }
	 
	 
	 // New Exceptions
	 @ExceptionHandler(IOException.class)
	 public String handleIOException(IOException ex) {
		 
		 logger.warn("IOException");
		 
//		 + ClassUtils.getShortName(ex.getClass())
		 return "default-error";
	  }

	 
	 // Helper method to redirect to home page / 
	 public void redirectToHome(HttpServletRequest req, HttpServletResponse resp, String exceptionName) {
		 
	    	try {  		
	    		
	    		resp.sendRedirect(req.getContextPath() + "/"); 
	    		
	    	} catch (IOException ex) {
	    		
	    		logger.warn("IOException in >>> " + exceptionName + " Handler");  
	    		
	    	}
		 
	 }
}























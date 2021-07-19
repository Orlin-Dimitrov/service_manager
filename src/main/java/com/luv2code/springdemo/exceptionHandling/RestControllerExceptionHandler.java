package com.luv2code.springdemo.exceptionHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.luv2code.springdemo.customExceptions.ApiError;
import com.luv2code.springdemo.customExceptions.JsonNoDbConnectionException;

@RestControllerAdvice
public class RestControllerExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

	// Exception Handler when JSON request is made and there is no connection to the Database
    @ExceptionHandler(JsonNoDbConnectionException.class)
    public ResponseEntity<Object> handleJsonException(JsonNoDbConnectionException ex, WebRequest request) {
    	
    	String error = "No DB Connection";
    	
    	// JavaScript Uses ex.getLocalizedMessage() for error logic in DataTables
    	ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), error);

    	logger.warn("!NO CONNECTION to DB! REST exception handler > Custom Exception: " + ex.getClass());
    	    	
    	return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
/*	// Exception Handler when JSON request is made and there is no connection to the Database NOT USED!!! leaved for reference.
    @ExceptionHandler(JsonNoDbConnectionException.class)
    public void handleJsonException(JsonNoDbConnectionException ex, WebRequest request, HttpServletResponse response) {
    	
    	UriComponents uriComponents = UriComponentsBuilder.newInstance().path("/db-error").build();
    	
    	HttpHeaders responseHeaders = new HttpHeaders();
    	
    	responseHeaders.setLocation(uriComponents.toUri());
    	
    	ResponseEntity<Void> forwardResponseEntity = new ResponseEntity<Void>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);
    	
    	logger.warn("!NO CONNECTION to DB! TEST exception handler > Custom Exception: " + ex.getClass());
    	    	
    	
		try {
			response.sendRedirect(uriComponents.toUri().toString());
			logger.info("REDIRECT EXECUTED");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
    }*/


}

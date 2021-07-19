package com.luv2code.springdemo.aspects;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

@Aspect
public class HandleNoDbConnectionErrorOnLoginAspect {

	private static final Logger logger = LoggerFactory.getLogger(HandleNoDbConnectionErrorOnLoginAspect.class);

/*    @AfterThrowing(value = "execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint theJoinPoint, InternalAuthenticationServiceException ex) throws Throwable {
    	
		String method = theJoinPoint.getSignature().toShortString();
		
		logger.warn(">>>Aspect ExResolver Pointcut>>> : " + method);
		logger.warn(">>>Aspect ExResolver>>> Exception: " + ex.getClass());
		logger.warn(">>>Aspect ExResolver>>> Cause: " +  ex.getCause());
		logger.warn(">>>Aspect ExResolver>>> Root cause: " + ExceptionUtils.getRootCause(ex));
    	
    }*/
	

	// SWALLOW InternalAuthenticationServiceException on No Connection to DB, else ERROR is thrown 
  	@Around("execution(* org.springframework.security.authentication.ProviderManager.authenticate(..))")
	public Object swallowInternalAuthenticationServiceException(ProceedingJoinPoint pjp)  throws Throwable{
		
  		Object retVal = null;
  		
        try {
        	retVal = pjp.proceed();
        	
        } catch (InternalAuthenticationServiceException ex ) {
  	        
        	// if this is not enough add printStackTrace
        	logger.warn(">>>Aspect Spring Security Provider Manager ExResolver Pointcut>>> : authenticate()");
        	logger.warn(">>>Aspect Spring Security Provider Manager >>> Exception: " + ex.getClass());
        	logger.warn(">>>Aspect Spring Security Provider Manager >>> Cause: " +  ex.getCause());
        	logger.warn(">>>Aspect Spring Security Provider Manager >>> Root cause: " + ExceptionUtils.getRootCause(ex));  
        }
        return retVal;
	}
      
}

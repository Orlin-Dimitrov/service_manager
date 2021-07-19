package com.luv2code.springdemo.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.PooledDataSource;

// Listener is manualy added because logback must be added first for hotdeployment/redeployement
//@WebListener
public class C3P0CloseOnRestart implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(C3P0CloseOnRestart.class);
	
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	} 
	
	// closing pooled data sources
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		int c3p0DataSources = 0;
		
		for (Object o : C3P0Registry.getPooledDataSources()) {
			try {
				((PooledDataSource) o).close();		    
			    c3p0DataSources++;
			} catch (Exception e) {
			// oh well, let tomcat do the complaing for us.
			}
		}
		
		logger.info("Number of C3P0 DataSources closed: " + c3p0DataSources);
		logger.info("Sleeping for 4 seconds to finish Thred Resource Destroyer in BasicResourcePool.close()");
		
		// increase interval if error on restart
		try {
			Thread.sleep(4000);
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
	}
}

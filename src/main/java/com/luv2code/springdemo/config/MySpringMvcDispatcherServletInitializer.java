package com.luv2code.springdemo.config;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.luv2code.springdemo.listeners.C3P0CloseOnRestart;

import ch.qos.logback.classic.selector.servlet.ContextDetachingSCL;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { DemoAppConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] { "/" };
	}

	// Adding necessary method to handle 404 Page Not Found
    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        
        return dispatcherServlet;
    }
    
    
    // manually adding weblisteners. First must be for logback, second is to close datasources and pools
	@Override
	protected void registerContextLoaderListener(ServletContext servletContext) {
		servletContext.addListener(ContextDetachingSCL.class);
		servletContext.addListener(C3P0CloseOnRestart.class);
		super.registerContextLoaderListener(servletContext);
	}
    
    /* NOT USED necessary for proper sessionRegistry operation (removing users when logout) NOT USED!!!
      configured in SecurityWebApplicationInitializer  !!!
    
    @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        super.registerDispatcherServlet(servletContext);

        servletContext.addListener(new HttpSessionEventPublisher());
    }
    */
    
    
//    // Support Cyrillic NOT WORKING , configured in security configuration because of CSRF
//    @Override
//    protected Filter[] getServletFilters() {
//    	
//        return new Filter[] { new CharacterEncodingFilter("UTF-8", true) };
//    }

}

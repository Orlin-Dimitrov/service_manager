package com.luv2code.springdemo.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
//import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages="com.luv2code.springdemo")
//if ERROR remove "basePackages"
@PropertySource({"classpath:persistence-mysql.properties", 
				"classpath:security-persistence-mysql.properties"})
public class DemoAppConfig implements WebMvcConfigurer {

	// set up variable to hold the properties
	@Autowired
	private Environment env;
	
	
	// set up a logger for diagnostics
//	private Logger logger = Logger.getLogger(getClass().getName());
	
	private static final Logger logger = LoggerFactory.getLogger(DemoAppConfig.class);
	
	
	// define a bean for ViewResolver	
	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		// ADDING NEW FOR CYRILIC SUPPORT
		viewResolver.setContentType("text/html; charset=UTF-8");
		
		return viewResolver;
	}
	
	 
	// define a bean for datasource, datasource is closed on reboot/shutdown by a listener to prevent error
	@Bean(destroyMethod = "")
	public DataSource myDataSource(){
			
		// create connection pool			
		ComboPooledDataSource myDataSource = new ComboPooledDataSource();
						
		// set the jdbc driver class		
		try {
			myDataSource.setDriverClass(env.getProperty("jdbc.driver"));
			
//			//IF ERROR, ENABLE !!! TESTING redeployment , REMOVE MEMMORY LEAK ON RESTART  
//			myDataSource.setContextClassLoaderSource(env.getProperty("contextClassLoaderSource"));
			
			} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
			}			
			
		// log the connection props
		// for sanity's sake, log this info
		// just to make sure we are REALLY reading data from properties file 			
		logger.info(">>> jdbc.url = " + env.getProperty("jdbc.url"));
		logger.info(">>> jdbc.user = " + env.getProperty("jdbc.user"));
						
		// set database connection props			
		myDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		myDataSource.setUser(env.getProperty("jdbc.user"));
		myDataSource.setPassword(env.getProperty("jdbc.password"));
		
		
		// set connection pool props			
		myDataSource.setInitialPoolSize(
				getIntProperty("connection.pool.initialPoolSize"));
			
		myDataSource.setMinPoolSize(
				getIntProperty("connection.pool.minPoolSize"));
			
		myDataSource.setMaxPoolSize(
				getIntProperty("connection.pool.maxPoolSize"));
		
		myDataSource.setMaxIdleTime(
				getIntProperty("connection.pool.maxIdleTime"));
		
		// TESTING IF ERROR REMOVE
		myDataSource.setIdleConnectionTestPeriod(getIntProperty("connection.pool.idleConnectionTestPeriod"));
		myDataSource.setTestConnectionOnCheckout(getBooleanProperty("connection.pool.testConnectionOnCheckout"));
		myDataSource.setMaxStatementsPerConnection(getIntProperty("connection.pool.maxStatementsPerConnection"));
		
		// FOR DB TESTING ONLY!!!
		myDataSource.setAcquireRetryAttempts(getIntProperty("connection.pool.acquireRetryAttempts"));
		
		return myDataSource;
	}
		 
		
	// define a bean for securitydatasource, datasource is closed on reboot/shutdown by a listener to prevent error
	@Bean(destroyMethod = "")
	public DataSource securityDataSource(){
		
		// create connection pool		
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
				
		// set the jdbc driver class		
		try {
			securityDataSource.setDriverClass(env.getProperty("security.jdbc.driver"));
			
//			//IF ERROR, ENABLE !!! TESTING redeployment , REMOVE MEMMORY LEAK ON RESTART
//			securityDataSource.setContextClassLoaderSource(env.getProperty("security.contextClassLoaderSource"));
			
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}
				
		// log the connection props
		// for sanity's sake, log this info
		// just to make sure we are REALLY reading data from properties file 
		
		logger.info(">>> security.jdbc.url = " + env.getProperty("security.jdbc.url"));
		logger.info(">>> security.jdbc.user = " + env.getProperty("security.jdbc.user"));
				
		// set database connection props		
		securityDataSource.setJdbcUrl(env.getProperty("security.jdbc.url"));
		securityDataSource.setUser(env.getProperty("security.jdbc.user"));
		securityDataSource.setPassword(env.getProperty("security.jdbc.password"));
		
			
		// set connection pool props		
		securityDataSource.setInitialPoolSize(
				getIntProperty("security.connection.pool.initialPoolSize"));
		
		securityDataSource.setMinPoolSize(
				getIntProperty("security.connection.pool.minPoolSize"));
		
		securityDataSource.setMaxPoolSize(
				getIntProperty("security.connection.pool.maxPoolSize"));
		
		securityDataSource.setMaxIdleTime(
				getIntProperty("security.connection.pool.maxIdleTime"));
		
		// TESTING IF ERROR REMOVE
		securityDataSource.setIdleConnectionTestPeriod(getIntProperty("security.connection.pool.idleConnectionTestPeriod"));
		securityDataSource.setTestConnectionOnCheckout(getBooleanProperty("security.connection.pool.testConnectionOnCheckout"));
		securityDataSource.setMaxStatementsPerConnection(getIntProperty("security.connection.pool.maxStatementsPerConnection"));
		
		// FOR DB TESTING ONLY!!!
		securityDataSource.setAcquireRetryAttempts(getIntProperty("security.connection.pool.acquireRetryAttempts"));

		
		return securityDataSource;
	}
	 
	
	// need a helper method
	// read environment property and convert to int	
	private int getIntProperty(String propName) {
		
		String propVal = env.getProperty(propName);
		
		// now convert to int
		int intPropVal = Integer.parseInt(propVal);
		
		return intPropVal;
	}
	
	
	// TESTING helper method for boolean
	private boolean getBooleanProperty(String propName) {
		
		String propVal = env.getProperty(propName);
		
		// convert to boolean
		boolean boolPropVal = Boolean.valueOf(propVal);
								
		return boolPropVal;
	}
	
		
	// Hibernate Section: method for handling Hibernate properties for CRM
	private Properties getHibernateProperties() {
		
		// set Hibernate properties
		Properties props = new Properties();
		
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		
//		//TESTING FOR SAVING MULTIPLE ENTRIES TO DB
//		props.setProperty("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));

		return props;
	}
	
	// Hibernate Section: method for handling Hibernate properties for Security User Management
	private Properties getSecurityHibernateProperties() {
		
		// set Hibernate properties
		Properties props = new Properties();
		
		props.setProperty("hibernate.dialect", env.getProperty("security.hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("security.hibernate.show_sql"));

		return props;
	}
	
	
	// method for creating the Hibernate session factory based on the datasource and 
	// configuration properties
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		
		// create session factory
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		// set the properties
		sessionFactory.setDataSource(myDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return sessionFactory;
	}
	
	// method for creating the Hibernate session factory based on the datasource and 
	// configuration properties for Security User Management
	@Bean
	public LocalSessionFactoryBean sessionSecurityFactory() {
		
		// create session factory
		LocalSessionFactoryBean sessionSecurityFactory = new LocalSessionFactoryBean();
		
		// set the properties
		sessionSecurityFactory.setDataSource(securityDataSource());
		sessionSecurityFactory.setPackagesToScan(env.getProperty("security.hibernate.packagesToScan"));
		sessionSecurityFactory.setHibernateProperties(getSecurityHibernateProperties());
		
		return sessionSecurityFactory;
	}
	
	// method for configuring the Hibernate transaction manager for CRM
	@Bean
	@Autowired
	@Qualifier(value="sessionFactory")
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		
		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		
		return txManager;
	}
	
	// method for configuring the Hibernate transaction manager for Security User Management
	@Bean
	@Autowired
	@Qualifier(value="sessionSecurityFactory")
	public HibernateTransactionManager transactionSecurityManager(SessionFactory sessionSecurityFactory) {
		
		// setup transaction manager based on session factory
		HibernateTransactionManager txSecurityManager = new HibernateTransactionManager();
		txSecurityManager.setSessionFactory(sessionSecurityFactory);
		
		return txSecurityManager;
	}
	
	
	// ATENTION !!!!!!!!!!  original  - > registry.addResourceHandler("/resources/**")
	
	// the App is going to use static web resources such as css, images, js etc. So
	// we add a resource handler	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	
		registry
		.addResourceHandler("/resources/**")
		.addResourceLocations("/resources/");
		
		registry
		.addResourceHandler("/resources/fonts/**").setCachePeriod(3600*24)
		.addResourceLocations("/resources/fonts/");
	}
	
	// resolving messages from resource bundles for different locales(eg. validation error msg)
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	     
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    
	    return messageSource;
	}
	
	// set the custom resource bundles
	@Bean
	public LocalValidatorFactoryBean getValidator() {
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    return bean;
	}
	
	
	// TEASTING CUSTOM MESSAGE SOURCE
	
	@Bean
	public MessageSource customTextMessageSource() {
	    ReloadableResourceBundleMessageSource customTextMessageSource = new ReloadableResourceBundleMessageSource();
	     
	    customTextMessageSource.setBasename("classpath:custom-text");
	    customTextMessageSource.setDefaultEncoding("UTF-8");
	    
	    return customTextMessageSource;
	}
}

package com.luv2code.springdemo.config;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import com.luv2code.springdemo.aspects.HandleNoDbConnectionErrorOnLoginAspect;
import com.luv2code.springdemo.generators.RequestInfoPage;
import com.luv2code.springdemo.generators.UserAccessLevel;

@Configuration
//@EnableAspectJAutoProxy
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class Beans {

	
	 private static final SessionRegistry SESSION_REGISTRY = new SessionRegistryImpl();
	
/*	@Autowired
	private RequesterService requesterService;
	*/
	
	// Bean for Aspect to handle error in console when trying to login when no connection to DB
    @Bean
    HandleNoDbConnectionErrorOnLoginAspect handleNoDbConnectionErrorOnLoginAspect () {
    	return new HandleNoDbConnectionErrorOnLoginAspect();
    }
		
	
	// Been for getting hashmap of the Requesters // NOT USED // Implemented transactional method in DAO
/*	@Bean
	@RequestScope
	public LinkedHashMap<Integer, String> getRequestersMap(){
		
		List<Requester> requesters = new ArrayList<Requester>();
		requesters = requesterService.getRequesters();
		
		LinkedHashMap<Integer, String> requestersMap = new LinkedHashMap<Integer, String>();
		
		for (int i = 0; i < requesters.size(); i++) {
			Requester tempRequest =  requesters.get(i);
			requestersMap.put(tempRequest.getId(), tempRequest.getName());			
		}
				
		return requestersMap;
	}*/
	
	// Been for getting Request info page
	@Bean
	@Scope("singleton")
//	@RequestScope
	public RequestInfoPage getRequestInfoPage(){
		
		return new RequestInfoPage();
	}
	
	//TESTING sessionRegistry.getAllSessions
	@Bean
	@Scope("singleton")
	public SessionRegistry getSessionRegistry() {
		return SESSION_REGISTRY;
	}
	
	
	// Bean that contains method to return user access level as a custom string
	@Bean
	@Scope("singleton")
	public UserAccessLevel getUserAccessLevel() {
		return new UserAccessLevel ();
		
	}
	
	
/*	// Bean with custom date formatter "dd-MM-yyyy"
	@Bean
	@Scope("singleton")
	public DateTimeFormatter  getCustomDateFormatter() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		return formatter;		
	}*/
	
	// Bean with custom date formatter "dd-MM-yyyy"
	@Bean
	@Scope("singleton")
	public DateTimeFormatter  customDateFormatter() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		return formatter;		
	}
	
	
//	// Bean with custom date formatter "yyyy, MM, dd"
//	@Bean
//	@Scope("singleton")
//	public DateTimeFormatter  customDateFormatterForStatisticsJSON() {
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy, MM, dd");
//		
//		return formatter;		
//	}
	
	
	
	
	
	
	
	
}

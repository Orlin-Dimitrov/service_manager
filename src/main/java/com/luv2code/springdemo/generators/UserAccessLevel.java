/*
 * Bean to generate logged-in user custom text access level text
 */
package com.luv2code.springdemo.generators;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;


public class UserAccessLevel {

	@Autowired
	private MessageSource customTextMessageSource;
	
	public UserAccessLevel() {
		
	}
	
	public String getAccessLevel() {
		
		Locale locale = Locale.getDefault();
		
		String accessLevel = "";
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    						
			Collection<? extends GrantedAuthority>authorities = authentication.getAuthorities();
			
			Set<String> textAuthorities  = AuthorityUtils.authorityListToSet(authorities);
						
			if(textAuthorities.contains("ROLE_SUADMIN")) {
				
				accessLevel = customTextMessageSource.getMessage("userAccessLevel.superAdministrator",null, locale);
				
			}else if(textAuthorities.contains("ROLE_ADMIN")) {
				
				accessLevel = customTextMessageSource.getMessage("userAccessLevel.administrator",null, locale);
				
			}else if(textAuthorities.contains("ROLE_MANAGER")) {
				
				accessLevel = customTextMessageSource.getMessage("userAccessLevel.manager",null, locale);
				
			}else {
				
				accessLevel = customTextMessageSource.getMessage("userAccessLevel.user",null, locale);

			}			
		}
		
		return accessLevel;
	}
}

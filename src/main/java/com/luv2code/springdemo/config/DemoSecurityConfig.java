package com.luv2code.springdemo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.luv2code.springdemo.customHandlers.CustomAccessDeniedHandler;
import com.luv2code.springdemo.filters.SameSiteFilter;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	// add a reference to our security datasource	
	@Autowired
	private DataSource securityDataSource;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// use jdbc authentication ... oh yeah!!!
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
		http.authorizeRequests()
				.antMatchers("/requests/**").hasRole("EMPLOYEE")
				.antMatchers("/search/**").hasRole("EMPLOYEE")				
				.antMatchers("/requestForm/**").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers("/requestFormUpdate/**").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers("/requestFormAdmin/**").hasRole("ADMIN")				
				.antMatchers("/component/**").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers("/componentEdit/**").hasRole("ADMIN")
				.antMatchers("/componentDelete/**").hasRole("ADMIN")				
				.antMatchers("/userManagement/**").hasRole("ADMIN")				
				.antMatchers("/json/**").hasRole("EMPLOYEE")
				.antMatchers("/admin-json/listUsers").hasRole("ADMIN")
				.antMatchers("/admin-json/listUsersSu").hasRole("SUADMIN")				
				.antMatchers("/resources/css/bootstrap.min*", "/resources/css/login.css",
								"/resources/css/jumbotron-vertCenter-when-navbar-used.css", "/resources/css/panel-danger-custom.css",
								"/resources/css/background.css", "/resources/css/page_shape.css", "/resources/css/panel-result-custom.css",
								"/resources/js/bootstrap.min.js", "/resources/js/jquery.min.js").permitAll()
				.antMatchers("/resources/fonts/**").permitAll()
				.antMatchers("/resources/**").hasRole("EMPLOYEE")
				.antMatchers("/statistics").hasRole("EMPLOYEE")
				.antMatchers("/session-expired").permitAll()
				.antMatchers("/").hasRole("EMPLOYEE")
				.anyRequest().authenticated()
			.and()	
				.formLogin()
					.loginPage("/login")
					.loginProcessingUrl("/authenticateTheUser")
					.permitAll()
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/out")
				.addLogoutHandler(new SecurityContextLogoutHandler())				
				.permitAll()
				.deleteCookies("JSESSIONID")				
			.and()
				.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
			.and()
				.sessionManagement()
					.invalidSessionUrl("/session-expired")
					.maximumSessions(3).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry).expiredUrl("/session-expired");
		
			// Necessary for Cyrillic support
	        CharacterEncodingFilter filter = new CharacterEncodingFilter();
	        filter.setEncoding("UTF-8");
	        filter.setForceEncoding(true);
	        http.addFilterBefore(filter,CsrfFilter.class);
	        
	        // TESTING FIX GOOGLE CHROME (PROCESSING REQUEST HANG)
	        http.addFilterAfter(new SameSiteFilter(), BasicAuthenticationFilter.class);
	        
	} 
	
	
	
	/*
	 * 
	 .accessDeniedHandler(new CustomAccessDeniedHandler())
	 * 
	 
	 	.and()
				.exceptionHandling().accessDeniedPage("/access-denied")
	 .and()
	 				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/out")
				.addLogoutHandler(new SecurityContextLogoutHandler())
				.clearAuthentication(true)
				.permitAll()
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
				
				
				
				
	 
	 */
	
	
	// IF THE WORKING CONFIGURATION IS A SECURITY RISK USE THIS
	/*
	 
	 		http.authorizeRequests()
				.antMatchers("/userManagement/**").hasRole("ADMIN")
				.antMatchers("/register/**").hasRole("ADMIN")
				.antMatchers("/customer/showForm*").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers("/customer/save*").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers("/customer/delete").hasRole("ADMIN")
				.antMatchers("/customer/**").hasRole("EMPLOYEE")
				.antMatchers("/").hasRole("EMPLOYEE")
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/session-expired").permitAll()

				.anyRequest().authenticated()
				
			.and()
				.formLogin()
					.loginPage("/login")
					.loginProcessingUrl("/authenticateTheUser")
					.permitAll()
			.and()
				.logout()

				.clearAuthentication(true)
				.permitAll()
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
			.and()
				.exceptionHandling().accessDeniedPage("/access-denied")
			.and()
				.sessionManagement()

					.maximumSessions(2).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry).expiredUrl("/session-expired");
	 
	 */

	/* BACKUP
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
		http.authorizeRequests()
				.antMatchers("/register/**").hasRole("ADMIN")
				.antMatchers("/customer/showForm*").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers("/customer/save*").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers("/customer/delete").hasRole("ADMIN")
				.antMatchers("/customer/**").hasRole("EMPLOYEE")
				.antMatchers("/").hasRole("EMPLOYEE")
				.antMatchers("/resources/**").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin()
					.loginPage("/login")
					.loginProcessingUrl("/authenticateTheUser")
					.permitAll()
			.and()
				.logout().permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/access-denied");
	}
	*/
	
	
	// If this is a SECURITY RISK don't use it.
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/out");
	  
//	    web.ignoring().antMatchers("/session-expired");
	}
	
	
	
	
	// creating JDBC User Details Manager bean. It provides	access to the database for creating users
	// and checking if user exists.	
	@Bean
	public UserDetailsManager userDetailsManager() {
		// METHOD NAME CHANGED FROM jdbcUserDetailsManager()
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		
		jdbcUserDetailsManager.setDataSource(securityDataSource);
		
		return jdbcUserDetailsManager;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

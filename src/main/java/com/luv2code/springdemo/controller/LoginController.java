package com.luv2code.springdemo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/login")
	public String showMyLoginPage() {
		
		logger.info("GET /login");

		return "fancy-login";
	}
	
	
	// add request mapping for /access-denied
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		
		logger.info("GET /access-denied");
		
		return "access-denied";
	}
	
	
/*	// NOT USED (implemented .accessDeniedHandler(new CustomAccessDeniedHandler() in DemoSecurityConfig.java) add POST request mapping for /access-denied
	@PostMapping("/access-denied")
	public String showAccessDeniedPOST(HttpServletRequest req, HttpServletResponse resp) {

		// When trying to authenticate on /login page when already authenticated
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				 SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
				 
				 //when Anonymous Authentication is enabled
				 !(SecurityContextHolder.getContext().getAuthentication() 
				          instanceof AnonymousAuthenticationToken) ) {
			
			logger.info("POST /access-denied >>> AUTHENTICATED");
			
			return "js-redirect-to-home-with-link";
		}
		
		// When submitting form and session has expired
		else {			
			
			logger.info("POST /access-denied >>> NOT AUTHENTICATED");
			return "js-redirect-to-session-expired-with-link";			
		}
	}
*/
	

	// add session expired page used also for invalid session url
	@GetMapping("/session-expired")
	public String sessionExpired(){
		
		logger.info("GET /session-expired");
		return "session-expired";
	}
	
	
/*	// add session expired page used also for invalid session url
	@GetMapping("/db-error")
	public String dbError(){
		
		logger.info("GET /db-error");
		return "db-error";
	}*/
	
	
	// add session expired page used also for invalid session url
	@PostMapping("/db-error")
	public String dbErrorPost(Model theModel, RedirectAttributes redirectAttributes){
		
	  	String dbErrorFlashAttribute = "dbError";
	  	redirectAttributes.addFlashAttribute("db-error", dbErrorFlashAttribute);

	  	return "redirect:/db-error";
	}
	
	
	// add session expired page used also for invalid session url
	@GetMapping("/db-error")
	public String dbError(Model theModel){
		
		if (theModel.containsAttribute("db-error")) {
			
			logger.info("GET /db-error");
			
			return "db-error";
			
		}else {
			return "redirect:/";
		}
	}
	

	@GetMapping("/out")
	public void showMyLogoutPage(HttpServletRequest req, HttpServletResponse resp) {
		
		// Creating new HttpSession on logout because the old session is INVALIDATED
		@SuppressWarnings("unused")
		HttpSession newSession = req.getSession(true);
		
		try {
			resp.sendRedirect(req.getContextPath() + "/");
		} catch (IOException e) {
			logger.warn("IOException in >>> /out");  	
		}	
	}
	
}

package com.luv2code.springdemo.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;


// TESTING FIX GOOGLE CHROME (PROCESSING REQUEST HANG)
public class SameSiteFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request,  ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Set-Cookie", "HttpOnly; SameSite=strict");
        
        chain.doFilter(request, response);
	}

}

package com.luv2code.springdemo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.luv2code.springdemo.generators.UserAccessLevel;

@Controller
public class HomeController {


	@Autowired
	private UserAccessLevel accessLevel; 
	
	@GetMapping("/")
	public String showHome(Model theModel) {
		
		// model attribute with custom text access level
		theModel.addAttribute("accessLevel", accessLevel.getAccessLevel());
		
		LocalDate today = LocalDate.now();
		String formatedDate =  today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		
		// model attribute with custom formated current date
		theModel.addAttribute("formatedDate", formatedDate);
		
		return "home";
	}
	
	@PostMapping("/")
	public String showHomeRedirect() {
		
		return "redirect:/";
	}
	
}

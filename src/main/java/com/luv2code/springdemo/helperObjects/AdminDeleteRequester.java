package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.Min;

import com.luv2code.springdemo.customAnnotation.RequesterNotDeleted;
import com.luv2code.springdemo.customAnnotation.RequesterNotInUse;

public class AdminDeleteRequester {


	private String name;
	
	@Min(value = 1, message="{adminDeleteRequester.id.notSelected}")
	@RequesterNotDeleted
	@RequesterNotInUse
	private int id;
		
	public AdminDeleteRequester() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		
}

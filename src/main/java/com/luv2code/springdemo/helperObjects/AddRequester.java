package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.luv2code.springdemo.customAnnotation.RequesterExists;

public class AddRequester {

	@NotNull(message="{addRequester.name.notSelected}")
	@Size(max = 50, message="{addRequester.name.max}")
	@RequesterExists
	private String name;
	
	public AddRequester() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

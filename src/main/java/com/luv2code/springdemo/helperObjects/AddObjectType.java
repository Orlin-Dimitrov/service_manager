package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.luv2code.springdemo.customAnnotation.ObjectTypeExists;

public class AddObjectType {

	@NotNull(message="{addObjectType.type.notSelected}")
	@Size(max = 50, message="{addObjectType.type.max}")
	@ObjectTypeExists
	private String type;
	
	public AddObjectType() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}

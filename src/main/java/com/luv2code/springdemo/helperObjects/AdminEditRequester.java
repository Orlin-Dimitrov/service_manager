package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.luv2code.springdemo.customAnnotation.RequesterExists;
import com.luv2code.springdemo.customAnnotation.RequesterNotDeleted;

public class AdminEditRequester {

	@NotNull(message="{adminEditRequester.name.notSelected}")
	@Size(max = 50, message="{adminEditRequester.name.max}")
	@RequesterExists
	private String name;
	
	@Min(value = 1, message="{adminEditRequester.id.notSelected}")
	@RequesterNotDeleted
	private int id;
	
	private String oldName;
	
	public AdminEditRequester() {
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

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	
	
}

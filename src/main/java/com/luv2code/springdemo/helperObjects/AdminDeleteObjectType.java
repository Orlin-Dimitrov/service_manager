package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.Min;

import com.luv2code.springdemo.customAnnotation.ObjectTypeNotInUse;
import com.luv2code.springdemo.customAnnotation.ObjectTypeNotDeleted;

public class AdminDeleteObjectType {


	private String type;
	
	@Min(value = 1, message="{adminDeleteObjectType.typeId.notSelected}")
	@ObjectTypeNotInUse
	@ObjectTypeNotDeleted
	private int typeId;
	
	
	public AdminDeleteObjectType() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}	
	
}

package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.luv2code.springdemo.customAnnotation.ObjectTypeExists;
import com.luv2code.springdemo.customAnnotation.ObjectTypeNotDeleted;

public class AdminEditObjectType {

	@NotNull(message="{adminEditObjectType.type.notSelected}")
	@Size(max = 50, message="{adminEditObjectType.type.max}")
	@ObjectTypeExists
	private String type;
	
	@Min(value = 1, message="{adminEditObjectType.typeId.notSelected}")
	@ObjectTypeNotDeleted
	private int typeId;
	
	private String oldType;
	
	public AdminEditObjectType() {
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

	public String getOldType() {
		return oldType;
	}

	public void setOldType(String oldType) {
		this.oldType = oldType;
	}
	
	
}

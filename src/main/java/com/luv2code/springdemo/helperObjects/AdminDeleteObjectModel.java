package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.Min;

import com.luv2code.springdemo.customAnnotation.ObjectModelNotDeleted;
import com.luv2code.springdemo.customAnnotation.ObjectModelNotInUse;
import com.luv2code.springdemo.customAnnotation.ObjectTypeNotDeleted;


public class AdminDeleteObjectModel {

	
	@Min(value = 1, message="{adminDeleteObjectModel.typeId.notSelected}")
	@ObjectTypeNotDeleted
	private int typeId;
	
	private String type;

	@Min(value = 1, message="{adminDeleteObjectModel.modelId.notSelected}")
	@ObjectModelNotInUse
	@ObjectModelNotDeleted	
	private int modelId;
	
	private String model;
	
	
	public AdminDeleteObjectModel() {
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}	
	
	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}

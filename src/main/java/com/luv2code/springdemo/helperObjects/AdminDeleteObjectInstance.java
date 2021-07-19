package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.Min;

import com.luv2code.springdemo.customAnnotation.ObjectInstanceNotDeleted;
import com.luv2code.springdemo.customAnnotation.ObjectInstanceNotInUse;
import com.luv2code.springdemo.customAnnotation.ObjectModelNotDeleted;
import com.luv2code.springdemo.customAnnotation.ObjectTypeNotDeleted;


public class AdminDeleteObjectInstance {

	
	@Min(value = 1, message="{adminDeleteObjectInstance.typeId.notSelected}")
	@ObjectTypeNotDeleted
	private int typeId;
	
	@Min(value = 1, message="{adminDeleteObjectInstance.modelId.notSelected}")
	@ObjectModelNotDeleted
	private int modelId;
	
	private String type;
	
	private String model;
	
	@Min(value = 1, message="{adminDeleteObjectInstance.instanceId.notSelected}")
	@ObjectInstanceNotInUse
	@ObjectInstanceNotDeleted	
	private int instanceId;
	
	private String instance;
	
	
	public AdminDeleteObjectInstance() {
	}


	public int getTypeId() {
		return typeId;
	}


	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}


	public int getModelId() {
		return modelId;
	}


	public void setModelId(int modelId) {
		this.modelId = modelId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}

	
	public int getInstanceId() {
		return instanceId;
	}


	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}


	public String getInstance() {
		return instance;
	}


	public void setInstance(String instance) {
		this.instance = instance;
	}	
	
}

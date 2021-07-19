package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.luv2code.springdemo.customAnnotation.ObjectInstanceExists;
import com.luv2code.springdemo.customAnnotation.ObjectModelNotDeleted;
import com.luv2code.springdemo.customAnnotation.ObjectTypeNotDeleted;

@ObjectInstanceExists
public class AddObjectInstance {


	
	@Min(value = 1, message="{addObjectInstance.typeId.notSelected}")
	@ObjectTypeNotDeleted
	private int typeId;

	private String typeName;
	
	@Min(value = 1, message="{addObjectInstance.modelId.notSelected}")
	@ObjectModelNotDeleted
	private int modelId;
	
	private String modelName;
	
	@NotNull(message="{addObjectInstance.instance.notSelected}")
	@Size(max = 50, message="{addObjectInstance.instance.max}")
	private String instance;
	
	
	public AddObjectInstance() {
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


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getModelName() {
		return modelName;
	}


	public void setModelName(String modelName) {
		this.modelName = modelName;
	}


	public String getInstance() {
		return instance;
	}


	public void setInstance(String instance) {
		this.instance = instance;
	}


	
	
}

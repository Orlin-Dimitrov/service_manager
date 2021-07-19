package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.luv2code.springdemo.customAnnotation.ObjectModelExists;
import com.luv2code.springdemo.customAnnotation.ObjectModelNotDeleted;
import com.luv2code.springdemo.customAnnotation.ObjectTypeNotDeleted;

@ObjectModelExists
public class AdminEditObjectModel {

	
	@Min(value = 1, message="{adminEditObjectModel.typeId.notSelected}")
	@ObjectTypeNotDeleted
	private int typeId;
	
	private String typeName;
	
	@NotNull(message="{adminEditObjectModel.model.notSelected}")
	@Size(max = 50, message="{adminEditObjectModel.model.max}")
	private String model;
	
	@Min(value = 1, message="{adminEditObjectModel.modelId.notSelected}")
	@ObjectModelNotDeleted
	private int modelId;
	
	private String oldModel;
	
	public AdminEditObjectModel() {
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
	
	public String getOldModel() {
		return oldModel;
	}

	public void setOldModel(String oldModel) {
		this.oldModel = oldModel;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}

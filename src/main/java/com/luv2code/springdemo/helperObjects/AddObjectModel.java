package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.luv2code.springdemo.customAnnotation.ObjectModelExists;
import com.luv2code.springdemo.customAnnotation.ObjectTypeNotDeleted;

@ObjectModelExists
public class AddObjectModel {


	
	@Min(value = 1, message="{addObjectModel.typeId.notSelected}")
	@ObjectTypeNotDeleted
	private int typeId;
	
	private String typeName;
	
	@NotNull(message="{addObjectModel.model.notSelected}")
	@Size(max = 50, message="{addObjectModel.model.max}")
//	@ObjectModelExists(parentTypeId=typeId)
	private String model;
	
	
	public AddObjectModel() {
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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

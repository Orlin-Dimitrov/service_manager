package com.luv2code.springdemo.service;

import java.util.List;

import com.luv2code.springdemo.entity.ObjectModel;

public interface ObjectModelService {

	public List<ObjectModel> getObjectModelsForCertainType(int theId);
	
	public ObjectModel getObjectModel(int theId);
	
	public boolean modelExists(String searchedModel, int parentTypeId);
	
	public void saveObjectModel(ObjectModel objectModel);
	
	public void deleteObjectModel(int theId);
	
	public boolean objectModelInUse(int theId);
}

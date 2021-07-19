package com.luv2code.springdemo.dao;

import java.util.List;

import com.luv2code.springdemo.entity.ObjectInstance;

public interface ObjectInstanceDAO {

	public List<ObjectInstance> getObjectInstancesForCertainModel(int theId);
	
	public ObjectInstance getObjectInstanceById(int theId);
	
	public boolean instanceExists(String searchedInstance, int parentModelId);
	
	public void saveObjectInstance(ObjectInstance objectInstance);
	
	public void deleteObjectInstance(int theId);
	
	public boolean objectInstanceInUse(int theId);
}

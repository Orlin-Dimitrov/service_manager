package com.luv2code.springdemo.dao;

import java.util.List;

import com.luv2code.springdemo.entity.ObjectType;

public interface ObjectTypeDAO {

	public List<ObjectType> getObjectTypes();
	
	public ObjectType getObjectType(int theId);
	
	public boolean typeExists(String searchedType);
	
	public void saveObjectType(ObjectType objectType);
	
	public void deleteObjectType(int theId);
	
	public boolean objectTypeInUse(int theId);

}

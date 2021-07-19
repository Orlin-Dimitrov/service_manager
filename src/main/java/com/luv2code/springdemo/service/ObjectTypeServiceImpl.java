package com.luv2code.springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.ObjectTypeDAO;
import com.luv2code.springdemo.entity.ObjectType;

@Service
public class ObjectTypeServiceImpl implements ObjectTypeService {
	
	// need to inject ObjectTypeDAO
	@Autowired
	private ObjectTypeDAO objectTypeDAO;

	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public List<ObjectType> getObjectTypes() {		
		return objectTypeDAO.getObjectTypes();
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public ObjectType getObjectType(int theId) {		
		return objectTypeDAO.getObjectType(theId);
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public boolean typeExists(String searchedType) {		
		return objectTypeDAO.typeExists(searchedType);
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public void saveObjectType(ObjectType objectType) {		
		objectTypeDAO.saveObjectType(objectType);
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public void deleteObjectType(int theId) {		
		objectTypeDAO.deleteObjectType(theId);
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public boolean objectTypeInUse(int theId) {		
		return objectTypeDAO.objectTypeInUse(theId);
	}
	
}

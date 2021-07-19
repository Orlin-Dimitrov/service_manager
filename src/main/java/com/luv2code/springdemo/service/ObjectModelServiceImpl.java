package com.luv2code.springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.ObjectModelDAO;
import com.luv2code.springdemo.entity.ObjectModel;

@Service
public class ObjectModelServiceImpl implements ObjectModelService {

	// need to inject ObjectModelDAO
	@Autowired
	private ObjectModelDAO objectModelDAO;
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public List<ObjectModel> getObjectModelsForCertainType(int theId) {

		return objectModelDAO.getObjectModelsForCertainType(theId);
	}

	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public ObjectModel getObjectModel(int theId) {		
		return objectModelDAO.getObjectModel(theId);
	}	
	
	@Override
	@Transactional(value="transactionManager")
	public boolean modelExists(String searchedModel, int parentTypeId) {		
		return objectModelDAO.modelExists(searchedModel, parentTypeId);
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public void saveObjectModel(ObjectModel objectModel) {		
		objectModelDAO.saveObjectModel(objectModel);
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public void deleteObjectModel(int theId) {		
		objectModelDAO.deleteObjectModel(theId);
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public boolean objectModelInUse(int theId) {		
		return objectModelDAO.objectModelInUse(theId);
	}
}

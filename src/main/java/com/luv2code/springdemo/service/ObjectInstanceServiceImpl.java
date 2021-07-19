package com.luv2code.springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.ObjectInstanceDAO;
import com.luv2code.springdemo.entity.ObjectInstance;

@Service
public class ObjectInstanceServiceImpl implements ObjectInstanceService {

	// need to inject ObjectInstanceDAO
	@Autowired
	private ObjectInstanceDAO objectInstanceDAO;
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public List<ObjectInstance> getObjectInstancesForCertainModel(int theId) {

		return objectInstanceDAO.getObjectInstancesForCertainModel(theId);
	}

	@Override
	@Transactional(value="transactionManager")
	public ObjectInstance getObjectInstanceById(int theId) {

		return objectInstanceDAO.getObjectInstanceById(theId);
	}
	
	@Override
	@Transactional(value="transactionManager")
	public boolean instanceExists(String searchedInstance, int parentModelId) {		
		return objectInstanceDAO.instanceExists(searchedInstance, parentModelId);
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public void saveObjectInstance(ObjectInstance objectInstance) {		
		objectInstanceDAO.saveObjectInstance(objectInstance);
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public void deleteObjectInstance(int theId) {		
		objectInstanceDAO.deleteObjectInstance(theId);;
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public boolean objectInstanceInUse(int theId) {		
		return objectInstanceDAO.objectInstanceInUse(theId);
	}
}

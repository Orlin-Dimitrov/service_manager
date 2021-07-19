package com.luv2code.springdemo.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.RequesterDAO;
import com.luv2code.springdemo.entity.Requester;

@Service
public class RequesterServiceImpl implements RequesterService {

	// need to inject Requester DAO
	@Autowired
	private RequesterDAO requesterDAO;
	
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public List<Requester> getRequesters() {

		return requesterDAO.getRequesters();
	}


	@Override
	@Transactional(value="transactionManager")
	public Requester getRequester(int theId) {

		return requesterDAO.getRequester(theId);
	}

	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public boolean requesterExists(String searchedRequester) {		
		return requesterDAO.requesterExists(searchedRequester);
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public void saveRequester(Requester requester) {		
		requesterDAO.saveRequester(requester);
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public void deleteRequester(int theId) {		
		requesterDAO.deleteRequester(theId);;
	}
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public boolean requesterInUse(int theId) {		
		return requesterDAO.requesterInUse(theId);
	}
	
	
	// TESTING
	@Override
	@Transactional(value="transactionManager")
	public LinkedHashMap<Integer, String> getRequestersMap(){		
		return requesterDAO.getRequestersMap();
	}
}

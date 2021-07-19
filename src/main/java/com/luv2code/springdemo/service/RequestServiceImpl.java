package com.luv2code.springdemo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.RequestDAO;
import com.luv2code.springdemo.entity.Request;
import com.luv2code.springdemo.helperObjects.SearchPaginatedPage;

@Service
public class RequestServiceImpl implements RequestService {

	// need to inject Request DAO	
	@Autowired
	private RequestDAO requestDAO;
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public List<Request> getRequests() {
		return requestDAO.getRequests();		
	}

	@Override
	@Transactional(value="transactionManager")
	public Request getRequest(int theId) {
		return requestDAO.getRequest(theId);
	}

	@Override
	@Transactional(value="transactionManager")
	public Request getLastRequest() {
		return requestDAO.getLastRequest();
	}
	
	@Override
	@Transactional(value="transactionManager")
	public void saveNewRequest(Request newRequest) {
		requestDAO.saveNewRequest(newRequest);
	}
	
	@Override
	@Transactional(value="transactionManager")
	public void updateExistingRequest(Request existingRequest) {
		requestDAO.updateExistingRequest(existingRequest);;
	}
	
	// List All Requests, Server Side Pagination
	@Override
	@Transactional(value="transactionManager")
	public List<Request> getRequestsPaginated(int firstObject, int numberOfResults) {
		return requestDAO.getRequestsPaginated(firstObject, numberOfResults);		
	}
	
	// Client Side Pagination when searching for Known Object
	@Override
	@Transactional(value="transactionManager")
	public List<Request> searchRequestResultsForKnownObject(int requesterId, int objectTypeId, int objectModelId, int objectInstanceId, LocalDate startDate, LocalDate endDate){
		return requestDAO.searchRequestResultsForKnownObject(requesterId, objectTypeId, objectModelId, objectInstanceId, startDate, endDate);
	}
		
	// Client Side Pagination when searching for Date/Requester
	@Override
	@Transactional(value="transactionManager")
	public List<Request> searchRequestResultsForDateRequester(int requesterId, LocalDate startDate, LocalDate endDate){
		return requestDAO.searchRequestResultsForDateRequester(requesterId, startDate, endDate);
	}
	
	
	// Server Side Pagination when searching for Known Object
	@Override
	@Transactional(value="transactionManager")
	public SearchPaginatedPage searchRequestResultsForKnownObjectSSPagination(int requesterId, int objectTypeId, int objectModelId, int objectInstanceId,
																				LocalDate startDate, LocalDate endDate,
																				int firstObject, int numberOfResults,
																				int orderByColumn, String orderDirection) {
		
		return requestDAO.searchRequestResultsForKnownObjectSSPagination(requesterId, objectTypeId, objectModelId, objectInstanceId, 
																		startDate, endDate, 
																		firstObject, numberOfResults, 
																		orderByColumn, orderDirection);
	}
	
	
	// Server Side Pagination when searching for Date/Requester
	@Override
	@Transactional(value="transactionManager")
	public SearchPaginatedPage searchRequestResultsForDateRequesterSSPagination(int requesterId,
																				LocalDate startDate, LocalDate endDate,
																				int firstObject, int numberOfResults,
																				int orderByColumn, String orderDirection) {
		
		return requestDAO.searchRequestResultsForDateRequesterSSPagination(requesterId, 
																			startDate, endDate, 
																			firstObject, numberOfResults, 
																			orderByColumn, orderDirection);
	}
	

	// For Testing 
	@Override
	@Transactional(value="transactionManager")
	public void saveMultipleRequests(List<Request> requests){
		requestDAO.saveMultipleRequests(requests);
	}
}

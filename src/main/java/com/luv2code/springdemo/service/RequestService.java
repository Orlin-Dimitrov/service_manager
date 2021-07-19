package com.luv2code.springdemo.service;

import java.time.LocalDate;
import java.util.List;

import com.luv2code.springdemo.entity.Request;
import com.luv2code.springdemo.helperObjects.SearchPaginatedPage;

public interface RequestService {

	public List<Request> getRequests();
	
	public Request getRequest(int theId);
	
	public Request getLastRequest();
	
	public void saveNewRequest(Request newRequest);
	
	public void updateExistingRequest(Request existingRequest);
	
	// List All Requests, Server Side Pagination
	public List<Request> getRequestsPaginated(int firstObject, int numberOfResults);
	
	// Client Side Pagination when searching for Known Object
	public List<Request> searchRequestResultsForKnownObject(int requesterId, int objectTypeId, int objectModelId, int objectInstanceId, LocalDate startDate, LocalDate endDate);
	
	// Client Side Pagination when searching for Date/Requester
	public List<Request> searchRequestResultsForDateRequester(int requesterId, LocalDate startDate, LocalDate endDate);
	
	// Server Side Pagination when searching for Known Object
	public SearchPaginatedPage searchRequestResultsForKnownObjectSSPagination(int requesterId, int objectTypeId, int objectModelId, int objectInstanceId,
																				LocalDate startDate, LocalDate endDate,
																				int firstObject, int numberOfResults,
																				int orderByColumn, String orderDirection);

	// Server Side Pagination when searching for Date/Requester
	public SearchPaginatedPage searchRequestResultsForDateRequesterSSPagination(int requesterId,
																				LocalDate startDate, LocalDate endDate,
																				int firstObject, int numberOfResults,
																				int orderByColumn, String orderDirection);
	
	// For Testing
	public void saveMultipleRequests(List<Request> requests);
}

package com.luv2code.springdemo.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.ObjectInstance;
import com.luv2code.springdemo.entity.ObjectModel;
import com.luv2code.springdemo.entity.ObjectType;
import com.luv2code.springdemo.entity.Request;
import com.luv2code.springdemo.entity.Requester;
import com.luv2code.springdemo.helperObjects.SearchPaginatedPage;

@Repository
public class RequestDAOImpl implements RequestDAO {

	// need to inject the session factory
	@Autowired
	@Qualifier(value="sessionFactory")
	private SessionFactory sessionFactory;
	
	//Retrieving list of all the request.
	@Override
	public List<Request> getRequests() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create query ... sort by id
		Query<Request> theQuery =
					currentSession.createQuery("from Request order by id", Request.class);
		
		// execute query and get result list		
		List<Request> requests = theQuery.getResultList();
		
		// return the results
		return requests;
	}

	//Retrieving request with specific id.
	@Override
	public Request getRequest(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		Request theRequest = currentSession.get(Request.class, theId); 
		
		// return the result
		return theRequest;
	}

	//Retrieving the last request.
	@Override
	public Request getLastRequest() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//retrieve/read from database the last Request
		Request theRequest = currentSession.createQuery("from Request ORDER BY id DESC", Request.class)
	            .setMaxResults(1).uniqueResult();
	
		// return the result
		return theRequest;
	}

	// Saving NEW Request
	@Override
	public void saveNewRequest(Request newRequest) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// saving the new Request
		currentSession.save(newRequest);
		
	}

	// Update Existing Request
	@Override
	public void updateExistingRequest(Request existingRequest) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// saving the new Request
		currentSession.update(existingRequest);
		
	}


	//Retrieving list of all the request. SERVER SIDE Pagination
	@Override
	public List<Request> getRequestsPaginated(int firstObject, int numberOfResults) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create query ... sort by id
		Query<Request> theQuery =
					currentSession.createQuery("from Request order by id DESC", Request.class);
		
		// setting the first result to be shown and the numbers of results to be displayed
		theQuery.setFirstResult(firstObject);
		theQuery.setMaxResults(numberOfResults);
		
		// execute query and get result list		
		List<Request> requests = theQuery.getResultList();
		
		// return the results
		return requests;
	}

	

	
	// Searching for known object. Client Side Pagination. Criteria API.
	@Override
	public List<Request> searchRequestResultsForKnownObject(int requesterId, int objectTypeId, int objectModelId, int objectInstanceId, LocalDate startDate, LocalDate endDate) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
						
		// using criteria builder for the query
		CriteriaBuilder builder = currentSession.getCriteriaBuilder();		
		
		// using criteria for the querying the Request object
		CriteriaQuery<Request> criteriaQuery = builder.createQuery(Request.class);		
		
		Root<Request> root = criteriaQuery.from(Request.class);

		criteriaQuery.select(root);
		
		// null values for Predicate , defined in the if else statement.
		Predicate inServiceBuilder = null;
		Predicate inOperationBuilder = null;
		
		// Using predefined methods setPredicateInService and setPredicateInOperation to set the Predicates	
		inServiceBuilder = setPredicateInService(objectTypeId, objectModelId, objectInstanceId, builder, root,
				inServiceBuilder);	
		
		inOperationBuilder = setPredicateInOperation(objectTypeId, objectModelId, objectInstanceId, builder, root,
				inOperationBuilder);
			
		// If Requester is selected
		if(requesterId > 0) {
			
			// Joining the table for Requesters
			Join<Request,Requester> r = root.join( "requester" , JoinType.LEFT);
			
			// Creating the query with the Predicated values for item in service or operation, date range and requester.
			criteriaQuery = criteriaQuery.select(root)
					.where(builder.and(
							builder.or(inServiceBuilder, inOperationBuilder),
							builder.between(root.get("date"), startDate, endDate),
							builder.equal(r.get("id"), requesterId))
							);
		}else {
			
			// Creating the query with the Predicated values for item in service or operation and date range.
			criteriaQuery = criteriaQuery.select(root)
					.where(builder.and(
							builder.or(inServiceBuilder, inOperationBuilder),
							builder.between(root.get("date"), startDate, endDate))
							);		
		}
		
		// Creating default order
		criteriaQuery.orderBy(builder.desc(root.get("id")));
		
		// Creating the query
		Query<Request> query = currentSession.createQuery(criteriaQuery);
		
		// Retrieving the list
		List<Request> results = query.getResultList();
	
		// Returning the result list
		return results;
	}	
	
	
	// Searching for known object. Server Side Pagination. Criteria API. START !!!
	@Override
	public SearchPaginatedPage searchRequestResultsForKnownObjectSSPagination(int requesterId, int objectTypeId, int objectModelId, int objectInstanceId,
																				LocalDate startDate, LocalDate endDate,
																				int firstObject, int numberOfResults,
																				int orderByColumn, String orderDirection) {
				
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();				
		
		// using criteria builder for the query
		CriteriaBuilder builder = currentSession.getCriteriaBuilder();		
		
		// Criteria query for the search of known object, paginated and ordered
		CriteriaQuery<Request> criteriaQuery = builder.createQuery(Request.class);
		
		// Criteria query for the total number of found known objects (Necessary for DataTables)
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
		
		// root for search of known object, paginated and ordered query
		Root<Request> root = criteriaQuery.from(Request.class);

		// root for the total number of found known objects query
		Root<Request> total = countQuery.from(Request.class);
		
		// This probably must be removed !!!
		criteriaQuery.select(root);
							
		// Predicates for search of known object, paginated and ordered query
		Predicate inServiceBuilder = null;
		Predicate inOperationBuilder = null;
		
		// Predicates for total number of found known objects query 
		Predicate inServiceBuilderTotal = null;
		Predicate inOperationBuilderTotal = null;
		
		// Using predefined methods setPredicateInService and setPredicateInOperation to set the Predicates		
		// search of known object, paginated and ordered query
		inServiceBuilder = setPredicateInService(objectTypeId, objectModelId, objectInstanceId, builder, root,
				inServiceBuilder);	
		
		inOperationBuilder = setPredicateInOperation(objectTypeId, objectModelId, objectInstanceId, builder, root,
				inOperationBuilder);
		
		// total number of found known objects query
		inServiceBuilderTotal = setPredicateInService(objectTypeId, objectModelId, objectInstanceId, builder, total,
				inServiceBuilderTotal);
		
		inOperationBuilderTotal = setPredicateInOperation(objectTypeId, objectModelId, objectInstanceId, builder, total,
				inOperationBuilderTotal);
		
		
		// Joining the table for Requesters for search of known object, paginated and ordered query. Also used for orderBy case
		Join<Request,Requester> r = root.join( "requester" , JoinType.LEFT);
		
		// Joining the table for Requesters for Total number of found known objects query
		Join<Request,Requester> rt = total.join( "requester" , JoinType.LEFT);
		
		// If Requester is selected
		if(requesterId > 0) {
					
			// Creating the query with the Predicated values for item in service or operation, date range and requester.
			criteriaQuery = criteriaQuery.select(root)
					.where(builder.and(
							builder.or(inServiceBuilder, inOperationBuilder),
							builder.between(root.get("date"), startDate, endDate),
							builder.equal(r.get("id"), requesterId))
							);
			
			// Total number of fount Requests
			countQuery = countQuery.select(builder.count(total)).where(builder.and(
							builder.or(inServiceBuilderTotal, inOperationBuilderTotal),
							builder.between(total.get("date"), startDate, endDate),
							builder.equal(rt.get("id"), requesterId))
							);					
		}
		// Requester is Not selected
		else {
			
			// Creating the query with the Predicated values for item in service or operation and date range.
			criteriaQuery = criteriaQuery.select(root)
					.where(builder.and(
							builder.or(inServiceBuilder, inOperationBuilder),
							builder.between(root.get("date"), startDate, endDate))
							);
			
			// Total number of fount Requests
			countQuery = countQuery.select(builder.count(total)).where(builder.and(
							builder.or(inServiceBuilderTotal, inOperationBuilderTotal),
							builder.between(total.get("date"), startDate, endDate))
							);			
		}
		
		// Creating order cases	for DataTables
		switch(orderByColumn) {
		
			// order by id asc/desc
			case 0: if(orderDirection.toLowerCase().equals("asc")) {
						criteriaQuery.orderBy(builder.asc(root.get("id")));
					}
					else{
						criteriaQuery.orderBy(builder.desc(root.get("id")));
					};
					break;
			
			// order by date asc/desc		
			case 1: if(orderDirection.toLowerCase().equals("asc")) {
						criteriaQuery.orderBy(builder.asc(root.get("date")));
					}
					else{
						criteriaQuery.orderBy(builder.desc(root.get("date")));
					};
					break;
			
			// order by Requester Id asc/desc		
			case 2: if(orderDirection.toLowerCase().equals("asc")) {
						criteriaQuery.orderBy(builder.asc(r.get("name")));
					}
					else{
						criteriaQuery.orderBy(builder.desc(r.get("name")));
					};
					break;
			
			// default order by id desc	
			default: 
					criteriaQuery.orderBy(builder.desc(root.get("id")));
	                break;		
		}
				
		// Creating the "Search of known object, paginated and ordered query"
		Query<Request> query = currentSession.createQuery(criteriaQuery);
		
		// Setting the first result and max results on the page
		query.setFirstResult(firstObject);
		query.setMaxResults(numberOfResults);		
	
		// Retrieving the paginated list
		List<Request> results = query.getResultList();
	
		
		//Retrieving total number of results found of the "Total number of found known objects query"	
		Long totalCount = currentSession.createQuery(countQuery).getSingleResult();
		
		// Creating SearchItemPaginatedPage for storing the paginated list of Requests and the total number of found known objects
		SearchPaginatedPage searchItemPaginatedPage = new SearchPaginatedPage();

		searchItemPaginatedPage.setRequestList(results);
		searchItemPaginatedPage.setTotalResults(totalCount);
		
		// Returning the created SearchItemPaginatedPage object used in ObjectsForJSONController -> searchItemPaginatedSS method.
		return searchItemPaginatedPage;
	}

	
	/*
	 * HELPER METHOD 
	 * 
	 * Set Predicate for Item in Service
	 */ 
	private Predicate setPredicateInService(int objectTypeId, int objectModelId, int objectInstanceId,
			CriteriaBuilder builder, Root<Request> root, Predicate inServiceBuilder) {
		
		// If Object Instance is selected
		if(objectInstanceId > 0) {
			
			// Joining the tables for Instance in Service
			Join<Request,ObjectInstance> tableService = root.join( "itemInService" , JoinType.LEFT);

			inServiceBuilder = builder.equal(tableService.get("id"), objectInstanceId);	
		}
		
		// If Object Model is selected
		else if(objectModelId > 0) {
			
			// First joining the tables for Instance in Service, than Object Model tables must be joined on the Id.
			Join<Request,ObjectInstance> tableServiceInstance = root.join( "itemInService" , JoinType.LEFT);			
			Join<Request,ObjectModel> tableService = tableServiceInstance.join( "objectModel" , JoinType.LEFT);
			
			inServiceBuilder = builder.equal(tableService.get("id"), objectModelId);	
		}
		
		// If Only Object Type is selected
		else if(objectTypeId > 0) {
			
			// First joining the tables for Instance in Service, than Object Model and than Object Type tables must be joined on the Id.
			Join<Request,ObjectInstance> tableServiceInstance = root.join( "itemInService" , JoinType.LEFT);		
			Join<Request,ObjectInstance> tableServiceModel = tableServiceInstance.join( "objectModel" , JoinType.LEFT);			
			Join<Request,ObjectType> tableService = tableServiceModel.join( "objectType" , JoinType.LEFT);
			
			inServiceBuilder = builder.equal(tableService.get("id"), objectTypeId);
		}		
		else {			
			/* Code should not come to here because of form validation
			   Just in case, in the ObjectsForJSONController objectTypeId is checked to be greater than 0.
			 */
		}
		return inServiceBuilder;
	}
	
	
	/*
	 * HELPER METHOD 
	 * 
	 * Set Predicate for Item in Operation
	 */ 
	private Predicate setPredicateInOperation(int objectTypeId, int objectModelId, int objectInstanceId,
			CriteriaBuilder builder, Root<Request> root, Predicate inOperationBuilder) {
		
		// If Object Instance is selected
		if(objectInstanceId > 0) {
			
			// Joining the tables for Instance in Operation
			Join<Request,ObjectInstance> tableOperation = root.join( "itemInOperation" , JoinType.LEFT);		

			inOperationBuilder = builder.equal(tableOperation.get("id"), objectInstanceId);			
		}
		
		// If Object Model is selected
		else if(objectModelId > 0) {
			
			// First joining the tables for Instance in Operation, than Object Model tables must be joined on the Id.
			Join<Request,ObjectInstance> tableOperationInstance = root.join( "itemInOperation" , JoinType.LEFT);			
			Join<Request,ObjectModel> tableOperation = tableOperationInstance.join( "objectModel" , JoinType.LEFT);			

			inOperationBuilder = builder.equal(tableOperation.get("id"), objectModelId);		
		}
		
		// If Only Object Type is selected
		else if(objectTypeId > 0) {
			
			// First joining the tables for Instance in Operation, than Object Model and than Object Type tables must be joined on the Id.
			Join<Request,ObjectInstance> tableOperationInstance = root.join( "itemInOperation" , JoinType.LEFT);			
			Join<Request,ObjectInstance> tableOperationModel = tableOperationInstance.join( "objectModel" , JoinType.LEFT);			
			Join<Request,ObjectType> tableOperation = tableOperationModel.join( "objectType" , JoinType.LEFT);
			
			inOperationBuilder = builder.equal(tableOperation.get("id"), objectTypeId);
		}
		
		else {			
			/* Code should not come to here because of form validation
			   Just in case, in the ObjectsForJSONController objectTypeId is checked to be greater than 0.
			 */
		}
		return inOperationBuilder;
	}

	// Searching for known object. Server Side Pagination. Criteria API. END!!!
	


	
	// Searching for date/requester. Client Side Pagination. Criteria API.
	@Override
	public List<Request> searchRequestResultsForDateRequester(int requesterId, LocalDate startDate, LocalDate endDate){
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
						
		// using criteria builder for the query
		CriteriaBuilder builder = currentSession.getCriteriaBuilder();
			
		// using criteria for the querying the Request object
		CriteriaQuery<Request> criteriaQuery = builder.createQuery(Request.class);
				
		Root<Request> root = criteriaQuery.from(Request.class);

		criteriaQuery.select(root);
				
		// If Requester is selected
		if(requesterId > 0) {
			
			// Joining the table for Requesters
			Join<Request,Requester> r = root.join( "requester" , JoinType.LEFT);
			
			// Creating the query with date range and requester.
			criteriaQuery = criteriaQuery.select(root)
					.where(builder.and(
							builder.between(root.get("date"), startDate, endDate),
							builder.equal(r.get("id"), requesterId))
							);
		}else {
			
			// Creating the query with  date range.
			criteriaQuery = criteriaQuery.select(root)
					.where(builder.and(
							builder.between(root.get("date"), startDate, endDate))
							);		
		}
		
		// Creating default order
		criteriaQuery.orderBy(builder.desc(root.get("id")));
		
		// Creating the query
		Query<Request> query = currentSession.createQuery(criteriaQuery);
		
		// Retrieving the list
		List<Request> results = query.getResultList();
	
		// Returning the result list
		return results;
	}
	
	
	// Searching for date/requester. Server Side Pagination. Criteria API.
	@Override
	public SearchPaginatedPage searchRequestResultsForDateRequesterSSPagination(int requesterId,
																					LocalDate startDate, LocalDate endDate,
																					int firstObject, int numberOfResults,
																					int orderByColumn, String orderDirection) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();				
		
		// using criteria builder for the query
		CriteriaBuilder builder = currentSession.getCriteriaBuilder();		
		
		// Criteria query for the search of date/requester, paginated and ordered
		CriteriaQuery<Request> criteriaQuery = builder.createQuery(Request.class);
		
		// Criteria query for the total number of found objects (Necessary for DataTables)
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
		
		// root for search of date/requester, paginated and ordered query
		Root<Request> root = criteriaQuery.from(Request.class);

		// root for the total number of found objects query
		Root<Request> total = countQuery.from(Request.class);
		
		// This probably must be removed !!!
		criteriaQuery.select(root);
					
		
		// Joining the table for Requesters for search of date/requester, paginated and ordered query. Also used for orderBy case
		Join<Request,Requester> r = root.join( "requester" , JoinType.LEFT);
		
		// Joining the table for Requesters for Total number of found objects query
		Join<Request,Requester> rt = total.join( "requester" , JoinType.LEFT);
		
		// If Requester is selected
		if(requesterId > 0) {
					
			// Creating the query with date range and requester.
			criteriaQuery = criteriaQuery.select(root)
					.where(builder.and(
							builder.between(root.get("date"), startDate, endDate),
							builder.equal(r.get("id"), requesterId))
							);
			
			// Total number of fount Requests
			countQuery = countQuery.select(builder.count(total)).where(builder.and(
							builder.between(total.get("date"), startDate, endDate),
							builder.equal(rt.get("id"), requesterId))
							);					
		}
		// Requester is Not selected
		else {
			
			// Creating the query with date range.
			criteriaQuery = criteriaQuery.select(root)
					.where(builder.and(
							builder.between(root.get("date"), startDate, endDate))
							);
			
			// Total number of fount Requests
			countQuery = countQuery.select(builder.count(total)).where(builder.and(
							builder.between(total.get("date"), startDate, endDate))
							);			
		}
		
		// Creating order cases	for DataTables
		switch(orderByColumn) {
		
			// order by id asc/desc
			case 0: if(orderDirection.toLowerCase().equals("asc")) {
						criteriaQuery.orderBy(builder.asc(root.get("id")));
					}
					else{
						criteriaQuery.orderBy(builder.desc(root.get("id")));
					};
					break;
			
			// order by date asc/desc		
			case 1: if(orderDirection.toLowerCase().equals("asc")) {
						criteriaQuery.orderBy(builder.asc(root.get("date")));
					}
					else{
						criteriaQuery.orderBy(builder.desc(root.get("date")));
					};
					break;
			
			// order by Requester Id asc/desc		
			case 2: if(orderDirection.toLowerCase().equals("asc")) {
						criteriaQuery.orderBy(builder.asc(r.get("name")));
					}
					else{
						criteriaQuery.orderBy(builder.desc(r.get("name")));
					};
					break;
			
			// default order by id desc	
			default: 
					criteriaQuery.orderBy(builder.desc(root.get("id")));
	                break;		
		}
				
		// Creating the "Search for date/requester, paginated and ordered query"
		Query<Request> query = currentSession.createQuery(criteriaQuery);
		
		// Setting the first result and max results on the page
		query.setFirstResult(firstObject);
		query.setMaxResults(numberOfResults);		
	
		// Retrieving the paginated list
		List<Request> results = query.getResultList();
	
		
		//Retrieving total number of results found of the "Total number of found objects when searching for date/requester query"	
		Long totalCount = currentSession.createQuery(countQuery).getSingleResult();
		
		// Creating SearchPaginatedPage for storing the paginated list of Requests and the total number of found known objects
		SearchPaginatedPage searchDateRequesterPaginatedPage = new SearchPaginatedPage();

		searchDateRequesterPaginatedPage.setRequestList(results);
		searchDateRequesterPaginatedPage.setTotalResults(totalCount);
		
		// Returning the created SearchPaginatedPage object used in ObjectsForJSONController -> searchDateRequesterPaginatedSS method.
		return searchDateRequesterPaginatedPage;
	}
	
	
	
	
	
	
	// For Testing purpose. Adding multiple Requests to the DB	
	@Override
	public void saveMultipleRequests(List<Request> requests) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// saving the Requests
		for(int i = 0; i < requests.size(); i++) {
			currentSession.save(requests.get(i));
			
		    if( i % 40 == 0 ) { // Same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		    	currentSession.flush();
		    	currentSession.clear();
		    }
		}
		
	}
		
}

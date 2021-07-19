package com.luv2code.springdemo.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Request;
import com.luv2code.springdemo.entity.Requester;

@Repository
public class RequesterDAOImpl implements RequesterDAO {

	// need to inject the session factory
	@Autowired
	@Qualifier(value="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public List<Requester> getRequesters() {
	
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create query ... sort by id
		Query<Requester> theQuery =
					currentSession.createQuery("from Requester order by name", Requester.class);
		
		// execute query and get result list		
		List<Requester> requesters = theQuery.getResultList();
		
		// return the results
		return requesters;
	}

	@Override
	public Requester getRequester(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		Requester theRequester = currentSession.get(Requester.class, theId); 
		
		return theRequester;
	}
	
	// TESTING Check if Requester exists
	@Override
	public boolean requesterExists(String searchedRequester) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query 
		Query<String> theQuery = 
				currentSession.createQuery("SELECT name from Requester where lower(name) like :searchedRequester",
						String.class).setMaxResults(1);
		
		theQuery.setParameter("searchedRequester", searchedRequester.toLowerCase());

		// execute query and get result list. if entry exists searched Requester found		
		List<String> found = theQuery.getResultList();
		
//		System.out.println(">>> INSIDE RequesterDAO >>> found Requester: " + found.toString());
			
		boolean requesterExists = true;
		if(found.isEmpty()) {
			requesterExists = false;
		}
				
		// return boolean requesterExists	
		return requesterExists;
	}
	
	// Saving new or updating existing Requester to the DB
	public void saveRequester(Requester newRequester) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// saving the new or updating Requester. The Requester is previously checked and is unique.
		currentSession.saveOrUpdate(newRequester);
	}
	
	// Delete Requester from the DB
	@Override
	public void deleteRequester(int theId) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieving Requester from DB
		Object obj = currentSession.get(Requester.class, theId);
		Requester requesterForDeletion = (Requester) obj;
		
		// deleting Requester. 
		if(requesterForDeletion != null) {
			currentSession.delete(requesterForDeletion);
		}
		
	}

	// Check if Requester is in use by a Request. 
	@Override
	public boolean requesterInUse(int theId) {

		boolean inUse = true;
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();		

		// create a query 
		Query<Request> theQuery = 
						currentSession.createQuery("from Request where requester.id = :theId",
													Request.class).setMaxResults(1);				

		theQuery.setParameter("theId", theId);
		
		// execute query and get result list. if entry exists, object Instance is in use in a Request		
		List<Request> found = theQuery.getResultList();		
		
		// If the list is empty, the ObjectInstance is not used in any Request
		if(found.isEmpty()) {
			inUse = false;
		}
		
		return inUse;
	}
	
	
	// Get requesters for Bootstrap Select Dropdown Menu
	@Override
	public LinkedHashMap<Integer, String> getRequestersMap(){
		
		List<Requester> requesters = new ArrayList<Requester>();
		
		LinkedHashMap<Integer, String> requestersMap = new LinkedHashMap<Integer, String>();
		
		requesters = getRequesters();
			
			for (int i = 0; i < requesters.size(); i++) {
				Requester tempRequest =  requesters.get(i);
				requestersMap.put(tempRequest.getId(), tempRequest.getName());			
			}	

		return requestersMap;
	}
	
}

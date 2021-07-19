package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.ObjectInstance;
import com.luv2code.springdemo.entity.Request;

@Repository
public class ObjectInstanceDAOImpl implements ObjectInstanceDAO {

	// need to inject the session factory
	@Autowired
	@Qualifier(value="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public List<ObjectInstance> getObjectInstancesForCertainModel(int theId){
			
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create query ... sort by id
		Query<ObjectInstance> query =  currentSession.createQuery("select oi from ObjectInstance oi "
									+ "where oi.objectModel.id=:theObjectModelId "
									+ "order by oi.serialNumber asc", ObjectInstance.class);
		
		query.setParameter("theObjectModelId", theId);
		
		List<ObjectInstance> objectInstances = query.getResultList(); 
		
		// return the results
		return objectInstances;
	}
	
	@Override
	public ObjectInstance getObjectInstanceById(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		ObjectInstance theObjectInstance = currentSession.get(ObjectInstance.class, theId);
		
		return theObjectInstance;
	}
	
	// TESTING Check if object Instance exists
	@Override
	public boolean instanceExists(String searchedInstance, int parentModelId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query 
		Query<String> theQuery = 
				currentSession.createQuery("SELECT serialNumber from ObjectInstance where objectModel.id = :parentModelId and lower(serialNumber) like :searchedInstance",
						String.class).setMaxResults(1);
		
		theQuery.setParameter("searchedInstance", searchedInstance.toLowerCase());
		theQuery.setParameter("parentModelId", parentModelId);

		// execute query and get result list. if entry exists searched instance found		
		List<String> found = theQuery.getResultList();
		
//		System.out.println(">>> INSIDE ObjectInstanceDAO >>> found instance: " + found.toString());
			
		boolean instanceExists = true;
		if(found.isEmpty()) {
			instanceExists = false;
		}
				
		// return boolean instanceExists	
		return instanceExists;
	}
	
	// Saving the new Object Instance
	@Override
	public void saveObjectInstance(ObjectInstance objectInstance) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// saving new or updating ObjectInstance. The ObjectInstance is previously checked and is unique for given ObjectModel
		currentSession.saveOrUpdate(objectInstance);
		
	}

	// Delete Object Instance from the DB
	@Override
	public void deleteObjectInstance(int theId) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieving ObjectInstance from DB
		Object obj = currentSession.get(ObjectInstance.class, theId);
		ObjectInstance objectInstanceForDeletion = (ObjectInstance) obj;
		
		// deleting ObjectInstance. 
		if(objectInstanceForDeletion != null) {
			currentSession.delete(objectInstanceForDeletion);
		}
		
	}

	// Check if Object Instance is in use by a Request. 
	@Override
	public boolean objectInstanceInUse(int theId) {

		boolean inUse = true;
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();		

		// create a query 
		Query<Request> theQuery = 
						currentSession.createQuery("from Request where itemInService is not null and itemInService.id = :theId"
													+ " or itemInOperation is not null and itemInOperation.id = :theId",
													Request.class).setMaxResults(1);				

		theQuery.setParameter("theId", theId);
		
		
		
		
//		// create a query NOT WORKING
//		Query<Request> theQuery = 
//						currentSession.createQuery("from Request CASE WHEN itemInService is not null then where itemInService.id = :theId"
//													+ "or CASE WHEN itemInOperation is not null then where itemInOperation.id = :theId",
//								Request.class).setMaxResults(1);				
//
//		theQuery.setParameter("theId", theId);

//		// create a query  BACKUP
//		Query<Request> theQuery = 
//						currentSession.createQuery("SELECT Request where itemInService.id = :theId or itemInOperation.id = :theId",
//								Request.class).setMaxResults(1);				
//
//		theQuery.setParameter("theId", theId);
		
		// execute query and get result list. if entry exists, object Instance is in use in a Request		
		List<Request> found = theQuery.getResultList();		
		
		// If the list is empty, the ObjectInstance is not used in any Request
		if(found.isEmpty()) {
			inUse = false;
		}
		
		return inUse;
	}
		
}

package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.ObjectModel;

@Repository
public class ObjectModelDAOImpl implements ObjectModelDAO {

	// need to inject the session factory
	@Autowired
	@Qualifier(value="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public List<ObjectModel> getObjectModelsForCertainType(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create query ... sort by id
		Query<ObjectModel> query =  currentSession.createQuery("select om from ObjectModel om "
									+ "where om.objectType.id=:theObjectTypeId "
									+ "order by om.model asc", ObjectModel.class);
		
		query.setParameter("theObjectTypeId", theId);
		
		List<ObjectModel> objectModels = query.getResultList(); 
		
		// return the results
		return objectModels;
	}

	@Override
	public ObjectModel getObjectModel(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		ObjectModel theObjectModel = currentSession.get(ObjectModel.class, theId);
		
		// return the result
		return theObjectModel;
	}
	
	
	// TESTING Check if object Model exists
	@Override
	public boolean modelExists(String searchedModel, int parentTypeId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query 
		Query<String> theQuery = 
				currentSession.createQuery("SELECT model from ObjectModel where objectType.id = :parentTypeId and lower(model) like :searchedModel",
						String.class).setMaxResults(1);
		
		theQuery.setParameter("searchedModel", searchedModel.toLowerCase());
		theQuery.setParameter("parentTypeId", parentTypeId);

		// execute query and get result list. if entry exists searched model found		
		List<String> found = theQuery.getResultList();
		
//		System.out.println(">>> INSIDE ObjectModelDAO >>> found model: " + found.toString());
			
		boolean modelExists = true;
		if(found.isEmpty()) {
			modelExists = false;
		}
				
		// return boolean modelExists	
		return modelExists;
	}

	// Saving the new Object Model
	@Override
	public void saveObjectModel(ObjectModel objectModel) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// saving new or updating the ObjectModel. The ObjectModel is previously checked and is unique for given ObjectType
		currentSession.saveOrUpdate(objectModel);
		
	}
	
	// Delete Object Model from the DB
	public void deleteObjectModel(int theId) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieving ObjectModel from DB
		Object obj = currentSession.get(ObjectModel.class, theId);
		ObjectModel objectModelForDeletion = (ObjectModel) obj;
		
		// deleting ObjectModel. 
		if(objectModelForDeletion != null) {
			currentSession.delete(objectModelForDeletion);
		}
		
	}
	
	// Check if Object Model is in use. If Object Model is deleted, while another windows is on Review page and form is submitted,
	// validation will pass, but will stop at @ObjectModelNotDeleted
	public boolean objectModelInUse(int theId) {
		
		boolean inUse = true;
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieving ObjectModel from DB
		Object obj = currentSession.get(ObjectModel.class, theId);
		ObjectModel objectModel = (ObjectModel) obj;
		
		// If Object Model is null, it has been DELETED. After that check if it has child associated instances.
		if(objectModel != null) {
			if(objectModel.getInstances().isEmpty()) {
				inUse = false;
			}
		}
		// Object Model has been deleted(doesn't exists). Validation will stop at @ObjectModelNotDeleted
		else {

			inUse = false;
		}
		
		return inUse;
	}
	
	
}

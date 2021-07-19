package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.ObjectType;

@Repository
public class ObjectTypeDAOImpl implements ObjectTypeDAO {

	// need to inject the session factory
	@Autowired
	@Qualifier(value="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public List<ObjectType> getObjectTypes() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create query ... sort by id
		Query<ObjectType> theQuery =
					currentSession.createQuery("from ObjectType order by type asc", ObjectType.class);
		
		// execute query and get result list		
		List<ObjectType> objectTypes = theQuery.getResultList();
		
		// return the results
		return objectTypes;
	}
	
	
	@Override
	public ObjectType getObjectType(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		ObjectType theObjectType = currentSession.get(ObjectType.class, theId);
		
		// return the result
		return theObjectType;
	}
	
	
	// TESTING Check if object Type exists
	@Override
	public boolean typeExists(String searchedType) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query 
		Query<String> theQuery = 
				currentSession.createQuery("SELECT type from ObjectType where lower(type) like :searchedType",
						String.class).setMaxResults(1);
		
		theQuery.setParameter("searchedType", searchedType.toLowerCase());

		// execute query and get result list. if entry exists searched type found		
		List<String> found = theQuery.getResultList();
		
//		System.out.println(">>> INSIDE ObjectTypeDAO >>> found types: " + found.toString());
			
		boolean typeExists = true;
		if(found.isEmpty()) {
			typeExists = false;
		}
				
		// return boolean typeExists	
		return typeExists;
	}
	
	
	// Saving new Object Type to the DB
	public void saveObjectType(ObjectType objectType) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// saving new or updating the ObjectType. The ObjectType is previously checked and is unique.
//		currentSession.save(newObjectType);
		currentSession.saveOrUpdate(objectType);
	}
	
	// Delete Object Type from the DB
	public void deleteObjectType(int theId) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieving ObjectType from DB
		Object obj = currentSession.get(ObjectType.class, theId);
		ObjectType objectTypeForDeletion = (ObjectType) obj;
		
		// deleting ObjectType. 
		if(objectTypeForDeletion != null) {
			currentSession.delete(objectTypeForDeletion);
		}
		
	}
	
	// Check if Object Type is in use. If Object Type is deleted, while another windows is on Review page and form is submitted,
	// validation will pass, but will stop at @ObjectTypeNotDeleted
	public boolean objectTypeInUse(int theId) {
		
		boolean inUse = true;
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieving ObjectType from DB
		Object obj = currentSession.get(ObjectType.class, theId);
		ObjectType objectType = (ObjectType) obj;
		
		// If Object Type is null, it has been DELETED. After that check if it has child associated models.
		if(objectType != null) {
			if(objectType.getModels().isEmpty()) {
				inUse = false;
			}
		}
		// Object Type has been deleted(doesn't exists). Validation will stop at @ObjectTypeNotDeleted
		else {

			inUse = false;
		}
		
		return inUse;
	}
	
	
}

package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.user.SimpleUser;

@Repository
public class UserDAOImpl implements UserDAO {

	
	
	// need to inject the session sessionSecurityFactory
	@Autowired
	@Qualifier(value="sessionSecurityFactory")
	private SessionFactory sessionSecurityFactory;
	

	// Retrieving username and authority for Employee and Manager ROLE users
	@Override
	public List<SimpleUser> getSimpleUsers() {
		
		// get the current hibernate session
		Session currentSession = sessionSecurityFactory.getCurrentSession();
		
		String sql = "SELECT * FROM (" + 
									" SELECT  u.username, a.authority FROM users u LEFT JOIN authorities a ON u.username = a.username " + 
										" WHERE NOT EXISTS(" + 
															" SELECT  a2.authority FROM users u2 LEFT JOIN authorities a2 on u2.username = a2.username" + 
															" WHERE (u2.username = u.username AND a2.authority = 'ROLE_ADMIN') OR (u2.username = u.username AND a2.authority = 'ROLE_MANAGER'))" + 
									" UNION" +
															
									" SELECT  u.username, a.authority FROM users u LEFT JOIN authorities a on u.username = a.username" + 
										" WHERE a.authority = 'ROLE_MANAGER'" +								
									
									" )" + 
					" AS final" +
					" ORDER BY final.username;";
				
		NativeQuery<SimpleUser> theQuery = currentSession.createNativeQuery(sql,SimpleUser.class);
				
		// execute query and get result list
		List<SimpleUser> users = theQuery.getResultList();	
		
		// return the results		
		return users;	
	}
	
	
	// Retrieving username and authority for Employee, Manager and Admin  ROLE users
	@Override
	public List<SimpleUser> getSimpleUsersSu() {
		
		// get the current hibernate session
		Session currentSession = sessionSecurityFactory.getCurrentSession();
		
		String sql = "SELECT * FROM (" + 
									" SELECT  u.username, a.authority FROM users u LEFT JOIN authorities a ON u.username = a.username " + 
										" WHERE NOT EXISTS(" + 
															" SELECT  a2.authority FROM users u2 LEFT JOIN authorities a2 on u2.username = a2.username" + 
															" WHERE (u2.username = u.username AND a2.authority = 'ROLE_ADMIN') OR (u2.username = u.username AND a2.authority = 'ROLE_MANAGER'))" + 
									" UNION" +
															
									" SELECT  u.username, a.authority FROM users u LEFT JOIN authorities a on u.username = a.username" + 
										" WHERE a.authority = 'ROLE_MANAGER'" +
										
									" UNION" +
										
									" SELECT  u.username, a.authority FROM users u LEFT JOIN authorities a on u.username = a.username" + 
										" WHERE a.authority = 'ROLE_ADMIN' AND u.username <> 'superadmin'" +
									
									" )" + 
					" AS final" +
					" ORDER BY final.username;";
				
		NativeQuery<SimpleUser> theQuery = currentSession.createNativeQuery(sql,SimpleUser.class);		
		
		// execute query and get result list
		List<SimpleUser> users = theQuery.getResultList();
								
		// return the results		
		return users;	
	}
	
	
	// NEW Saving login date and time for user
	public void saveLoginDateTime(String userName, String dateTime) {
		
		// get the current hibernate session
		Session currentSession = sessionSecurityFactory.getCurrentSession();

		@SuppressWarnings("rawtypes")
		NativeQuery theQuery = 
				currentSession.createNativeQuery("UPDATE users SET last_login = :dateTime WHERE username =:userName");
		
		theQuery.setParameter("userName", userName);
		theQuery.setParameter("dateTime", dateTime);
		
		theQuery.executeUpdate();
	}
	
	
	// NEW Getting last login date and time	for user
	public String getLastLoginDateTime(String userName) {
		
		// get the current hibernate session
		Session currentSession = sessionSecurityFactory.getCurrentSession();
		
		NativeQuery<String> theQuery = 
				currentSession.createNativeQuery("SELECT last_login FROM users WHERE users.username =:userName", String.class);
		
		theQuery.setParameter("userName", userName);
		
		String dateTime = "";
		
		String result = theQuery.getSingleResult();
		
		// If no entry for last login date time, return empty string ""
		if(result != null) {
			dateTime = result;
		}
		
		return dateTime;
	}
	
}

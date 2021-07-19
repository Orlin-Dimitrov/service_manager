package com.luv2code.springdemo.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.RequestsPerDay;

@Repository
public class StatisticsDAOImpl implements StatisticsDAO {

	
	
	@Autowired
	@Qualifier(value="sessionFactory")
	private SessionFactory sessionFactory;
	

	// Retrieving All Requests Per day
	@Override
	public List<RequestsPerDay> getTotalRequestsPerDay() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		String sql = "SELECT cal.dt AS date, cal.requests_perday + (CASE WHEN dat.requestsN IS NULL THEN 0 ELSE dat.requestsN END) AS requests"
					+ " FROM (SELECT dt, requests_perday FROM calendar) cal"
					+ " LEFT JOIN ("
						+ "SELECT request_date, COUNT(*) as requestsN FROM request GROUP BY request_date"
						+") dat ON cal.dt = dat.request_date WHERE cal.dt between '2016-01-01' and CURDATE()";
				
		NativeQuery<RequestsPerDay> theQuery = currentSession.createNativeQuery(sql,RequestsPerDay.class);
				
		// execute query and get result list
		List<RequestsPerDay> totalRequestsPerDay = theQuery.getResultList();	
		
		// return the results		
		return totalRequestsPerDay;	
	}
	
	
	
	// Retrieving Requests per Day for specific Year
	@Override
	public List<RequestsPerDay> getRequestsPerDayForSpecificYear(String year) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		String sql = "SELECT cal.dt AS date, cal.requests_perday + (CASE WHEN dat.requestsN IS NULL THEN 0 ELSE dat.requestsN END) AS requests"
					+ " FROM (SELECT dt, requests_perday FROM calendar) cal"
					+ " LEFT JOIN ("
						+ "SELECT request_date, COUNT(*) as requestsN FROM request GROUP BY request_date"
						+") dat ON cal.dt = dat.request_date WHERE cal.dt between :startDay and :endDay";
				
		NativeQuery<RequestsPerDay> theQuery = currentSession.createNativeQuery(sql,RequestsPerDay.class);
		
		LocalDate startDay = LocalDate.parse(year+"-01-01");
		LocalDate endDay = LocalDate.parse(year+"-12-31");
		
		theQuery.setParameter("startDay", startDay);
		theQuery.setParameter("endDay", endDay);
		
		// execute query and get result list
		List<RequestsPerDay> totalRequestsPerDay = theQuery.getResultList();	
		
		// return the results		
		return totalRequestsPerDay;	
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

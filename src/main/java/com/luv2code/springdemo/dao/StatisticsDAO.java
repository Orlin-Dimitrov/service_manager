package com.luv2code.springdemo.dao;

import java.util.List;

import com.luv2code.springdemo.entity.RequestsPerDay;

public interface StatisticsDAO {
	
	// 
	public List<RequestsPerDay> getTotalRequestsPerDay();
	
	public List<RequestsPerDay> getRequestsPerDayForSpecificYear(String year);
}

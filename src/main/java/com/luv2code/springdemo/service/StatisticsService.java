package com.luv2code.springdemo.service;

import java.util.List;

import com.luv2code.springdemo.entity.RequestsPerDay;

public interface StatisticsService {

	public List<RequestsPerDay> getTotalRequestsPerDay();
	
	public List<RequestsPerDay> getRequestsPerDayForSpecificYear(String year);
}

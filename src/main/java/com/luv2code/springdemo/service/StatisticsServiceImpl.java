package com.luv2code.springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.StatisticsDAO;
import com.luv2code.springdemo.entity.RequestsPerDay;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	// 	
	@Autowired
	private StatisticsDAO statisticsDAO;
	
	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public List<RequestsPerDay> getTotalRequestsPerDay() {
		return statisticsDAO.getTotalRequestsPerDay();		
	}


	// need to specific the Transaction manager : transactionManager
	@Override
	@Transactional(value="transactionManager")
	public List<RequestsPerDay> getRequestsPerDayForSpecificYear(String year) {
		return statisticsDAO.getRequestsPerDayForSpecificYear(year);		
	}
}

package com.luv2code.springdemo.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.luv2code.springdemo.entity.Requester;

public interface RequesterDAO {

	public List<Requester> getRequesters();
	
	public Requester getRequester(int theId);
	
	public boolean requesterExists(String searchedRequester);
	
	public void saveRequester(Requester requester);
	
	public void deleteRequester(int theId);
	
	public boolean requesterInUse(int theId);
	
	public LinkedHashMap<Integer, String> getRequestersMap();
}

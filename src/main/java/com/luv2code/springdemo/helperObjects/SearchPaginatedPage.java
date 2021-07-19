// Object to store list of requests for paginated result search page and the total number of results from the search.

package com.luv2code.springdemo.helperObjects;

import java.util.List;

import com.luv2code.springdemo.entity.Request;

public class SearchPaginatedPage {

	// returned Request list for a specific page
	private List<Request> requestsList;
	
	// total Request entries in the database for the searched critertia
	private Long totalResults;
	
	
	public Long getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(Long totalResults) {
		this.totalResults = totalResults;
	}
	public List<Request> getRequestList() {
		return requestsList;
	}
	public void setRequestList(List<Request> requestsList) {
		this.requestsList = requestsList;
	}



}

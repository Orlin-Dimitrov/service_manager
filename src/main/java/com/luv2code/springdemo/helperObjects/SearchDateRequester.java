// Object to store Date and/or Requester "Search" criteria parameters when searching for Request.

package com.luv2code.springdemo.helperObjects;

import com.luv2code.springdemo.customAnnotation.SearchFormDate;

public class SearchDateRequester {

	private int requesterId;
		
	@SearchFormDate
	private String dateStart;
	
	@SearchFormDate
	private String dateEnd;
	
	private String requesterName;

	//Used for identified DataTables unique statesave id
	private String dataTablesUniqueId;
	

	public SearchDateRequester() {		
	}

	public int getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(int requesterId) {
		this.requesterId = requesterId;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	public String getDataTablesUniqueId() {
		return dataTablesUniqueId;
	}

	public void setDataTablesUniqueId(String dataTablesUniqueId) {
		this.dataTablesUniqueId = dataTablesUniqueId;
	}
}

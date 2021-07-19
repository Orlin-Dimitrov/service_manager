package com.luv2code.springdemo.helperObjects;

import java.util.ArrayList;

//Object used for sending JSON data for DataTables. Paginated list of Requests.
public class PaginatedRequestsJSON {

	// Used Values for Server Side Pagination by DataTables
	private int draw;
	private int recordsTotal;
	private int recordsFiltered;
	private ArrayList<RequestForJSON> data;

	
	public PaginatedRequestsJSON() {		
	}


	public int getDraw() {
		return draw;
	}


	public void setDraw(int draw) {
		this.draw = draw;
	}


	public int getRecordsTotal() {
		return recordsTotal;
	}


	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}


	public int getRecordsFiltered() {
		return recordsFiltered;
	}


	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}


	public ArrayList<RequestForJSON> getData() {
		return data;
	}


	public void setData(ArrayList<RequestForJSON> data) {
		this.data = data;
	}

	
	
}

package com.luv2code.springdemo.helperObjects;

// Helper Object used by PaginatedRequestsJSON object. 
public class RequestForJSON {

	private int id;
	private String date;
	private String requester;
	private String requesterComment;
	private String typeModelInService;
	private String typeModelInOperation;
	private String command;

	
	public RequestForJSON() {		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getRequesterComment() {
		return requesterComment;
	}

	public void setRequesterComment(String requesterComment) {
		this.requesterComment = requesterComment;
	}

	public String getTypeModelInService() {
		return typeModelInService;
	}

	public void setTypeModelInService(String typeModelInService) {
		this.typeModelInService = typeModelInService;
	}

	public String getTypeModelInOperation() {
		return typeModelInOperation;
	}

	public void setTypeModelInOperation(String typeModelInOperation) {
		this.typeModelInOperation = typeModelInOperation;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	
}

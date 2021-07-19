package com.luv2code.springdemo.helperObjects;

// Helper Object for displaying data when adding new Request. Used on /requestForm/review
public class AddRequestReview {

	private int requestId;
	
	private String date;
	
	private String requester;
	private String requesterComment;
	
	private String objectTypeInService;
	private String objectModelInService;
	private String objectInstanceInService;
	
	private String objectTypeInOperation;
	private String objectModelInOperation;
	private String objectInstanceInOperation;
	
	private String itemComment;
	
	public AddRequestReview() {
		
	}
	
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
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

	public String getObjectTypeInService() {
		return objectTypeInService;
	}

	public void setObjectTypeInService(String objectTypeInService) {
		this.objectTypeInService = objectTypeInService;
	}

	public String getObjectModelInService() {
		return objectModelInService;
	}

	public void setObjectModelInService(String objectModelInService) {
		this.objectModelInService = objectModelInService;
	}

	public String getObjectInstanceInService() {
		return objectInstanceInService;
	}

	public void setObjectInstanceInService(String objectInstanceInService) {
		this.objectInstanceInService = objectInstanceInService;
	}

	public String getObjectTypeInOperation() {
		return objectTypeInOperation;
	}

	public void setObjectTypeInOperation(String objectTypeInOperation) {
		this.objectTypeInOperation = objectTypeInOperation;
	}

	public String getObjectModelInOperation() {
		return objectModelInOperation;
	}

	public void setObjectModelInOperation(String objectModelInOperation) {
		this.objectModelInOperation = objectModelInOperation;
	}

	public String getObjectInstanceInOperation() {
		return objectInstanceInOperation;
	}

	public void setObjectInstanceInOperation(String objectInstanceInOperation) {
		this.objectInstanceInOperation = objectInstanceInOperation;
	}

	public String getItemComment() {
		return itemComment;
	}

	public void setItemComment(String itemComment) {
		this.itemComment = itemComment;
	}
	
	
	
}


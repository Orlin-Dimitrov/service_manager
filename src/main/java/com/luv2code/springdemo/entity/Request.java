package com.luv2code.springdemo.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="request")
public class Request {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="request_date")
	private LocalDate date;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="requester_id")
	private Requester requester;
	
	@Column(name="requester_comment")
	private String requesterComment;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="item_in_service_id")
	private ObjectInstance itemInService;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="item_in_operation_id")
	private ObjectInstance itemInOperation;
	
	@Column(name="item_comment")
	private String itemComment;
		
	@Column(name="user_name")
	private String userName;
	
	@Column(name="request_modification_date")
	private LocalDate modificationDate;
	
	public Request() {
	}

	public Request(LocalDate date, Requester requester, String requesterComment, ObjectInstance itemInService,
			ObjectInstance itemInOperation, String itemComment, String userName, LocalDate modificationDate) {
		this.date = date;
		this.requester = requester;
		this.requesterComment = requesterComment;
		this.itemInService = itemInService;
		this.itemInOperation = itemInOperation;
		this.itemComment = itemComment;
		this.userName = userName;
		this.modificationDate = modificationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Requester getRequester() {
		return requester;
	}

	public void setRequester(Requester requester) {
		this.requester = requester;
	}

	public String getRequesterComment() {
		return requesterComment;
	}

	public void setRequesterComment(String requesterComment) {
		this.requesterComment = requesterComment;
	}

	public ObjectInstance getItemInService() {
		return itemInService;
	}

	public void setItemInService(ObjectInstance itemInService) {
		this.itemInService = itemInService;
	}

	public ObjectInstance getItemInOperation() {
		return itemInOperation;
	}

	public void setItemInOperation(ObjectInstance itemInOperation) {
		this.itemInOperation = itemInOperation;
	}

	public String getItemComment() {
		return itemComment;
	}

	public void setItemComment(String itemComment) {
		this.itemComment = itemComment;
	}

		
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public LocalDate getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(LocalDate modificationDate) {
		this.modificationDate = modificationDate;
	}

	// The toString list ObjectInstance, ObjectModel, Object Type
	@Override
	public String toString() {
		return "Request [date=" + date + ", requester=" + requester + ", requesterComment=" + requesterComment
				+ ", itemInService=" + itemInService + ", itemInOperation=" + itemInOperation				
				+ ", itemComment=" + itemComment
				+ ", userName=" + userName + ", modificationDate=" + modificationDate +"]";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

package com.luv2code.springdemo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="calendar")
public class RequestsPerDay {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="date")
	private LocalDate date;
	
	
	@Column(name="requests")
	private int requests;

	
	public RequestsPerDay() {
	}


	public RequestsPerDay(LocalDate date, int requests) {
		this.date = date;
		this.requests = requests;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getRequests() {
		return requests;
	}

	public void setRequests(int requests) {
		this.requests = requests;
	}

}

package com.luv2code.springdemo.entity;

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
@Table(name="object_instance")
public class ObjectInstance {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="serial_number")
	private String serialNumber;
	
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="model_id")
	private ObjectModel objectModel;
	
	public ObjectInstance() {
		
	}

	public ObjectInstance(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public ObjectInstance(String serialNumber, ObjectModel objectModel) {
		this.serialNumber = serialNumber;
		this.objectModel = objectModel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public ObjectModel getObjectModel() {
		return objectModel;
	}

	public void setObjectModel(ObjectModel objectModel) {
		this.objectModel = objectModel;
	}

	@Override
	public String toString() {
		return "ObjectInstance [id=" + id + ", serialNumber=" + serialNumber + "]";
	}
	
	
	
}

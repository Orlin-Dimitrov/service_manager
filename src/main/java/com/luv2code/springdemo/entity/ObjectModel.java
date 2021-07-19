package com.luv2code.springdemo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="object_model")
public class ObjectModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="model")
	private String model;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="object_type_id")
	private ObjectType objectType;
	
	
	@OneToMany(fetch=FetchType.LAZY,
			   mappedBy="objectModel",
			   cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})	
	private List<ObjectInstance> instances;
	
	public ObjectModel() {
	}

	public ObjectModel(String model) {
		this.model = model;
	}
	
	public ObjectModel(String model, ObjectType objectType) {
		this.model = model;
		this.objectType = objectType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public ObjectType getObjectType() {
		return objectType;
	}

	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}
	
	public List<ObjectInstance> getInstances() {
		return instances;
	}

	public void setInstances(List<ObjectInstance> instances) {
		this.instances = instances;
	}

	//Associated objectType is removed from toString method + ", objectType=" + objectType + "]"
	@Override
	public String toString() {
		return "ObjectModel [id=" + id + ", model=" + model +"]" ;
	}

	
	// adding convenience method for bi-directional relationship
	public void add(ObjectInstance tempInstance) {
		
		if(instances == null) {
			instances = new ArrayList<>();
		}
		
		instances.add(tempInstance);
		
		tempInstance.setObjectModel(this);
	}
	
}

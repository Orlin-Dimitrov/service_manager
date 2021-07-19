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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="object_type")
public class ObjectType {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="object_type")
	private String type;

	@OneToMany(fetch=FetchType.LAZY,
			   mappedBy="objectType", 
			   cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	private List<ObjectModel> models;
	
	public ObjectType() {
	}	
	
	public ObjectType(String objectType) {
		this.type = objectType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	public List<ObjectModel> getModels() {
		return models;
	}

	public void setModels(List<ObjectModel> models) {
		this.models = models;
	}

	@Override
	public String toString() {
		return "ObjectType [id=" + id + ", Type=" + type + "]";
	}
	
	
	// add a convenience method for bi-directional relationship
	
	public void add(ObjectModel tempModel) {
		
		if(models == null) {
			models = new ArrayList<>();
		}
		
		models.add(tempModel);
		
		tempModel.setObjectType(this);
	}
	
}

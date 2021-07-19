/* Object to store (Object Type, Model, Instance), Date, Requester "Search" criteria parameters
   when searching for specific Request.
*/

package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.Min;

import com.luv2code.springdemo.customAnnotation.SearchFormDate;

public class SearchItem {

	private int requesterId;
	
	@Min(value = 1, message="{searchItem.objectTypeId.min}")
	private int objectTypeId;
	
	private int objectModelId;
	private int objectInstanceId;
	
	@SearchFormDate
	private String dateStart;
	
	@SearchFormDate
	private String dateEnd;
	
	private String requesterName;
	private String objectTypeName;
	private String objectModelName;
	private String objectInstanceName;
	
	//Used for identified DataTables unique statesave id
	private String dataTablesUniqueId;
	


	public SearchItem() {		
	}

	public int getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(int requesterId) {
		this.requesterId = requesterId;
	}

	public int getObjectTypeId() {
		return objectTypeId;
	}

	public void setObjectTypeId(int objectTypeId) {
		this.objectTypeId = objectTypeId;
	}

	public int getObjectModelId() {
		return objectModelId;
	}

	public void setObjectModelId(int objectModelId) {
		this.objectModelId = objectModelId;
	}

	public int getObjectInstanceId() {
		return objectInstanceId;
	}

	public void setObjectInstanceId(int objectInstanceId) {
		this.objectInstanceId = objectInstanceId;
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

	public String getObjectTypeName() {
		return objectTypeName;
	}

	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}

	public String getObjectModelName() {
		return objectModelName;
	}

	public void setObjectModelName(String objectModelName) {
		this.objectModelName = objectModelName;
	}

	public String getObjectInstanceName() {
		return objectInstanceName;
	}

	public void setObjectInstanceName(String objectInstanceName) {
		this.objectInstanceName = objectInstanceName;
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

package com.luv2code.springdemo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luv2code.springdemo.customExceptions.JsonNoDbConnectionException;
import com.luv2code.springdemo.entity.ObjectInstance;
import com.luv2code.springdemo.entity.ObjectModel;
import com.luv2code.springdemo.entity.ObjectType;
import com.luv2code.springdemo.entity.Request;
import com.luv2code.springdemo.helperObjects.ObjectInstanceForJSON;
import com.luv2code.springdemo.helperObjects.ObjectModelForJSON;
import com.luv2code.springdemo.helperObjects.ObjectTypeForJSON;
import com.luv2code.springdemo.helperObjects.PaginatedRequestsJSON;
import com.luv2code.springdemo.helperObjects.RequestForJSON;
import com.luv2code.springdemo.helperObjects.SearchPaginatedPage;
import com.luv2code.springdemo.service.ObjectInstanceService;
import com.luv2code.springdemo.service.ObjectModelService;
import com.luv2code.springdemo.service.ObjectTypeService;
import com.luv2code.springdemo.service.RequestService;

// Controller for getting JSON Data for dropdown select In JSPs for adding and editing Request.

@Controller
@RequestMapping("/json")
public class ObjectsForJSONController {

	private static final Logger logger = LoggerFactory.getLogger(ObjectsForJSONController.class);

	
	// need to inject our objectType, objectModel and objectInstance services
	@Autowired
	private ObjectTypeService objectTypeService;
	
	@Autowired
	private ObjectModelService objectModelService;
	
	@Autowired
	private ObjectInstanceService objectInstanceService;
	
	@Autowired
	private RequestService requestService;
	
	// autowired custom date formater of pattern "dd-MM-yyyy"
	@Autowired
	@Qualifier(value="customDateFormatter")
	private DateTimeFormatter customDateFormatter;
	
//	@Autowired
//	@Qualifier(value="customDateFormatterForStatisticsJSON")
//	private DateTimeFormatter customDateFormatterForStatisticsJSON;
	

	
	
	// Getting JSON data to populate form select for Object Type inside JSP
	@GetMapping("/objectTypeList")
	@ResponseBody
	public List<ObjectTypeForJSON> showObjectTypeList() throws JsonNoDbConnectionException {
		
		try {
			
			// get the request from our service
			List<ObjectType> theObjectTypes = objectTypeService.getObjectTypes();
			
			List<ObjectTypeForJSON> listObjectTypes = new ArrayList<ObjectTypeForJSON>();

			//adding List with helper object ObjectTypeForJSON - id/type for select dropdown in the jsp
			for(ObjectType entry : theObjectTypes) {
				ObjectTypeForJSON tempObject = new ObjectTypeForJSON();
				tempObject.setId(entry.getId());
				tempObject.setType(entry.getType());
				listObjectTypes.add(tempObject);
			}
			return listObjectTypes;
			
		}
		
		//No connection to the DataBase
		catch(CannotCreateTransactionException ex) {
			
			logger.warn("!NO CONNECTION to DB! ObjectsForJSONController(/json/objectTypeList) > Exception: " + ex.getClass() + " > Cause: " + ex.getCause() + " > Root cause: " + ExceptionUtils.getRootCause(ex));

			// DO NOT RENAME Error Message "DataBase error". It is used in handle-dberror-sessionexpired.js
			throw new JsonNoDbConnectionException("DataBase error");
		}
		

	}
	

	// Getting JSON data to populate form select for Object Model inside JSP
	@GetMapping("/objectModelList")
	@ResponseBody
	public List<ObjectModelForJSON> showObjectModel(@RequestParam(value="objectTypeId", required = true) int objectTypeId) throws JsonNoDbConnectionException {
		
		
		try {
			
			// get the request from our service
			List<ObjectModel> theObjectModels = objectModelService.getObjectModelsForCertainType(objectTypeId);

			List<ObjectModelForJSON> listObjectModels = new ArrayList<ObjectModelForJSON>();
			
			//adding List with helper object ObjectModelForJSON - id/model for select dropdown in the jsp
			for(ObjectModel entry : theObjectModels) {
				ObjectModelForJSON tempObject = new ObjectModelForJSON();
				tempObject.setId(entry.getId());
				tempObject.setModel(entry.getModel());
				listObjectModels.add(tempObject);
			}	
			return listObjectModels;
		}
		
		//No connection to the DataBase
		catch(CannotCreateTransactionException ex) {
			
			logger.warn("!NO CONNECTION to DB! ObjectsForJSONController(/json/objectModelList) > Exception: " + ex.getClass() + " > Cause: " + ex.getCause() + " > Root cause: " + ExceptionUtils.getRootCause(ex));

			// DO NOT RENAME Error Message "DataBase error". It is used in handle-dberror-sessionexpired.js
			throw new JsonNoDbConnectionException("DataBase error");
		}

	}
	
	
	// Getting JSON data to populate form select for Object Instance inside JSP
	@GetMapping("/objectInstanceList")
	@ResponseBody
	public List<ObjectInstanceForJSON> showObjectInstance(@RequestParam(value="objectModelId", required = true) int objectModelId) throws JsonNoDbConnectionException {
		
		try {
			
			// get the request from our service
			List<ObjectInstance> theObjectInstances = objectInstanceService.getObjectInstancesForCertainModel(objectModelId);

			List<ObjectInstanceForJSON> listObjectInstances = new ArrayList<ObjectInstanceForJSON>();
			
			//adding List with helper object ObjectInstanceForJSON - id/serialNumber for select dropdown in the jsp
			for(ObjectInstance entry : theObjectInstances) {
				ObjectInstanceForJSON tempObject = new ObjectInstanceForJSON();
				tempObject.setId(entry.getId());
				tempObject.setSerialNumber(entry.getSerialNumber());
				listObjectInstances.add(tempObject);
			}
			return listObjectInstances;
			
		}
		
		//No connection to the DataBase
		catch(CannotCreateTransactionException ex) {
			
			logger.warn("!NO CONNECTION to DB! ObjectsForJSONController(/json/objectInstanceList) > Exception: " + ex.getClass() + " > Cause: " + ex.getCause() + " > Root cause: " + ExceptionUtils.getRootCause(ex));

			// DO NOT RENAME Error Message "DataBase error". It is used in handle-dberror-sessionexpired.js
			throw new JsonNoDbConnectionException("DataBase error");
		}
		
		

	}
	

	/* List Requests SERVER SIDE PAGINATION 
	   Getting JSON data to populate pagination for listing Requests
	*/
	@GetMapping("/listRequestsPaginated")
	@ResponseBody
	public PaginatedRequestsJSON listRequestsPaginated(@RequestParam("draw") int drawId,
														@RequestParam("start") int start, 
														@RequestParam("length") int length) throws JsonNoDbConnectionException {
		try {
			
			// NECESSERY AGAINST SQL INJECTION
			int drawIdInUse = drawId;
			int startInUse = start;
			int lenghtInUse = length;
			
			// Retrieving data for single page from db
			List<Request> theRequests = requestService.getRequestsPaginated(startInUse, lenghtInUse);
			
			// Creating list from RequestForJSON objects, which will be added to the PaginatedRequestsJSON object
			ArrayList<RequestForJSON> listRequests = new ArrayList<RequestForJSON>();
			
			// Creating createListOfRequestsForJSON. Source - theRequests; Stored In - listRequests
			createListOfRequestsForJSON(theRequests, listRequests);
			
			// Creating PaginatedRequestsJSON object used for sending JSON data to be used by DataTables at /requests/list
			PaginatedRequestsJSON paginatedRequests = new PaginatedRequestsJSON();
			
			int totalEntries = requestService.getLastRequest().getId();
			
			// Populating the necessary data used by DataTables
			paginatedRequests.setDraw(drawIdInUse);
			paginatedRequests.setRecordsTotal(totalEntries);
			paginatedRequests.setRecordsFiltered(totalEntries);
			paginatedRequests.setData(listRequests);
			
			return paginatedRequests;		
		}
		
		//No connection to the DataBase
		catch(CannotCreateTransactionException ex) {
			
			logger.warn("!NO CONNECTION to DB! ObjectsForJSONController(/json/listRequestsPaginated) > Exception: " + ex.getClass() + " > Cause: " + ex.getCause() + " > Root cause: " + ExceptionUtils.getRootCause(ex));

			throw new JsonNoDbConnectionException("DataBase error");
		}
	}
	
	
	
	/* Search for known Item SERVER SIDE PAGINATION
	   Getting JSON data to populate pagination for listing Requests WHEN SEARCHIN FOR ITEM
	*/
	@GetMapping("/searchItem")
	@ResponseBody
	public PaginatedRequestsJSON searchItemPaginatedSS(@RequestParam("draw") int drawId,
														@RequestParam("start") int start, 
														@RequestParam("length") int length,
														@RequestParam("order[0][column]") int orderByColumn,
														@RequestParam("order[0][dir]") String orderDirection,
														@RequestParam("requesterId") int requesterId,
														@RequestParam("objectTypeId") int objectTypeId, 
														@RequestParam("objectModelId") int objectModelId,
														@RequestParam("objectInstanceId") int objectInstanceId,
														@RequestParam("startDate") String startDate,
														@RequestParam("endDate") String endDate) throws JsonNoDbConnectionException {
		
		try {
			
			// NECESSERY AGAINST SQL INJECTION
			int drawIdInUse = drawId;
			int startInUse = start;
			int lenghtInUse = length;
			int orderByColumnInUse = orderByColumn;
			String orderDirectionInUse = orderDirection;
			
			int requesterIdInUse = requesterId;
			int objectTypeIdInUse = objectTypeId;
			int objectModelIdInUse = objectModelId;
			int objectInstanceIdInUse = objectInstanceId;
//			LocalDate startDateInUse = LocalDate.parse(startDate , DateTimeFormatter.ISO_LOCAL_DATE);
//			LocalDate endDateInUse = LocalDate.parse(endDate , DateTimeFormatter.ISO_LOCAL_DATE);
			
			// Converting date from String "dd-MM-yyyy" to ISO_LOCAL_DATE for query method
			LocalDate startDateInUse = LocalDate.parse(startDate , customDateFormatter);
			LocalDate endDateInUse = LocalDate.parse(endDate , customDateFormatter);
			
			
			// Retrieving data from db
			SearchPaginatedPage searchItemPaginatedPage = requestService.searchRequestResultsForKnownObjectSSPagination(requesterIdInUse, objectTypeIdInUse, objectModelIdInUse,
																						objectInstanceIdInUse, startDateInUse, endDateInUse,
																						startInUse, lenghtInUse,
																						orderByColumnInUse, orderDirectionInUse);
						
			// Creating list from RequestForJSON objects, which will be added to the PaginatedRequestsJSON object
			ArrayList<RequestForJSON> listRequests = new ArrayList<RequestForJSON>();
			
			// Creating createListOfRequestsForJSON. Source - theRequests; Stored In - listRequests
			createListOfRequestsForJSON(searchItemPaginatedPage.getRequestList(), listRequests);
			
			// Creating PaginatedRequestsJSON object used for sending JSON data to be used by DataTables
			PaginatedRequestsJSON paginatedRequests = new PaginatedRequestsJSON();
			
			// Populating the necessary data used by DataTables
			paginatedRequests.setDraw(drawIdInUse);
			paginatedRequests.setRecordsTotal(searchItemPaginatedPage.getTotalResults().intValue());
			paginatedRequests.setRecordsFiltered(searchItemPaginatedPage.getTotalResults().intValue());
			paginatedRequests.setData(listRequests);
			
			return paginatedRequests;			
		}
		
		//No connection to the DataBase
		catch(CannotCreateTransactionException ex) {
			
			logger.warn("!NO CONNECTION to DB! ObjectsForJSONController(/json/searchItem) > Exception: " + ex.getClass() + " > Cause: " + ex.getCause() + " > Root cause: " + ExceptionUtils.getRootCause(ex));

			throw new JsonNoDbConnectionException("DataBase error");
		}
	}
	
	
	/* Search for known Item CLIENT SIDE PAGINATION - WORKING
	 Getting JSON data to populate pagination for listing Requests WHEN SEARCHIN FOR ITEM
	*/
	/*
	@GetMapping("/searchItem")
	@ResponseBody
	public List<RequestForJSON> searchItem(@RequestParam("requesterId") int requesterId,
											@RequestParam("objectTypeId") int objectTypeId, 
											@RequestParam("objectModelId") int objectModelId,
											@RequestParam("objectInstanceId") int objectInstanceId,
											@RequestParam("startDate") String startDate,
											@RequestParam("endDate") String endDate) {
		
		// NECESSERY AGAINST SQL INJECTION
		int requesterIdInUse = requesterId;
		int objectTypeIdInUse = objectTypeId;
		int objectModelIdInUse = objectModelId;
		int objectInstanceIdInUse = objectInstanceId;
		//LocalDate startDateInUse = LocalDate.parse(startDate , DateTimeFormatter.ISO_LOCAL_DATE);
		//LocalDate endDateInUse = LocalDate.parse(endDate , DateTimeFormatter.ISO_LOCAL_DATE);
		
		// Converting date from String "dd-MM-yyyy" to ISO_LOCAL_DATE for query method
		LocalDate startDateInUse = LocalDate.parse(startDate , customDateFormatter);
		LocalDate endDateInUse = LocalDate.parse(endDate , customDateFormatter);
		
		
		// Retrieving data from db
		List<Request> theRequests = requestService.searchRequestResultsForKnownObject(requesterIdInUse, objectTypeIdInUse, objectModelIdInUse,
																					objectInstanceIdInUse, startDateInUse, endDateInUse);
		
		// Creating list from RequestForJSON objects, which will be returned to the DataTables
		List<RequestForJSON> listRequests = new ArrayList<RequestForJSON>();
		
		// Creating createListOfRequestsForJSON. Source - theRequests; Stored In - listRequests
		createListOfRequestsForJSON(theRequests, listRequests);
		
		return listRequests;
	}
	*/
	
	
	/* Search for Date/Requester SERVER SIDE PAGINATION
	   Getting JSON data to populate pagination for listing Requests WHEN SEARCHIN FOR DATE/REQUESTER
	*/
	@GetMapping("/searchDateRequester")
	@ResponseBody
	public PaginatedRequestsJSON searchDateRequesterPaginatedSS(@RequestParam("draw") int drawId,
																@RequestParam("start") int start, 
																@RequestParam("length") int length,
																@RequestParam("order[0][column]") int orderByColumn,
																@RequestParam("order[0][dir]") String orderDirection,
																@RequestParam("requesterId") int requesterId,
																@RequestParam("startDate") String startDate,
																@RequestParam("endDate") String endDate) throws JsonNoDbConnectionException {
		
		try {
			
			// NECESSERY AGAINST SQL INJECTION
			int drawIdInUse = drawId;
			int startInUse = start;
			int lenghtInUse = length;
			int orderByColumnInUse = orderByColumn;
			String orderDirectionInUse = orderDirection;
			
			int requesterIdInUse = requesterId;
			
//			LocalDate startDateInUse = LocalDate.parse(startDate , DateTimeFormatter.ISO_LOCAL_DATE);
//			LocalDate endDateInUse = LocalDate.parse(endDate , DateTimeFormatter.ISO_LOCAL_DATE);
			
			// Converting date from String "dd-MM-yyyy" to ISO_LOCAL_DATE for query method
			LocalDate startDateInUse = LocalDate.parse(startDate , customDateFormatter);
			LocalDate endDateInUse = LocalDate.parse(endDate , customDateFormatter);
			
			// Retrieving data from db
			SearchPaginatedPage searchDateRequesterPaginatedPage = requestService.searchRequestResultsForDateRequesterSSPagination(requesterIdInUse,
																														startDateInUse, endDateInUse,
																														startInUse, lenghtInUse,
																														orderByColumnInUse, orderDirectionInUse);
				
			// Creating list from RequestForJSON objects, which will be added to the PaginatedRequestsJSON object
			ArrayList<RequestForJSON> listRequests = new ArrayList<RequestForJSON>();
			
			// Creating createListOfRequestsForJSON. Source - theRequests; Stored In - listRequests
			createListOfRequestsForJSON(searchDateRequesterPaginatedPage.getRequestList(), listRequests);
			
			// Creating PaginatedRequestsJSON object used for sending JSON data to be used by DataTables
			PaginatedRequestsJSON paginatedRequests = new PaginatedRequestsJSON();
			
			// Populating the necessary data used by DataTables
			paginatedRequests.setDraw(drawIdInUse);
			paginatedRequests.setRecordsTotal(searchDateRequesterPaginatedPage.getTotalResults().intValue());
			paginatedRequests.setRecordsFiltered(searchDateRequesterPaginatedPage.getTotalResults().intValue());
			paginatedRequests.setData(listRequests);
			
			return paginatedRequests;
		}
		
		//No connection to the DataBase
		catch(CannotCreateTransactionException ex) {
			
			logger.warn("!NO CONNECTION to DB! ObjectsForJSONController(/json/searchDateRequester) > Exception: " + ex.getClass() + " > Cause: " + ex.getCause() + " > Root cause: " + ExceptionUtils.getRootCause(ex));

			throw new JsonNoDbConnectionException("DataBase error");
		}
	}
		
	
	/* Search for Date/Requester CLIENT SIDE PAGINATION - WORKING !!!
	   Getting JSON data to populate pagination for listing Requests WHEN SEARCHIN FOR DATE/REQUESTER
	
	@GetMapping("/searchDateRequester")
	@ResponseBody
	public List<RequestForJSON> searchDateRequester(@RequestParam("requesterId") int requesterId,
													@RequestParam("startDate") String startDate,
													@RequestParam("endDate") String endDate) {
		
		// NECESSERY AGAINST SQL INJECTION
		int requesterIdInUse = requesterId;
		//LocalDate startDateInUse = LocalDate.parse(startDate , DateTimeFormatter.ISO_LOCAL_DATE);
		//LocalDate endDateInUse = LocalDate.parse(endDate , DateTimeFormatter.ISO_LOCAL_DATE);
		
		// Converting date from String "dd-MM-yyyy" to ISO_LOCAL_DATE for query method
		LocalDate startDateInUse = LocalDate.parse(startDate , customDateFormatter);
		LocalDate endDateInUse = LocalDate.parse(endDate , customDateFormatter);
		
		// Retrieving data from db
		List<Request> theRequests = requestService.searchRequestResultsForDateRequester(requesterIdInUse, startDateInUse, endDateInUse);

		// Creating list from RequestForJSON objects, which will be returned to the DataTables
		List<RequestForJSON> listRequests = new ArrayList<RequestForJSON>();
		
		// Creating createListOfRequestsForJSON. Source - theRequests; Stored In - listRequests
		createListOfRequestsForJSON(theRequests, listRequests);
		
		return listRequests;
	}
	 
	 */
	

	/*  Method to create List of Requests to be displayed on page used by DataTables using AJAX
		Source - List<Request> theRequests
		Stored In - List<RequestForJSON> listRequests
	*/
	private void createListOfRequestsForJSON(List<Request> theRequests, List<RequestForJSON> listRequests) {
		
		for(Request entry : theRequests) {
			RequestForJSON tempRequestJSON = new RequestForJSON();
			tempRequestJSON.setId(entry.getId());
			
			// formating Date to "dd-MM-yyyy"
			tempRequestJSON.setDate(entry.getDate().format(customDateFormatter));
			
			tempRequestJSON.setRequester(entry.getRequester().getName());
			tempRequestJSON.setRequesterComment(entry.getRequesterComment());
						
			if(entry.getItemInService() != null) {
				tempRequestJSON.setTypeModelInService(entry.getItemInService().getObjectModel().getObjectType().getType() +
						" - " + entry.getItemInService().getObjectModel().getModel());
			}else {
				tempRequestJSON.setTypeModelInService("");
			}

			if(entry.getItemInOperation() != null) {
				tempRequestJSON.setTypeModelInOperation(entry.getItemInOperation().getObjectModel().getObjectType().getType() +
						" - " + entry.getItemInOperation().getObjectModel().getModel());
			}else {
				tempRequestJSON.setTypeModelInOperation("");
			}

			listRequests.add(tempRequestJSON);
		}
	}
	
	// Used by AJAX to display db-error page
	@PostMapping("/db-error")
	public String renderFooList() {
	  	
	  	logger.info("Triggered /json/db-error page POST!");

	    return "db-error-ajax";
	}
	
	
	
/*	// TESTING DB-ERROR redirect. NOT USED !!!, leaved for reference
	// Getting JSON data to populate form select for Object Type inside JSP
	@PostMapping("/db-error")
	@ResponseBody
	public void dbError(HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
		
	  	String dbErrorFlashAttribute = "dbError";
	  	redirectAttributes.addFlashAttribute("db-error", dbErrorFlashAttribute);
		logger.info("Triggered /json/db-error page !");
		
		 response.sendRedirect("/db-error");
		
		// DO NOT RENAME Error Message "DataBase error". It is used in handle-dberror-sessionexpired.js
//		throw new CannotCreateTransactionException("DataBase error");
		
	}*/

	
}
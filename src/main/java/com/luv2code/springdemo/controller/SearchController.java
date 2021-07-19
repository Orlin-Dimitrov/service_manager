// Controller to Display List of Requests and Single Request Info Page when Searching for specific parameters

package com.luv2code.springdemo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luv2code.springdemo.entity.ObjectInstance;
import com.luv2code.springdemo.entity.ObjectModel;
import com.luv2code.springdemo.entity.ObjectType;
import com.luv2code.springdemo.entity.Requester;
import com.luv2code.springdemo.generators.RequestInfoPage;
import com.luv2code.springdemo.helperObjects.RequestNumber;
import com.luv2code.springdemo.helperObjects.SearchDateRequester;
import com.luv2code.springdemo.helperObjects.SearchItem;
import com.luv2code.springdemo.service.ObjectInstanceService;
import com.luv2code.springdemo.service.ObjectModelService;
import com.luv2code.springdemo.service.ObjectTypeService;
import com.luv2code.springdemo.service.RequesterService;

@Controller
@RequestMapping("/search")
public class SearchController {

	// need to inject our objectInstance services
	@Autowired
	private ObjectTypeService objectTypeService;
	
	@Autowired
	private ObjectModelService objectModelService;
	
	@Autowired
	private ObjectInstanceService objectInstanceService;
	
	@Autowired
	private RequesterService requesterService;
	
	// Injecting bean with requesters hashmap(scope Request)
/*	@Autowired
	private LinkedHashMap<Integer, String> requestersMap;
*/
	@Autowired
	private MessageSource messageSource;
	
	//injecting RequestInfoPage (with a method to generate request info page)
	@Autowired
	private RequestInfoPage requestInfoPage;
	
	// autowired custom date formater of pattern "dd-MM-yyyy"
	@Autowired
	private DateTimeFormatter customDateFormatter;
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		
	}
	
	
	// Search for Item START
	
	// Selecting the Desired Search Parameters when searching for specific Item(Object Type, Model or Instance).
	@GetMapping("/selectItem")
	public String selectItem(Model theModel){
		
		if (theModel.containsAttribute("searchItem") == false) {
		//Generating random UID for DataTables identifier for table state saving
		UUID uuid = UUID.randomUUID();
		String dataTablesUniqueId = uuid.toString();
						
		// Adding requestersMap (For Requester select from dropdown) to the model
		theModel.addAttribute("requestersMap", requesterService.getRequestersMap());
		
		// creating new search object
		SearchItem searchItem = new SearchItem();
		
		// Setting default values
		searchItem.setRequesterId(0);
		searchItem.setObjectTypeId(0);
		searchItem.setObjectModelId(0);
		searchItem.setObjectInstanceId(0);
		
		//Setting the random UID for DataTables identifier for state saving
		searchItem.setDataTablesUniqueId(dataTablesUniqueId);
		
		// Adding the helper object to the model.
		theModel.addAttribute("searchItem", searchItem);
		}
		
		return "search-select-item";
	}
	
	
	// Results from the Search - POST
	@PostMapping("/resultsItem")
	public String resultsItem(@Valid @ModelAttribute("searchItem") SearchItem searchItem,
								BindingResult theBindingResult,										
								Model theModel,
								RedirectAttributes redirectAttributes){		
	
		if (theBindingResult.hasErrors()) {
			
		    // If errors, add flash attributes and redirect to /search/selectItem page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.searchItem", theBindingResult);
			redirectAttributes.addFlashAttribute("searchItem", searchItem);
			redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
			
/*			// Adding requestersMap (For Requester select from dropdown) to the model
			theModel.addAttribute("requestersMap", requesterService.getRequestersMap());
						
		    // If errors, display /selectKnown page
			return "search-select-item";*/
			
			return "redirect:/search/selectItem";	
		}

		
		// Setting default dates for the query method
//		LocalDate startDate = LocalDate.parse("2016-01-01" , DateTimeFormatter.ISO_LOCAL_DATE);
//		LocalDate endDate = LocalDate.now();
//		
		
		// Setting default dates in ISO_LOCAL_DATE format
		LocalDate startDate = LocalDate.parse("01-01-2016" , customDateFormatter);
		LocalDate endDate = LocalDate.now();
//		System.out.println("Inside search controler, hardcodded dates: startDate - " + startDate + "endDate - " + endDate);
		
		
		// Setting selected dates for the query method
//		if(searchItem.getDateStart() != null) {
//			startDate = LocalDate.parse(searchItem.getDateStart() , DateTimeFormatter.ISO_LOCAL_DATE);
//		}
//		if(searchItem.getDateEnd() != null) {
//			endDate = LocalDate.parse(searchItem.getDateEnd() , DateTimeFormatter.ISO_LOCAL_DATE);
//		}

		// Converting dates from "dd-MM-yyyy" to ISO_LOCAL_DATE
		if(searchItem.getDateStart() != null) {
			startDate = LocalDate.parse(searchItem.getDateStart() , customDateFormatter);
		}
		if(searchItem.getDateEnd() != null) {
			endDate = LocalDate.parse(searchItem.getDateEnd() , customDateFormatter);
		}
		
		//Validation to check if start date is not after end date
		if(startDate.isAfter(endDate)) {
			
			// Adding requestersMap (For Requester select from dropdown) to the model
			theModel.addAttribute("requestersMap", requesterService.getRequestersMap());

			Locale locale = Locale.getDefault();
			
			// creating custom FieldError
			theBindingResult.addError(new FieldError("searchItem", "dateStart",searchItem.getDateStart(),false, null, null,
										messageSource.getMessage("searchItem.dateStart.afterDateEnd",null, locale)));
			
			return "search-select-item";
		}
				
		// Setting default names
		//converting dates back to "dd-MM-yyyy" String
		searchItem.setDateStart(startDate.format(customDateFormatter));
		searchItem.setDateEnd(endDate.format(customDateFormatter));
		
		searchItem.setRequesterName("-");
		searchItem.setObjectTypeName("-");
		searchItem.setObjectModelName("-");
		searchItem.setObjectInstanceName("-");
		
		// Setting parameters for the query method to retrieve and store names
		int requesterId = searchItem.getRequesterId();
		int objectTypeId = searchItem.getObjectTypeId();
		int objectModelId = searchItem.getObjectModelId();
		int objectInstanceId = searchItem.getObjectInstanceId();
		

		// Setting Requester to be displayed on the page
		if(requesterId > 0) {
			Requester tempRequester = requesterService.getRequester(requesterId);
			
			if(tempRequester != null) {
				searchItem.setRequesterName(tempRequester.getName());
			}else {
				
				String requesterDeletedError = "requesterDeletedError";
				
				redirectAttributes.addFlashAttribute("requesterDeletedError", requesterDeletedError);
				
				return "redirect:/search/resultsItem/requesterDeleted";	
				
//				return "search-select-item-requester-deleted";
			}			
		}
		
		// Setting Instance, Model and Type to be displayed on the page
		if(objectInstanceId > 0 ) {
			ObjectInstance tempObjectInstance = objectInstanceService.getObjectInstanceById(objectInstanceId);
			
			if (tempObjectInstance != null) {
				searchItem.setObjectTypeName(tempObjectInstance.getObjectModel().getObjectType().getType());
				searchItem.setObjectModelName(tempObjectInstance.getObjectModel().getModel());
				searchItem.setObjectInstanceName(tempObjectInstance.getSerialNumber());				
			}
			else {
				
				String instanceDeletedError = "instanceDeletedError";
				
				redirectAttributes.addFlashAttribute("instanceDeletedError", instanceDeletedError);
				
				return "redirect:/search/resultsItem/instanceDeleted";					
				
//				return "search-select-item-instance-deleted";
			}
		}else if(objectModelId > 0) {
			ObjectModel tempObjectModel = objectModelService.getObjectModel(objectModelId);
			
			if(tempObjectModel != null) {
				searchItem.setObjectTypeName(tempObjectModel.getObjectType().getType());
				searchItem.setObjectModelName(tempObjectModel.getModel());
			}
			else {
				
				String modelDeletedError = "modelDeletedError";
				
				redirectAttributes.addFlashAttribute("modelDeletedError", modelDeletedError);
				
				return "redirect:/search/resultsItem/modelDeleted";	
				
//				return "search-select-item-model-deleted";
			}
			
		}else if(objectTypeId > 0) {
			
			ObjectType tempObjectType = objectTypeService.getObjectType(objectTypeId);
			
			if(tempObjectType != null) {
				searchItem.setObjectTypeName(tempObjectType.getType());
			}
			else {
				
				String typeDeletedError = "typeDeletedError";
				
				redirectAttributes.addFlashAttribute("typeDeletedError", typeDeletedError);
				
				return "redirect:/search/resultsItem/typeDeleted";	
				
//				return "search-select-item-type-deleted";
			}
		}		
		else {
			// Code should not come to this. Will stop at validation for objectTypeId >0
		}
				
		/* adding searchItemResults flash model attribute for storing search criteria parameters when visiting request info page 
		   and returning back to the search results page(using form:hidden)
		*/
		redirectAttributes.addFlashAttribute("searchItemResult", searchItem);

		return "redirect:/search/resultsItem";				
	}
	
	
	// Error Pages
	// Displaying Search Error Page - REQUESTER DELETED
	@GetMapping("/resultsItem/requesterDeleted")
	public String requesterDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("requesterDeletedError")) {	
			
			return "search-select-item-requester-deleted";			
		}else {
			return "redirect:/search/selectItem";
		}
	}
	
	
	// Displaying Search Error Page - INSTANCE DELETED
	@GetMapping("/resultsItem/instanceDeleted")
	public String instanceDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("instanceDeletedError")) {	
			
			return "search-select-item-instance-deleted";			
		}else {
			return "redirect:/search/selectItem";
		}
	}
		
	
	// Displaying Search Error Page - MODEL DELETED
	@GetMapping("/resultsItem/modelDeleted")
	public String modelDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("modelDeletedError")) {	
			
			return "search-select-item-model-deleted";			
		}else {
			return "redirect:/search/selectItem";
		}
	}
	

	// Displaying Search Error Page - TYPE DELETED
	@GetMapping("/resultsItem/typeDeleted")
	public String typeDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("typeDeletedError")) {	
			
			return "search-select-item-type-deleted";			
		}else {
			return "redirect:/search/selectItem";
		}
	}
	
	
	
	
	
	//Results from the Search - GET - Implemented PRG
	@GetMapping("/resultsItem")
	public String resultsItemGet(Model theModel) {
		
		// If after redirecting the model contains "searchItemResult" flash attribute display the page, else redirect to the select page
		if (theModel.containsAttribute("searchItemResult")) {
			
			// add searchItem object to the model , used to submit search query criteria to request info page and going back to the list requests page
			theModel.addAttribute("searchItemToInfo", new SearchItem());
			
			return "search-results-item";
		}else {
			return "redirect:/search/selectItem";
		}		
	}
	
	
	// Displaying single Request info page - POST
	@PostMapping("/resultsItem/{requestId}")
	public String showRequestWhenSearchingItem(@PathVariable("requestId") int theId,
												@ModelAttribute("searchItemToInfo") SearchItem searchItem,
												Model theModel,
												RedirectAttributes redirectAttributes) {
		
		/* adding searchItemToInfo flash model attribute for storing search criteria parameters when visiting request info page 
		   and returning back to the search results page(using form:hidden)
		*/ 
		redirectAttributes.addFlashAttribute("searchItemToInfo", searchItem);

		return "redirect:/search/resultsItem/" + theId;
	}

	
	//Displaying single Request info page - GET implemented PRG
	@GetMapping("/resultsItem/{requestId}")
	public String showRequestWhenSearchingItemGet(@PathVariable("requestId") int theId,Model theModel) {
		
		if (theModel.containsAttribute("searchItemToInfo")) {
			
			// add searchItem object to the model , used to submit search query criteria to request info page and going back to the list requests page
			theModel.addAttribute("searchItem", new SearchItem());
			
			// populating data on info page			
			requestInfoPage.infoPage(theId, theModel);
			
			return "search-results-item-info";
		}else {
			return "redirect:/search/selectItem";
		}
		
	}
	
	// Search for Item END	

	
	//--------------------------------
	
	
	// Search for Date/Requester START
	
	// Selecting the Desired Search Parameters when searching for Date or/and Requester.
	@GetMapping("/selectDateRequester")
	public String selectDateRequester(Model theModel){
		
		if (theModel.containsAttribute("searchDateRequester") == false) {
		
		//Generating random UID for DataTables identifier for table state saving
		UUID uuid = UUID.randomUUID();
		String dataTablesUniqueId = uuid.toString();
		
		// Adding requestersMap (For Requester select from dropdown) to the model
		theModel.addAttribute("requestersMap", requesterService.getRequestersMap());
		
		// creating new search object
		SearchDateRequester searchDateRequester = new SearchDateRequester();
		
		// Setting default values
		searchDateRequester.setRequesterId(0);

		//Setting the random UID for DataTables identifier for state saving
		searchDateRequester.setDataTablesUniqueId(dataTablesUniqueId);
		
		// Adding the helper object to the model.
		theModel.addAttribute("searchDateRequester", searchDateRequester);
		
		}
		
		return "search-select-date-requester";
	}
	
	// Results from the Search - POST
	@PostMapping("/resultsDateRequester")
	public String resultsDateRequester(@Valid @ModelAttribute("searchDateRequester") SearchDateRequester searchDateRequester,
										BindingResult theBindingResult,										
										Model theModel,
										RedirectAttributes redirectAttributes){		
	
		if (theBindingResult.hasErrors()) {
			
		    // If errors, add flash attributes and redirect to /search/selectItem page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.searchDateRequester", theBindingResult);
			redirectAttributes.addFlashAttribute("searchDateRequester", searchDateRequester);
			redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
			
			return "redirect:/search/selectDateRequester";				
			
		    // If errors, display /selectDateRequester page
//			return "search-select-date-requester";
		}

//		// Setting default dates for the query method 
//		LocalDate startDate = LocalDate.parse("2016-01-01" , DateTimeFormatter.ISO_LOCAL_DATE);
//		LocalDate endDate = LocalDate.now();
		
		// Setting default dates in ISO_LOCAL_DATE format
		LocalDate startDate = LocalDate.parse("01-01-2016" , customDateFormatter);
		LocalDate endDate = LocalDate.now();
		
		
		// Setting selected dates for the query method
//		if(searchDateRequester.getDateStart() != null) {
//			startDate = LocalDate.parse(searchDateRequester.getDateStart() , DateTimeFormatter.ISO_LOCAL_DATE);
//		}
//		if(searchDateRequester.getDateEnd() != null) {
//			endDate = LocalDate.parse(searchDateRequester.getDateEnd() , DateTimeFormatter.ISO_LOCAL_DATE);
//		}
		
		if(searchDateRequester.getDateStart() != null) {
			startDate = LocalDate.parse(searchDateRequester.getDateStart() , customDateFormatter);
		}
		if(searchDateRequester.getDateEnd() != null) {
			endDate = LocalDate.parse(searchDateRequester.getDateEnd() , customDateFormatter);
		}
		
		
		//Validation to check if start date is not after end date
		if(startDate.isAfter(endDate)) {
			
			// Adding requestersMap (For Requester select from dropdown) to the model
//			theModel.addAttribute("requestersMap", requesterService.getRequestersMap());

			Locale locale = Locale.getDefault();
			
			// creating custom FieldError
			theBindingResult.addError(new FieldError("searchDateRequester", "dateStart",searchDateRequester.getDateStart(),false, null, null,
										messageSource.getMessage("searchDateRequester.dateStart.afterDateEnd",null, locale)));
			
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.searchDateRequester", theBindingResult);
			redirectAttributes.addFlashAttribute("searchDateRequester", searchDateRequester);
			redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
			
			return "redirect:/search/selectDateRequester";	
			
			//return "search-select-date-requester";
		}
				
		// Setting default names
//		searchDateRequester.setDateStart(startDate.toString());
//		searchDateRequester.setDateEnd(endDate.toString());
		
		//converting dates back to "dd-MM-yyyy" String
		searchDateRequester.setDateStart(startDate.format(customDateFormatter));
		searchDateRequester.setDateEnd(endDate.format(customDateFormatter));
		
		searchDateRequester.setRequesterName("-");
		
		// Setting parameters for the query method to retrieve and store names
		int requesterId = searchDateRequester.getRequesterId();		

		// Setting Requester to be displayed on the page
		if(requesterId > 0) {
			Requester tempRequester = requesterService.getRequester(requesterId);
			
			if(tempRequester != null) {
				searchDateRequester.setRequesterName(tempRequester.getName());
			}else {
				
				String requesterDeletedError = "requesterDeletedError";
				
				redirectAttributes.addFlashAttribute("requesterDeletedError", requesterDeletedError);
				
				return "redirect:/search/resultsDateRequester/requesterDeleted";					
				
//				return "search-select-date-requester-requester-deleted";
			}
		}
		
		/* adding searchItemResults flash model attribute for storing search criteria parameters when visiting request info page 
		   and returning back to the search results page(using form:hidden)
		*/
		redirectAttributes.addFlashAttribute("searchDateRequesterResult", searchDateRequester);

		return "redirect:/search/resultsDateRequester";
	}
	
	
	// Error Pages
	// Displaying Search Error Page - REQUESTER DELETED
	@GetMapping("/resultsDateRequester/requesterDeleted")
	public String requesterDeletedErrorSearchDateRequester(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("requesterDeletedError")) {	
			
			return "search-select-date-requester-requester-deleted";			
		}else {
			return "redirect:/search/selectDateRequester";
		}
	}
	
	
	//Results from the Search - GET - Implemented PRG
	@GetMapping("/resultsDateRequester")
	public String resultsDateRequesterGet(Model theModel) {
		
		// If after redirecting the model contains "searchDateRequesterResult" flash attribute display the page, else redirect to the select page
		if (theModel.containsAttribute("searchDateRequesterResult")) {
			
			// add searchItem object to the model , used to submit search query criteria to request info page and going back to the list requests page
			theModel.addAttribute("searchDateRequesterToInfo", new SearchDateRequester());
			
			return "search-results-date-requester";
		}else {
			return "redirect:/search/selectDateRequester";
		}		
	}
	
	
	// Displaying single Request info page - POST
	@PostMapping("/resultsDateRequester/{requestId}")
	public String showRequestWhenSearchingDateRequester(@PathVariable("requestId") int theId,
														@ModelAttribute("searchDateRequesterToInfo") SearchDateRequester searchDateRequester,									
														Model theModel,
														RedirectAttributes redirectAttributes) {
		
		/* adding searchDateRequesterToInfo flash model attribute for storing search criteria parameters when visiting request info page 
		   and returning back to the search results page(using form:hidden)
		*/ 
		redirectAttributes.addFlashAttribute("searchDateRequesterToInfo", searchDateRequester);
		
		//populating data on info page		
//		requestInfoPage.infoPage(theId, theModel);

		return "redirect:/search/resultsDateRequester/" + theId;
	}
	
	
	//Displaying single Request info page - GET implemented PRG
	@GetMapping("/resultsDateRequester/{requestId}")
	public String showRequestWhenSearchingDateRequesterGet(@PathVariable("requestId") int theId,Model theModel) {
		
		if (theModel.containsAttribute("searchDateRequesterToInfo")) {
			
			// add searchDateRequester object to the model , used to submit search query criteria to request info page and going back to the list requests page
			theModel.addAttribute("searchDateRequester", new SearchDateRequester());
			
			// populating data on info page			
			requestInfoPage.infoPage(theId, theModel);
			
			return "search-results-date-requester-info";
		}else {
			return "redirect:/search/selectDateRequester";
		}		
	}
		
	// Search for Date/Requester END
	
	
	//--------------------------------
	
	
	// Search for Specific Request START
	@GetMapping("/selectRequest")
	public String selectRequest(Model theModel){
		
		if (theModel.containsAttribute("requestNumber") == false){
			
			// Creating helper object RequestNumber (has annotations for validating request number)
			RequestNumber requestNumber = new RequestNumber();
			
			// Setting the number to null. Input field of the form will display nothing.
			requestNumber.setNumber(null);
			
			// Adding the helper object to the model.
			theModel.addAttribute("requestNumber", requestNumber);			
		}
		// ADD THIS or input field wont behave properly
		else {	
			
			RequestNumber requestNumber = (RequestNumber)theModel.asMap().get("requestNumber");
			// Adding the helper object to the model.
			theModel.addAttribute("requestNumber", requestNumber);
		}
		
		return "search-select-request";
	}
	
	
	// Displaying selected Request POST
	@PostMapping("/resultRequest")
	public String requestInfo(@Valid @ModelAttribute("requestNumber") RequestNumber requestNumber,
								BindingResult theBindingResult,										
								Model theModel,
								RedirectAttributes redirectAttributes) {
	
		if (theBindingResult.hasErrors()) {		
			
		    // If errors, add flash attributes and redirect to /search/selectRequest page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.requestNumber", theBindingResult);
			redirectAttributes.addFlashAttribute("requestNumber", requestNumber);
			
			return "redirect:/search/selectRequest";
		}
		
		// adding requestNumber flash model attribute for PRG
		redirectAttributes.addFlashAttribute("requestNumber", requestNumber);

		
		return "redirect:/search/resultRequest";
	}

	
	// Displaying selected Request GET implemented PRG
	@GetMapping("/resultRequest")
	public String requestInfoGet(Model theModel) {
	
		if (theModel.containsAttribute("requestNumber")){
			
			// Retrieving the flash attribute from the model
			RequestNumber requestNumber = (RequestNumber)theModel.asMap().get("requestNumber");
			
			// populating data on info page	
			requestInfoPage.infoPage(requestNumber.getNumber(), theModel);
			
			return "search-result-request-info";
			
		}else {
			return "redirect:/search/selectRequest";
		}
	}
			
}

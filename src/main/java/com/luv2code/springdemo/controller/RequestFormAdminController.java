package com.luv2code.springdemo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luv2code.springdemo.entity.ObjectInstance;
import com.luv2code.springdemo.entity.Request;
import com.luv2code.springdemo.helperObjects.AddRequestReview;
import com.luv2code.springdemo.helperObjects.AdminUpdateObjectForm;
import com.luv2code.springdemo.helperObjects.RequestNumber;
import com.luv2code.springdemo.service.ObjectInstanceService;
import com.luv2code.springdemo.service.RequestService;
import com.luv2code.springdemo.service.RequesterService;

@Controller
@RequestMapping("/requestFormAdmin")
public class RequestFormAdminController {

	// need to inject our objectInstance services
	
	@Autowired
	private ObjectInstanceService objectInstanceService;
	
	@Autowired
	private RequesterService requesterService;

	@Autowired
	private RequestService requestService;
	
	// Injecting bean with requesters hashmap(scope Request)
/*	@Autowired
	private LinkedHashMap<Integer, String> requestersMap;*/
	
	// autowired custom date formater of pattern "dd-MM-yyyy"
	@Autowired
	private DateTimeFormatter customDateFormatter;
	
//	private static final Logger logger = LoggerFactory.getLogger(RequestFormAdminController.class);
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);		
	}
	
	
	// Search for Specific Request
	@GetMapping("/select")
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

		return "admin-select-request";
	}
	
	
	// Displaying selected Request
	@PostMapping("/info")
	public String requestInfo(@Valid @ModelAttribute("requestNumber") RequestNumber requestNumber,
								BindingResult theBindingResult,										
								Model theModel,
								RedirectAttributes redirectAttributes) {
	
		if (theBindingResult.hasErrors()) {
//			System.out.println(">>>>> Error detected!");
//					
//			
//		    List<FieldError> errors = theBindingResult.getFieldErrors();
//		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		    }
			
		    // If errors, add flash attributes and redirect to /requestFormAdmin/select page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.requestNumber", theBindingResult);
			redirectAttributes.addFlashAttribute("requestNumber", requestNumber);
			
			return "redirect:/requestFormAdmin/select";
		}
		
		// get the request from our service
		Request existingRequest = requestService.getRequest(requestNumber.getNumber());	
		
		// set request as a model attribute 
//		theModel.addAttribute("existingRequest", existingRequest);
		
		// Creating helper object AdminUpdateObjectForm to be submitted as a form with hidden attributes.
		AdminUpdateObjectForm adminHelperFormExistingRequest = new AdminUpdateObjectForm();
		
		// Setting requestId
		adminHelperFormExistingRequest.setRequestId(existingRequest.getId());
		
		// !!! Setting FORMATED Date
		String formatedDate = existingRequest.getDate().format(customDateFormatter);		
		adminHelperFormExistingRequest.setDateSelected(formatedDate);
		
		// Setting Requester
		adminHelperFormExistingRequest.setRequesterId(existingRequest.getRequester().getId());
		
		// Setting Requester Comment
		adminHelperFormExistingRequest.setRequesterComment(existingRequest.getRequesterComment());
		
		// Setting itemInService if exists
		if(existingRequest.getItemInService() != null) {
			adminHelperFormExistingRequest.setCheckBoxInService(true);
			adminHelperFormExistingRequest.setObjectTypeIdInService(existingRequest.getItemInService().getObjectModel().getObjectType().getId());
			adminHelperFormExistingRequest.setObjectModelIdInService(existingRequest.getItemInService().getObjectModel().getId());
			adminHelperFormExistingRequest.setObjectInstanceIdInService(existingRequest.getItemInService().getId());			
		}
		
		// Setting itemInOperation if exists
		if(existingRequest.getItemInOperation() != null) {
			adminHelperFormExistingRequest.setCheckBoxInOperation(true);
			adminHelperFormExistingRequest.setObjectTypeIdInOperation(existingRequest.getItemInOperation().getObjectModel().getObjectType().getId());
			adminHelperFormExistingRequest.setObjectModelIdInOperation(existingRequest.getItemInOperation().getObjectModel().getId());
			adminHelperFormExistingRequest.setObjectInstanceIdInOperation(existingRequest.getItemInOperation().getId());
		}
		
		//Setting itemComment
		adminHelperFormExistingRequest.setItemComment(existingRequest.getItemComment());
		
		// Adding Modification Date TESTING !!!
		String formatedModificationDate = "";
		
		if(existingRequest.getModificationDate() != null) {
			formatedModificationDate = existingRequest.getModificationDate().format(customDateFormatter);
		}
		
		//Adding AdminUpdateObjectForm helperForm object to the model
		redirectAttributes.addFlashAttribute("adminHelperFormExistingRequest", adminHelperFormExistingRequest);
		
		// set request as a model attribute 
		redirectAttributes.addFlashAttribute("existingRequest", existingRequest);
		
		// adding model attribute with formated date
		redirectAttributes.addFlashAttribute("formatedDate", formatedDate);
		
		// adding model attribute with formated MODIFICATION date TESTING!!!
		redirectAttributes.addFlashAttribute("formatedModificationDate", formatedModificationDate);
		
		return "redirect:/requestFormAdmin/info";
	}
	
	
	// Displaying selected Request
	@GetMapping("/info")
	public String requestInfoGet(Model theModel, RedirectAttributes redirectAttributes) {

		if (theModel.containsAttribute("adminHelperFormExistingRequest")){
			
			AdminUpdateObjectForm adminHelperFormExistingRequest = (AdminUpdateObjectForm)theModel.asMap().get("adminHelperFormExistingRequest");
			
			theModel.addAttribute("adminHelperFormExistingRequest", adminHelperFormExistingRequest);
			
			return "admin-request-info";
			
		}else {
			return "redirect:/requestFormAdmin/select";
		}
	}
		
	
	// Form for editing existing request
	@PostMapping("/update")
	public String showFormForExistingRequestPost(@Valid @ModelAttribute("adminHelperFormExistingRequest") AdminUpdateObjectForm adminHelperFormExistingRequest,
													BindingResult theBindingResult,
													Model theModel,
													RedirectAttributes redirectAttributes) {		
		
//		System.out.println("Invoking Edit Existing Request()");

		if (theBindingResult.hasErrors()) {
//			System.out.println(">>>>> Error detected!");
			
			// TESTING
			ArrayList<String> errorListString = new ArrayList<String>();
						
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		        System.out.println ("Field: " + error.getField() + " : " + "Code: " + error.getCode());
		        
		        errorListString.add(error.getField() + " : " + error.getCode());     
		    }
		    
		    // Resetting values for proper animation on page
		    if(errorListString.contains("requesterId : RequesterNotDeleted")) {
		    	adminHelperFormExistingRequest.setRequesterId(0);
//		    	System.out.println("Resetting Requester Id");
		    }
		    
		    if(errorListString.contains("objectTypeIdInService : ObjectTypeNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectTypeIdInService(0);
//		    	System.out.println("Resetting TypeId InService");
		    }
		    if(errorListString.contains("objectModelIdInService : ObjectModelNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectModelIdInService(0);
//		    	System.out.println("Resetting ModelId InService");
		    }
		    if(errorListString.contains("objectInstanceIdInService : ObjectInstanceNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectInstanceIdInService(0);
//		    	System.out.println("Resetting InstanceId InService");
		    }
		    
		    if(errorListString.contains("objectTypeIdInOperation : ObjectTypeNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectTypeIdInOperation(0);
//		    	System.out.println("Resetting TypeId InOperation");
		    }
		    if(errorListString.contains("objectModelIdInOperation : ObjectModelNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectModelIdInOperation(0);
//		    	System.out.println("Resetting ModelId InOperation");
		    }
		    if(errorListString.contains("objectInstanceIdInOperation : ObjectInstanceNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectInstanceIdInOperation(0);
//		    	System.out.println("Resetting InstanceId InOperation");
		    }    
			
		    // If errors, add flash attributes and redirect to /requestFormAdmin/update page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminHelperFormExistingRequest", theBindingResult);
			redirectAttributes.addFlashAttribute("adminHelperFormExistingRequest", adminHelperFormExistingRequest);
			redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
		    			
			return "redirect:/requestFormAdmin/update";
		}
						
		redirectAttributes.addFlashAttribute("adminHelperFormExistingRequest", adminHelperFormExistingRequest);
		redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
	    			
		return "redirect:/requestFormAdmin/update";
	}
	
	
	// Form for editing existing request
	@GetMapping("/update")
	public String showFormForExistingRequestGet(Model theModel, RedirectAttributes redirectAttributes) {		
		
		if (theModel.containsAttribute("adminHelperFormExistingRequest")){
			
			AdminUpdateObjectForm adminHelperFormExistingRequest = new AdminUpdateObjectForm();
			
			adminHelperFormExistingRequest = (AdminUpdateObjectForm)theModel.asMap().get("adminHelperFormExistingRequest");
			
			theModel.addAttribute("adminHelperFormExistingRequest", adminHelperFormExistingRequest);
			
			// send over to admin-edit-existing-request page		
			return "admin-edit-existing-request";
			
		}else {			
			return "redirect:/requestFormAdmin/select";
		}
	}	
	
	
	// Review made changes
	@PostMapping("/review")
	public String review(@Valid @ModelAttribute("adminHelperFormExistingRequest") AdminUpdateObjectForm adminHelperFormExistingRequest,
							BindingResult theBindingResult,										
							Model theModel,
							RedirectAttributes redirectAttributes) {

//		System.out.println("Invoking Review()");		
		
		// If the form has errors, return to the form	
		if (theBindingResult.hasErrors()) {
//			System.out.println(">>>>> Error detected!");
			
			
			// TESTING
			ArrayList<String> errorListString = new ArrayList<String>();
						
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		        System.out.println ("Field: " + error.getField() + " : " + "Code: " + error.getCode());
		        
		        errorListString.add(error.getField() + " : " + error.getCode());     
		    }
		    
		    // Resetting values for proper animation on page
		    if(errorListString.contains("requesterId : RequesterNotDeleted")) {
		    	adminHelperFormExistingRequest.setRequesterId(0);
//		    	System.out.println("Resetting Requester Id");
		    }
		    
		    if(errorListString.contains("objectTypeIdInService : ObjectTypeNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectTypeIdInService(0);
//		    	System.out.println("Resetting TypeId InService");
		    }
		    if(errorListString.contains("objectModelIdInService : ObjectModelNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectModelIdInService(0);
//		    	System.out.println("Resetting ModelId InService");
		    }
		    if(errorListString.contains("objectInstanceIdInService : ObjectInstanceNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectInstanceIdInService(0);
//		    	System.out.println("Resetting InstanceId InService");
		    }
		    
		    if(errorListString.contains("objectTypeIdInOperation : ObjectTypeNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectTypeIdInOperation(0);
//		    	System.out.println("Resetting TypeId InOperation");
		    }
		    if(errorListString.contains("objectModelIdInOperation : ObjectModelNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectModelIdInOperation(0);
//		    	System.out.println("Resetting ModelId InOperation");
		    }
		    if(errorListString.contains("objectInstanceIdInOperation : ObjectInstanceNotDeleted")) {
		    	adminHelperFormExistingRequest.setObjectInstanceIdInOperation(0);
//		    	System.out.println("Resetting InstanceId InOperation");
		    }    
						
		    // If errors, add flash attributes and redirect to /requestFormAdmin/update page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminHelperFormExistingRequest", theBindingResult);
			redirectAttributes.addFlashAttribute("adminHelperFormExistingRequest", adminHelperFormExistingRequest);
			redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());

		    // If errors, redirect to update form page
			return "redirect:/requestFormAdmin/update"; 
		}
		
		// Adding AddRequestReview helper object for displaying data on the page
		AddRequestReview requestReviewObject = new AddRequestReview();
		
		// Setting RequestId for displaying from the helper object (String)
		requestReviewObject.setRequestId(adminHelperFormExistingRequest.getRequestId());
		
		// Setting Date for displaying from the helper object (String)
		requestReviewObject.setDate(adminHelperFormExistingRequest.getDateSelected());
		
		// Setting Requester for displaying from the helper object (String)
		requestReviewObject.setRequester(requesterService.getRequester(adminHelperFormExistingRequest.getRequesterId()).getName());
		
		// Setting Requester Comment for displaying from the helper object (String)
		requestReviewObject.setRequesterComment(adminHelperFormExistingRequest.getRequesterComment());
		
		// Setting Item in Service		
		if(adminHelperFormExistingRequest.isCheckBoxInService()) {			
			
			ObjectInstance itemInService = new ObjectInstance();
			
			itemInService = objectInstanceService.getObjectInstanceById(adminHelperFormExistingRequest.getObjectInstanceIdInService());
			
			// Setting Type (String)
			requestReviewObject.setObjectTypeInService(itemInService.getObjectModel().getObjectType().getType());
			
			// Setting Model (String)
			requestReviewObject.setObjectModelInService(itemInService.getObjectModel().getModel());			
			
			// Setting Instance (String)
			requestReviewObject.setObjectInstanceInService(itemInService.getSerialNumber());							
		}		
		
		// Setting Item in Operation		
		if(adminHelperFormExistingRequest.isCheckBoxInOperation()) {			
			
			ObjectInstance itemInOperation = new ObjectInstance();
			
			itemInOperation = objectInstanceService.getObjectInstanceById(adminHelperFormExistingRequest.getObjectInstanceIdInOperation());
			
			// Setting Type (String)
			requestReviewObject.setObjectTypeInOperation(itemInOperation.getObjectModel().getObjectType().getType());
			
			// Setting Model (String)
			requestReviewObject.setObjectModelInOperation(itemInOperation.getObjectModel().getModel());			
			
			// Setting Instance (String)
			requestReviewObject.setObjectInstanceInOperation(itemInOperation.getSerialNumber());							
		}			
		
		// Setting Item Comment (String)
		requestReviewObject.setItemComment(adminHelperFormExistingRequest.getItemComment());		
		
		// Adding the VALID helperForm to the model, necessary for submitting form for returning for Edit
		redirectAttributes.addFlashAttribute("adminHelperFormExistingRequest", adminHelperFormExistingRequest);
		
		// Adding the requestReviewObject to the Model. It's data will be displayed for the Review.
		redirectAttributes.addFlashAttribute("requestReviewObject", requestReviewObject);
					
		return "redirect:/requestFormAdmin/review";
	}


	// Review made changes
	@GetMapping("/review")
	public String reviewGet(Model theModel,	RedirectAttributes redirectAttributes) {


		if (theModel.containsAttribute("requestReviewObject")){
			
			AdminUpdateObjectForm adminHelperFormExistingRequest = (AdminUpdateObjectForm)theModel.asMap().get("adminHelperFormExistingRequest");
			
			theModel.addAttribute("adminHelperFormExistingRequest", adminHelperFormExistingRequest);
						
			// Adding AdminUpdateObjectForm helperFormSaving with values from the Validated helperForm, necessary for Saving the Updated Request
			AdminUpdateObjectForm adminHelperFormSavingUpdate = new AdminUpdateObjectForm();
			
			theModel.addAttribute("adminHelperFormSavingUpdate", adminHelperFormSavingUpdate);
			
			return "admin-request-review";
			
		}else {
			return "redirect:/requestFormAdmin/select";
		}	
	}
	
	
	// Validation is done again. WARNING !!! Admin must know what is doing and can break the system!!!(can break the Date validation)
	@PostMapping("/save")
	public String processShowFormForUpdate(@Valid @ModelAttribute("adminHelperFormSavingUpdate") AdminUpdateObjectForm adminHelperFormSavingUpdate,
											BindingResult theBindingResult,
											Model theModel,			
											RedirectAttributes redirectAttributes) {

//		System.out.println("Invoking Save Existing Request()");
		// If the BindingResult has errors, redirect to save error page
		if (theBindingResult.hasErrors()) {
			
//			System.out.println(">>>>> Error detected!");

			// TESTING
			ArrayList<String> errorListString = new ArrayList<String>();
						
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		        System.out.println ("Field: " + error.getField() + " : " + "Code: " + error.getCode());
		        
		        errorListString.add(error.getField() + " : " + error.getCode());
      
		    }
		    
		    // Display error page for certain error
		    if(errorListString.contains("requesterId : RequesterNotDeleted")) {
		    	
		    	String requesterDeleted = "requesterDeleted";
				
				redirectAttributes.addFlashAttribute("requesterDeleted", requesterDeleted);
		    	
		    	return "redirect:/requestFormAdmin/requesterDeleted";		
		    }		    
		    
		    else if(errorListString.contains("objectTypeIdInService : ObjectTypeNotDeleted") || errorListString.contains("objectTypeIdInOperation : ObjectTypeNotDeleted")) {
		    	
		    	String typeDeleted = "typeDeleted";
				
				redirectAttributes.addFlashAttribute("typeDeleted", typeDeleted);
		    	
		    	return "redirect:/requestFormAdmin/typeDeleted";
		    }
		    
		    else if(errorListString.contains("objectModelIdInService : ObjectModelNotDeleted") || errorListString.contains("objectModelIdInOperation : ObjectModelNotDeleted")) {
		    	
		    	String modelDeleted = "modelDeleted";
				
				redirectAttributes.addFlashAttribute("modelDeleted", modelDeleted);
		    	
		    	return "redirect:/requestFormAdmin/modelDeleted";	    	
		    }
		    
		    else if(errorListString.contains("objectInstanceIdInService : ObjectInstanceNotDeleted") || errorListString.contains("objectInstanceIdInOperation : ObjectInstanceNotDeleted")) {
		    	
		    	String instanceDeleted = "instanceDeleted";
				
				redirectAttributes.addFlashAttribute("instanceDeleted", instanceDeleted);
		    	
		    	return "redirect:/requestFormAdmin/instanceDeleted";	
		    }
			else {
		    	
		    	//This part of the code should not be executed, but just in case
		    	return "default-error";
		    }
		}
		
		
//		logger.info("The Request is: " + adminHelperFormSavingUpdate.getRequestId());
		
		// UPDATING REQUEST
		// Retrieving Request object
		Request request = requestService.getRequest(adminHelperFormSavingUpdate.getRequestId());
		
		// Setting Request Date - REMOVED OR ADMIN CAN BREAK!!! THE DB
//		LocalDate dateOfRequest = LocalDate.parse(adminHelperFormSavingUpdate.getDateSelected() , DateTimeFormatter.ISO_LOCAL_DATE);
//		request.setDate(dateOfRequest);
		
		// Setting Requester
		request.setRequester(requesterService.getRequester(adminHelperFormSavingUpdate.getRequesterId()));	
		
		// Setting the new Requester Comment (or overwriting it again)
		request.setRequesterComment(adminHelperFormSavingUpdate.getRequesterComment());
		
		// Setting Item in Service and Item in Operation (or overwriting it again) - START
		// !!! do NOT remove ObjectInstance initialization, it is necessary for clearing the filed on the review page after the checkbox is unchecked
		// TEST AND REMOVE IF NECESSERY
		
		ObjectInstance itemInService = new ObjectInstance();
		ObjectInstance itemInOperation = new ObjectInstance();

		if(adminHelperFormSavingUpdate.isCheckBoxInService()) {
			itemInService = objectInstanceService.getObjectInstanceById(adminHelperFormSavingUpdate.getObjectInstanceIdInService());			
		}else {
			// If checkBox is not selected item MUST be NULL for proper saving to the DB
			itemInService = null;
		}
		
		if(adminHelperFormSavingUpdate.isCheckBoxInOperation()) {
			itemInOperation = objectInstanceService.getObjectInstanceById(adminHelperFormSavingUpdate.getObjectInstanceIdInOperation());
		}else {
			// If checkBox is not selected item MUST be NULL for proper saving to the DB
			itemInOperation = null;
		}
				
		// Setting the Item In Service and In Operation, even if ObjectInstance is null
		request.setItemInService(itemInService);
		request.setItemInOperation(itemInOperation);
		
		// Setting Item in Service and Item in Operation - END

		// Setting Item Comment
		request.setItemComment(adminHelperFormSavingUpdate.getItemComment());
		
		// Setting User Name
		request.setUserName(adminHelperFormSavingUpdate.getUserName());
		
		// TESTING !!! ADDING MODIFICATION DATE
		LocalDate today = LocalDate.now();
		request.setModificationDate(today);		
		
		// Updating the Request to the DB
		requestService.updateExistingRequest(request);
		
		String requestUpdated= "requestUpdated";
		
		redirectAttributes.addFlashAttribute("requestUpdated", requestUpdated);
	
		// Implementing PRG, preventing form re-submission on page refresh
		return "redirect:/requestFormAdmin/updateSaved";
	}
	
	
	// Displaying page for confirmation message of Request update
	@GetMapping("/updateSaved")
	public String updatedRequest(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("requestUpdated")) {
			
			return "admin-request-saved";		
			
		}else {
			return "redirect:/";
		}	
	}		
	
	
	//----------------------------------------------------------------
	
	// ERORRS START
	
	
	// Displaying Update Request Error Page - REQUESTER DELETED
	@GetMapping("/requesterDeleted")
	public String requesterDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("requesterDeleted")) {
			
			return "admin-request-saved-error-requester-deleted";			
			
		}else {
			return "redirect:/";
		}
	}
	
	
	// Displaying Update Request Error Page - TYPE DELETED
	@GetMapping("/typeDeleted")
	public String typeDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("typeDeleted")) {
			
			return "admin-request-saved-error-type-deleted";			
			
		}else {
			return "redirect:/";
		}
	}
	
	
	// Displaying Update Request Error Page - MODEL DELETED
	@GetMapping("/modelDeleted")
	public String modelDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("modelDeleted")) {
			
			return "admin-request-saved-error-model-deleted";			
			
		}else {
			return "redirect:/";
		}
	}
	
	
	// Displaying Update Request Error Page - INSTANCE DELETED
	@GetMapping("/instanceDeleted")
	public String instanceDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("instanceDeleted")) {
			
			return "admin-request-saved-error-instance-deleted";			
			
		}else {
			return "redirect:/";
		}
	}
	
}

package com.luv2code.springdemo.controller;

import java.time.LocalDate;
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
import com.luv2code.springdemo.helperObjects.UpdateObjectForm;
import com.luv2code.springdemo.service.ObjectInstanceService;
import com.luv2code.springdemo.service.RequestService;

@Controller
@RequestMapping("/requestFormUpdate")
public class RequestFormUpdateController {

	// need to inject our objectInstance services
	
	@Autowired
	private ObjectInstanceService objectInstanceService;

	@Autowired
	private RequestService requestService;
		
//	private static final Logger logger = LoggerFactory.getLogger(RequestFormUpdateController.class);
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		
	}
		
	
	// Form for editing existing request
	@PostMapping("/update")
	public String showFormForExistingRequestPost(@Valid @ModelAttribute("helperFormExistingRequest") UpdateObjectForm helperFormExistingRequest,
													BindingResult theBindingResult,	
													Model theModel,
													RedirectAttributes redirectAttributes) {		
		
//		System.out.println("Invoking Edit Existing Request()");
		
		if (theBindingResult.hasErrors()) {

			// TESTING
			ArrayList<String> errorListString = new ArrayList<String>();
						
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		        System.out.println ("Field: " + error.getField() + " : " + "Code: " + error.getCode());
		        
		        errorListString.add(error.getField() + " : " + error.getCode());     
		    }
		    
		    // Resetting values for proper animation on page		    
		    if(errorListString.contains("objectTypeIdInService : ObjectTypeNotDeleted")) {
		    	helperFormExistingRequest.setObjectTypeIdInService(0);
//		    	System.out.println("Resetting TypeId InService");
		    }
		    if(errorListString.contains("objectModelIdInService : ObjectModelNotDeleted")) {
		    	helperFormExistingRequest.setObjectModelIdInService(0);
//		    	System.out.println("Resetting ModelId InService");
		    }
		    if(errorListString.contains("objectInstanceIdInService : ObjectInstanceNotDeleted")) {
		    	helperFormExistingRequest.setObjectInstanceIdInService(0);
//		    	System.out.println("Resetting InstanceId InService");
		    }
		    
		    if(errorListString.contains("objectTypeIdInOperation : ObjectTypeNotDeleted")) {
		    	helperFormExistingRequest.setObjectTypeIdInOperation(0);
//		    	System.out.println("Resetting TypeId InOperation");
		    }
		    if(errorListString.contains("objectModelIdInOperation : ObjectModelNotDeleted")) {
		    	helperFormExistingRequest.setObjectModelIdInOperation(0);
//		    	System.out.println("Resetting ModelId InOperation");
		    }
		    if(errorListString.contains("objectInstanceIdInOperation : ObjectInstanceNotDeleted")) {
		    	helperFormExistingRequest.setObjectInstanceIdInOperation(0);
//		    	System.out.println("Resetting InstanceId InOperation");
		    }    
						
		    // If errors, add flash attributes and redirect to /requestFormUpdate/update page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.helperFormExistingRequest", theBindingResult);
			redirectAttributes.addFlashAttribute("helperFormExistingRequest", helperFormExistingRequest);
		    
//			System.out.println(">>> ERROR IN UPDATE BINDING RESULT");
			
		    // If errors, redirect to update form page
			return "redirect:/requestFormUpdate/update";
		}
				
		// adding the existing helperForm object to the model (the data is not lost)
		redirectAttributes.addFlashAttribute("helperFormExistingRequest", helperFormExistingRequest);
		
		// redirect to /update page	
		return "redirect:/requestFormUpdate/update";
	}
	
	
	// Form for editing existing request
	@GetMapping("/update")
	public String showFormForExistingRequestGet(Model theModel, RedirectAttributes redirectAttributes) {		
		
		// If the model contains "helperFormExistingRequest" redirect attribute display page, else redirect to /requests/list (page has been refreshed)
		if (theModel.containsAttribute("helperFormExistingRequest") == true) {
			
			UpdateObjectForm helperFormExistingRequest = new UpdateObjectForm();
					
			helperFormExistingRequest = (UpdateObjectForm)theModel.asMap().get("helperFormExistingRequest");
			
			theModel.addAttribute("helperFormExistingRequest", helperFormExistingRequest);
			
			// display page for editing request	
			return "edit-existing-request";
			
		}else {
			return "redirect:/requests/list";
		}	
	}
	
	
	// Review edited request POST
	@PostMapping("/reviewExisting")
	public String reviewExisting(@Valid @ModelAttribute("helperFormExistingRequest") UpdateObjectForm helperFormExistingRequest,
									BindingResult theBindingResult,				
									Model theModel,
									RedirectAttributes redirectAttributes) {

//		System.out.println("Invoking Review Existing Request()");	
		
		// If the form has errors, return to the form	
		if (theBindingResult.hasErrors()) {

			ArrayList<String> errorListString = new ArrayList<String>();
						
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		        System.out.println ("Field: " + error.getField() + " : " + "Code: " + error.getCode());
		        
		        errorListString.add(error.getField() + " : " + error.getCode());     
		    }
		    
		    // Resetting values for proper animation on page		    
		    if(errorListString.contains("objectTypeIdInService : ObjectTypeNotDeleted")) {
		    	helperFormExistingRequest.setObjectTypeIdInService(0);
//		    	System.out.println("Resetting TypeId InService");
		    }
		    if(errorListString.contains("objectModelIdInService : ObjectModelNotDeleted")) {
		    	helperFormExistingRequest.setObjectModelIdInService(0);
//		    	System.out.println("Resetting ModelId InService");
		    }
		    if(errorListString.contains("objectInstanceIdInService : ObjectInstanceNotDeleted")) {
		    	helperFormExistingRequest.setObjectInstanceIdInService(0);
//		    	System.out.println("Resetting InstanceId InService");
		    }
		    
		    if(errorListString.contains("objectTypeIdInOperation : ObjectTypeNotDeleted")) {
		    	helperFormExistingRequest.setObjectTypeIdInOperation(0);
//		    	System.out.println("Resetting TypeId InOperation");
		    }
		    if(errorListString.contains("objectModelIdInOperation : ObjectModelNotDeleted")) {
		    	helperFormExistingRequest.setObjectModelIdInOperation(0);
//		    	System.out.println("Resetting ModelId InOperation");
		    }
		    if(errorListString.contains("objectInstanceIdInOperation : ObjectInstanceNotDeleted")) {
		    	helperFormExistingRequest.setObjectInstanceIdInOperation(0);
//		    	System.out.println("Resetting InstanceId InOperation");
		    }
	
		    // If errors, add flash attributes and redirect to /requestFormUpdate/update page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.helperFormExistingRequest", theBindingResult);
			redirectAttributes.addFlashAttribute("helperFormExistingRequest", helperFormExistingRequest);

		    // If errors, redirect to update form page
			return "redirect:/requestFormUpdate/update";  
		}
		
		// Adding AddRequestReview helper object for displaying data on the page
		AddRequestReview requestReviewObject = new AddRequestReview();
		
		// Setting RequestId for displaying from the helper object (String)
		requestReviewObject.setRequestId(helperFormExistingRequest.getRequestId());
		
		// Setting Date for displaying from the helper object (String)
		requestReviewObject.setDate(helperFormExistingRequest.getDateSelected());
		
		// Setting Requester for displaying from the helper object (String)
		requestReviewObject.setRequester(helperFormExistingRequest.getRequesterName());
		
		// Setting Requester Comment for displaying from the helper object (String)
		requestReviewObject.setRequesterComment(helperFormExistingRequest.getRequesterComment());
		
		// Setting Item in Service		
		if(helperFormExistingRequest.isCheckBoxInService()) {			
			
			ObjectInstance itemInService = new ObjectInstance();
			
			itemInService = objectInstanceService.getObjectInstanceById(helperFormExistingRequest.getObjectInstanceIdInService());
			
			// Setting Type (String)
			requestReviewObject.setObjectTypeInService(itemInService.getObjectModel().getObjectType().getType());
			
			// Setting Model (String)
			requestReviewObject.setObjectModelInService(itemInService.getObjectModel().getModel());			
			
			// Setting Instance (String)
			requestReviewObject.setObjectInstanceInService(itemInService.getSerialNumber());							
		}		
		
		// Setting Item in Operation		
		if(helperFormExistingRequest.isCheckBoxInOperation()) {			
			
			ObjectInstance itemInOperation = new ObjectInstance();
			
			itemInOperation = objectInstanceService.getObjectInstanceById(helperFormExistingRequest.getObjectInstanceIdInOperation());
			
			// Setting Type (String)
			requestReviewObject.setObjectTypeInOperation(itemInOperation.getObjectModel().getObjectType().getType());
			
			// Setting Model (String)
			requestReviewObject.setObjectModelInOperation(itemInOperation.getObjectModel().getModel());			
			
			// Setting Instance (String)
			requestReviewObject.setObjectInstanceInOperation(itemInOperation.getSerialNumber());							
		}			
		
		// Setting Item Comment (String)
		requestReviewObject.setItemComment(helperFormExistingRequest.getItemComment());
				
		// Adding the requestReviewObject to the Model. It's data will be displayed for the Review.
		redirectAttributes.addFlashAttribute("requestReviewObject", requestReviewObject);
		
		// Adding the VALID helperFormExistingRequest to the model, necessary for submitting form for returning for Edit
		redirectAttributes.addFlashAttribute("helperFormExistingRequest", helperFormExistingRequest);	
		
	    // Redirect to review form page
		return "redirect:/requestFormUpdate/reviewExisting";	
		
	}
	
	
	// Review edited request GET
	@GetMapping("/reviewExisting")
	public String reviewExistingGet(Model theModel, RedirectAttributes redirectAttributes) {

		if (theModel.containsAttribute("requestReviewObject")) {
			
			// Adding UpdateObjectForm helperFormSaving with values from the Validated helperFormExistingRequest, necessary for Saving the Updated Request
			UpdateObjectForm helperFormSavingUpdate = new UpdateObjectForm();
			
			theModel.addAttribute("helperFormSavingUpdate", helperFormSavingUpdate);
			
			// Adding UpdateObjectForm helperFormExistingRequest for going back and edit
			UpdateObjectForm helperFormExistingRequest = (UpdateObjectForm)theModel.asMap().get("helperFormExistingRequest");
			
			theModel.addAttribute("helperFormExistingRequest", helperFormExistingRequest);
			
			return "edit-existing-request-review";
			
		}else {

			return "redirect:/requests/list";
		}	
	}
		

	// Validation is done again. requestId - CAN'T BE CHANGED; requesterId and Date CAN be CHANGED by ADMIN, but will have values.
	@PostMapping("/saveExisting")
	public String processShowFormForUpdate(@Valid @ModelAttribute("helperFormSavingUpdate") UpdateObjectForm helperFormSavingUpdate,
											BindingResult theBindingResult,
											Model theModel,
											RedirectAttributes redirectAttributes) {

//		System.out.println("Invoking Save Existing Request()");
		
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
		    if(errorListString.contains("objectTypeIdInService : ObjectTypeNotDeleted") || errorListString.contains("objectTypeIdInOperation : ObjectTypeNotDeleted")) {
		    	
		    	String typeDeleted = "typeDeleted";
				
				redirectAttributes.addFlashAttribute("typeDeleted", typeDeleted);
		    	
		    	return "redirect:/requestFormUpdate/typeDeleted";	 		    	
		    }
		    
		    else if(errorListString.contains("objectModelIdInService : ObjectModelNotDeleted") || errorListString.contains("objectModelIdInOperation : ObjectModelNotDeleted")) {
		    	
		    	String modelDeleted = "modelDeleted";
				
				redirectAttributes.addFlashAttribute("modelDeleted", modelDeleted);
		    	
		    	return "redirect:/requestFormUpdate/modelDeleted";			    	
		    }
		    
		    else if(errorListString.contains("objectInstanceIdInService : ObjectInstanceNotDeleted") || errorListString.contains("objectInstanceIdInOperation : ObjectInstanceNotDeleted")) {
		    	
		    	String instanceDeleted = "instanceDeleted";
				
				redirectAttributes.addFlashAttribute("instanceDeleted", instanceDeleted);
		    	
		    	return "redirect:/requestFormUpdate/instanceDeleted";	 	    	
		    }
			else {
		    	
		    	//This part of the code should not be executed, but just in case
		    	return "default-error";
		    }
		}
		
		// TESTING UPDATING REQUEST
		//Adding new Request object
		Request request = requestService.getRequest(helperFormSavingUpdate.getRequestId());
		
		// Setting the new Requester Comment (or overwriting it again)
		request.setRequesterComment(helperFormSavingUpdate.getRequesterComment());
		
		// Setting Item in Service and Item in Operation (or overwriting it again) - START
		// !!! do NOT remove ObjectInstance initialization, it is necessary for clearing the filed on the review page after the checkbox is unchecked
		// TEST AND REMOVE IF NECESSERY
		
		ObjectInstance itemInService = new ObjectInstance();
		ObjectInstance itemInOperation = new ObjectInstance();

		if(helperFormSavingUpdate.isCheckBoxInService()) {
			itemInService = objectInstanceService.getObjectInstanceById(helperFormSavingUpdate.getObjectInstanceIdInService());			
		}else {
			// If checkBox is not selected item MUST be NULL for proper saving to the DB
			itemInService = null;
		}
		
		if(helperFormSavingUpdate.isCheckBoxInOperation()) {
			itemInOperation = objectInstanceService.getObjectInstanceById(helperFormSavingUpdate.getObjectInstanceIdInOperation());
		}else {
			// If checkBox is not selected item MUST be NULL for proper saving to the DB
			itemInOperation = null;
		}
				
		// Setting the Item In Service and In Operation, even if ObjectInstance is null
		request.setItemInService(itemInService);
		request.setItemInOperation(itemInOperation);
		
		// Setting Item in Service and Item in Operation - END

		// Setting Item Comment
		request.setItemComment(helperFormSavingUpdate.getItemComment());
		
		// Setting User name
		request.setUserName(helperFormSavingUpdate.getUserName());
		
		// TESTING !!! ADDING MODIFICATION DATE
		LocalDate today = LocalDate.now();
		request.setModificationDate(today);
		
		// Updating the Request to the DB
		requestService.updateExistingRequest(request);
		
		String requestUpdated= "requestUpdated";
		
		redirectAttributes.addFlashAttribute("requestUpdated", requestUpdated);
				
		return "redirect:/requestFormUpdate/updateSaved";
	}
	
	
	@GetMapping("/updateSaved")
	public String updatedRequest(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("requestUpdated")) {
			
			return "edit-existing-request-saved";		
			
		}else {
			return "redirect:/";
		}

	}	
	
	
	//----------------------------------------------------------------
	
	// ERORRS START
	
	
	// Displaying Update Request Error Page - TYPE DELETED
	@GetMapping("/typeDeleted")
	public String typeDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("typeDeleted")) {
			
			return "edit-existing-request-saved-error-type-deleted";			
			
		}else {
			return "redirect:/";
		}
	}
	
	
	// Displaying Update Request Error Page - MODEL DELETED
	@GetMapping("/modelDeleted")
	public String modelDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("modelDeleted")) {
			
			return "edit-existing-request-saved-error-model-deleted";			
			
		}else {
			return "redirect:/";
		}
	}
	
	
	// Displaying Update Request Error Page - INSTANCE DELETED
	@GetMapping("/instanceDeleted")
	public String instanceDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("instanceDeleted")) {
			
			return "edit-existing-request-saved-error-instance-deleted";			
			
		}else {
			return "redirect:/";
		}
	}
			
}

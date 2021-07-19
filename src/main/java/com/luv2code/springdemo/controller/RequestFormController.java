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
import com.luv2code.springdemo.helperObjects.AddObjectForm;
import com.luv2code.springdemo.helperObjects.AddRequestReview;
import com.luv2code.springdemo.service.ObjectInstanceService;
import com.luv2code.springdemo.service.RequestService;
import com.luv2code.springdemo.service.RequesterService;

@Controller
@RequestMapping("/requestForm")
public class RequestFormController {

	// need to inject our objectInstance services
	
	@Autowired
	private ObjectInstanceService objectInstanceService;
	
	@Autowired
	private RequesterService requesterService;

	@Autowired
	private RequestService requestService;
	
	// Injecting bean with requesters hashmap(scope Request)
/*	@Autowired
	private LinkedHashMap<Integer, String> requestersMap;
	*/
	
	// autowired custom date formatter of pattern "dd-MM-yyyy"
	@Autowired
	private DateTimeFormatter customDateFormatter;
	
//	private static final Logger logger = LoggerFactory.getLogger(RequestFormController.class);
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		
	}
	
	
	// Form for adding new request
	@GetMapping("/add")
	public String showFormForAdd(Model theModel) {
			
//		System.out.println("Invoking Add()");
		
		if (theModel.containsAttribute("helperForm") == false) {
			
			// Adding requestersMap (For Requester select from dropdown) to the model
			theModel.addAttribute("requestersMap", requesterService.getRequestersMap());
			
			// Adding NEW helperform to the model
			AddObjectForm helperForm = new AddObjectForm();
			
			theModel.addAttribute("helperForm", helperForm);
			
		}
				
		// Displaying add-request page		
		return "add-request";
	}
	
	
	// Form for editing new request, "Edit" button on /review JSP page.
	@PostMapping("/add")
	public String showFormForAddPost(@Valid @ModelAttribute("helperForm") AddObjectForm helperForm,
										BindingResult theBindingResult,	
										Model theModel,
										RedirectAttributes redirectAttributes) {
		
//		System.out.println("Invoking Edit()");
		
		if (theBindingResult.hasErrors()) {
//			System.out.println(">>>>> Error detected!");
			
			//Adding requestersMap (For Requester select from dropdown) to the model
//			theModel.addAttribute("requestersMap", requestersMap);
			
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
		    	helperForm.setRequesterId(0);
//		    	System.out.println("Resetting Requester Id");
		    }
		    
		    if(errorListString.contains("objectTypeIdInService : ObjectTypeNotDeleted")) {
		    	helperForm.setObjectTypeIdInService(0);
//		    	System.out.println("Resetting TypeId InService");
		    }
		    if(errorListString.contains("objectModelIdInService : ObjectModelNotDeleted")) {
		    	helperForm.setObjectModelIdInService(0);
//		    	System.out.println("Resetting ModelId InService");
		    }
		    if(errorListString.contains("objectInstanceIdInService : ObjectInstanceNotDeleted")) {
		    	helperForm.setObjectInstanceIdInService(0);
//		    	System.out.println("Resetting InstanceId InService");
		    }
		    
		    if(errorListString.contains("objectTypeIdInOperation : ObjectTypeNotDeleted")) {
		    	helperForm.setObjectTypeIdInOperation(0);
//		    	System.out.println("Resetting TypeId InOperation");
		    }
		    if(errorListString.contains("objectModelIdInOperation : ObjectModelNotDeleted")) {
		    	helperForm.setObjectModelIdInOperation(0);
//		    	System.out.println("Resetting ModelId InOperation");
		    }
		    if(errorListString.contains("objectInstanceIdInOperation : ObjectInstanceNotDeleted")) {
		    	helperForm.setObjectInstanceIdInOperation(0);
//		    	System.out.println("Resetting InstanceId InOperation");
		    }    
						
		    // If errors, add flash attributes and redirect to /requestForm/add page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.helperForm", theBindingResult);
			redirectAttributes.addFlashAttribute("helperForm", helperForm);
			redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
		    
		    // If errors, redirect to add form page
			return "redirect:/requestForm/add";
		}
		
		
		//Adding requestersMap (For Requester select from dropdown) to the model
		redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
		
		// adding the existing helperForm object to the model (the data is not lost)
		redirectAttributes.addFlashAttribute("helperForm", helperForm);			
			
		// redirect to add form page		
		return "redirect:/requestForm/add";
	}
	
	
	@PostMapping("/review")
	public String review(@Valid @ModelAttribute("helperForm") AddObjectForm helperForm,
							BindingResult theBindingResult,										
							Model theModel,
							RedirectAttributes redirectAttributes) {

//		System.out.println("Invoking Review()");		
		
		// If the form has errors, return to the form	
		if (theBindingResult.hasErrors()) {
//			System.out.println(">>>>> Error detected!");
			
			//Adding requestersMap (For Requester select from dropdown) to the model
//			theModel.addAttribute("requestersMap", requestersMap);			
						
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
		    	helperForm.setRequesterId(0);
//		    	System.out.println("Resetting Requester Id");
		    }
		    
		    if(errorListString.contains("objectTypeIdInService : ObjectTypeNotDeleted")) {
		    	helperForm.setObjectTypeIdInService(0);
//		    	System.out.println("Resetting TypeId InService");
		    }
		    if(errorListString.contains("objectModelIdInService : ObjectModelNotDeleted")) {
		    	helperForm.setObjectModelIdInService(0);
//		    	System.out.println("Resetting ModelId InService");
		    }
		    if(errorListString.contains("objectInstanceIdInService : ObjectInstanceNotDeleted")) {
		    	helperForm.setObjectInstanceIdInService(0);
//		    	System.out.println("Resetting InstanceId InService");
		    }
		    
		    if(errorListString.contains("objectTypeIdInOperation : ObjectTypeNotDeleted")) {
		    	helperForm.setObjectTypeIdInOperation(0);
//		    	System.out.println("Resetting TypeId InOperation");
		    }
		    if(errorListString.contains("objectModelIdInOperation : ObjectModelNotDeleted")) {
		    	helperForm.setObjectModelIdInOperation(0);
//		    	System.out.println("Resetting ModelId InOperation");
		    }
		    if(errorListString.contains("objectInstanceIdInOperation : ObjectInstanceNotDeleted")) {
		    	helperForm.setObjectInstanceIdInOperation(0);
//		    	System.out.println("Resetting InstanceId InOperation");
		    }    
			
		    // If errors, add flash attributes and redirect to /requestForm/add page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.helperForm", theBindingResult);
			redirectAttributes.addFlashAttribute("helperForm", helperForm);
			redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
		    
		    // If errors, redirect to add form page
			return "redirect:/requestForm/add";
		}
		
		// Adding AddRequestReview helper object for displaying data on the page
		AddRequestReview requestReviewObject = new AddRequestReview();
		
		// Setting Date for displaying from the helper object (String)
		requestReviewObject.setDate(helperForm.getDateSelected());
		
		// Setting Requester for displaying from the helper object (String)
		requestReviewObject.setRequester(requesterService.getRequester(helperForm.getRequesterId()).getName());
		
		// Setting Requester Comment for displaying from the helper object (String)
		requestReviewObject.setRequesterComment(helperForm.getRequesterComment());
		
		// Setting Item in Service		
		if(helperForm.isCheckBoxInService()) {			
			
			ObjectInstance itemInService = new ObjectInstance();
			
			itemInService = objectInstanceService.getObjectInstanceById(helperForm.getObjectInstanceIdInService());
			
			// Setting Type (String)
			requestReviewObject.setObjectTypeInService(itemInService.getObjectModel().getObjectType().getType());
			
			// Setting Model (String)
			requestReviewObject.setObjectModelInService(itemInService.getObjectModel().getModel());			
			
			// Setting Instance (String)
			requestReviewObject.setObjectInstanceInService(itemInService.getSerialNumber());				
			
		}		
		
		// Setting Item in Operation		
		if(helperForm.isCheckBoxInOperation()) {			
			
			ObjectInstance itemInOperation = new ObjectInstance();
			
			itemInOperation = objectInstanceService.getObjectInstanceById(helperForm.getObjectInstanceIdInOperation());
			
			// Setting Type (String)
			requestReviewObject.setObjectTypeInOperation(itemInOperation.getObjectModel().getObjectType().getType());
			
			// Setting Model (String)
			requestReviewObject.setObjectModelInOperation(itemInOperation.getObjectModel().getModel());			
			
			// Setting Instance (String)
			requestReviewObject.setObjectInstanceInOperation(itemInOperation.getSerialNumber());				
			
		}			
		
		// Setting Item Comment (String)
		requestReviewObject.setItemComment(helperForm.getItemComment());	    

		// Adding the requestReviewObject to the Model. It's data will be displayed for the Review.
		redirectAttributes.addFlashAttribute("requestReviewObject", requestReviewObject);
		
		// Adding the VALID helperForm to the model, necessary for submitting form for returning for Edit
		redirectAttributes.addFlashAttribute("helperForm", helperForm);
			    
	    // If errors, redirect to review form page
		return "redirect:/requestForm/review";	
	}
	
	
	@GetMapping("/review")
	public String reviewGet(Model theModel, RedirectAttributes redirectAttributes) {

		if (theModel.containsAttribute("requestReviewObject")) {
			
			// Adding AddObjectForm helperFormSaving with values from the Validated helperForm, necessary for Saving the new Request
			AddObjectForm helperFormSaving = new AddObjectForm();
			theModel.addAttribute("helperFormSaving", helperFormSaving);
			
			return "add-request-review";
		}else {
			return "redirect:/requestForm/add";
		}
	}
	

	@PostMapping("/save")
	public String processShowFormForAdd(@Valid @ModelAttribute("helperFormSaving") AddObjectForm helperFormSaving,
										BindingResult theBindingResult,
										Model theModel,
										RedirectAttributes redirectAttributes) {
		
//		System.out.println("Invoking Save()");
		
		// ATTENTION !!! Validation is done AGAIN. Checking if new Request has been saved in the meantime(Date is important)
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
		    	
		    	return "redirect:/requestForm/requesterDeleted";		    	
		    }		    
		    
		    else if(errorListString.contains("objectTypeIdInService : ObjectTypeNotDeleted") || errorListString.contains("objectTypeIdInOperation : ObjectTypeNotDeleted")) {
		    	
		    	String typeDeleted = "typeDeleted";
				
				redirectAttributes.addFlashAttribute("typeDeleted", typeDeleted);
		    	
		    	return "redirect:/requestForm/typeDeleted";	 	
		    }
		    
		    else if(errorListString.contains("objectModelIdInService : ObjectModelNotDeleted") || errorListString.contains("objectModelIdInOperation : ObjectModelNotDeleted")) {
		    	
		    	String modelDeleted = "modelDeleted";
				
				redirectAttributes.addFlashAttribute("modelDeleted", modelDeleted);
		    	
		    	return "redirect:/requestForm/modelDeleted";	 
		    }
		    
		    else if(errorListString.contains("objectInstanceIdInService : ObjectInstanceNotDeleted") || errorListString.contains("objectInstanceIdInOperation : ObjectInstanceNotDeleted")) {
		    	
		    	String instanceDeleted = "instanceDeleted";
				
				redirectAttributes.addFlashAttribute("instanceDeleted", instanceDeleted);
		    	
		    	return "redirect:/requestForm/instanceDeleted";	  	
		    }
			
		    else if(errorListString.contains("dateSelected : AddObjectFormDate")) {
			    
		    	// Adding flash attribute for redirecting. The helperForm object will be available for Edit (using form submission).
			    redirectAttributes.addFlashAttribute("helperFormSaving", helperFormSaving);
			    
			    // Implementing PRG. If page is refreshed, only the error page will be displayed.
			    // On Refresh data from helperFormSaving will be lost and after clicking "Edit" the form fields will be empty.
				return "redirect:/requestForm/dateError";
				
		    }else {
		    	
		    	//This part of the code should not be executed, but just in case
		    	return "default-error";
		    }

		}
				
		// NO ERRORS

		//Adding new Request object
		Request request = new Request();
		
		// Setting Request values.
		
		// Setting Request Date
		LocalDate dateOfRequest = LocalDate.parse(helperFormSaving.getDateSelected() , customDateFormatter);
		request.setDate(dateOfRequest);
		
		// Setting Requester
		request.setRequester(requesterService.getRequester(helperFormSaving.getRequesterId()));
		
		// Setting Requester comment
		request.setRequesterComment(helperFormSaving.getRequesterComment());
				
		// Setting Item in Service and Item in Operation - START
		// !!! do NOT remove ObjectInstance initialization, it is necessary for clearing the filed on the review page after the checkbox is unchecked
		// TEST AND REMOVE IF NECESSERY
		
		ObjectInstance itemInService = new ObjectInstance();
		ObjectInstance itemInOperation = new ObjectInstance();

		if(helperFormSaving.isCheckBoxInService()) {
			
			itemInService = objectInstanceService.getObjectInstanceById(helperFormSaving.getObjectInstanceIdInService());	
			
		}else {
			// If checkBox is not selected item MUST be NULL for proper saving to the DB
			itemInService = null;
		}
		
		if(helperFormSaving.isCheckBoxInOperation()) {
			
			itemInOperation = objectInstanceService.getObjectInstanceById(helperFormSaving.getObjectInstanceIdInOperation());
			
		}else {
			// If checkBox is not selected item MUST be NULL for proper saving to the DB
			itemInOperation = null;
		}
				
		// Setting the Item In Service and In Operation, even if ObjectInstance is null
		request.setItemInService(itemInService);
		request.setItemInOperation(itemInOperation);
		
		// Setting Item in Service and Item in Operation - END
		
		// Setting Item comment
		request.setItemComment(helperFormSaving.getItemComment());
		
		// Setting userName -> Who has entered the request.
		request.setUserName(helperFormSaving.getUserName());
					
//		logger.info("The Requester is: " + request.getRequester().getName());
				
		//Saving the request
		requestService.saveNewRequest(request);
		
//		System.out.println(">>> New Request Saved!");
		
		String requestSaved = "requestSaved";
		
		redirectAttributes.addFlashAttribute("requestSaved", requestSaved);
				
		// Implementing PRG ! IMPORTANT !!! no duplicating saves to DB after page refresh.
		return "redirect:/requestForm/saved";
	}
	
	
	@GetMapping("/saved")
	public String savedRequest(Model theModel, RedirectAttributes redirectAttributes){
		
//		System.out.println(">>> DISPLAYING PAGE AFTER REQUEST HAS BEEN SAVED !!!!");
		
		if (theModel.containsAttribute("requestSaved")) {
			
			return "add-request-saved";			
			
		}else {
			return "redirect:/";
		}	
	}
	
	
	//----------------------------------------------------------------
	// ERORRS START
	
	// Displaying Add Request Error Page - REQUESTER DELETED
	@GetMapping("/requesterDeleted")
	public String requesterDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("requesterDeleted")) {
			
			return "add-request-saved-error-requester-deleted";			
			
		}else {
			return "redirect:/";
		}
	}
	
	
	// Displaying Add Request Error Page - TYPE DELETED
	@GetMapping("/typeDeleted")
	public String typeDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("typeDeleted")) {
			
			return "add-request-saved-error-type-deleted";			
			
		}else {
			return "redirect:/";
		}
	}
	
	
	// Displaying Add Request Error Page - MODEL DELETED
	@GetMapping("/modelDeleted")
	public String modelDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("modelDeleted")) {
			
			return "add-request-saved-error-model-deleted";			
			
		}else {
			return "redirect:/";
		}
	}
	
	
	// Displaying Add Request Error Page - INSTANCE DELETED
	@GetMapping("/instanceDeleted")
	public String instanceDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("instanceDeleted")) {
			
			return "add-request-saved-error-instance-deleted";			
			
		}else {
			return "redirect:/";
		}
	}
	
	// Displaying Add Request Error Page - DATE ERROR (request with newer date has been saved)
	@GetMapping("/dateError")
	public String dateError(Model theModel, RedirectAttributes redirectAttributes){
		
		if (theModel.containsAttribute("helperFormSaving")) {
			
			AddObjectForm helperFormReEdit = new AddObjectForm();
			
			theModel.addAttribute("helperForm", helperFormReEdit);
			
			return "add-request-saved-error-date";			
			
		}else {
			return "redirect:/";
		}		
	}
	
}

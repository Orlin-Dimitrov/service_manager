package com.luv2code.springdemo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luv2code.springdemo.entity.ObjectInstance;
import com.luv2code.springdemo.entity.ObjectModel;
import com.luv2code.springdemo.entity.ObjectType;
import com.luv2code.springdemo.entity.Requester;
import com.luv2code.springdemo.helperObjects.AddObjectInstance;
import com.luv2code.springdemo.helperObjects.AddObjectModel;
import com.luv2code.springdemo.helperObjects.AddObjectType;
import com.luv2code.springdemo.helperObjects.AddRequester;
import com.luv2code.springdemo.service.ObjectInstanceService;
import com.luv2code.springdemo.service.ObjectModelService;
import com.luv2code.springdemo.service.ObjectTypeService;
import com.luv2code.springdemo.service.RequesterService;

@Controller
@RequestMapping("/component")
public class AddComponentController {

	// need to inject our objectInstance services
	
	@Autowired
	private ObjectTypeService objectTypeService;
	
	@Autowired
	private ObjectModelService objectModelService;
	
	@Autowired
	private ObjectInstanceService objectInstanceService;
	
	@Autowired
	private RequesterService requesterService;


//	private static final Logger logger = LoggerFactory.getLogger(AddComponentController.class);

	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		
	}
	
	
	// Page to select Type/Model/Serial Number
	@GetMapping("/addItem")
	public String addItem() {		
		return "add-item-select";
	}
	
	//-----------------------------------------------------
	
	
	//  Adding object Type START
	
	// Page with Form for adding new object Type
	@GetMapping("/addType")
	public String addType(Model theModel) {
	
		if (theModel.containsAttribute("newObjectType") == false) {
		
			AddObjectType newObjectType = new AddObjectType();
			
			// adding helper object AddObjectType to the model. It has custom validation properties.
			theModel.addAttribute("newObjectType", newObjectType);
		}
		
		// Displaying add-item-type page		
		return "add-item-type";
	}
	
	
	// Returning to Edit the New AddObjectType
	@PostMapping("/addType")
	public String addTypeEdit(@ModelAttribute("newObjectType") AddObjectType newObjectType,
								Model theModel,
								RedirectAttributes redirectAttributes) {
		
		// Adding the object to the model so the input field will have a value.
		redirectAttributes.addFlashAttribute("newObjectType", newObjectType);
		
		return "redirect:/component/addType";
	}
	
	
	// Page to Review the New AddObjectType
	@PostMapping("/reviewType")
	public String reviewType(@Valid @ModelAttribute("newObjectType") AddObjectType newObjectType,
								BindingResult theBindingResult,										
								Model theModel,
								RedirectAttributes redirectAttributes) {
		
		if (theBindingResult.hasErrors()) {
			
		    // If errors, add flash attributes and redirect to /component/addType page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newObjectType", theBindingResult);
			redirectAttributes.addFlashAttribute("newObjectType", newObjectType);
			
			return "redirect:/component/addType";
		}
				
		// Adding the verified AddObjectType object again to the model for going back and editing the Type,		
        redirectAttributes.addFlashAttribute("newObjectType", newObjectType);
		
        return "redirect:/component/reviewType";	
	}
	
	
	// Page to Review the New AddObjectType
	@GetMapping("/reviewType")
	public String reviewTypeGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("newObjectType")) {
			
			// using AddObjectType(newObjectType) value for setting the new created AddObjectType(newObjectTypeForSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("newObjectTypeForSaving", new AddObjectType());
			
			return "add-item-type-review";
		}else {
			return "redirect:/component/addType";
		}	
	}
	
	
	// Saving the New Object Type
	@PostMapping("/saveType")
	public String saveType(@Valid @ModelAttribute("newObjectTypeForSaving") AddObjectType newObjectTypeForSaving,
							BindingResult theBindingResult,
							RedirectAttributes redirectAttributes) {
		
		// Validation is done again to check if someone else has not saved the same object type in the meantime.
		if (theBindingResult.hasErrors()) {
			
			String typeExistsError = "typeExistsError";
			
			redirectAttributes.addFlashAttribute("typeExistsError", typeExistsError);
			
			return "redirect:/component/typeExistsError";		
		}		
		
//		System.out.println(">>> SAVING ... NEW Object Type :" + newObjectTypeForSaving.getType());
		
		// Creating new ObjectType
		ObjectType newObjectType = new ObjectType();
		
		// Setting the new ObjectType type. It is previously checked and is unique.
		newObjectType.setType(newObjectTypeForSaving.getType());
		
		// Saving the new ObjectType
		objectTypeService.saveObjectType(newObjectType);
		
		String typeSaved = "typeSaved";
		
		redirectAttributes.addFlashAttribute("typeSaved", typeSaved);
		
		// Implementing PRG
		return "redirect:/component/typeSaved";
	}
	
	
	// Displaying confirmation page for saved new Object Type
	@GetMapping("/typeSaved")
	public String typeSaved(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("typeSaved")) {			
			return "add-item-type-saved";			
		}else {			
			return "redirect:/component/addItem";
		}	
	}
	
	
	// Displaying Object Type Saving Error Page - TYPE ALREADY EXISTS
	@GetMapping("/typeExistsError")
	public String typeSavedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("typeExistsError")) {			
			return "add-item-type-saved-error";			
		}else {
			return "redirect:/component/addItem";
		}
	}
	
	// Adding object Type END
	
	
	// -------------------------------------------------------------------
	
	
	//  Adding object Model START
	
	// Page with Form for adding new object Model
	@GetMapping("/addModel")
	public String addModel(Model theModel) {
	
		if (theModel.containsAttribute("newObjectModel") == false) {
		
			AddObjectModel newObjectModel = new AddObjectModel();
			
			// adding helper object AddObjectModel to the model. It has custom validation properties.
			theModel.addAttribute("newObjectModel", newObjectModel);			
		}

		// Displaying add-item-model page		
		return "add-item-model";
	}
	
	
	// Returning to Edit the New AddObjectModel
	@PostMapping("/addModel")
	public String addModelEdit(@Valid @ModelAttribute("newObjectModel") AddObjectModel newObjectModel,
								BindingResult theBindingResult,
								Model theModel,
								RedirectAttributes redirectAttributes) {
		
		// Validation is done again to check if  parent type has been deleted. Necessary for proper animation on JSP.
		if (theBindingResult.hasErrors()) {
			
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		        
		        // If parent Type deleted, set it to 0 to NOT display animation on JSP
		        if(error.getCode().equals("ObjectTypeNotDeleted")) {
		        	newObjectModel.setTypeId(0);
		        }
		    }

		    // If errors, add flash attributes and redirect to /component/addModel page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newObjectModel", theBindingResult);
			redirectAttributes.addFlashAttribute("newObjectModel", newObjectModel);
			
			return "redirect:/component/addModel";		    
		}
		
		// Adding the object to the model so the input field will have a value.
		redirectAttributes.addFlashAttribute("newObjectModel", newObjectModel);
		
		return "redirect:/component/addModel";
		
	}
	
	
	// Page to Review the New AddObjectModel
	@PostMapping("/reviewModel")
	public String reviewModel(@Valid @ModelAttribute("newObjectModel") AddObjectModel newObjectModel,
								BindingResult theBindingResult,										
								Model theModel,
								RedirectAttributes redirectAttributes) {
		
		if (theBindingResult.hasErrors()) {
			
			// TESTING
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		        
		        // If parent Type deleted, set it to 0 to NOT display animation on JSP
		        if(error.getCode().equals("ObjectTypeNotDeleted")) {
		        	newObjectModel.setTypeId(0);
		        }
		    }
		    
		    // If errors, add flash attributes and redirect to /component/addModel page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newObjectModel", theBindingResult);
			redirectAttributes.addFlashAttribute("newObjectModel", newObjectModel);
			
			return "redirect:/component/addModel";	
		}
		
		//Retrieving the selected object Type from DB for it's name and setting it to the AddObjectModel to be displayed on the page
		newObjectModel.setTypeName(objectTypeService.getObjectType(newObjectModel.getTypeId()).getType());
		
		// Adding the verified AddObjectModel object again to the model for going back and editing the Model,		
        redirectAttributes.addFlashAttribute("newObjectModel", newObjectModel);
		
        return "redirect:/component/reviewModel";
        
	}
	
	
	// Page to Review the New AddObjectModel
	@GetMapping("/reviewModel")
	public String reviewModelGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("newObjectModel")) {
			
			// using AddObjectModel(newObjectModel) value for setting the new created AddObjectModel(newObjectModelForSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("newObjectModelForSaving", new AddObjectModel());
			
			return "add-item-model-review";
		}else {
			return "redirect:/component/addModel";
		}
	}
	

	
	// Saving the New Object Model
	@PostMapping("/saveModel")
	public String saveModel(@Valid @ModelAttribute("newObjectModelForSaving") AddObjectModel newObjectModelForSaving,
							BindingResult theBindingResult,
							RedirectAttributes redirectAttributes) {
		
		// Validation is done again to check if someone else has not saved the same object model in the meantime or parent type has been deleted.
		if (theBindingResult.hasErrors()) {
			
			String currentError = null;
						
			// The only possible field error is for @ObjectTypeNotDeleted
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		        System.out.println("CODE: " + error.getCode());
//		        System.out.println("FIELD: " + error.getField());
		        
		        currentError = error.getCode();
		    }		

		    if(currentError != null && currentError.equals("ObjectTypeNotDeleted")) {
		    
				String parentTypeDeletedError = "parentTypeDeletedError";
				
				redirectAttributes.addFlashAttribute("parentTypeDeletedError", parentTypeDeletedError);
				
				return "redirect:/component/parentTypeDeletedError";	
		    }
						
			// The only possible object error is for @ObjectModelExists
			List<ObjectError> objectError = theBindingResult.getGlobalErrors();
			
		    for (ObjectError error : objectError) {
//		        System.out.println ("OBJECT ERROR: " + error.getObjectName() + " - " + error.getDefaultMessage());
//		        System.out.println("CODE: " + error.getCode());
		        		        
		        currentError = error.getCode();
		    }	
		    
		    if(currentError != null && currentError.equals("ObjectModelExists")) {
		    	
				String modelExistsError = "modelExistsError";
				
				redirectAttributes.addFlashAttribute("modelExistsError", modelExistsError);
				
				return "redirect:/component/modelExistsError";	
		    }
		    
		    // The code should not come to here. Just in case return default error page
		    return "default-error";
		}		
		
//		System.out.println(">>> SAVING ... NEW Object Model :" + newObjectModelForSaving.getModel());
		
		// Creating new ObjectModel
		ObjectModel newObjectModel = new ObjectModel();
		
		// Setting the new ObjectModel model. It is previously checked and is unique.
		newObjectModel.setModel(newObjectModelForSaving.getModel());
		
		// Setting the new ObjectModel parent type. 
		newObjectModel.setObjectType(objectTypeService.getObjectType(newObjectModelForSaving.getTypeId()));
		
		// Saving the new ObjectModel
		objectModelService.saveObjectModel(newObjectModel);
		
		String modelSaved = "modelSaved";
		
		redirectAttributes.addFlashAttribute("modelSaved", modelSaved);
		
		// Implementing PRG
		return "redirect:/component/modelSaved";
	}
	
	// Displaying confirmation page for saved new Object Model
	@GetMapping("/modelSaved")
	public String modelSaved(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("modelSaved")) {			
			return "add-item-model-saved";			
		}else {			
			return "redirect:/component/addItem";
		}			
	}
	
	
	// Displaying Object Model Saving Error Page - PARENT TYPE DELETED
	@GetMapping("/parentTypeDeletedError")
	public String parentTypeDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("parentTypeDeletedError")) {	
			
			return "add-item-model-saved-error-type-deleted";			
		}else {
			return "redirect:/component/addItem";
		}
	}
	
	// Displaying Object Model Saving Error Page - MODEL ALREADY EXISTS
	@GetMapping("/modelExistsError")
	public String modelExistsError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("modelExistsError")) {	
			
			return "add-item-model-saved-error-exists";			
		}else {
			return "redirect:/component/addItem";
		}
	}
	
	
	// Adding object Model END
	
	
	// -------------------------------------------------------------------
	
	
	//  Adding object Instance START
	
	// Page with Form for adding new object Instance
	@GetMapping("/addInstance")
	public String addInstance(Model theModel, RedirectAttributes redirectAttributes) {
	
		if (theModel.containsAttribute("newObjectInstance") == false) {
			
			AddObjectInstance newObjectInstance = new AddObjectInstance();
			
			// adding helper object AddObjectInstance to the model. It has custom validation properties.
			theModel.addAttribute("newObjectInstance", newObjectInstance);			
		}
		
		// Displaying add-item-instance page
		
		// TESTING DB-ERROR PAGE
		
		String dbErrorFlashAttribute = "dbError";
		redirectAttributes.addFlashAttribute("db-error", dbErrorFlashAttribute);
		
		return "add-item-instance";
	}
	
	
	// Returning to Edit the New AddObjectInstance
	@PostMapping("/addInstance")
	public String addInstanceEdit(@Valid @ModelAttribute("newObjectInstance") AddObjectInstance newObjectInstance,
									BindingResult theBindingResult,
									Model theModel,
									RedirectAttributes redirectAttributes) {
		
		// Validation is done again to check if  parent model or type has been deleted. Necessary for proper animation on JSP.
		if (theBindingResult.hasErrors()) {

		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		        
		        // If parent Type deleted, set it to 0 to NOT display animation on JSP		
		        if(error.getCode().equals("ObjectTypeNotDeleted")) {
		        	newObjectInstance.setTypeId(0);
		        }
		        // If parent Model deleted, set it to 0 to NOT display animation on JSP		
		        if(error.getCode().equals("ObjectModelNotDeleted")) {
		        	newObjectInstance.setModelId(0);
		        }
		    }
		    
		    // If errors, add flash attributes and redirect to /component/addInstance page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newObjectInstance", theBindingResult);
			redirectAttributes.addFlashAttribute("newObjectInstance", newObjectInstance);
		    
		    // If errors, display addInstance page
			return "redirect:/component/addInstance";
		}
		
		// Adding the object to the model so the input field will have a value.
		redirectAttributes.addFlashAttribute("newObjectInstance", newObjectInstance);
		
		return "redirect:/component/addInstance";
	}
	
	
	// Page to Review the New AddObjectInstance
	@PostMapping("/reviewInstance")
	public String reviewInstance(@Valid @ModelAttribute("newObjectInstance") AddObjectInstance newObjectInstance,
									BindingResult theBindingResult,										
									Model theModel,
									RedirectAttributes redirectAttributes) {
		
		if (theBindingResult.hasErrors()) {
			
			// TESTING
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		        
		        // If parent Type deleted, set it to 0 to NOT display animation on JSP		TEST TEST !!!!
		        if(error.getCode().equals("ObjectTypeNotDeleted")) {
		        	newObjectInstance.setTypeId(0);
		        }
		        // If parent Model deleted, set it to 0 to NOT display animation on JSP		TEST TEST !!!!
		        if(error.getCode().equals("ObjectModelNotDeleted")) {
		        	newObjectInstance.setModelId(0);
		        }
		    }
		    
		    // If errors, add flash attributes and redirect to /component/addInstance page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newObjectInstance", theBindingResult);
			redirectAttributes.addFlashAttribute("newObjectInstance", newObjectInstance);
		    
		    // If errors, display addInstance page
			return "redirect:/component/addInstance";
		}
		
		//Retrieving the selected object Model from DB for it's name and setting it to the AddObjectInstance to be displayed on the page
		newObjectInstance.setModelName(objectModelService.getObjectModel(newObjectInstance.getModelId()).getModel());
		
		//Retrieving the selected object Type from DB for it's name and setting it to the AddObjectInstance to be displayed on the page
		newObjectInstance.setTypeName(objectModelService.getObjectModel(newObjectInstance.getModelId()).getObjectType().getType());
		
		// Adding the verified AddObjectInstance object again to the model for going back and editing the Instance,		
        redirectAttributes.addFlashAttribute("newObjectInstance", newObjectInstance);
		
		return "redirect:/component/reviewInstance";
	}
	

	// Page to Review the New AddObjectInstance
	@GetMapping("/reviewInstance")
	public String reviewInstanceGet(Model theModel,	RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("newObjectInstance")) {
			
			// using AddObjectInstance(newObjectInstance) value for setting the new created AddObjectInstance(newObjectInstanceSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("newObjectInstanceForSaving", new AddObjectInstance());
			
			return "add-item-instance-review";
		}else {
			return "redirect:/component/addInstance";
		}	
	}
		
		
	// Saving the New Object Instance
	@PostMapping("/saveInstance")
	public String saveInstance(@Valid @ModelAttribute("newObjectInstanceForSaving") AddObjectInstance newObjectInstanceForSaving,
								BindingResult theBindingResult,
								RedirectAttributes redirectAttributes) {
		
		// Validation is done again to check if someone else has not saved the same object instance in the meantime.
		if (theBindingResult.hasErrors()) {
			
			ArrayList<String> errorList = new ArrayList<String>();
			
			// The possible field errors @ObjectTypeNotDeleted, @ObjectModelNotDeleted
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		        System.out.println("CODE: " + error.getCode());
//		        System.out.println("FIELD: " + error.getField());
		        
		        errorList.add(error.getCode());
		    }
		  		    
		    // Object Model Deleted Error and Object Type Deleted Error
		    if (errorList.contains("ObjectModelNotDeleted")) {
		    	
				String parentModelDeletedError = "parentModelDeletedError";
				
				redirectAttributes.addFlashAttribute("parentModelDeletedError", parentModelDeletedError);
		    		    	
				return "redirect:/component/parentModelDeletedError";
		    }
			
		 // The only possible object error is for @ObjectInstanceExists
		    List<ObjectError> objectError = theBindingResult.getGlobalErrors();
		    
		 	String objectErrorInstanceExists = null;
		    
		    for (ObjectError error : objectError) {
//		    	System.out.println ("OBJECT ERROR: " + error.getObjectName() + " - " + error.getDefaultMessage());
//		    	System.out.println("CODE: " + error.getCode());
		 		        		        
		    	objectErrorInstanceExists = error.getCode();
		    	}	
		 		    
		    if(objectErrorInstanceExists != null && objectErrorInstanceExists.equals("ObjectInstanceExists")) {
		    	
				String instanceExistsError = "instanceExistsError";
				
				redirectAttributes.addFlashAttribute("instanceExistsError", instanceExistsError);
				
				return "redirect:/component/instanceExistsError";	
		    }
		    
		    // This part of the code should not be reached, just in case return default error page
		    return "default-error";				
		}		
		
//		System.out.println(">>> SAVING ... NEW Object Instance :" + newObjectInstanceForSaving.getInstance());
		
		// Creating new ObjectInstance
		ObjectInstance newObjectInstance = new ObjectInstance();
		
		// Setting the new ObjectInstance serial number. It is previously checked and is unique.
		newObjectInstance.setSerialNumber(newObjectInstanceForSaving.getInstance());
		
		// Setting the new ObjectInstance parent model. 
		newObjectInstance.setObjectModel(objectModelService.getObjectModel(newObjectInstanceForSaving.getModelId()));
		
		// Saving the new ObjectInstance
		objectInstanceService.saveObjectInstance(newObjectInstance);
		
		String instanceSaved = "instanceSaved";
		
		redirectAttributes.addFlashAttribute("instanceSaved", instanceSaved);
				
		// Implementing PRG
		return "redirect:/component/instanceSaved";
	}
	
	
	// Displaying confirmation page for saved new Object Instance
	@GetMapping("/instanceSaved")
	public String instanceSaved(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("instanceSaved")) {	
			
			return "add-item-instance-saved";	
			
		}else {			
			return "redirect:/component/addItem";
		}
	}
		
	
	// Displaying Object Instance Saving Error Page - PARENT MODEL DELETED
	@GetMapping("/parentModelDeletedError")
	public String parentModelDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("parentModelDeletedError")) {	
			
			return "add-item-instance-saved-error-model-deleted";
			
		}else {
			return "redirect:/component/addItem";
		}
	}
	
	
	// Displaying Object Instance Saving Error Page - INSTANCE ALREADY EXISTS
	@GetMapping("/instanceExistsError")
	public String instanceExistsError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("instanceExistsError")) {	
			
			return "add-item-instance-saved-error-exists";
			
		}else {
			return "redirect:/component/addItem";
		}
	}
	
	
	// Adding object Instance END

	
	// -------------------------------------------------------------------
	
	
	//  Adding Requester START
	
	// Page with Form for adding new Requester
	@GetMapping("/addRequester")
	public String addRequester(Model theModel) {
	
		if (theModel.containsAttribute("newRequester") == false) {
			
			AddRequester newRequester = new AddRequester();
			
			// adding helper object AddRequester to the model. It has custom validation properties.
			theModel.addAttribute("newRequester", newRequester);			
		}
	
		// Displaying add-requester page		
		return "add-requester";
	}
	
	
	// Returning to Edit the New AddRequester
	@PostMapping("/addRequester")
	public String addRequesterEdit(@ModelAttribute("newRequester") AddRequester newRequester,
									Model theModel,
									RedirectAttributes redirectAttributes) {
		
		// Adding the object to the model so the input field will have a value.
		redirectAttributes.addFlashAttribute("newRequester", newRequester);
		
		return "redirect:/component/addRequester";
	}
	
	
	// Page to Review the New AddRequester
	@PostMapping("/reviewRequester")
	public String reviewRequester(@Valid @ModelAttribute("newRequester") AddRequester newRequester,
									BindingResult theBindingResult,										
									Model theModel,
									RedirectAttributes redirectAttributes) {
		
		if (theBindingResult.hasErrors()) {

		    // If errors, add flash attributes and redirect to /component/addRequester page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newRequester", theBindingResult);
			redirectAttributes.addFlashAttribute("newRequester", newRequester);
			
			return "redirect:/component/addRequester";
		}
		
		// Adding the verified AddRequester object again to the model for going back and editing the Requester,		
        redirectAttributes.addFlashAttribute("newRequester", newRequester);
		
        return "redirect:/component/reviewRequester";
	}
	
	
	// Page to Review the New AddRequester
	@GetMapping("/reviewRequester")
	public String reviewRequesterGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("newRequester")) {
			
			// using AddRequester(newRequester) value for setting the new created AddRequester(newRequesterForSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("newRequesterForSaving", new AddRequester());
			
			return "add-requester-review";
		}else {
			return "redirect:/component/addRequester";
		}	
	}

	
	// Saving the New Requester
	@PostMapping("/saveRequester")
	public String saveRequester(@Valid @ModelAttribute("newRequesterForSaving") AddRequester newRequesterForSaving,
								BindingResult theBindingResult,
								RedirectAttributes redirectAttributes) {
		
		// Validation is done again to check if someone else has not saved the same Requester in the meantime.
		if (theBindingResult.hasErrors()) {

			String requesterSavedError = "requesterSavedError";
			
			redirectAttributes.addFlashAttribute("requesterSavedError", requesterSavedError);
			
			return "redirect:/component/requesterSavedError";
		}		
		
//		System.out.println(">>> SAVING ... NEW Requester :" + newRequesterForSaving.getName());
		
		// Creating new Requester Object
		Requester newRequester = new Requester();
		
		// Setting the new Requester name. It is previously checked and is unique.
		newRequester.setName(newRequesterForSaving.getName());
		
		// Saving the new Requester
		requesterService.saveRequester(newRequester);
		
		String requesterSaved = "requesterSaved";
		
		redirectAttributes.addFlashAttribute("requesterSaved", requesterSaved);
		
		// Implementing PRG
		return "redirect:/component/requesterSaved";
	}
	
	
	// Displaying confirmation page for saved new Requester
	@GetMapping("/requesterSaved")
	public String requesterSaved(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("requesterSaved")) {			
			return "add-requester-saved";			
		}else {			
			return "redirect:/component/addRequester";
		}
	}
	
	
	// Displaying Requester Saving Error Page
	@GetMapping("/requesterSavedError")
	public String requesterSavedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("requesterSavedError")) {			
			return "add-requester-saved-error";			
		}else {
			return "redirect:/component/addRequester";
		}
	}
	
	// Adding Requester END

	
}

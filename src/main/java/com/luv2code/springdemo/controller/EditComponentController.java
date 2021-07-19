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
import com.luv2code.springdemo.helperObjects.AdminEditObjectInstance;
import com.luv2code.springdemo.helperObjects.AdminEditObjectModel;
import com.luv2code.springdemo.helperObjects.AdminEditObjectType;
import com.luv2code.springdemo.helperObjects.AdminEditRequester;
import com.luv2code.springdemo.service.ObjectInstanceService;
import com.luv2code.springdemo.service.ObjectModelService;
import com.luv2code.springdemo.service.ObjectTypeService;
import com.luv2code.springdemo.service.RequesterService;

@Controller
@RequestMapping("/componentEdit")
public class EditComponentController {

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
	private LinkedHashMap<Integer, String> requestersMap;*/
	
//	private static final Logger logger = LoggerFactory.getLogger(AddComponentController.class);
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		
	}
	
	
	// Page to select Type/Model/Serial Number for Editing
	@GetMapping("/editItem")
	public String editItem() {		
		return "edit-item-select";
	}
	
	//-------------------------------------------------
	
	
	//  Edit object Type START

	
	// Page to select for editing existing object Type
	@GetMapping("/editType")
	public String editType(Model theModel) {
	
		if (theModel.containsAttribute("editObjectType") == false) {
					
			AdminEditObjectType editObjectType = new AdminEditObjectType();
			
			// adding helper object AdminEditObjectType to the model. It has custom validation properties.
			theModel.addAttribute("editObjectType", editObjectType);		
		}
		
		// Displaying edit-item-type page		
		return "edit-item-type";
	}	
	
	
	// Returning to Edit object Type after Review
	@PostMapping("/editType")
	public String editTypeAfterReview(@Valid @ModelAttribute("editObjectType") AdminEditObjectType editObjectType,
										BindingResult theBindingResult,
										Model theModel, 
										RedirectAttributes redirectAttributes) {
	
		// Validation is done again to check if type has been deleted or type with the same name has been saved.
		if (theBindingResult.hasErrors()) {

		    // If errors, add flash attributes and redirect to /componentEdit/editType page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editObjectType", theBindingResult);
			redirectAttributes.addFlashAttribute("editObjectType", editObjectType);
			
			return "redirect:/componentEdit/editType";		    
		}
		
		// adding helper object AdminEditObjectType to the model. It has custom validation properties.
		redirectAttributes.addFlashAttribute("editObjectType", editObjectType);
		
		// Redirecting to edit-item-type page		
		return "redirect:/componentEdit/editType";
	}
	
	
	// Page to Review the New AdminEditObjectType
	@PostMapping("/reviewEditType")
	public String reviewEditType(@Valid @ModelAttribute("editObjectType") AdminEditObjectType editObjectType,
									BindingResult theBindingResult,										
									Model theModel,
									RedirectAttributes redirectAttributes) {
		
		if (theBindingResult.hasErrors()) {
			
//			// TESTING
//		    List<FieldError> errors = theBindingResult.getFieldErrors();
//		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		    }
			
		    // If errors, add flash attributes and redirect to /componentEdit/editType page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editObjectType", theBindingResult);
			redirectAttributes.addFlashAttribute("editObjectType", editObjectType);
			
		    // If errors, display redirect to editType page
			return "redirect:/componentEdit/editType";
		}
		
		// Retrieving the old Type name form DB and setting it to be displayed on the review page
		String oldTypeName = objectTypeService.getObjectType(editObjectType.getTypeId()).getType();
		editObjectType.setOldType(oldTypeName);
		
		// Adding the verified AdminEditObjectType object again to the model for going back and editing the Type,		
        redirectAttributes.addFlashAttribute("editObjectType", editObjectType);
		
        return "redirect:/componentEdit/reviewEditType";
	}

	
	// Page to Review the New AdminEditObjectType
	@GetMapping("/reviewEditType")
	public String reviewEditTypeGet(Model theModel,	RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("editObjectType")) {
			
			// using AdminEditObjectType(editObjectType) value for setting the new created AdminEditObjectType(editObjectTypeForSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("editObjectTypeForSaving", new AdminEditObjectType());
			
			return "edit-item-type-review";
			
		}else {			
			return "redirect:/componentEdit/editType";
		}		
	}
		
	
	// Saving the edited Object Type
	@PostMapping("/saveEditType")
	public String saveEditType(@Valid @ModelAttribute("editObjectTypeForSaving") AdminEditObjectType editObjectTypeForSaving,
								BindingResult theBindingResult,
								RedirectAttributes redirectAttributes) {		
		
		// Validation is done again to check is someone has not saved object with the same name, or type has been deleted!
		if (theBindingResult.hasErrors()) {
			
			String currentError = null;
			
			// TESTING
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		        System.out.println("CODE: " + error.getCode());
//		        System.out.println("FIELD: " + error.getField());
		        
		        currentError = error.getCode();
		    }
		    
		    if(currentError.equals("ObjectTypeNotDeleted")) {
		    	
				String typeDeletedError = "typeDeletedError";
				
				redirectAttributes.addFlashAttribute("typeDeletedError", typeDeletedError);
				
				return "redirect:/componentEdit/typeDeletedError";
		    	
		    }else if (currentError.equals("ObjectTypeExists")) {
		    	
				String typeExistsError = "typeExistsError";
				
				redirectAttributes.addFlashAttribute("typeExistsError", typeExistsError);
				
				return "redirect:/componentEdit/typeExistsError";
		    	
		    }else {
		    	//Not used, but just in case.!!!
		    	return "default-error";
		    }		    
		}
		
//		System.out.println(">>> SAVING ... Edited Object Type :" + editObjectTypeForSaving.getType());
		
		// Retrieving the ObjectType for update
		ObjectType editedObjectType = objectTypeService.getObjectType(editObjectTypeForSaving.getTypeId());
				
		editedObjectType.setType(editObjectTypeForSaving.getType());
		
		// Saving the edited ObjectType
		objectTypeService.saveObjectType(editedObjectType);
		
		String typeSaved = "typeSaved";
		
		redirectAttributes.addFlashAttribute("typeSaved", typeSaved);
		
		// Implementing PRG
		return "redirect:/componentEdit/editTypeSaved";
	}
	
	// Displaying confirmation page for saved updated Object Type
	@GetMapping("/editTypeSaved")
	public String editTypeSaved(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("typeSaved")) {
			
			return "edit-item-type-saved";		
			
		}else {			
			return "redirect:/componentEdit/editItem";
		}
	}
		
	
	// Displaying Object Type Saving Error Page - TYPE ALREADY EXISTS
	@GetMapping("/typeExistsError")
	public String typeSavedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("typeExistsError")) {
			
			return "edit-item-type-saved-error-exists";		
			
		}else {
			return "redirect:/componentEdit/editItem";
		}
	}
	
	
	// Displaying Object Type Saving Error Page - TYPE DELETED
	@GetMapping("/typeDeletedError")
	public String typeDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("typeDeletedError")) {
			
			return "edit-item-type-saved-error-type-deleted";
			
		}else {
			return "redirect:/componentEdit/editItem";
		}
	}
		
	
	// Edit object Type END
	
	
	// -------------------------------------------------------------------
	
	
	//  Edit object Model START
	
	// Page with Form for editing object Model
	@GetMapping("/editModel")
	public String editModel(Model theModel) {
	
		if (theModel.containsAttribute("editObjectModel") == false) {
			
			AdminEditObjectModel editObjectModel = new AdminEditObjectModel();
			
			// adding helper object AddObjectModel to the model. It has custom validation properties.
			theModel.addAttribute("editObjectModel", editObjectModel);			
		}

		// Displaying edit-item-model page		
		return "edit-item-model";
	}	
	
	
	// Page with Form for editing object Model after Review
	@PostMapping("/editModel")
	public String editModelAfterReview(@Valid @ModelAttribute("editObjectModel") AdminEditObjectModel editObjectModel,
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
		        	editObjectModel.setTypeId(0);
		        }
		    }

		    // If errors, add flash attributes and redirect to /componentEdit/editModel page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editObjectModel", theBindingResult);
			redirectAttributes.addFlashAttribute("editObjectModel", editObjectModel);
			
			return "redirect:/componentEdit/editModel";
		}
		
		// Adding the object to the model so the input field will have a value.
		redirectAttributes.addFlashAttribute("editObjectModel", editObjectModel);
		
		// Displaying edit-item-model page		
		return "redirect:/componentEdit/editModel";
	}
	
	
	// Page to Review the AdminEditObjectModel
	@PostMapping("/reviewEditModel")
	public String reviewEditModel(@Valid @ModelAttribute("editObjectModel") AdminEditObjectModel editObjectModel,
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
		        	editObjectModel.setTypeId(0);
		        }
		    }
		    
		    // If errors, add flash attributes and redirect to /component/addModel page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editObjectModel", theBindingResult);
			redirectAttributes.addFlashAttribute("editObjectModel", editObjectModel);
			
			return "redirect:/componentEdit/editModel";	
		}
		
		// Retrieving the old object Model from DB
		ObjectModel oldObjectModel = objectModelService.getObjectModel(editObjectModel.getModelId());
		
		// setting the Type and Old Model Names to be displayed in the review page
		editObjectModel.setTypeName(oldObjectModel.getObjectType().getType());
		editObjectModel.setOldModel(oldObjectModel.getModel());
		
		// Adding the verified AdminEditObjectModel object again to the model for going back and editing the Model,		
        redirectAttributes.addFlashAttribute("editObjectModel", editObjectModel);
		
        return "redirect:/componentEdit/reviewEditModel";	
	}
	

	// Page to Review the AdminEditObjectModel
	@GetMapping("/reviewEditModel")
	public String reviewEditModelGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("editObjectModel")) {
			
			// using AddObjectModel(editObjectModel) value for setting the new created AddObjectModel(editObjectModelForSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("editObjectModelForSaving", new AdminEditObjectModel());
			
			return "edit-item-model-review";
			
		}else {
			return "redirect:/componentEdit/editModel";
		}
	}
	
	
	// Saving the Edited Object Model
	@PostMapping("/saveEditModel")
	public String saveEditModel(@Valid @ModelAttribute("editObjectModelForSaving") AdminEditObjectModel editObjectModelForSaving,
								BindingResult theBindingResult,
								RedirectAttributes redirectAttributes) {		
		
		// Validation is done again to check is someone has not saved object with the same name or deleted object
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
		  		    
		    // Object Model Deleted Error
		    if(errorList.size() == 1 && errorList.get(0).equals("ObjectModelNotDeleted")) {
		    	
				String modelDeletedError = "modelDeletedError";
				
				redirectAttributes.addFlashAttribute("modelDeletedError", modelDeletedError);
				
				return "redirect:/componentEdit/modelDeletedError";		    	
		    }
		    
		    // Object Model Deleted Error and Object Type Deleted Error
		    if (errorList.size() > 1 && errorList.contains("ObjectTypeNotDeleted")) {
		    	
				String parentTypeDeletedError = "parentTypeDeletedError";
				
				redirectAttributes.addFlashAttribute("parentTypeDeletedError", parentTypeDeletedError);
				
				return "redirect:/componentEdit/parentTypeDeletedError";
		    }
			
		 // The only possible object error is for @ObjectModelExists
		    List<ObjectError> objectError = theBindingResult.getGlobalErrors();
		 	String objectErrorModelExists = null;
		    
		    for (ObjectError error : objectError) {
//		    	System.out.println ("OBJECT ERROR: " + error.getObjectName() + " - " + error.getDefaultMessage());
//		    	System.out.println("CODE: " + error.getCode());
		 		        		        
		    	objectErrorModelExists = error.getCode();
		    	}	
		 		    
		    if(objectErrorModelExists != null && objectErrorModelExists.equals("ObjectModelExists")) {
		    	
				String modelExistsError = "modelExistsError";
				
				redirectAttributes.addFlashAttribute("modelExistsError", modelExistsError);
				
				return "redirect:/componentEdit/modelExistsError";	
		    }
		    
		    // This part of the code should not be reached, just in case return default error page
		    return "default-error";
		}
		
//		System.out.println(">>> SAVING ... Edited Object Model :" + editObjectModelForSaving.getModel());

		// Retrieving the ObjectModel for update
		ObjectModel editedObjectModel = objectModelService.getObjectModel(editObjectModelForSaving.getModelId());		
		
		editedObjectModel.setModel(editObjectModelForSaving.getModel());
		
		// Saving the edited ObjectModel
		objectModelService.saveObjectModel(editedObjectModel);
		
		String modelSaved = "modelSaved";
		
		redirectAttributes.addFlashAttribute("modelSaved", modelSaved);
							
		// Implementing PRG
		return "redirect:/componentEdit/editModelSaved";
	}
	
	// Displaying confirmation page for saved updated Object Model
	@GetMapping("/editModelSaved")
	public String editModelSaved(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("modelSaved")) {	
			
			return "edit-item-model-saved";	
			
		}else {			
			return "redirect:/componentEdit/editItem";
		}	
	}
	
	
	// Displaying Object Model Saving Error Page - MODEL DELETED
	@GetMapping("/modelDeletedError")
	public String modelDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("modelDeletedError")) {
			
			return "edit-item-model-saved-error-deleted";
			
		}else {
			return "redirect:/componentEdit/editItem";
		}
	}
	
	
	// Displaying Object Model Saving Error Page - PARENT TYPE DELETED
	@GetMapping("/parentTypeDeletedError")
	public String parentTypeDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("parentTypeDeletedError")) {	
			
			return "edit-item-model-saved-error-type-deleted";	
			
		}else {
			return "redirect:/componentEdit/editItem";
		}
	}
	
	
	// Displaying Object MOdel Saving Error Page - MODEL ALREADY EXISTS
	@GetMapping("/modelExistsError")
	public String modelSavedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("modelExistsError")) {
			
			return "edit-item-model-saved-error-exists";		
			
		}else {
			return "redirect:/componentEdit/editItem";
		}
	}
	
	
	// Edit object Model END
	
	
	// -------------------------------------------------------------------
	
	
	//  Edit object Instance START
	
	// Page with Form for edit existing object Instance
	@GetMapping("/editInstance")
	public String editInstance(Model theModel) {
	
		if (theModel.containsAttribute("editObjectInstance") == false) {
			
			AdminEditObjectInstance editObjectInstance = new AdminEditObjectInstance();
			
			// adding helper object AdminEditObjectInstance to the model. It has custom validation properties.
			theModel.addAttribute("editObjectInstance", editObjectInstance);
			
		}

		// Displaying add-item-instance page		
		return "edit-item-instance";
	}
	
	
	// Returning to Edit the New AdminEditObjectInstance
	@PostMapping("/editInstance")
	public String editInstanceAfterReview(@Valid @ModelAttribute("editObjectInstance") AdminEditObjectInstance editObjectInstance,
											BindingResult theBindingResult,
											Model theModel,
											RedirectAttributes redirectAttributes) {
		
		// Validation is done again to check if instance exists(or parent Model or Type has been deleted). Necessary for proper animation on JSP.
		if (theBindingResult.hasErrors()) {
			
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		        
		        // If parent Type deleted, set it to 0 to NOT display animation on JSP
		        if(error.getCode().equals("ObjectTypeNotDeleted")) {
		        	editObjectInstance.setTypeId(0);
		        }
		        // If parent Model deleted, set it to 0 to NOT display animation on JSP		TEST TEST !!!!
		        if(error.getCode().equals("ObjectModelNotDeleted")) {
		        	editObjectInstance.setModelId(0);
		        }
		    }
		    
		    // If errors, add flash attributes and redirect to /componentEdit/editInstance page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editObjectInstance", theBindingResult);
			redirectAttributes.addFlashAttribute("editObjectInstance", editObjectInstance);
		    
		    // If errors, display editInstance page
			return "redirect:/componentEdit/editInstance";
		}
		
		// Adding the object to the model so the input field will have a value.
		redirectAttributes.addFlashAttribute("editObjectInstance", editObjectInstance);
		
		return "redirect:/componentEdit/editInstance";
	}
	
	
	// Page to Review the New AdminEditObjectInstance
	@PostMapping("/reviewEditInstance")
	public String reviewEditInstance(@Valid @ModelAttribute("editObjectInstance") AdminEditObjectInstance editObjectInstance,
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
		        	editObjectInstance.setTypeId(0);
		        }
		        // If parent Model deleted, set it to 0 to NOT display animation on JSP	
		        if(error.getCode().equals("ObjectModelNotDeleted")) {
		        	editObjectInstance.setModelId(0);
		        }
		    }
		    
		    // If errors, add flash attributes and redirect to /componentEdit/editInstance page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editObjectInstance", theBindingResult);
			redirectAttributes.addFlashAttribute("editObjectInstance", editObjectInstance);
		    
		    // If errors, display editInstance page
			return "redirect:/componentEdit/editInstance";
		}
		
		// Retrieving the old object Instance from DB
		ObjectInstance oldObjectInstance = objectInstanceService.getObjectInstanceById(editObjectInstance.getInstanceId());
		

		// setting the Type, Model and Old Serial Number to be displayed in the review page		
		editObjectInstance.setTypeName(oldObjectInstance.getObjectModel().getObjectType().getType());
		
		editObjectInstance.setModelName(oldObjectInstance.getObjectModel().getModel());
		
		editObjectInstance.setOldInstance(oldObjectInstance.getSerialNumber());
		
		
		// Adding the verified AdminEditObjectInstance object again to the model for going back and editing the Instance,		
        redirectAttributes.addFlashAttribute("editObjectInstance", editObjectInstance);
		
		return "redirect:/componentEdit/reviewEditInstance";
	}
	

	// Page to Review the New AdminEditObjectInstance
	@GetMapping("/reviewEditInstance")
	public String reviewEditInstanceGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("editObjectInstance")) {
			
			// using AdminEditObjectInstance(editObjectInstance) value for setting the new created AdminEditObjectInstance(editObjectInstanceForSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("editObjectInstanceForSaving", new AdminEditObjectInstance());
			
			return "edit-item-instance-review";
		}else {
			return "redirect:/componentEdit/editInstance";
		}
	}
	
	
	// Saving the Edited Object Instance
	@PostMapping("/saveEditInstance")
	public String saveEditInstance(@Valid @ModelAttribute("editObjectInstanceForSaving") AdminEditObjectInstance editObjectInstanceForSaving,
									BindingResult theBindingResult,
									RedirectAttributes redirectAttributes) {
			
		// Validation is done again to check is someone has not saved object with the same name, or deleted object
		if (theBindingResult.hasErrors()) {
			
			ArrayList<String> errorList = new ArrayList<String>();
			
			// The possible field errors @ObjectTypeNotDeleted, @ObjectModelNotDeleted, @ObjectInstanceNotDeleted
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		        System.out.println("CODE: " + error.getCode());
//		        System.out.println("FIELD: " + error.getField());
		        
		        errorList.add(error.getCode());
		    }
		  		    
		    // Object Instance Deleted Error
		    if(errorList.size() == 1 && errorList.get(0).equals("ObjectInstanceNotDeleted")) {
		    	
				String instanceDeletedError = "instanceDeletedError";
				
				redirectAttributes.addFlashAttribute("instanceDeletedError", instanceDeletedError);
				
				return "redirect:/componentEdit/instanceDeletedError";			    	
		    }
		    
		    // Object Model Deleted Error and Object Type Deleted Error
		    if (errorList.size() > 1 && errorList.contains("ObjectModelNotDeleted")) {
		    	
				String parentModelDeletedError = "parentModelDeletedError";
				
				redirectAttributes.addFlashAttribute("parentModelDeletedError", parentModelDeletedError);
		    		    	
				return "redirect:/componentEdit/parentModelDeletedError";
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
				
				return "redirect:/componentEdit/instanceExistsError";
		    }
		    
		    // This part of the code should not be reached, just in case return default error page
		    return "default-error";			

		}
		
//		System.out.println(">>> SAVING ... Edited Object Instance :" + editObjectInstanceForSaving.getInstance());
		
		// Retrieving the ObjectInstance for update from DB
		ObjectInstance editedObjectInstance = objectInstanceService.getObjectInstanceById(editObjectInstanceForSaving.getInstanceId());
		
		// Setting the edited ObjectInstance serial number. It is previously checked and is unique.
		editedObjectInstance.setSerialNumber(editObjectInstanceForSaving.getInstance());
				
		// Saving the edited ObjectInstance
		objectInstanceService.saveObjectInstance(editedObjectInstance);
		
		String instanceSaved = "instanceSaved";
		
		redirectAttributes.addFlashAttribute("instanceSaved", instanceSaved);
		
		// Implementing PRG
		return "redirect:/componentEdit/editInstanceSaved";
	}
	
	// Displaying confirmation page for saved updated Object Instance
	@GetMapping("/editInstanceSaved")
	public String editInstanceSaved(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("instanceSaved")) {	
			
			return "edit-item-instance-saved";	
			
		}else {			
			return "redirect:/componentEdit/editItem";
		}		
	}
	
	
	// Displaying Object Instance Saving Error Page - INSTANCE DELETED
	@GetMapping("/instanceDeletedError")
	public String instanceDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("instanceDeletedError")) {
			
			return "edit-item-instance-saved-error-deleted";
			
		}else {
			return "redirect:/componentEdit/editItem";
		}
	}
	
	
	// Displaying Object Instance Saving Error Page - PARENT MODEL DELETED
	@GetMapping("/parentModelDeletedError")
	public String parentModelDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("parentModelDeletedError")) {	
			
			return "edit-item-instance-saved-error-model-deleted";	
			
		}else {
			return "redirect:/componentEdit/editItem";
		}
	}
	
	
	// Displaying Object Instance Saving Error Page - INSTANCE ALREADY EXISTS
	@GetMapping("/instanceExistsError")
	public String instanceSavedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("instanceExistsError")) {
			
			return "edit-item-instance-saved-error-exists";		
			
		}else {
			return "redirect:/componentEdit/editItem";
		}
	}
	

	// Edit object Instance END

	
	// -------------------------------------------------------------------
	
	
	//  Edit Requester START
	
	// Page with Form for edit Requester
	@GetMapping("/editRequester")
	public String editRequester(Model theModel) {
	

		
		if (theModel.containsAttribute("editRequester") == false) {
			
			AdminEditRequester editRequester = new AdminEditRequester();
			
			// adding helper object AdminEditRequester to the model. It has custom validation properties.
			theModel.addAttribute("editRequester", editRequester);
			
			//Adding requestersMap (For Requester select from dropdown) to the model
			theModel.addAttribute("requestersMap", requesterService.getRequestersMap());
		}

		// Displaying edit-requester page		
		return "edit-requester";
	}
	
	
	// Returning to Edit the New AdminEditRequester
	@PostMapping("/editRequester")
	public String editRequesterAfterReview(@Valid @ModelAttribute("editRequester") AdminEditRequester editRequester,
											BindingResult theBindingResult,			
											Model theModel,
											RedirectAttributes redirectAttributes) {
		
		if (theBindingResult.hasErrors()) {
			
			// TESTING
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		        
		        // If Requester is deleted, set it to 0 so selectpicker to have empty selected value JSP
		        if(error.getCode().equals("RequesterNotDeleted")) {
		        	editRequester.setId(0);
		        }
		    }
		    
		    // If errors, add flash attributes and redirect to /componentEdit/editeRequester page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editRequester", theBindingResult);
			redirectAttributes.addFlashAttribute("editRequester", editRequester);
			redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
		    
		    // If errors, redirect to editRequester page
			return "redirect:/componentEdit/editRequester";
		}
		
		// Adding the verified AdminEditRequester object again to the model for going back and editing the Requester,		
        redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
		
        redirectAttributes.addFlashAttribute("editRequester", editRequester);
		
        return "redirect:/componentEdit/editRequester";
	}
	
	
	// Page to Review the New AdminEditRequester
	@PostMapping("/reviewEditRequester")
	public String reviewEditRequester(@Valid @ModelAttribute("editRequester") AdminEditRequester editRequester,
										BindingResult theBindingResult,										
										Model theModel,
										RedirectAttributes redirectAttributes) {
		
		if (theBindingResult.hasErrors()) {
			
			// TESTING
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		        
		        // If Requester is deleted, set it to 0 so selectpicker to have empty selected value JSP
		        if(error.getCode().equals("RequesterNotDeleted")) {
		        	editRequester.setId(0);
		        }
		    }
		    
		    // If errors, add flash attributes and redirect to /component/addRequester page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editRequester", theBindingResult);
			redirectAttributes.addFlashAttribute("editRequester", editRequester);
			redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
		    
		    // If errors, redirect to editRequester page
			return "redirect:/componentEdit/editRequester";
		}
		
		// Retrieving the old Requester name form DB and setting it to be displayed on the review page
		String oldName = requesterService.getRequester(editRequester.getId()).getName();
		editRequester.setOldName(oldName);
				
		// Adding the verified AdminEditRequester object again to the model for going back and editing the Requester,		
        redirectAttributes.addFlashAttribute("editRequester", editRequester);
		
		return "redirect:/componentEdit/reviewEditRequester";	
	}
	
	
	// Page to Review the AdminEditRequester
	@GetMapping("/reviewEditRequester")
	public String reviewRequesterGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("editRequester")) {
			
			// using AdminEditRequester value for setting the new created AdminEditRequester(editRequesterForSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("editRequesterForSaving", new AdminEditRequester());
			
			return "edit-requester-review";
		}else {
			return "redirect:/componentEdit/editRequester";
		}	
	}
	
	
	// Saving the updated Requester
	@PostMapping("/saveEditRequester")
	public String saveEditRequester(@Valid @ModelAttribute("editRequesterForSaving") AdminEditRequester editRequesterForSaving,
									BindingResult theBindingResult,
									RedirectAttributes redirectAttributes) {
		
		// Validation is done again to check if someone else has not saved the same Requester in the meantime.
		if (theBindingResult.hasErrors()) {
			
			ArrayList<String> errorList = new ArrayList<String>();
			
			// TESTING
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		        System.out.println("CODE: " + error.getCode());
//		        System.out.println("FIELD: " + error.getField());
		        
		        errorList.add(error.getCode());
		    }
		    
		    // Requester Deleted Error
		    if(errorList.size() == 1 && errorList.get(0).equals("RequesterNotDeleted")) {
		    	
		    	String requesterDeleted = "requesterDeleted";
				
				redirectAttributes.addFlashAttribute("requesterDeleted", requesterDeleted);
		    	
		    	return "redirect:/componentEdit/requesterDeleted";
		    
		    }
		    // Requester exists
		    else if (errorList.size() == 1 && errorList.get(0).equals("RequesterExists")) {		   
		    	
		    	String requesterExists = "requesterExists";
				
				redirectAttributes.addFlashAttribute("requesterExists", requesterExists);
		    	
		    	return "redirect:/componentEdit/requesterExists";
		    	
		    }
		    else {
		    	//Not used, but just in case.!!!
		    	return "default-error";
		    }

		}		
		
//		System.out.println(">>> SAVING ... Edited Requester :" + editRequesterForSaving.getName());
		
		// Retrieving the Requester for update from DB
		Requester editedRequester = requesterService.getRequester(editRequesterForSaving.getId());
		
		// Setting the new Requester name. It is previously checked and is unique.
		editedRequester.setName(editRequesterForSaving.getName());
		
		// Saving the new Requester
		requesterService.saveRequester(editedRequester);
		
		String editRequesterSaved = "editRequesterSaved";
		
		redirectAttributes.addFlashAttribute("editRequesterSaved", editRequesterSaved);
		
		// Implementing PRG
		return "redirect:/componentEdit/editRequesterSaved";
	}
	
	
	// Displaying confirmation page for saved updated Requester
	@GetMapping("/editRequesterSaved")
	public String editRequesterSaved(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("editRequesterSaved")) {	
			
			return "edit-requester-saved";			
			
		}else {			
			return "redirect:/componentEdit/editRequester";
		}
	}
	
	
	// Displaying Requester Saving Error Page - REQUESTER DELETED
	@GetMapping("/requesterDeleted")
	public String requesterDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("requesterDeleted")) {
			
			return "edit-requester-saved-error-deleted";			
			
		}else {
			return "redirect:/componentEdit/editRequester";
		}
	}
	
	
	// Displaying Requester Saving Error Page - REQUESTER EXISTS
	@GetMapping("/requesterExists")
	public String requesterExists(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("requesterExists")) {
			
			return "edit-requester-saved-error-exists";			
			
		}else {
			return "redirect:/componentEdit/editRequester";
		}
	}
	
	// Edit Requester END

	
}

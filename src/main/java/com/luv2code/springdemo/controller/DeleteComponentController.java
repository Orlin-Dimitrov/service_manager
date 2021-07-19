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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luv2code.springdemo.entity.ObjectInstance;
import com.luv2code.springdemo.entity.ObjectModel;
import com.luv2code.springdemo.helperObjects.AdminDeleteObjectInstance;
import com.luv2code.springdemo.helperObjects.AdminDeleteObjectModel;
import com.luv2code.springdemo.helperObjects.AdminDeleteObjectType;
import com.luv2code.springdemo.helperObjects.AdminDeleteRequester;
import com.luv2code.springdemo.service.ObjectInstanceService;
import com.luv2code.springdemo.service.ObjectModelService;
import com.luv2code.springdemo.service.ObjectTypeService;
import com.luv2code.springdemo.service.RequesterService;

@Controller
@RequestMapping("/componentDelete")
public class DeleteComponentController {

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
	
	
	// Page to select Type/Model/Serial Number for Deleting
	@GetMapping("/deleteItem")
	public String deleteItem() {
		
		return "delete-item-select";
		
	}
	
	//------------------------------------------------------------------
	
	
	//  Delete object Type START

	// Page to select for deleting object Type
	@GetMapping("/deleteType")
	public String deleteType(Model theModel) {
	
		if (theModel.containsAttribute("deleteObjectType") == false) {
			
			AdminDeleteObjectType deleteObjectType = new AdminDeleteObjectType();
			
			// adding helper object AdminDeleteObjectType to the model. It has custom validation properties.
			theModel.addAttribute("deleteObjectType", deleteObjectType);			
		}
		
		// Displaying delete-item-type page		
		return "delete-item-type";
	}	
	
	
	// Page with Form for editing object Type after Review
	@PostMapping("/deleteType")
	public String deleteTypeAfterReview(@Valid @ModelAttribute("deleteObjectType") AdminDeleteObjectType deleteObjectType,
										BindingResult theBindingResult,
										Model theModel,
										RedirectAttributes redirectAttributes) {
	
		// Check if selected Object Type has not been delete or display error msg
		if (theBindingResult.hasErrors()) {
			
		    // If errors, add flash attributes and redirect to /componentDelete/deleteType page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deleteObjectType", theBindingResult);
			redirectAttributes.addFlashAttribute("deleteObjectType", deleteObjectType);
			
			return "redirect:/componentDelete/deleteType";
		}
		
		// adding helper object AdminDeleteObjectType to the model. It has custom validation properties.
		redirectAttributes.addFlashAttribute("deleteObjectType", deleteObjectType);
		
		// Redirecting to delete-item-type page		
		return "redirect:/componentDelete/deleteType";
	}
	
	
	// Page to Review the New AdminDeleteObjectType
	@PostMapping("/reviewDeleteType")
	public String reviewDeleteType(@Valid @ModelAttribute("deleteObjectType") AdminDeleteObjectType deleteObjectType,
									BindingResult theBindingResult,										
									Model theModel,
									RedirectAttributes redirectAttributes) {
		
		if (theBindingResult.hasErrors()) {
			
			// TESTING
//		    List<FieldError> errors = theBindingResult.getFieldErrors();
//		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//		        
//		    }
			
		    // If errors, add flash attributes and redirect to /componentDelete/deleteType page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deleteObjectType", theBindingResult);
			redirectAttributes.addFlashAttribute("deleteObjectType", deleteObjectType);
			
			return "redirect:/componentDelete/deleteType";
		}
		
		// Retrieving the Type name form DB and setting it to be displayed on the review page
		deleteObjectType.setType(objectTypeService.getObjectType(deleteObjectType.getTypeId()).getType());
		
		// Adding the verified AdminDeleteObjectType object again to the model for going back and editing the Type,		
        redirectAttributes.addFlashAttribute("deleteObjectType", deleteObjectType);
		
		return "redirect:/componentDelete/reviewDeleteType";
	}

	
	// Page to Review the New AdminDeleteObjectType
	@GetMapping("/reviewDeleteType")
	public String reviewDeleteTypeGet(Model theModel, RedirectAttributes redirectAttributes) {

		if (theModel.containsAttribute("deleteObjectType")) {
			
			// using AdminDeleteObjectType(deleteObjectType) value for setting the new created AdminDeleteObjectType(deleteObjectTypeForSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("deleteObjectTypeForSaving", new AdminDeleteObjectType());
			
			return "delete-item-type-review";
			
		}else {			
			return "redirect:/componentDelete/deleteType";
		}
	}
	
	
	// Deleting Object Type
	@PostMapping("/saveDeleteType")
	public String saveDeleteType(@Valid @ModelAttribute("deleteObjectTypeForSaving") AdminDeleteObjectType deleteObjectTypeForSaving,
									BindingResult theBindingResult,
									RedirectAttributes redirectAttributes) {		
		
		// Validation is done again to check is someone has not Added child ObjectModels or the Object model hasn't been deleted.
		// Only one error is possible. int typeId is previously set to > 0.
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
		    
		    // If Errors occur, display error pages
		    if(currentError.equals("ObjectTypeNotDeleted")) {
		    	
				String typeDeletedError = "typeDeletedError";
				
				redirectAttributes.addFlashAttribute("typeDeletedError", typeDeletedError);
				
				return "redirect:/componentDelete/typeDeletedError";
		    	
		    }else if (currentError.equals("ObjectTypeNotInUse")) {
		    	
				String modelAddedError = "modelAddedError";
				
				redirectAttributes.addFlashAttribute("modelAddedError", modelAddedError);
				
				return "redirect:/componentDelete/modelAddedError";
				
		    }else {
		    	//Not used, but just in case.!!!
		    	return "default-error";
		    }		    
		}
		
//		System.out.println(">>> Deleting ... Object Type :" + deleteObjectTypeForSaving.getTypeId());
		
		// Deleting ObjectType
		objectTypeService.deleteObjectType(deleteObjectTypeForSaving.getTypeId());
		
		String typeDeleted = "typeDeleted";
		
		redirectAttributes.addFlashAttribute("typeDeleted", typeDeleted);
		
		// Implementing PRG
		return "redirect:/componentDelete/typeDeleted";
	}
	
	
	// Displaying confirmation page for deleted Object Type
	@GetMapping("/typeDeleted")
	public String typeDeleted(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("typeDeleted")) {
			
			return "delete-item-type-saved";		
			
		}else {			
			return "redirect:/componentDelete/deleteItem";
		}	
	}
	
	
	// Displaying Object Type Saving Error Page - TYPE DELETED
	@GetMapping("/typeDeletedError")
	public String typeDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("typeDeletedError")) {
			
			return "delete-item-type-saved-error-deleted";
			
		}else {
			return "redirect:/componentDelete/deleteItem";
		}
	}
	
	
	// Displaying Object Type Saving Error Page - MODEL ADDED
	@GetMapping("/modelAddedError")
	public String modelAddedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("modelAddedError")) {
			
			return "delete-item-type-saved-error-model-added";
			
		}else {
			return "redirect:/componentDelete/deleteItem";
		}
	}
	
	
	// Delete object Type END
	
	
	// -------------------------------------------------------------------
	
	
	//  Delete object Model START
	
	// Page to select for deleting object Model
	@GetMapping("/deleteModel")
	public String deleteModel(Model theModel) {
	
		if (theModel.containsAttribute("deleteObjectModel") == false) {
			
			AdminDeleteObjectModel deleteObjectModel = new AdminDeleteObjectModel();
			
			// adding helper object AdminDeleteObjectModel to the model. It has custom validation properties.
			theModel.addAttribute("deleteObjectModel", deleteObjectModel);
			
		}
		
		// Displaying delete-item-model page		
		return "delete-item-model";
	}	
	
	
	// Page with Form for editing object Model after Review
	@PostMapping("/deleteModel")
	public String deleteModelAfterReview(@Valid @ModelAttribute("deleteObjectModel") AdminDeleteObjectModel deleteObjectModel,
											BindingResult theBindingResult,
											Model theModel,
											RedirectAttributes redirectAttributes) {
	
		// Validation is done again to check if model exists(or parent type has been deleted). Necessary for proper animation on JSP.
		if (theBindingResult.hasErrors()) {
			
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		        
		        // If parent Type deleted, set it to 0 to NOT display animation on JSP
		        if(error.getCode().equals("ObjectTypeNotDeleted")) {
		        	deleteObjectModel.setTypeId(0);
		        }
		    }

		    // If errors, add flash attributes and redirect to /componentDelete/deleteModel page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deleteObjectModel", theBindingResult);
			redirectAttributes.addFlashAttribute("deleteObjectModel", deleteObjectModel);
			
			return "redirect:/componentDelete/deleteModel";
		}
				
		// Adding the object to the model so the input field will have a value.
		redirectAttributes.addFlashAttribute("deleteObjectModel", deleteObjectModel);
		
		// Displaying delete-item-model page		
		return "redirect:/componentDelete/deleteModel";
	}
	
	
	// Page to Review the AdminDeleteObjectModel
	@PostMapping("/reviewDeleteModel")
	public String reviewDeleteModel(@Valid @ModelAttribute("deleteObjectModel") AdminDeleteObjectModel deleteObjectModel,
									BindingResult theBindingResult,										
									Model theModel,
									RedirectAttributes redirectAttributes) {
		
		// Check if error occur
		if (theBindingResult.hasErrors()) {
			
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		    
		        // If parent Type deleted, set it to 0 to NOT display animation on JSP
		        if(error.getCode().equals("ObjectTypeNotDeleted")) {
		        	deleteObjectModel.setTypeId(0);
		        }		        
		    }
		    
		    // If errors, add flash attributes and redirect to /componentDelete/deleteModel page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deleteObjectModel", theBindingResult);
			redirectAttributes.addFlashAttribute("deleteObjectModel", deleteObjectModel);
			
			return "redirect:/componentDelete/deleteModel";
		}
		
		// Retrieving the object Model from DB
		ObjectModel selectedObjectModel = objectModelService.getObjectModel(deleteObjectModel.getModelId());
		
		// setting the Type and Model Names to be displayed in the review page
		deleteObjectModel.setType(selectedObjectModel.getObjectType().getType());
		deleteObjectModel.setModel(selectedObjectModel.getModel());
		
		// Adding the verified AdminDeleteObjectModel object again to the model for going back and editing the Model,		
        redirectAttributes.addFlashAttribute("deleteObjectModel", deleteObjectModel);
		
        return "redirect:/componentDelete/reviewDeleteModel";
	}
	

	// Page to Review the AdminDeleteObjectModel
	@GetMapping("/reviewDeleteModel")
	public String reviewDeleteModelGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("deleteObjectModel")) {
			
			// using AdminDeleteObjectModel(deleteObjectModel) value for setting the new created AdminDeleteObjectModel(deleteObjectModelForSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("deleteObjectModelForSaving", new AdminDeleteObjectModel());
			
			return "delete-item-model-review";
			
		}else {
			return "redirect:/componentDelete/deleteModel";
		}
	}
	
	
	// Deleting Object Model
	@PostMapping("/saveDeleteModel")
	public String saveDeleteModel(@Valid @ModelAttribute("deleteObjectModelForSaving") AdminDeleteObjectModel deleteObjectModelForSaving,
									BindingResult theBindingResult,
									RedirectAttributes redirectAttributes) {		
		
		// Validation is done again to check if Object Model is not deleted, has no child Object Instances, or parent Object Type deleted!!!
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
		  		    
		    // Object Model Deleted Error
		    if(errorList.size() == 1 && errorList.get(0).equals("ObjectModelNotDeleted")) {
		    	
				String modelDeletedError = "modelDeletedError";
				
				redirectAttributes.addFlashAttribute("modelDeletedError", modelDeletedError);
				
				return "redirect:/componentDelete/modelDeletedError";		
		    }
		    
		    // Object Model in use Error
		    else if (errorList.size() == 1 && errorList.get(0).equals("ObjectModelNotInUse")) {
		    	
				String instanceAddedError = "instanceAddedError";
				
				redirectAttributes.addFlashAttribute("instanceAddedError", instanceAddedError);
				
				return "redirect:/componentDelete/instanceAddedError";		
		    }
		    
		    // Object Model Deleted Error and Object Type Deleted Error
		    else if (errorList.size() > 1 && errorList.contains("ObjectTypeNotDeleted")) {
		    	
				String parentTypeDeletedError = "parentTypeDeletedError";
				
				redirectAttributes.addFlashAttribute("parentTypeDeletedError", parentTypeDeletedError);
				
				return "redirect:/componentDelete/parentTypeDeletedError";	
 	
		    }else {
		    	//Not used, but just in case.!!!
		    	return "default-error";
		    }	    	    
		}
		
//		System.out.println(">>> Deleting ... Object Model :" + deleteObjectModelForSaving.getModelId());
		
		// Deleting ObjectModel		
		objectModelService.deleteObjectModel(deleteObjectModelForSaving.getModelId());
		
		String modelDeleted = "modelDeleted";
		
		redirectAttributes.addFlashAttribute("modelDeleted", modelDeleted);
		
		// Implementing PRG
		return "redirect:/componentDelete/modelDeleted";
	}
	
	// Displaying confirmation page for deleted Object Model
	@GetMapping("/modelDeleted")
	public String modelDeleted(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("modelDeleted")) {	
			
			return "delete-item-model-saved";
			
		}else {			
			return "redirect:/componentDelete/deleteItem";
		}	
	}
		
	
	// Displaying Object Model Saving Error Page - MODEL DELETED
	@GetMapping("/modelDeletedError")
	public String modelDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("modelDeletedError")) {
			
			return "delete-item-model-saved-error-deleted";
			
		}else {
			return "redirect:/componentDelete/deleteItem";
		}
	}
	
	
	// Displaying Object Model Saving Error Page - INSTANCE ADDED
	@GetMapping("/instanceAddedError")
	public String instanceAddedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("instanceAddedError")) {
			
			return "delete-item-model-saved-error-instance-added";
			
		}else {
			return "redirect:/componentDelete/deleteItem";
		}
	}
	
	
	// Displaying Object Model Saving Error Page - PARENT TYPE DELETED
	@GetMapping("/parentTypeDeletedError")
	public String parentTypeDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("parentTypeDeletedError")) {
			
			return "delete-item-model-saved-error-type-deleted";
			
		}else {
			return "redirect:/componentDelete/deleteItem";
		}
	}
	
	
	// Delete object Model END
	
	
	// -------------------------------------------------------------------
	
	
	//  Delete object Instance START
	
	// Page with Form for edit existing object Instance
	@GetMapping("/deleteInstance")
	public String deleteInstance(Model theModel) {
	
		if (theModel.containsAttribute("deleteObjectInstance") == false) {
			
			AdminDeleteObjectInstance deleteObjectInstance = new AdminDeleteObjectInstance();
			
			// adding helper object AdminDeleteObjectInstance to the model. It has custom validation properties.
			theModel.addAttribute("deleteObjectInstance", deleteObjectInstance);			
		}
		
		// Displaying delete-item-instance page		
		return "delete-item-instance";
	}
	
	
	// Returning to Edit the New AdminDeleteObjectInstance
	@PostMapping("/deleteInstance")
	public String deleteInstanceAfterReview(@Valid @ModelAttribute("deleteObjectInstance") AdminDeleteObjectInstance deleteObjectInstance,
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
		        	deleteObjectInstance.setTypeId(0);
		        }
		        // If parent Model deleted, set it to 0 to NOT display animation on JSP		TEST TEST !!!!
		        if(error.getCode().equals("ObjectModelNotDeleted")) {
		        	deleteObjectInstance.setModelId(0);
		        }
		    }

		    // If errors, add flash attributes and redirect to /componentEdit/editInstance page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deleteObjectInstance", theBindingResult);
			redirectAttributes.addFlashAttribute("deleteObjectInstance", deleteObjectInstance);
		    
		    // If errors, display deleteInstance page
			return "redirect:/componentDelete/deleteInstance";
		}
		
		// Adding the object to the model so the input field will have a value.
		redirectAttributes.addFlashAttribute("deleteObjectInstance", deleteObjectInstance);	
		
		return "redirect:/componentDelete/deleteInstance";
	}
	
	
	// Page to Review the AdminDeleteObjectInstance
	@PostMapping("/reviewDeleteInstance")
	public String reviewDeleteInstance(@Valid @ModelAttribute("deleteObjectInstance") AdminDeleteObjectInstance deleteObjectInstance,
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
		        	deleteObjectInstance.setTypeId(0);
		        }
		        // If parent Model deleted, set it to 0 to NOT display animation on JSP		TEST TEST !!!!
		        if(error.getCode().equals("ObjectModelNotDeleted")) {
		        	deleteObjectInstance.setModelId(0);
		        }		        
		    }
		    
		    // If errors, add flash attributes and redirect to /componentEdit/editInstance page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deleteObjectInstance", theBindingResult);
			redirectAttributes.addFlashAttribute("deleteObjectInstance", deleteObjectInstance);
		    
		    // If errors, display deleteInstance page
			return "redirect:/componentDelete/deleteInstance";
		}
		
		// Retrieving the object Instance from DB
		ObjectInstance selectedObjectInstance = objectInstanceService.getObjectInstanceById(deleteObjectInstance.getInstanceId());
		

		// setting the Type, Model and Serial Number to be displayed in the review page		
		deleteObjectInstance.setType(selectedObjectInstance.getObjectModel().getObjectType().getType());		
		deleteObjectInstance.setModel(selectedObjectInstance.getObjectModel().getModel());		
		deleteObjectInstance.setInstance(selectedObjectInstance.getSerialNumber());
		
		
		// Adding the verified AdminDeleteObjectInstance object again to the model for going back and editing the Instance,
		redirectAttributes.addFlashAttribute("deleteObjectInstance", deleteObjectInstance);
		
		return "redirect:/componentDelete/reviewDeleteInstance";
	}
	

	// Page to Review the AdminDeleteObjectInstance
	@GetMapping("/reviewDeleteInstance")
	public String reviewDeleteInstanceGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("deleteObjectInstance")) {
			
			// using AdminDeleteObjectInstance(deleteObjectInstance) value for setting the new created AdminDeleteObjectInstance(deleteObjectInstanceForSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("deleteObjectInstanceForSaving", new AdminDeleteObjectInstance());
			
			return "delete-item-instance-review";
			
		}else {
			return "redirect:/componentDelete/deleteInstance";
		}
	}
	
	
	// Deleting Object Instance
	@PostMapping("/saveDeleteInstance")
	public String saveDeleteInstance(@Valid @ModelAttribute("deleteObjectInstanceForSaving") AdminDeleteObjectInstance deleteObjectInstanceForSaving,
										BindingResult theBindingResult,
										RedirectAttributes redirectAttributes) {
		
		
		// Validation is done again to check if Object Instance is not deleted, is not used in Request, parent Object Type not deleted or parent Object Model not deleted!!!
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
		  		    
		    // Object Instance Deleted Error
		    if(errorList.size() == 1 && errorList.get(0).equals("ObjectInstanceNotDeleted")) {
		    	
				String instanceDeletedError = "instanceDeletedError";
				
				redirectAttributes.addFlashAttribute("instanceDeletedError", instanceDeletedError);
				
				return "redirect:/componentDelete/instanceDeletedError";
		    	
		    }
		    
		    // Object Instance in use Error
		    else if (errorList.size() == 1 && errorList.get(0).equals("ObjectInstanceNotInUse")) {
		    	
				String instanceInUseError = "instanceInUseError";
				
				redirectAttributes.addFlashAttribute("instanceInUseError", instanceInUseError);
				
				return "redirect:/componentDelete/instanceInUseError";	
		    }
		    
		    // Object Instance Deleted Error and Object Model Deleted Error (Also Object Type Deleted) 
		    else if (errorList.size() > 1 && errorList.contains("ObjectModelNotDeleted")) {
		    	
				String parentModelDeletedError = "parentModelDeletedError";
				
				redirectAttributes.addFlashAttribute("parentModelDeletedError", parentModelDeletedError);
				
				return "redirect:/componentDelete/parentModelDeletedError";
		    }
		    
//		    // Object Instance Deleted Error, Model Deleted Error and Object Type Deleted Error	  NOT NEADED
//		    else if (errorList.size() > 1 && errorList.contains("ObjectTypeNotDeleted")) {
//		    	return "delete-item-instance-saved-error-type-deleted";
//		    }
		    
		    else {
		    	//Not used, but just in case.!!!
		    	return "default-error";
		    }	    
		    
		}
			
//		System.out.println(">>> Deleting ... Object Instance :" + deleteObjectInstanceForSaving.getInstance());
		
		// Deleting ObjectInstance		
		objectInstanceService.deleteObjectInstance(deleteObjectInstanceForSaving.getInstanceId());
		
		String instanceDeleted = "instanceDeleted";
		
		redirectAttributes.addFlashAttribute("instanceDeleted", instanceDeleted);
		
		// Implementing PRG
		return "redirect:/componentDelete/instanceDeleted";
	}
	
	// Displaying confirmation page for deleted Object Instance
	@GetMapping("/instanceDeleted")
	public String instanceDeleted(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("instanceDeleted")) {	
			
			return "delete-item-instance-saved";	
			
		}else {			
			return "redirect:/componentDelete/deleteItem";
		}	
	}
	

	// Displaying Object Instance Saving Error Page - INSTANCE DELETED
	@GetMapping("/instanceDeletedError")
	public String instanceDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("instanceDeletedError")) {
			
			return "delete-item-instance-saved-error-deleted";
			
		}else {
			return "redirect:/componentDelete/deleteItem";
		}
	}
	
	
	// Displaying Object Instance Saving Error Page - INSTANCE IN USE
	@GetMapping("/instanceInUseError")
	public String instanceInUseError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("instanceInUseError")) {
			
			return "delete-item-instance-saved-error-used";
			
		}else {
			return "redirect:/componentDelete/deleteItem";
		}
	}
	
	
	// Displaying Object Instance Saving Error Page - PARENT MODEL DELETED
	@GetMapping("/parentModelDeletedError")
	public String parentModelDeletedError(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("parentModelDeletedError")) {
			
			return "delete-item-instance-saved-error-model-deleted";
			
		}else {
			return "redirect:/componentDelete/deleteItem";
		}
	}
	

	// Delete object Instance END

	
	// -------------------------------------------------------------------
	
	
	//  Delete Requester START
	
	// Page with Form for deleting Requester
	@GetMapping("/deleteRequester")
	public String deleteRequester(Model theModel) {
	
		if (theModel.containsAttribute("deleteRequester") == false) {
			
			AdminDeleteRequester deleteRequester = new AdminDeleteRequester();
			
			// adding helper object AdminDeleteRequester to the model. It has custom validation properties.
			theModel.addAttribute("deleteRequester", deleteRequester);
			
			//Adding requestersMap (For Requester select from dropdown) to the model
			theModel.addAttribute("requestersMap", requesterService.getRequestersMap());		
		}

		// Displaying delete-requester page		
		return "delete-requester";
	}
	
	
	// Returning to Edit the New AdminDeleteRequester
	@PostMapping("/deleteRequester")
	public String deleteRequesterAfterReview(@Valid @ModelAttribute("deleteRequester") AdminDeleteRequester deleteRequester,
												BindingResult theBindingResult,	
												Model theModel,
												RedirectAttributes redirectAttributes) {
		
		if (theBindingResult.hasErrors()) {
			
//			//Adding requestersMap (For Requester select from dropdown) to the model
//			theModel.addAttribute("requestersMap", requestersMap);
			
			// TESTING
		    List<FieldError> errors = theBindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
//		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		        
		        // If Requester is deleted, set it to 0 so selectpicker to have empty selected value JSP
		        if(error.getCode().equals("RequesterNotDeleted")) {
		        	deleteRequester.setId(0);
		        }
		    }
		
		    // If errors, add flash attributes and redirect to /componentDelete/deleteRequester page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deleteRequester", theBindingResult);
			redirectAttributes.addFlashAttribute("deleteRequester", deleteRequester);
			redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
		    
		    // If errors, redirect to deleteRequester page
			return "redirect:/componentDelete/deleteRequester";	
		}	
		
		// Adding the verified AdminDeleteRequester object again to the model for going back and editing the Requester,		
        redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
		
        redirectAttributes.addFlashAttribute("deleteRequester", deleteRequester);
			
        return "redirect:/componentDelete/deleteRequester";	
	}
	
	
	// Page to Review the New AdminDeleteRequester
	@PostMapping("/reviewDeleteRequester")
	public String reviewDeleteRequester(@Valid @ModelAttribute("deleteRequester") AdminDeleteRequester deleteRequester,
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
		        	deleteRequester.setId(0);
		        }
		    }
		    
		    // If errors, add flash attributes and redirect to /componentDelete/deleteRequester page		    
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deleteRequester", theBindingResult);
			redirectAttributes.addFlashAttribute("deleteRequester", deleteRequester);
			redirectAttributes.addFlashAttribute("requestersMap", requesterService.getRequestersMap());
		    
		    // If errors, redirect to deleteRequester page
			return "redirect:/componentDelete/deleteRequester";	
		}
		
		// Retrieving the Requester name form DB and setting it to be displayed on the review page
		String name = requesterService.getRequester(deleteRequester.getId()).getName();
		deleteRequester.setName(name);		
		
		// Adding the verified AdminDeleteRequester object again to the model for going back and editing the Requester,		
        redirectAttributes.addFlashAttribute("deleteRequester", deleteRequester);
		
		return "redirect:/componentDelete/reviewDeleteRequester";	
	}
	
	
	// Page to Review the New AdminDeleteRequester
	@GetMapping("/reviewDeleteRequester")
	public String reviewDeleteRequesterGet(Model theModel,	RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("deleteRequester")) {
			
			// using AdminDeleteRequester value for setting the new created AdminDeleteRequester(deleteRequesterForSaving) form for saving. Inputs are hidden.
			theModel.addAttribute("deleteRequesterForSaving", new AdminDeleteRequester());
			
			return "delete-requester-review";
		}else {
			return "redirect:/componentDelete/deleteRequester";
		}
	}

	
	// Deleting the Requester
	@PostMapping("/saveDeleteRequester")
	public String saveDeleteRequester(@Valid @ModelAttribute("deleteRequesterForSaving") AdminDeleteRequester deleteRequesterForSaving,
										BindingResult theBindingResult,
										RedirectAttributes redirectAttributes) {
		
		// Validation is done again to check if someone else has not deleted the Requester or it is in use in a Request in the meantime.
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
		    	
		    	return "redirect:/componentDelete/requesterDeleted";	
		    }
		    // Requester in use Error
		    else if (errorList.size() == 1 && errorList.get(0).equals("RequesterNotInUse")) {
		    	
		    	String requesterInUse = "requesterInUse";
				
				redirectAttributes.addFlashAttribute("requesterInUse", requesterInUse);
		    	
		    	return "redirect:/componentDelete/requesterInUse";	    	
		    }
		    else {
		    	//Not used, but just in case.!!!
		    	return "default-error";
		    }
		}		
		
//		System.out.println(">>> Deleting Requester :" + deleteRequesterForSaving.getId());
	
		// Deleting the new Requester
		requesterService.deleteRequester(deleteRequesterForSaving.getId());
		
		String deleteRequesterSaved = "deleteRequesterSaved";
		
		redirectAttributes.addFlashAttribute("deleteRequesterSaved", deleteRequesterSaved);
		
		// Implementing PRG
		return "redirect:/componentDelete/deleteRequesterSaved";
	}
	
	
	// Displaying confirmation page for deleted Requester
	@GetMapping("/deleteRequesterSaved")
	public String deleteRequesterSaved(Model theModel, RedirectAttributes redirectAttributes) {
		
		if (theModel.containsAttribute("deleteRequesterSaved")) {	
			
			return "delete-requester-saved";			
			
		}else {			
			return "redirect:/componentDelete/deleteRequester";
		}	
	}
	
	
	// Displaying Requester Deleting Error Page - REQUESTER DELETED
	@GetMapping("/requesterDeleted")
	public String requesterDeleted(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("requesterDeleted")) {
			
			return "delete-requester-saved-error-deleted";			
			
		}else {
			return "redirect:/componentDelete/deleteRequester";
		}
	}
	
	
	// Displaying Requester Deleting Error Page - REQUESTER IN USE
	@GetMapping("/requesterInUse")
	public String requesterInUse(Model theModel, RedirectAttributes redirectAttributes) {
				
		if (theModel.containsAttribute("requesterInUse")) {
			
			return "delete-requester-saved-error-used";			
			
		}else {
			return "redirect:/componentDelete/deleteRequester";
		}
	}
	
	
	// Delete Requester END
	
}

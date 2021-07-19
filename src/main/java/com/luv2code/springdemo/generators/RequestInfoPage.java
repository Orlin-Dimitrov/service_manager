package com.luv2code.springdemo.generators;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.luv2code.springdemo.entity.Request;
import com.luv2code.springdemo.helperObjects.UpdateObjectForm;
import com.luv2code.springdemo.service.RequestService;


public class RequestInfoPage {

	@Autowired
	private RequestService requestService;
	
	// autowired custom date formater of pattern "dd-MM-yyyy"
	@Autowired
	private DateTimeFormatter customDateFormatter;
	
	public RequestInfoPage() {
		
	}
	
	// Method for populating info page
		public void infoPage(int theId, Model theModel) {
			// get the request from our service
			Request existingRequest = requestService.getRequest(theId);	
			
			// TESTING
			UpdateObjectForm helperFormExistingRequest = new UpdateObjectForm();
			
			// Adding UpdateObjectForm object for form submission START
			// Setting requestId
			helperFormExistingRequest.setRequestId(existingRequest.getId());
			
			// Setting Date
//			helperFormExistingRequest.setDateSelected(existingRequest.getDate().toString());
			
			String formatedDate = existingRequest.getDate().format(customDateFormatter);			
			helperFormExistingRequest.setDateSelected(formatedDate);
					
//			System.out.println(">>>>>> INSIDE requestinfopageGenerator: " + helperFormExistingRequest.getDateSelected());
					
			// Setting Requester
			helperFormExistingRequest.setRequesterName(existingRequest.getRequester().getName());
			
			// Setting Requester Comment
			helperFormExistingRequest.setRequesterComment(existingRequest.getRequesterComment());
			
			// Setting itemInService if exists
			if(existingRequest.getItemInService() != null) {
				helperFormExistingRequest.setCheckBoxInService(true);
				helperFormExistingRequest.setObjectTypeIdInService(existingRequest.getItemInService().getObjectModel().getObjectType().getId());
				helperFormExistingRequest.setObjectModelIdInService(existingRequest.getItemInService().getObjectModel().getId());
				helperFormExistingRequest.setObjectInstanceIdInService(existingRequest.getItemInService().getId());			
			}
			
			// Setting itemInOperation if exists
			if(existingRequest.getItemInOperation() != null) {
				helperFormExistingRequest.setCheckBoxInOperation(true);
				helperFormExistingRequest.setObjectTypeIdInOperation(existingRequest.getItemInOperation().getObjectModel().getObjectType().getId());
				helperFormExistingRequest.setObjectModelIdInOperation(existingRequest.getItemInOperation().getObjectModel().getId());
				helperFormExistingRequest.setObjectInstanceIdInOperation(existingRequest.getItemInOperation().getId());
			}
			
			//Setting itemComment
			helperFormExistingRequest.setItemComment(existingRequest.getItemComment());
			
		
			// Adding Modification Date TESTING !!!
			String formatedModificationDate = "";
			
			if(existingRequest.getModificationDate() != null) {
				formatedModificationDate = existingRequest.getModificationDate().format(customDateFormatter);
			}
			
			//Adding UpdateObjectForm object for form submission START
			
			//Adding UpdateObjectForm helperForm object to the model
			theModel.addAttribute("helperFormExistingRequest", helperFormExistingRequest);
			
			// set request as a model attribute 
			theModel.addAttribute("existingRequest", existingRequest);
			
			// adding model attribute with formated date
			theModel.addAttribute("formatedDate", formatedDate);
			
			// adding model attribute with formated MODIFICATION date TESTING!!!
			theModel.addAttribute("formatedModificationDate", formatedModificationDate);
		}	
}


	var runOnceInstance = {
			InService : true,
			InOperation : true
	}			
	
    var counterI = 0;
	
		$(document).ready(
				function() { 
					$('#objectModelsFromJSONInService, #objectModelsFromJSONInOperation').change(
					
					function() {
						
						//necessary for code reuse
						var suffixInstance = null;
						var suffixInstanceOpposite = null;
						
						if(this.id == 'objectModelsFromJSONInService'){
							suffixInstance = 'InService';
							suffixInstanceOpposite = 'InOperation';
						}else{
							suffixInstance = 'InOperation';
							suffixInstanceOpposite = 'InService';
						}
						
						//Clear Instance options on Model change
						$('#objectInstancesFromJSON' + suffixInstance).empty().selectpicker('refresh');							
						
						$.getJSON(customFindObjectInstancesURL, {
							objectModelId : $(this).val(),
							ajax : 'true'
						}, function(data) {						
							 var jsonData = JSON.stringify(data);
						     $.each(JSON.parse(jsonData), function (idx, obj) {
						        $('#objectInstancesFromJSON' + suffixInstance).append('<option value="' + obj.id + '">' + obj.serialNumber + '</option>');
						     });
						    
						     //TEST slectpicker.refresh  //Setting the Default Value for Object Instance to NULL
						     $('#objectInstancesFromJSON' + suffixInstance).selectpicker('refresh').selectpicker('val', null);
		     
//						     console.log("AJAX Instance > " + closingScreenRunOnceInstance[suffixInstance]);
						     
//						     closingScreenRunOnceInstance[suffixInstance] = false;
						     
							//If checkbox is selected and the model attribute has a value for Object Instance after EDIT select the chosen Object Instance				     
							if(checkBox[suffixInstance] == true && runOnceInstance[suffixInstance] == true && objectInstanceId[suffixInstance] > 0){
								
								runOnceInstance[suffixInstance] = false;
								
//								closingScreenRunOnceInstance[suffixInstance] = false;
								
								$('#objectInstancesFromJSON' + suffixInstance).selectpicker('val', objectInstanceId[suffixInstance]);
								
								//Only Instance in Service OR in Operation
								if(objectInstanceId[suffixInstanceOpposite] == 0){
									
//									console.log("I_UP_Display_Full_Page!");
									
									hideLoadingQuick();
									
								}
								//Instance in Service AND in Operation
								else if (objectInstanceId[suffixInstanceOpposite] > 0){	
									
//									console.log("I_DOWN_Display_Full_Page!");
									
									hideLoadingQuick();
									
								}else{
//									console.log("I_ELSE_NO_Full_page_display!");
								}								
							}						     						    
							
							
							// TESTING DISABLING SELECTPICKER, data cant be modified if it is already saved!
							if(objectInstanceId[suffixInstance] > 0 && savedCheckBox[suffixInstance] === true){
												
								$('#objectTypesFromJSON' + suffixInstance).prop('disabled',true);
								$('#objectModelsFromJSON' + suffixInstance).prop('disabled',true);
								$('#objectInstancesFromJSON' + suffixInstance).prop('disabled',true);
								
							};
							
							//INSTANCE AJAX COMPLEATE
						})  .done(function() {
//							    console.log( "END INSTANCE: " + suffixInstance);
							    
							    if(objectModelId[suffixInstance] > 0 && objectModelId[suffixInstanceOpposite] > 0){
	
							    	counterI++;
							    	
//							    	console.log("i = " + counterI);
							    	
							    	if(counterI > 1){							    		
//							    		console.log("TEST_UP");							    		
							    		closingScreenRunOnceInstance[suffixInstance] = false;
							    		closingScreenRunOnceInstance[suffixInstanceOpposite] = false;
							    	}else{
							    		
							    	}
							    }else{
//							    	console.log("TEST_DOWN");							    	
						    		closingScreenRunOnceInstance[suffixInstance] = false;
						    		closingScreenRunOnceInstance[suffixInstanceOpposite] = false;
							    }

						  })
					.fail(function(jqxhr, textStatus, error){
								dbErrorAndSessionExpiredHandler (jqxhr, textStatus, error);
								}
							);
					});
				});
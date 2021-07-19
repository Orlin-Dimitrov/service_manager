//<!-- Script for populating Object Type selectpicker -->		
		var runOnceType = {
				InService : true,
				InOperation : true
		}	
		
		var closingScreenRunOnceInstance = {
				InService : true,
				InOperation : true
		}
		
		$(document).ready(
				function () {
//					console.log("TEST VARIABE " + closingScreenRunOnceInstance['InService']);
					
					//console.log('Populating object type');
					
					$.getJSON(customFindObjectTypesURL, {
						ajax : 'true'
					}, function(data) {
						 var jsonData = JSON.stringify(data);
						 
						 
					     $.each(JSON.parse(jsonData), function (idx, obj) {
					        $('#objectTypesFromJSONInService, #objectTypesFromJSONInOperation').append('<option value="' + obj.id + '">' + obj.type + '</option>');				       
					     });
//					     console.log("TEST VARIABE " + closingScreenRunOnceInstance['InService']);
					     //TEST slectpicker.refresh  // Setting the Default Value for Object Type to NULL-->
					     $('#objectTypesFromJSONInService, #objectTypesFromJSONInOperation').selectpicker('refresh').selectpicker('val', null);					     
//					     console.log("TEST VARIABE " + closingScreenRunOnceInstance['InService']);
//					     console.log("AJAX Type > " + closingScreenRunOnceInstance['InService']);
					     
					     // CONVERT TO ITEM IN SERVICE AND ITEM IN OPERATION !!!
					     //If checkbox is selected after EDIT enable the selectpickers (even if validation hasn't passed)
					     
					     var suffixes = ['InService', 'InOperation'];
					     //console.log('DEBUG suffixes : ' + suffixes );
					     
					     for (i = 0; i < suffixes.length ; i++){
					    	 
					    	 var suffixType = suffixes[i];
					    	 var suffixTypeOpposite = null;
					    	 
					    	 if(suffixType == 'InService'){
					    		 suffixTypeOpposite = 'InOperation';
					    	 }else{
					    		 suffixTypeOpposite = 'InService';
					    	 }

		    	 
						     if(checkBox[suffixType] == true){
									
									$('#objectTypesFromJSON' + suffixType).prop("disabled", false).selectpicker('refresh').selectpicker('val', null);				  
									$('#objectModelsFromJSON' + suffixType).prop("disabled", false).selectpicker('refresh').selectpicker('val', null);
									$('#objectInstancesFromJSON' + suffixType).prop("disabled", false).selectpicker('refresh').selectpicker('val', null);
						     }			    	 
					    	 
//						    if (objectTypeId[suffixType] === 0 & objectTypeId[suffixTypeOpposite] === 0){
//						    	closingScreenRunOnceInstance[suffixType] = false;
//								closingScreenRunOnceInstance[suffixTypeOpposite] = false;
//						    }
						     
							//If checkbox is selected and the model attribute has a value for Object Type select the chosen Object Type
							if(checkBox[suffixType] == true && runOnceType[suffixType] == true && objectTypeId[suffixType] > 0){							
									
								runOnceType[suffixType] = false;							
								
								$('#objectTypesFromJSON' + suffixType).selectpicker('val', objectTypeId[suffixType]).change(); 
									 
								//Only Type in Service OR in Operation without selected Model (After DELETE)
								if(objectTypeId[suffixTypeOpposite] == 0 && objectModelId[suffixType] == 0 ){
									
//									console.log(">>> CSROI_T = " + closingScreenRunOnceInstance[suffixType]);
//									
//									console.log("T_UP_Display_Full_Page!");
									
									hideLoadingQuick();
								
								}
								//Type in Service AND in Operation without selected Model (After DELETE)
								else if	(objectTypeId[suffixTypeOpposite] > 0 && objectModelId[suffixType] == 0 && objectModelId[suffixTypeOpposite] == 0){										
										
//									closingScreenRunOnceInstance[suffixType] = false;
									
//									console.log(">>> CSROI_T = " + closingScreenRunOnceInstance[suffixType]);

									if(runOnceType[suffixType] === false && runOnceType[suffixTypeOpposite] === false){
//										console.log("T_DOWN_Display_Full_Page!");
										
										hideLoadingQuick();
									}
									
								}
								// Selected Model exist > 0
								else{
									
//									console.log("T_ELSE_NO_Full_page_display!");
									
								}									
							}
					     }					     

					})  .fail(function(jqxhr, textStatus, error){
							dbErrorAndSessionExpiredHandler (jqxhr, textStatus, error);
							}
						);
				});
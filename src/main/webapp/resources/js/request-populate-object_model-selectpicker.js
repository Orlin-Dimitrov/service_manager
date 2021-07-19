//<!-- Script for populating Object Model selectpicker when Object Type is selected -->	
	var runOnceModel = {
			InService : true,
			InOperation : true
	}
	
	 var counterM = 0;
	
		$(document).ready(
				function() { 
					$('#objectTypesFromJSONInService, #objectTypesFromJSONInOperation').change(
									
					function() {
						
						//necessary for code reuse
						var suffixModel = null;
						var suffixModelOpposite = null;
						
						if(this.id == 'objectTypesFromJSONInService'){
							suffixModel = 'InService';
							suffixModelOpposite = 'InOperation';
//							console.log('Inside pick suffixModel : ' + suffixModel);
						}else{
							suffixModel = 'InOperation';
							suffixModelOpposite = 'InService';
//							console.log('Inside pick suffixModel : ' + suffixModel);
						}						
						
//						console.log('Change objectTypesFromJSON : ' + suffixModel);
						
						// Clear Model and Instance options on Type change		
						$('#objectModelsFromJSON' + suffixModel).empty().selectpicker('refresh');
						$('#objectInstancesFromJSON' + suffixModel).empty().selectpicker('refresh');							
//						console.log('Clear model and instance with suffix : ' + suffixModel);
						
						
						$.getJSON(customFindObjectModelsURL, {
							objectTypeId : $(this).val(),
							ajax : 'true'
						}, function(data) {						
							 var jsonData = JSON.stringify(data);
						     $.each(JSON.parse(jsonData), function (idx, obj) {
						        $('#objectModelsFromJSON' + suffixModel).append('<option value="' + obj.id + '">' + obj.model + '</option>');
						     });
						     
						     //TEST slectpicker.refresh //Setting the Default Value for Object Model to NULL
						     $('#objectModelsFromJSON' + suffixModel).selectpicker('refresh').selectpicker('val', null);					     

//						     console.log("AJAX Model > " + closingScreenRunOnceInstance[suffixModel]);

						     

						     
							//If checkbox is selected and the model attribute has a value for Object Model select the chosen Object Model				  
							if( checkBox[suffixModel] == true && runOnceModel[suffixModel] == true && objectModelId[suffixModel] > 0){
								
								runOnceModel[suffixModel] = false;
																
								$('#objectModelsFromJSON' + suffixModel).selectpicker('val', objectModelId[suffixModel]).change();

								//Only Model in Service OR in Operation without selected Instance (After DELETE)
								if(objectModelId[suffixModelOpposite] == 0 && objectInstanceId[suffixModel] == 0 ){

//									closingScreenRunOnceInstance[suffixModel] = false;
	
//									console.log(">>> CSROI_M = " + closingScreenRunOnceInstance[suffixModel]);
									
//									console.log("M_UP_Display_Full_Page!");
									
									hideLoadingQuick();
								}
								
								//Model in Service AND in Operation without selected Instance (After DELETE)
								else if (objectModelId[suffixModelOpposite] > 0 && objectInstanceId[suffixModel] == 0 && objectInstanceId[suffixModelOpposite] == 0){									

//									closingScreenRunOnceInstance[suffixModel] = false;
									
//									console.log(">>> CSROI_M = " + closingScreenRunOnceInstance[suffixModel]);
									
//									console.log("M_DOWN_Display_Full_Page!");
									
									if(runOnceModel[suffixModel] === false && runOnceModel[suffixModelOpposite] === false){
										
										hideLoadingQuick();
									
									}	
								}
								// Selected Instance exist > 0
								else{
//									console.log("M_ELSE_NO_Full_page_display!");
								}								

							}
							
							//MODEL AJAX COMPLEATE
						})  .done(function() {

							if(objectModelId[suffixModel] === 0 && objectModelId[suffixModelOpposite] === 0){
								
								counterM++;
									
								if(objectTypeId[suffixModel]>0 && objectTypeId[suffixModelOpposite]>0){
										
									if(counterM > 1){

//										console.log("M: " + counterM);										
										closingScreenRunOnceInstance[suffixModel] = false;
										closingScreenRunOnceInstance[suffixModelOpposite] = false;											
									}
								}else{
									closingScreenRunOnceInstance[suffixModel] = false;
									closingScreenRunOnceInstance[suffixModelOpposite] = false;
								}
							}
						  })

						.fail(function(jqxhr, textStatus, error){
								dbErrorAndSessionExpiredHandler (jqxhr, textStatus, error);
								}
							);
					});
				});
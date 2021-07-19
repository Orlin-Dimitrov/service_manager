
	var runOnceInstance = {
			InService : true,
			InOperation : true
	}			
	
	var closingScreenRunOnceInstance = {
			InService : true,
			InOperation : true
	}
	
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
		     
							//If checkbox is selected and the model attribute has a value for Object Instance after EDIT select the chosen Object Instance				     
							if(checkBox[suffixInstance] == true && runOnceInstance[suffixInstance] == true && objectInstanceId[suffixInstance] > 0){
								runOnceInstance[suffixInstance]= false;
								$('#objectInstancesFromJSON' + suffixInstance).selectpicker('val', objectInstanceId[suffixInstance]);
								
								closingScreenRunOnceInstance[suffixInstance] = false;
								 console.log("CSRO = " + closingScreenRunOnceInstance[suffixInstance]);	
								
								//TESTING HIDING THE LOADING SCREEN
								if(objectInstanceId[suffixInstance] > 0  && objectInstanceId[suffixInstanceOpposite] == 0){
									//hideLoading();
									hideLoadingQuick();
								}
								//scipping the population of itemInService and hiding the animation when itemInOperation is fully loaded
								if(objectInstanceId[suffixInstance] > 0 && objectInstanceId[suffixInstanceOpposite] > 0 && closingScreenRunOnceInstance[suffixInstance] === false){
									//hideLoading();
									hideLoadingQuick();
								}
			
							};
							
							// TESTING DISABLING SELECTPICKER, data cant be modified if it is already saved!
							if(objectInstanceId[suffixInstance] > 0 && savedCheckBox[suffixInstance] == true){
												
								$('#objectTypesFromJSON' + suffixInstance).prop('disabled',true);
								$('#objectModelsFromJSON' + suffixInstance).prop('disabled',true);
								$('#objectInstancesFromJSON' + suffixInstance).prop('disabled',true);
								
							};
							
							console.log("closingScreenRunOnceInstance = " + closingScreenRunOnceInstance);
						})  .fail(function(jqxhr, textStatus, error){
								dbErrorAndSessionExpiredHandler (jqxhr, textStatus, error);
								}
							);
					});
				});
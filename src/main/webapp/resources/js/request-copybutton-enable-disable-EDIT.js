//<!-- TESTING BUTTON ENABLE/DISABLE ON EDIT PAGE, NEW SCRIPT CREATED: request-copybutton-enable-disable-EDIT.js-->		
	var copyButtonRunOnce = {
			InService : true,
			InOperation : true
	}
	
	var copyButtonEnabled = {
			InService : null,
			InOperation : null
	}
	
	$(document).ready(
			function() { 
		
				$('#objectInstancesFromJSONInService, #objectModelsFromJSONInService, #objectTypesFromJSONInService, #checkBoxInService, ' + 
				  '#objectInstancesFromJSONInOperation, #objectModelsFromJSONInOperation, #objectTypesFromJSONInOperation, #checkBoxInOperation').change(
				
				function() {
	
					// necessary for code reuse				
					// source
					var suffixCopyButton = null;
					
					// destination
					var suffixCopyTo = null;
					
					if(this.id == 'objectInstancesFromJSONInService' || this.id == 'objectModelsFromJSONInService' || this.id == 'objectTypesFromJSONInService' || this.id == 'checkBoxInService'){
						suffixCopyButton = 'InService';
						suffixCopyTo = 'ToOperation';
					}else{
						suffixCopyButton = 'InOperation';
						suffixCopyTo = 'ToService';
					}
					
					// Integer - Selected value of selectpicker #objectInstancesFromJSON + suffixCopyButton (InService or InOperation)
					copyButtonEnabled[suffixCopyButton] = $('#objectInstancesFromJSON' + suffixCopyButton).val();					
					
					//console.log('Enabled value for : ' + suffixCopyButton + ' is >>> ' + copyButtonEnabled[suffixCopyButton]);
					
					// DISABLE BOUTH BUTTONS WHEN Item in Service and Item in Operation already exist. If clicking the buttons fast data can be changed!!!					
					if(savedCheckBox['InService'] == true && savedCheckBox['InOperation'] == true){
						$('#copy' + 'InService').prop('disabled', true);
						$('#copy' + 'InOperation').prop('disabled', true);
					}else{

						// If Selected value of selectpicker #objectInstancesFromJSON is picked and #checkBox is selected ENABLE the CopyButton.
						// !!! Modification savedCheckBox must be true, so the new selectPicker values cant overwiter the old values
						if(copyButtonEnabled[suffixCopyButton] != null && $('#checkBox' + suffixCopyButton).prop('checked') == true && savedCheckBox[suffixCopyButton] == true){		
							$('#copy' + suffixCopyTo).prop('disabled', false);				
						}else{
							$('#copy' + suffixCopyTo).prop('disabled', true);					
						}						

						// variable with value objectModelsFromJSONInService or objectModelsFromJSONInOperation					
						var objectModelsFromJSONSelected = 'objectModelsFromJSON' + suffixCopyButton;
						
						// Necessery for enabling copy button after choosing to EDIT the form
						// !!! Modification savedCheckBox must be true, so the new selectPicker values cant overwiter the old values
						if(this.id == objectModelsFromJSONSelected && objectInstanceId[suffixCopyButton] > 0 && copyButtonRunOnce[suffixCopyButton] == true && savedCheckBox[suffixCopyButton] == true){
							$('#copy' + suffixCopyTo).prop('disabled', false);
							copyButtonRunOnce[suffixCopyButton] = false;
						}
						
						
					}				
					

				});			
			});	
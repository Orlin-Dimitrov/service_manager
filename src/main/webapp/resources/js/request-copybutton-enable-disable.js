//<!-- TESTING BUTTON ENABLE/DISABLE , NEW SCRIPT CREATED: request-copybutton-enable-disable.js-->	
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
					
					// If Selected value of selectpicker #objectInstancesFromJSON is picked and #checkBox is selected ENABLE the CopyButton	!!! ATENTION CHECK WHEN DELETING INSTANCE SET IN CONTROLER TO 0
					if(copyButtonEnabled[suffixCopyButton] != null && $('#checkBox' + suffixCopyButton).prop('checked') == true ){		
						$('#copy' + suffixCopyTo).prop('disabled', false);				
					}else{
						$('#copy' + suffixCopyTo).prop('disabled', true);					
					}						

					// variable with value objectModelsFromJSONInService or objectModelsFromJSONInOperation					
					var objectModelsFromJSONSelected = 'objectModelsFromJSON' + suffixCopyButton;
					
					//Necessery for enabling copy button after choosing to EDIT the form
					if(this.id == objectModelsFromJSONSelected && objectInstanceId[suffixCopyButton] > 0 && copyButtonRunOnce[suffixCopyButton] == true){
						$('#copy' + suffixCopyTo).prop('disabled', false);
						copyButtonRunOnce[suffixCopyButton] = false;
					}								
				});			
			});
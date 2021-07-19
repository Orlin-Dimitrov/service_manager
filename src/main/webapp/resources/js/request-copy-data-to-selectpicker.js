//<!-- TESTING COPING SELECTED DATA TO OPERATION, , NEW SCRIPT CREATED: request-copy-data-to-selectpicker.js-->	
	$(document).ready(function() {
		$('#copyToOperation, #copyToService').click(
				
			function() {
			
				//necessary for code reuse
				var suffixCopyingDataSource = null;
				var suffixCopyingDataDestination = null;
				
				if(this.id == 'copyToOperation'){
					suffixCopyingDataSource = 'InService';
					suffixCopyingDataDestination = 'InOperation';
				}else{
					suffixCopyingDataSource = 'InOperation';
					suffixCopyingDataDestination = 'InService';
				}
			
				//Enabling the Destination Selectpickers
				$('#checkBox' + suffixCopyingDataDestination).prop('checked', true) ;
				$('#objectTypesFromJSON' + suffixCopyingDataDestination).prop("disabled", false).selectpicker('refresh');		
				$('#objectModelsFromJSON' + suffixCopyingDataDestination).prop("disabled", false).selectpicker('refresh');
				$('#objectInstancesFromJSON' + suffixCopyingDataDestination).prop("disabled", false).selectpicker('refresh');
				
				// Setting the destination variables with values from the source Selectpicker
				objectTypeId[suffixCopyingDataDestination] = $('#objectTypesFromJSON' + suffixCopyingDataSource).find("option:selected").val();
				objectModelId[suffixCopyingDataDestination] = $('#objectModelsFromJSON' + suffixCopyingDataSource).find("option:selected").val();			
				objectInstanceId[suffixCopyingDataDestination] = $('#objectInstancesFromJSON' + suffixCopyingDataSource).find("option:selected").val();		
			
				//Enable values for the Destination Selectpickers so the selectpickers can be autoselected
				checkBox[suffixCopyingDataDestination] = true;	
				runOnceType[suffixCopyingDataDestination] = true;
				runOnceModel[suffixCopyingDataDestination] = true;
				runOnceInstance[suffixCopyingDataDestination] = true;
				
				// TESTING
				copyButtonRunOnce[suffixCopyingDataDestination] = true;
				
				//Triggering destination the selectpicker
				$('#objectTypesFromJSON' + suffixCopyingDataDestination).selectpicker('val', objectTypeId[suffixCopyingDataDestination]).change();
				
	
			});
		});
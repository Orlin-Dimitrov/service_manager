//<!-- Script for checkBoxInService to enable/disable selectpicker -->
		$(document).ready(
			function() { 		
				$('#checkBoxInService, #checkBoxInOperation').change(
					
				function () {
					
					//necessary for code reuse
					var suffixCheckBox = null;
					if(this.id == 'checkBoxInService'){
						suffixCheckBox = 'InService';
					}else{
						suffixCheckBox = 'InOperation';
					}
					
					if($(this).prop('checked') == true) {
						$('#objectTypesFromJSON' + suffixCheckBox).prop("disabled", false).selectpicker('refresh').selectpicker('val', null);						  
						$('#objectModelsFromJSON'+ suffixCheckBox).prop("disabled", false).selectpicker('refresh').selectpicker('val', null);
						$('#objectInstancesFromJSON'+ suffixCheckBox).prop("disabled", false).selectpicker('refresh').selectpicker('val', null);	
						    
					} else {
						$('#objectTypesFromJSON'+ suffixCheckBox).prop("disabled", true).selectpicker('refresh').selectpicker('val', null);   
						$('#objectModelsFromJSON'+ suffixCheckBox).prop("disabled", true).empty().selectpicker('refresh').selectpicker('val', null);
						$('#objectInstancesFromJSON'+ suffixCheckBox).prop("disabled", true).empty().selectpicker('refresh').selectpicker('val', null);	
					}
				});
			});
//<!-- TESTING DISABLING CHANGING VALUES OF SELECTPICKER AND CHECKBOX,   NEW SCRIPT CREATED: request-disable-changing-values-selectpicker-checkbox-EDIT.js-->

$(document).ready(function() {
	
	  $('#registrationForm').bind('submit', function () {
		  
		    $(this).find('#objectTypesFromJSONInService').prop('disabled', false);
		    $(this).find('#objectTypesFromJSONInOperation').prop('disabled', false);
		  
		    $(this).find('#objectModelsFromJSONInService').prop('disabled', false);
		    $(this).find('#objectModelsFromJSONInOperation').prop('disabled', false);
		  
		    $(this).find('#objectInstancesFromJSONInService').prop('disabled', false);
		    $(this).find('#objectInstancesFromJSONInOperation').prop('disabled', false);
		  });
	  	
	  
	  $('#checkBoxInService').on("click", function (e) {
		    if (savedCheckBox['InService'] == true) {
		        e.preventDefault();
		        return false;
		    }
		});
	  
	  $('#checkBoxInOperation').on("click", function (e) {
		    if (savedCheckBox['InOperation'] == true) {
		        e.preventDefault();
		        return false;
		    }
		});

});
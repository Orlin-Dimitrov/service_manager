		$(document).ready(function () {
		    // debugger;
	
		    $('.date').datepicker({
		        format: "dd-mm-yyyy",
		        startDate: "01-01-2016",
		        endDate: "31-12-2026",
		        maxViewMode: 3,
		        todayBtn: "linked",
		        orientation: "bottom auto",
		        weekStart: 1,
		        autoclose: true,
		        todayHighlight: true,
		        showClear: true,
		        language: 'bg',
		    });

		});
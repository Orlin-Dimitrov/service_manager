		$(document).ready(function () {
			if(objectTypeId['InService'] > 0 || objectTypeId['InOperation'] > 0){
				showLoading();		
			}else{
				hideLoading();
			}   
		});
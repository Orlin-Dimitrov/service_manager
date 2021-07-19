	 $(document).bind("ajaxSend", function(){
		    $("#jsonLoader").show(0);
		    
		   
		    
		 }).bind("ajaxComplete", function(){
		
			 
			// if(closingScreenRunOnceInstance == true || closingScreenRunOnceType == true || closingScreenRunOnceModel == true){
			if(closingScreenRunOnceInstance['InService'] === false || closingScreenRunOnceInstance['InOperation'] === false){
			 
//				console.log("dot-loader-CSROI - I = " + closingScreenRunOnceInstance['InService']);
//				console.log("dot-loader-CSROI - O = " + closingScreenRunOnceInstance['InOperation']); 

				 $("#jsonLoader").delay(800).fadeOut(400);
				 
//				 console.log("hide SMALL loader - delay 1s");
			 }
//			else if(closingScreenRunOnceModel['InService'] === false && closingScreenRunOnceModel['InOperation'] === false){
//				 $("#jsonLoader").delay(1000).hide(0);
//			 }
			
			else{
				
				 $("#jsonLoader").hide(0);
//				 console.log("hide SMALL loader - NO delay");
				 
			 }
		 });
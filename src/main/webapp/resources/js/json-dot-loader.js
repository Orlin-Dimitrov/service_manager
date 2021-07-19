	 $(document).bind("ajaxSend", function(){
		    $("#jsonLoader").show(0);
		 }).bind("ajaxComplete", function(){
			 
			 if(closingScreenRunOnceType === false){
//				 console.log('jsonLoader Delay: ' + closingScreenRunOnceType);
				 $("#jsonLoader").delay(800).fadeOut(400);
			 }else{
				 closingScreenRunOnceType = false;
				 $("#jsonLoader").hide(0);
			 }
			 
			 
		 });
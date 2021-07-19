

function dbErrorAndSessionExpiredHandler (jqxhr, textStatus, error) {

	var xhrStatusText = jqxhr.statusText;
	var xhrStatus = jqxhr.status;
	
//	console.log("CTX :" + String(ctx));
	
//	console.log("xhr statusText :" + xhrStatusText);

	if( xhrStatusText == "parsererror"){

		window.location.href = String(ctx) + '/session-expired';

	}
	else if( xhrStatusText == "error"){
		
		var errorData = jqxhr.responseText;
		var jsonErrorData = JSON.parse(errorData);
		var jsonErrorDataResponseMessage = jsonErrorData["message"];
		
//		console.log("xhr responseText :" + errorData);
//		console.log("xhr responseText.message :" + jsonErrorDataResponseMessage);
		
		if(jsonErrorDataResponseMessage.toString() == "DataBase error"){

			$(function () {
				
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				var dbErrorPage = String(ctx)  + '/json/db-error';
				  
				$(document).ajaxSend(function(e, xhr, options) {
					xhr.setRequestHeader(header, token);
				});
				  
				$.ajax({
					type : "POST",
					async: true,
					url: String(dbErrorPage),
					success:function(response) { 
						$('#mainView').html(response);					        	
						},
					error: function(){
						window.location.href = String(ctx)  + '/';
						}
					});				  
			});

//			window.location.href = String(ctx)  + '/db-error';
			
		}else{
			console.log("DEFAULT ERROR HANDLER inside jsonError = error");
			window.location.href = String(ctx)  + '/';
		}
		
	}else{
		// any other error
//		console.log("DEFAULT ERROR HANDLER");

		window.location.href = String(ctx)  + '/';
	}

  }

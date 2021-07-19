function showLoading() {			
	document.getElementById('loader').style.display = 'block';		    
};
		
function hideLoading() {
			
	setTimeout(function(){
				
		document.getElementById('loader').style.display = 'none';
		document.getElementById('loadedPage').style.display = 'block';
	}, 1000);		    
};

function hideLoadingQuick() {
	
//	console.log("HIDING FULLPAGE LOADER");
	
	setTimeout(function(){
				
		document.getElementById('loader').style.display = 'none';
		document.getElementById('loadedPage').style.display = 'block';
	}, 1200);		    
};
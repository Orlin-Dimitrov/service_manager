function showLoading() {			
	document.getElementById('loader').style.display = 'block';		    
};
		
function hideLoading() {
			
	setTimeout(function(){
				
		document.getElementById('loader').style.display = 'none';
		document.getElementById('loadedPage').style.display = 'block';
	}, 1000);		    
};

function hideLoadingExtraDelay() {
	
	setTimeout(function(){
				
		document.getElementById('loader').style.display = 'none';
		document.getElementById('loadedPage').style.display = 'block';
	}, 1500);		    
};

function hideLoadingDelay3S() {
	
	setTimeout(function(){
				
		document.getElementById('loader').style.display = 'none';
		document.getElementById('loadedPage').style.display = 'block';
	}, 3000);		    
};

function hideLoadingDelay4S() {
	
	setTimeout(function(){
				
		document.getElementById('loader').style.display = 'none';
		document.getElementById('loadedPage').style.display = 'block';
	}, 4000);		    
};

function hideLoadingQuick() {
	
	console.log("FULLLOADER HIDE Quick");
	
	setTimeout(function(){
				
		document.getElementById('loader').style.display = 'none';
		document.getElementById('loadedPage').style.display = 'block';
	}, 1200);		    
};
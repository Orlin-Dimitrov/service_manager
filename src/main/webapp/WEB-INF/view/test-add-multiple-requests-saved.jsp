<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>

<!DOCTYPE html>
<html>

<head>
	<title>Request Saved</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	
	<!-- Style of the Review Add Request Page (place before inserting bootstrap)-->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/request-review-page.css">
		  
	<!-- import Bootstrap and jQuery  ATTENTION !!! bootstrap must be inserted after page shape css-->	  
	<c:import url="../view/fragments/bootstrap.jsp"></c:import>
	
	<!-- Style of the jumbotron for vertical centering when using navbar -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/jumbotron-vertCenter-when-navbar-used.css">
	

</head>

<body>
	
	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>
	

	<div class="jumbotron vertical-center " >
		<div class="container">
			<div class="col-sm-8 col-sm-offset-2">

				
			
					<div class="row">
						<div class="col-sm-10 col-sm-offset-1">
							<div class="panel panel-success">
								<div class="panel-heading text-center">Request Saved!</div>
								<div class="panel-body text-center">
							    	<p>Total number of Request saved: ${entriesAddedToDB}!</p>
								</div>	
							</div>
						</div>
					</div>
				

					 
					<div class="row"> 
					   
					        <div class="text-center">
								<a class="btn btn-default" href="${pageContext.request.contextPath}/test/addEntriesToDB" role="button">Back to List</a>
					        </div>
					   
					</div>
				
			</div>		 
			
		</div>
	
	</div>	
</body>

</html>











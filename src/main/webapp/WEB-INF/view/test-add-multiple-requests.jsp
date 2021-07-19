<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<!doctype html>
<html lang="en">

<head>
	
	<title>Add Multiple</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		
	<!-- style for page shape -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/page_shape.css" />
	
	<!-- import Bootstrap and jQuery -->	
	<c:import url="../view/fragments/bootstrap.jsp"></c:import>

	<!-- Style of the jumbotron for vertical centering when using navbar -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/jumbotron-vertCenter-when-navbar-used.css">



<!--
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.js"></script>
  -->

	<!--  importing bootstrap-select for select drop down menu picker -->
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-select.min.css"/>" type="text/css" />
	<script src="<c:url value="/resources/js/bootstrap-select.min.js"/>"></script>
	
	<!--  importing bootstrap-datepicker for selecting date -->
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-datepicker3.min.css"/>" type="text/css" />
	<script src="<c:url value="/resources/js/bootstrap-datepicker.min.js"/>"></script>





	<!-- Customised Datepicker config -->
	<script src="<c:url value="/resources/js/customised-datepicker-config.js"/>"></script>




	

	<!-- TESTING LOADING ANIMATION configuration script-->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/loading.css" />

	<script type="text/javascript">
	
		function showLoading() {			
			document.getElementById('loader').style.display = 'block';		    
		};
		
		function hideLoading() {
			document.getElementById('loader').style.display = 'none';
			document.getElementById('loadedPage').style.display = 'block';	    
		};
	
		
	</script>

	<script type="text/javascript">
	
		$(document).ready(function () {

				hideLoading();
 
		});
	</script>



<style>
.error {
	color: #ff0000;
}
.labelNormal {
	font-weight: normal;
}
</style>






</head>

<body>


	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>
	
	<div class="jumbotron vertical-center " >
		<div class="container">
	
			<!-- showing loading animation while AJAX calls are made and copy btn are enabled -->
			<div id="loader" style="display: none" class="mainbox">
				<div class="spinner">
					<div class="bounce1"></div>
					<div class="bounce2"></div>
					<div class="bounce3"></div>
				</div>
			</div>
	
	
			<div id="loadedPage" style="display: none">
			
				<div id="loginbox" class="mainbox col-sm-6 col-sm-offset-3">
					<div class="panel panel-primary">
		
						<div class="panel-heading">
							<div class="panel-title">Add Multiple Request</div>
						</div>
		
						<div style="padding-top: 30px" class="panel-body">
		
							<!-- Registration Form -->
							<form:form action="${pageContext.request.contextPath}/test/saveEntriesToDB"
									   modelAttribute="helperForm" 
									   method="POST" 
								       class="form-horizontal"
								       id="registrationForm">
								
								<!-- Adding Date -->
								<div style="margin-bottom: 25px; margin-left: 0px; margin-right: 0px "  class="form-group date">								
									<label for="date">Date</label>										
									<div class="col-sm-4 input-group">								
										<form:input type="text" class="form-control" path="dateSelected" autocomplete="off" placeholder="Select Date" onkeydown="return false"  />	
										<span class="input-group-addon" > <i class="glyphicon glyphicon-calendar"></i></span>				
									</div>	
		  							<form:errors path="dateSelected"  cssClass="error"/>	
								</div>
										 
		
								<!-- Adding Number of Requests -->
								<div class="row">
									<div style="margin-bottom: 25px; margin-left: 0px; margin-right: 0px"  class="form-group col-sm-10">																					
										<label for="numberOfRequests">Number of Requests</label>				
										<div>
											<form:input  class="form-control" path="numberOfRequests" autocomplete="off" />				
										</div>
										
									</div>
								</div>	
								
								<security:authentication var="user" property="principal.username"/>
								<form:hidden path="userName" value="${user} " />
																							
								<!-- Submit / Cancel Button -->
								<div class="row">								
									<div class="col-xs-6">
										<div class="back text-left" style="margin-top: 10px ; margin-left: 0px">												
																	
										</div>							
									</div>
								
									<div class="col-xs-6" >									
										<div class="submit text-right">	
											<div style="margin-top: 10px ; margin-right: 0px" class="form-group">																				
												<button type="submit" class="btn btn-primary">Submit</button>									
											</div>										
										</div>									
									</div>															
								</div>
								
							</form:form>
							
						</div>
					</div>			
				</div>
		
			<!-- end of div class loadedPage -->
			</div>
		<!-- end of container -->		
		</div>
	<!-- END JUMBOTRON -->		
	</div>
</body>
</html>
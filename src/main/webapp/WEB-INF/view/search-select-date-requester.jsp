<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	
	<title>Търсене Дата / Заявител</title>
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

	<!-- CSS Style for Object Form -->
	<link rel="stylesheet" href="<c:url value="/resources/css/object-form.css"/>" type="text/css" />

	<!--  importing bootstrap-select for select drop down menu picker -->
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-select.min.css"/>" type="text/css" />
	<script src="<c:url value="/resources/js/bootstrap-select.min.js"/>"></script>
	
	<!--  importing bootstrap-datepicker for selecting date -->
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-datepicker3.min.css"/>" type="text/css" />
	<script src="<c:url value="/resources/js/bootstrap-datepicker.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap-datepicker.new.bg.min.js"/>" charset="UTF-8"></script>

	<script>
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
		})
	</script>

	<!-- On Initial page load display custom message inside selectpicker for Requester, requesterId = 0.  -->
	<script type="text/javascript">
	var requesterId = <c:out value="${searchDateRequester.requesterId}" />;
	$(document).ready(
			function () {				
				if(requesterId == 0){
					$('#requester').selectpicker('refresh').selectpicker('val', null);
				}	
			});
	</script>
	
	<!-- Reset Requester  -->
	<script type="text/javascript">

	$(document).ready(
			function() {
			      $("#clearRequester").click( function(){
			    	  $('#requester').selectpicker('refresh').selectpicker('val', null);
			           }
			      );
			});
	</script>
	
	<!-- Customised Datepicker config -->
	<script src="<c:url value="/resources/js/customised-datepicker-config.js"/>"></script>
	
	<!-- Reset Date  -->
	<script type="text/javascript">

	$(document).ready(
		function() {
			$("#clearDateStart, #clearDateEnd").click( function(){
			    	  
				if(this.id == 'clearDateStart'){
					$("#dateStart").datepicker("clearDates");
				}else{
					$("#dateEnd").datepicker("clearDates");
				}
			}
		);
	});
	
	</script>

</head>

<body>


	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>
	
	<div class="jumbotron vertical-center " >
		<div class="container">
	
				<div class="row" >
				<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<div class="row" >
		
				<div id="searchbox" class="mainbox">
					<div class="panel panel-primary" >
		
						<div class="panel-heading">
							<div class="panel-title">Търсене Дата / Заявител</div>
						</div>
		
						<div class="panel-body">
		
							<!-- Search Request Form -->
							<form:form action="${pageContext.request.contextPath}/search/resultsDateRequester"
									   modelAttribute="searchDateRequester" 
									   method="POST" 
								       class="form-horizontal"
								       id="searchForm">								
								
								<div class="row item-panel"  >
								<div class="panel panel-default add-color col-xs-12 col-sm-10 col-sm-offset-1"  >
								<div class="panel-body ">
								
								<!-- Adding Date From and To -->																															
								<div class="row">
																																															
									<div class="col-xs-10">																		
										<div id="dateStart" class="form-group form-group-custom date">																						
											<label for="date">Начална Дата</label>																																	
											<div class="input-group" >																																										
												<form:input type="text" class="form-control" path="dateStart" autocomplete="off" placeholder="Избери Дата" onkeydown="return false"/>	
												<span class="input-group-addon" > <i class="glyphicon glyphicon-calendar"></i></span>																																												
											</div>
											<form:errors path="dateStart" cssClass="error"/>			
										</div>
									</div>
										
									<div class="col-xs-2" >
										<div class="reset-btn form-group ">											
											<label for="clearDateStart"> &nbsp;</label>
											<div>
												<a class="btn btn-default" id="clearDateStart" role="button" data-toggle="tooltip" data-placement="top" title="Нулирай"><i class="glyphicon glyphicon-retweet"></i></a>
											</div>
										</div>
									</div>														  											  							
								</div>

								
								<div class="row">																																							
									<div class="col-xs-10">																		
										<div id="dateEnd" class="form-group form-group-custom date">																						
											<label for="date">Крайна Дата</label>																																	
											<div class="input-group">																																										
												<form:input type="text" class="form-control" path="dateEnd" autocomplete="off" placeholder="Избери Дата" onkeydown="return false"/>	
												<span class="input-group-addon" > <i class="glyphicon glyphicon-calendar"></i></span>																																												
											</div>
											<form:errors path="dateEnd" cssClass="error"/>
										</div>
									</div>
										
									<div class="col-xs-2" >
										<div class="form-group reset-btn">											
											<label for="clearDateEnd"> &nbsp;</label>
											<div>
												<a class="btn btn-default" id="clearDateEnd" role="button" data-toggle="tooltip" data-placement="top" title="Нулирай"><i class="glyphicon glyphicon-retweet"></i></a>
											</div>
										</div>
									</div>					  											  							
								</div>

																
 								<!-- Adding Requester -->
 								<div class="row">																																							
									<div class="col-xs-10">	
										<div class="form-group form-group-custom">
											<label for="requester">Заявител</label>							
											<div >
												<form:select id="requester" path="requesterId"  class="selectpicker col-xs-12 pad-left-zero" data-live-search="true" data-none-results-text="Неса намерени резултати" data-none-selected-text="Избери Заявител">															
													<form:options items="${requestersMap}" />									
												</form:select>
											</div>
										</div>
									</div>
									
									<div class="col-xs-2" >
										<div class="form-group reset-btn">
											<label for="clearRequester"> &nbsp;</label>
											<div>
											<a class="btn btn-default " id="clearRequester" role="button" data-toggle="tooltip" data-placement="top" title="Нулирай"><i class="glyphicon glyphicon-retweet"></i></a> 				
											</div>
										</div>
									</div>	
								</div>																						

								</div>
								</div>
								</div>


								<form:hidden path="dataTablesUniqueId" value="${searchDateRequester.dataTablesUniqueId}" />
								
								<div class="row">
									<div class="col-xs-12">														
										<div class="save  text-right btn-right form-group" >																					
											<button type="submit" class="btn btn-primary" ><i class="glyphicon glyphicon-search"></i> Търси</button>										 
										</div>
									</div>
								</div>
								
							</form:form>
							
						</div>
					</div>
				<!-- end of searchbox -->							
				</div>
				
				</div>
				</div>
				</div>	

			
		<!-- end of container -->		
		</div>
	<!-- END JUMBOTRON -->		
	</div>
</body>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	
	<title>Търсене на Устройство</title>
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

	<c:url var="findObjectTypesURL" value="/json/objectTypeList" />	
	<c:url var="findObjectModelsURL" value="/json/objectModelList" />	
	<c:url var="findObjectInstancesURL" value="/json/objectInstanceList" />

	<!-- MY CUSTOM SCRIPTS -->

	<!-- import Handle db-error, session-expired, and loading animations scripts and css -->
	<c:import url="../view/fragments/dberror-sessionexpired.jsp"></c:import>	
	<c:import url="../view/fragments/animations.jsp"></c:import>

	<script type="text/javascript">
		
		var typeId = <c:out value="${searchItem.objectTypeId}" />;		
		var modelId = <c:out value="${searchItem.objectModelId}" />;		
		var instanceId = <c:out value="${searchItem.objectInstanceId}" />;
		
	</script>

	<!-- Script FullPage loading animation logic. PLACE AFTER typeId is set-->
	<script src="<c:url value="/resources/js/show-hide-loading.js"/>"></script>

	<!-- On Initial page load display custom message inside selectpicker for Requester, requesterId = 0.  -->
	<script type="text/javascript">
	var requesterId = <c:out value="${searchItem.requesterId}" />;
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


	<!-- Script for populating Object Type selectpicker -->
	<script type="text/javascript">
	
	var closingScreenRunOnceType = true;
	
		$(document).ready(
				function () {
					
					//console.log('Populating object type');
					
					$.getJSON('${findObjectTypesURL}', {
						ajax : 'true'
					}, function(data) {
						 var jsonData = JSON.stringify(data);
					     $.each(JSON.parse(jsonData), function (idx, obj) {
					        $('#objectTypesFromJSON').append('<option value="' + obj.id + '">' + obj.type + '</option>');				       
					     });

					     //TEST slectpicker.refresh  // Setting the Default Value for Object Type to NULL-->
					     $('#objectTypesFromJSON').selectpicker('refresh').selectpicker('val', null);					     
		     
					     if(typeId > 0){
					    	 $('#objectTypesFromJSON').selectpicker('val', typeId).change();
					    	 
					    	 if(modelId == 0){
					    		 hideLoading();
					    	 }
					     }
					     
					})  .fail(function(jqxhr, textStatus, error){
							dbErrorAndSessionExpiredHandler (jqxhr, textStatus, error);
							}
						);
				});
	</script>
	
	<!-- Script for populating Object Model selectpicker when Object Type is selected -->
	<script type="text/javascript">
	
		$(document).ready(
				function() { 
					$('#objectTypesFromJSON').change(
									
					function() {
						
						// Clear Model and Instance options on Type change	
						$('#objectModelsFromJSON').empty().selectpicker('refresh');
						$('#objectInstancesFromJSON').empty().selectpicker('refresh');	
						
						$.getJSON('${findObjectModelsURL}', {
							objectTypeId : $(this).val(),
							ajax : 'true'
						}, function(data) {						
							 var jsonData = JSON.stringify(data);
						     $.each(JSON.parse(jsonData), function (idx, obj) {
						        $('#objectModelsFromJSON').append('<option value="' + obj.id + '">' + obj.model + '</option>');
						     });
						     
						     //TEST slectpicker.refresh //Setting the Default Value for Object Model to NULL
						     $('#objectModelsFromJSON').selectpicker('refresh').selectpicker('val', null);					     

						     if(modelId > 0){
						    	 $('#objectModelsFromJSON').selectpicker('val', modelId).change();
						    	 
						    	 if(instanceId == 0){
						    		 //hideLoading();
						    		 hideLoadingExtraDelay();
						    	 }
						     }
						})  .fail(function(jqxhr, textStatus, error){
								dbErrorAndSessionExpiredHandler (jqxhr, textStatus, error);
								}
							);
					});
				});
	</script>
	
	<!-- Script for populating Object Instance selectpicker when Object Model is selected -->
	<script type="text/javascript">
	
		$(document).ready(
				function() { 
					$('#objectModelsFromJSON').change(
									
					function() {
						
						// Clear Instance options on Model change	
						$('#objectInstancesFromJSON').empty().selectpicker('refresh');	
						
						$.getJSON('${findObjectInstancesURL}', {
							objectModelId : $(this).val(),
							ajax : 'true'
						}, function(data) {						
							 var jsonData = JSON.stringify(data);
						     $.each(JSON.parse(jsonData), function (idx, obj) {
						        $('#objectInstancesFromJSON').append('<option value="' + obj.id + '">' + obj.serialNumber + '</option>');
						     });
						     
						     //TEST slectpicker.refresh //Setting the Default Value for Object Instance to NULL
						     $('#objectInstancesFromJSON').selectpicker('refresh').selectpicker('val', null);					     

						     if(instanceId > 0){
						    	 $('#objectInstancesFromJSON').selectpicker('val', instanceId).change();
						    	 //hideLoading();
						    	 hideLoadingExtraDelay();
						     }
						})  .fail(function(jqxhr, textStatus, error){
							dbErrorAndSessionExpiredHandler (jqxhr, textStatus, error);
								}
							);
					});
				});
	</script>



</head>

<body>


	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>
	
	<div class="jumbotron vertical-center " id="mainView">
		<div class="container">

			<!-- showing loading animation while AJAX calls are made -->
			<div id="loader" style="display: none" class="mainbox">
				<div class="spinner">
					<div class="bounce1"></div>
					<div class="bounce2"></div>
					<div class="bounce3"></div>
				</div>
			</div>
			
			<div id="loadedPage" style="display: none">
		
				<div class="row" >
				<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<div class="row" >
		
				<div id="searchbox" class="mainbox">
					<div class="panel panel-primary" >
		
						<div class="panel-heading">
							<div class="panel-title">Търсене на Устройство</div>
						</div>
		
						<div class="panel-body">
		
							<!-- Search Request Form -->
							<form:form action="${pageContext.request.contextPath}/search/resultsItem"
									   modelAttribute="searchItem" 
									   method="POST" 
								       class="form-horizontal"
								       id="searchForm">								
								
								<div class="row item-panel"  >
								<div class="panel panel-default col-xs-12 col-sm-10 col-sm-offset-1"  >
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
								
								<div class="row item-panel"  >
								<div class="panel add-color panel-default col-xs-12 col-sm-10 col-sm-offset-1"  >
								<div class="panel-body ">
																
								<div class="form-group form-group-custom">
									<label for="objectTypesFromJSON" >Тип</label>							
									<div>
										<form:select id="objectTypesFromJSON" path="objectTypeId"  class="selectpicker  pad-left-zero col-xs-12  objectTypesFromJSON"
													data-live-search="true" data-none-results-text="Неса намерени резултати" data-none-selected-text="Избери Тип"/>									
									</div>
										<form:errors path="objectTypeId" cssClass="error"/> 
								</div>
								
								<div class="form-group form-group-custom">
									<label for="objectModelsFromJSON" >Модел</label>							
									<div>
										<form:select id="objectModelsFromJSON" path="objectModelId"  class="selectpicker  pad-left-zero col-xs-12   objectModelsFromJSON"
													data-live-search="true" data-none-results-text="Неса намерени резултати" data-none-selected-text="Избери Модел"/>									
									</div>
								</div>
								
								<div class="form-group form-group-custom">
									<label for="objectInstancesFromJSON" >Сериен номер</label>							
									<div>
										<form:select id="objectInstancesFromJSON" path="objectInstanceId"  class="selectpicker  pad-left-zero col-xs-12   objectInstancesFromJSON"
													data-live-search="true" data-none-results-text="Неса намерени резултати" data-none-selected-text="Избери Сериен номер"/>									
									</div>
								</div>										

								</div>
								</div>
								</div>

								<form:hidden path="dataTablesUniqueId" value="${searchItem.dataTablesUniqueId}" />																						
								
								<!-- Navigation buttons -->
								<div class="row">
									<div class="col-xs-4">															
									</div>
									
									<div class="col-xs-4  text-center jsonLoaderPlace" >
								        <div id="jsonLoader" >
								          <span></span>
								          <span></span>
								          <span></span>
								        </div>
								    </div>
									
									<div class="col-xs-4">
										<div class="save  text-right btn-right form-group">
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
			
			<!-- end of loaded page -->
			</div>
			
		<!-- end of container -->		
		</div>
	<!-- END JUMBOTRON -->		
	</div>
</body>
</html>
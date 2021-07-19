<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	
	<title>Редактирай Тип Устройство</title>
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


	<c:url var="findObjectTypesURL" value="/json/objectTypeList" />

	<!-- MY CUSTOM SCRIPTS -->

	<!-- import Handle db-error, session-expired, and loading animations scripts and css -->	
	<c:import url="../view/fragments/dberror-sessionexpired.jsp"></c:import>
	<c:import url="../view/fragments/animations.jsp"></c:import>

	<script type="text/javascript">
		
		var typeId = <c:out value="${editObjectType.typeId}" />;
				
	</script>

	<!-- Script FullPage loading animation logic. PLACE AFTER typeId is set-->
	<script src="<c:url value="/resources/js/show-hide-loading.js"/>"></script>



	<!-- Script for populating Object Type selectpicker -->
	<script type="text/javascript">
		
		var runOnceType = true;		
		
		var closingScreenRunOnceType = true;
		
		$(document).ready(
				function () {
					
					// console.log('Populating object type');
					
					$.getJSON('${findObjectTypesURL}', {
						ajax : 'true'
					}, function(data) {
						 var jsonData = JSON.stringify(data);
					     $.each(JSON.parse(jsonData), function (idx, obj) {
					        $('#objectTypesFromJSON').append('<option value="' + obj.id + '">' + obj.type + '</option>');				       
					     });

					     //TEST slectpicker.refresh  // Setting the Default Value for Object Type to NULL-->
					     $('#objectTypesFromJSON').selectpicker('refresh').selectpicker('val', null);					     
									    	 
					    	 
							//After selecting Edit, select the Object Type
							if(runOnceType == true && typeId > 0){							
								runOnceType = false;
									
	//							console.log('Type ID is: ' + typeId);
									
								$('#objectTypesFromJSON').selectpicker('val', typeId).change(); 
								
								hideLoading();
							}			     
					})  .fail(function(jqxhr, textStatus, error){
							dbErrorAndSessionExpiredHandler (jqxhr, textStatus, error);
							}
						);
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
		
				<div id="inputbox" class="mainbox">
					<div class="panel edit-color panel-edit" >
		
						<div class="panel-heading edit-color-heading">
							<div class="panel-title">Редактирай Тип устройство</div>
						</div>
		
						<div class="panel-body">
		
							<!-- Add Type Form -->
							<form:form action="${pageContext.request.contextPath}/componentEdit/reviewEditType"
									   modelAttribute="editObjectType" 
									   method="POST" 
								       class="form-horizontal"
								       id="editTypeForm">								
									
								<div class="row item-panel"  >
								<div class="panel edit-color panel-default col-xs-12 col-sm-10 col-sm-offset-1" >
								<div class="panel-body">
								
								<!-- Select Type Name -->																        									 								
								<div class="row form-group form-group-custom">
									<label for="objectTypesFromJSON" >Тип за редактиране</label>							
									<div>
										<form:select id="objectTypesFromJSON" path="typeId"  class="selectpicker pad-left-zero col-xs-12 objectTypesFromJSON"
													data-live-search="true" data-none-results-text="Неса намерени резултати" data-none-selected-text="Избери Тип"/>									
									</div>
										<form:errors path="typeId" cssClass="error"/> 
								</div>																
								
								
								<!-- Input New Type Name -->																        									 								
								<div class="row form-group form-group-custom">
									<div>																																	
										<label for="number">Ново наименование на Типа</label>																													
									</div>
									<div class="pad-left-zero col-xs-12">
										<form:input id="type" type="text" class="form-control" path="type" autocomplete="off" />																				 
								    </div>
								    <form:errors path="type"  cssClass="error"/>														    
								</div>
								
								</div>
								</div>
								</div>
								
								<!-- Navigation buttons -->
								<div class="row">
									<div class="col-xs-4">								
										<div class="cancel text-left btn-left" >
											<a class="btn btn-default"  href="${pageContext.request.contextPath}/componentEdit/editItem" role="button">
											<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Отказ</a>
										</div>								
									</div>
									
									<div class="col-xs-4 text-center jsonLoaderPlace">
								        <div id="jsonLoader" >
								          <span></span>
								          <span></span>
								          <span></span>
								        </div>
								    </div>
									
									<div class="col-xs-4">
										<div class="save  text-right btn-right form-group">
											<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Потвърди</button>										 								
										</div>								
									</div>									
								</div>								
								
							</form:form>							
						</div>
					</div>
				<!-- end of input -->							
				</div>
				
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
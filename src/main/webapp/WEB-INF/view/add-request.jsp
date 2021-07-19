<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	
	<title>Добавяне на нова Заявка</title>
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
	<script src="<c:url value="/resources/js/bootstrap-datepicker.new.bg.min.js"/>" charset="UTF-8"></script>
	
	
	
	<!-- CSS Style for Request Form -->
	<link rel="stylesheet" href="<c:url value="/resources/css/request-form.css"/>" type="text/css" />
	
	

	<c:url var="findObjectTypesURL" value="/json/objectTypeList" />
	<c:url var="findObjectModelsURL" value="/json/objectModelList" />
	<c:url var="findObjectInstancesURL" value="/json/objectInstanceList" />

	<!-- MY CUSTOM SCRIPTS -->

	<!-- import Handle db-error, session-expired, and loading animations scripts and css -->
	<c:import url="../view/fragments/dberror-sessionexpired.jsp"></c:import>	
	<c:import url="../view/fragments/animations-request-form.jsp"></c:import>
	
	<script type="text/javascript">
		var customFindObjectTypesURL = "${findObjectTypesURL}";
		var customFindObjectModelsURL = "${findObjectModelsURL}";
		var customFindObjectInstancesURL = "${findObjectInstancesURL}";
	
	</script>


	<!-- On Initial page load display custom message inside selectpicker for Requester, requesterId = 0.  -->
	<script type="text/javascript">
	
	var requesterId = <c:out value="${helperForm.requesterId}" />;
	$(document).ready(
			function () {				
				if(requesterId == 0){
					$('#requester').selectpicker('refresh').selectpicker('val', null);
				}	
			});
	
	</script>

	<!-- Customised Datepicker config -->
	<script src="<c:url value="/resources/js/customised-datepicker-config.js"/>"></script>


	<!-- TESTING  -->

	<!-- Defining variables for SelectPicker InService and In Operation from the model attribute "helperForm" -->
	<script type="text/javascript">
	
		var checkBox = {
				InService : <c:out value="${helperForm.checkBoxInService}" />,
				InOperation : <c:out value="${helperForm.checkBoxInOperation}" />
		}
		
		var objectTypeId = {
				InService : <c:out value="${helperForm.objectTypeIdInService}" />,
				InOperation : <c:out value="${helperForm.objectTypeIdInOperation}" />
		}
		
		var objectModelId = {
				InService : <c:out value="${helperForm.objectModelIdInService}" />,
				InOperation : <c:out value="${helperForm.objectModelIdInOperation}" />
		}
		
		var objectInstanceId = {
			InService : <c:out value="${helperForm.objectInstanceIdInService}" />,
			InOperation : <c:out value="${helperForm.objectInstanceIdInOperation}" />
		}
		
		var savedCheckBox = {
				InService : false,
				InOperation : false
		}

	</script>

	<!-- Script for initial show/hide loading : request-initial-show-hide-loading.js-->
	<script src="<c:url value="/resources/js/request-initial-show-hide-loading.js"/>"></script>

	<!-- Script for checkBoxInService to enable/disable selectpicker , NEW SCRIPT CREATED: request-checkbox-enable-dasable.js-->
	<script src="<c:url value="/resources/js/request-checkbox-enable-disable.js"/>"></script>

	<!-- Script for populating Object Type selectpicker , NEW SCRIPT CREATED: request-populate-object_type-selectpicker.js -->
	<script src="<c:url value="/resources/js/request-populate-object_type-selectpicker.js"/>"></script>

	<!-- Script for populating Object Model selectpicker when Object Type is selected, NEW SCRIPT CREATED: request-populate-object_model-selectpicker.js -->
	<script src="<c:url value="/resources/js/request-populate-object_model-selectpicker.js"/>"></script>

	<!-- Script for populating Object Instance selectpicker when Object Model is selected, NEW SCRIPT CREATED: request-populate-object_instance-selectpicker.js  -->
	<script src="<c:url value="/resources/js/request-populate-object_instance-selectpicker.js"/>"></script>

	<!-- TESTING BUTTON ENABLE/DISABLE , NEW SCRIPT CREATED: request-copybutton-enable-disable.js-->
	<script src="<c:url value="/resources/js/request-copybutton-enable-disable.js"/>"></script>

	<!-- TESTING COPING SELECTED DATA TO OPERATION,  NEW SCRIPT CREATED: request-copy-data-to-selectpicker.js-->
	<script src="<c:url value="/resources/js/request-copy-data-to-selectpicker.js"/>"></script>


<script type="text/javascript">
/* $(document).ready(
		function() { 
			
			if (
					objectTypeId['InService'] > 0 || objectTypeId['InOperation']	> 0 ||
					objectModelId['InService'] > 0 || objectModelId['InOperation'] > 0 ||
					objectInstanceId['InService'] > 0 || objectInstanceId['InOperation'] > 0		
			){
				
				closingScreenRunOnceType = true;
				closingScreenRunOnceModel = true;
//				closingScreenRunOnceInstance = true;
				
			}else{
				closingScreenRunOnceType = false;
				closingScreenRunOnceModel = false;
//				closingScreenRunOnceInstance = false;
			}
			

		}); */
</script>


</head>

<body>


	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>
	
	<div class="jumbotron vertical-center " id="mainView">
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
			<div class="row">
				<div id="newRequest" class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
					<div class="panel panel-primary" >
		
						<div class="panel-heading">
							<div class="panel-title">Добавяне на НОВА Заявка</div>
						</div>
		
						<div class="panel-body" >
		
						<div class="row internal-padding">
						<div class="col-xs-12" >
		
							<!-- Registration Form -->
							<form:form action="${pageContext.request.contextPath}/requestForm/review"
									   modelAttribute="helperForm" 
									   method="POST" 
								       class="form-horizontal"
								       id="registrationForm">
								
								<!-- Adding Date -->
								<div class="row form-group form-group-custom date">								
									<div>
										<label for="date">Дата</label>
									</div>										
									<div class="col-xs-12 col-sm-10 col-md-8 col-lg-8 input-group">								
										<form:input type="text" class="form-control" path="dateSelected" autocomplete="off" placeholder="Избери Дата" onkeydown="return false"  />	
										<span class="input-group-addon" > <i class="glyphicon glyphicon-calendar"></i></span>				
									</div>	
		  							<form:errors path="dateSelected"  cssClass="error"/>	
								</div>
								
								<!-- Adding Requester -->							
								<div class="row form-group form-group-custom"  >
									<div>
										<label for="requester">Заявител</label>	
									</div>						
									<div>
										<form:select id="requester" path="requesterId"  class="selectpicker pad-left-zero col-xs-12 col-sm-10 col-md-8 col-lg-8" 
													data-live-search="true" data-none-results-text="Неса намерени резултати" data-none-selected-text="Избери Заявител">															
											<form:options items="${requestersMap}" />									
										</form:select> 				
									</div>
									<form:errors path="requesterId" cssClass="error"/>
								</div>			 
								
								<!-- Adding Requester comment -->
								<div class="row form-group form-group-custom">
									<div>																										
										<label for="requesterComment">Коментар (Заявител)</label>
									</div>					
									<div class="pad-left-zero col-xs-12 col-sm-10 ">
										<form:textarea type="text" rows="3" class="form-control" path="requesterComment" autocomplete="off" />				
									</div>
									<form:errors path="requesterComment" cssClass="error"/>									
								</div>	
									
								<!-- Adding Object In Service section  NEW NEW NEW -->		
								
								<div class="row item-panel">
									<div> <!-- removed class="checkbox" -->
										<label for="checkBoxInService" class="checkBox">Устройство в Сервиз </label> 
											<form:checkbox id="checkBoxInService"  class="checkBox" path="checkBoxInService" />	
											<form:errors path="itemSelectedValidation" cssClass="error"/> 					    
									</div>
								</div>
		
								<div class="row item-panel"  >
									<div class="panel panel-default col-xs-12 col-sm-10" >
										<div class="panel-body">
		
											<div class="form-group form-group-custom">
												<label for="objectTypesFromJSONInService" class="labelNormal">Тип</label>							
												<div>
													<form:select id="objectTypesFromJSONInService" path="objectTypeIdInService"  class="selectpicker pad-left-zero col-xs-12 col-sm-10 objectTypesFromJSON"
																 data-live-search="true" data-none-results-text="Неса намерени резултати" data-none-selected-text="Избери Тип" disabled="true"/>									
												</div>
													<form:errors path="objectTypesMatchValidation" cssClass="error"/>
													<form:errors path="objectTypeIdInService" cssClass="error"/> 
											</div>
					
											<div   class="form-group form-group-custom">
												<label for="objectModelsFromJSONInService" class="labelNormal">Модел</label>
												<div>
													<form:select id="objectModelsFromJSONInService" path="objectModelIdInService"  class="selectpicker pad-left-zero col-xs-12 col-sm-10 objectModelsFromJSON"
																 data-live-search="true" data-none-results-text="Неса намерени резултати" data-none-selected-text="Избери Модел" disabled="true"/>
												</div>
												<form:errors path="objectModelIdInService" cssClass="error"/>
											</div>
											
											<div   class="form-group form-group-custom">
												<label for="objectInstancesFromJSONInService" class="labelNormal">Сериен номер</label>		
												<div>
													<form:select id="objectInstancesFromJSONInService" path="objectInstanceIdInService"  class="selectpicker pad-left-zero col-xs-12 col-sm-10"
																 data-live-search="true" data-none-results-text="Неса намерени резултати" data-none-selected-text="Избери Сериен номер" disabled="true"/>		 									 		 
												</div>
												<form:errors path="serviceSelectedValidation" cssClass="error"/>
												<form:errors path="objectInstanceIdInService" cssClass="error"/> 
											</div>
											
											<button type="button" id="copyToOperation" class="btn btn-success btn-xs copyButton" disabled="disabled">
												<span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span> Копирай в Експлоатация
											</button>
								
										</div>
									</div>
								</div>

								<!-- Select Loader -->
								<div class="row"  >										
									<div class="col-xs-3"  >
								        <div class="jsonLoaderPlaceHight" >
								        </div>
								    </div>
									<div class="col-xs-6 text-center" >
								        <div id="jsonLoader" >
								          <span></span>
								          <span></span>
								          <span></span>
								        </div>
								    </div>
								</div>
								   
											
								
								<!-- Adding Object In Operation section  NEW NEW NEW -->		
								<div class="row item-panel">
									<label for="checkBoxInOperation" class="checkBox">Устройство в Експлоатация</label> 
										<form:checkbox id="checkBoxInOperation"  class="checkBox" path="checkBoxInOperation" />	
										<form:errors path="itemSelectedValidation" cssClass="error"/> 	
								</div>
		
								<div class="row item-panel"  >
									<div class="panel panel-default col-xs-12 col-sm-10">
										<div class="panel-body">
											<div class="form-group form-group-custom">
												<label for="objectTypesFromJSONInOperation" class="labelNormal">Тип</label>							
												<div>
													<form:select id="objectTypesFromJSONInOperation" path="objectTypeIdInOperation"  class="selectpicker  pad-left-zero col-xs-12 col-sm-10 objectTypesFromJSON"
																 data-live-search="true" data-none-results-text="Неса намерени резултати"  data-none-selected-text="Избери Тип" disabled="true"/>																
												</div>
												<form:errors path="objectTypesMatchValidation" cssClass="error"/>
												<form:errors path="objectTypeIdInOperation" cssClass="error"/>  
											</div>
					
											<div class="form-group form-group form-group-custom">
												<label for="objectModelsFromJSONInOperation" class="labelNormal">Модел</label>
												<div>
													<form:select id="objectModelsFromJSONInOperation" path="objectModelIdInOperation"  class="selectpicker  pad-left-zero col-xs-12 col-sm-10 objectModelsFromJSON"
																 data-live-search="true" data-none-results-text="Неса намерени резултати"  data-none-selected-text="Избери Модел" disabled="true"/>
												</div>
												<form:errors path="objectModelIdInOperation" cssClass="error"/>
											</div>
											
											<div class="form-group form-group form-group-custom">
												<label for="objectInstancesFromJSONInOperation" class="labelNormal">Сериен номер</label>		
												<div>
													<form:select id="objectInstancesFromJSONInOperation" path="objectInstanceIdInOperation"  class="selectpicker  pad-left-zero col-xs-12 col-sm-10"
																 data-live-search="true" data-none-results-text="Неса намерени резултати"  data-none-selected-text="Избери Сериен номер" disabled="true"/>																		 
												</div>
												<form:errors path="operationSelectedValidation" cssClass="error"/>
												<form:errors path="objectInstanceIdInOperation" cssClass="error"/> 
											</div>
											
											<button type="button" id="copyToService" class="btn btn-success btn-xs copyButton" disabled="disabled">
												<span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span> Копирай в Сервиз
											</button>
										</div>
									</div>
								</div>
								
	
								
								<!-- Adding Item comment -->
								<div class="row form-group form-group-custom">																												
									<div>
										<label for="itemComment">Коментар (Устройство)</label>
									</div>				
									<div class="pad-left-zero col-xs-12 col-sm-10 ">
										<form:textarea type="text" rows="3" class="form-control" path="itemComment" autocomplete="off" />				
									</div>
									<form:errors path="itemComment" cssClass="error"/>									
								</div>	
								
								<!-- Submit / Cancel Button -->
								<div class="row">								
									<div class="col-xs-6">
										<div class="back text-left btn-left">												
											<a class="btn btn-default" href="${pageContext.request.contextPath}/" role="button">
											<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Отказ</a>								
										</div>							
									</div>
								
									<div class="col-xs-6" >									
										<div class="submit text-right btn-right form-group">																															
											<button type="submit" class="btn btn-primary"> 
												<span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span> Потвърди</button>																													
										</div>									
									</div>															
								</div>
								
							</form:form>
						
						</div>
						</div>			
							
						</div><!-- panel-body end -->
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
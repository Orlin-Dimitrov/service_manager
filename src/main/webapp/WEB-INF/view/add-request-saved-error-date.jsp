<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	<title>Грешка Запис Заявка</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	
	<!-- style for page shape -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/page_shape.css" />
		  
	<!-- import Bootstrap and jQuery  ATTENTION !!! bootstrap must be inserted after page shape css-->	  
	<c:import url="../view/fragments/bootstrap.jsp"></c:import>
	
	<!-- Style of the jumbotron for vertical centering when using navbar -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/jumbotron-vertCenter-when-navbar-used.css">
	
	<!-- style result panel -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/panel-result-custom.css" />
	

</head>

<body>
	
	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>
	

	<div class="jumbotron vertical-center">
		<div class="container">
		
				<div class="row" >
				<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<div class="row" >
		
				<div id="info-box" class="mainbox">
					<div class="panel panel-red">							
						<div class="panel-heading panel-red-heading">
							<div class="panel-title text-center">Грешка Запис Заявка!</div>
						</div>

						<div class="panel-body">																
							<div class="well well-result text-center" >
								
								<span class="glyphicon glyphicon-warning-sign red-text" aria-hidden="true"></span>
								&nbsp;Записана е Заявка с по-нова Дата!											
								
							</div>								
							
							<div class="row"> 	   
						        <div class="text-center">
									<form:form action="${pageContext.request.contextPath}/requestForm/add"
														   modelAttribute="helperForm"
													       class="form-horizontal"
													       method="POST">
										<form:hidden path="requesterId" value="${helperFormSaving.requesterId}" />
										<form:hidden path="dateSelected" value="${helperFormSaving.dateSelected}" />
										<form:hidden path="requesterComment" value="${helperFormSaving.requesterComment}" />
										
										<form:hidden path="checkBoxInService" value="${helperFormSaving.checkBoxInService}" />
										<form:hidden path="objectTypeIdInService" value="${helperFormSaving.objectTypeIdInService}" />
										<form:hidden path="objectModelIdInService" value="${helperFormSaving.objectModelIdInService}" />
										<form:hidden path="objectInstanceIdInService" value="${helperFormSaving.objectInstanceIdInService}" />
										
										<form:hidden path="checkBoxInOperation" value="${helperFormSaving.checkBoxInOperation}" />
										<form:hidden path="objectTypeIdInOperation" value="${helperFormSaving.objectTypeIdInOperation}" />
										<form:hidden path="objectModelIdInOperation" value="${helperFormSaving.objectModelIdInOperation}" />
										<form:hidden path="objectInstanceIdInOperation" value="${helperFormSaving.objectInstanceIdInOperation}" />
																												
										<form:hidden path="itemComment" value="${helperFormSaving.itemComment}" />
																			
										
										<div style="margin-top: 10px" class="form-group">						
											<button type="submit" class="btn btn-primary btn-center">Редактирай</button>										
										</div>	       
									</form:form>
						        </div>	
							</div><!-- end row -->
							
						</div><!-- end of panel body -->
					</div><!-- end of panel -->					
				</div><!-- end of mainbox -->
				
				</div>
				</div>
				</div>							
		</div><!-- end of container -->	
	</div><!-- END JUMBOTRON -->	
</body>

</html>

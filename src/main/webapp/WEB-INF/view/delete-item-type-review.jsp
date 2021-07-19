<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	<title>Преглед на Изтриване Тип Устройство</title>
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

	<!-- CSS Style for Object Review -->
	<link rel="stylesheet" href="<c:url value="/resources/css/object-review.css"/>" type="text/css" />
	
	<!-- Script for checkBoxConfirm to enable/disable saveButton -->
	<script src="<c:url value="/resources/js/checkbox-review.js"/>"></script>
	
</head>

<body>
	
	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>
	

	<div class="jumbotron vertical-center " >
		<div class="container">

			<div class="row" >
			<div class="col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2 ">
			<div class="panel  main-panel-review-object" >
			<div class="panel-body ">

					<div class="row" >
						<div class="col-xs-12" >				
							<div class="panel panel-object" id="panelObjectInfo">
								<div class="panel-heading ">Устройство за Изтриване</div>
							
								<table class="table ">								
									<tr>
										<td class="col-xs-4 right">Tип </td>
										<td class="col-xs-8 text-center active-object" >${deleteObjectType.type}</td>
									</tr>
								</table>
							</div>
						</div>
					</div><!-- end of first row -->
					
					<div class="row">
						<div class="col-xs-12 " >	
							<div class="panel panel-danger">
								<div class="panel-heading text-center">ВНИМАНИЕ! ВНИМАНИЕ! ВНИМАНИЕ!</div>
								<div class="panel-body text-center">
									<div>Изтриване на Тип устройство от База Данни</div>
									
									<div class="checkBoxConfirm">    
										<label for="checkBoxConfirm">Разбирам.</label> 
										<input type="checkbox"  id="checkBoxConfirm" />
									</div>	
								</div>	
							</div>
						</div>
					</div><!-- end of second row -->

					<div class="row ">
						<div class="col-xs-6 " >					  
							<div class="text-left form-group btn-left">
								<form:form action="${pageContext.request.contextPath}/componentDelete/deleteType"
													   modelAttribute="deleteObjectType"
												       class="form-horizontal"
												       method="POST">

									<form:hidden id="typeIdEdit" path="typeId" value="${deleteObjectType.typeId}" />									
					
										<button type="submit" class="btn btn-primary">
											<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Редактирай
										</button>
										
										&nbsp;
																				
										<a class="btn btn-default" href="${pageContext.request.contextPath}/componentDelete/deleteItem" role="button">
											<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Отказ
										</a>
								
								</form:form>	
							</div>
						</div>			  	
						
						<div class="col-xs-6">							
							<div class="save text-right form-group btn-right">
								<form:form action="${pageContext.request.contextPath}/componentDelete/saveDeleteType"
													   modelAttribute="deleteObjectTypeForSaving"
												       class="form-horizontal"
												       method="POST">
												       
									<form:hidden id="typeIdSave" path="typeId" value="${deleteObjectType.typeId}" />
					       
										<button type="submit" id="saveButton" class="btn btn-danger" disabled="disabled">
											<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Изтрий
										</button>
								       
								</form:form>
							</div>
						</div>						
					</div><!-- end of third row -->															
							
			</div>	
			</div><!-- Outer Panel -->
			</div>
			</div><!-- Outer row -->
			
		</div><!-- end of container -->	
	</div><!-- end of jumbotron -->	
</body>

</html>











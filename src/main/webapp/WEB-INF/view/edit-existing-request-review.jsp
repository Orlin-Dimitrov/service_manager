<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	<title>Преглед актуализиране на Заявка</title>
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
								<div class="panel-heading ">Иформация за Заявка</div>
							
								<table class="table ">								

									<thead>
										<tr>
										<th class="col-xs-4 col-sm-4 right">Заявка N </th>
										<th class="col-xs-8 col-sm-8 text-center">${requestReviewObject.requestId}</th>
										</tr>
									</thead>

									<tbody>
										<tr>
											<td class="right">Дата</td>
											<td>${requestReviewObject.date}</td>
										</tr>
										
										<tr>
											<td class="right">Заявител</td>
											<td>${requestReviewObject.requester}</td>
										</tr>
										<tr>
											<td class="right">Коментар (Заявител)</td>
											<td>${requestReviewObject.requesterComment}</td>
										</tr>
												
										<tr>
											<td class="right"><span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>&nbsp;Устройство в Сервиз</td>
											<td>${requestReviewObject.objectTypeInService}</td>
										</tr>
											
										<tr>
											<td class="right">Модел</td>
											<td>${requestReviewObject.objectModelInService}</td>
										</tr>
												
										<tr>
											<td class="right">Сериен номер</td>
											<td>${requestReviewObject.objectInstanceInService}</td>
										</tr>
												
										<tr>
											<td class="right"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span>&nbsp;Устройство в Експлоатация</td>
											<td>${requestReviewObject.objectTypeInOperation}</td>
										</tr>
											
										<tr>
											<td class="right">Модел</td>
											<td>${requestReviewObject.objectModelInOperation}</td>
										</tr>
												
										<tr>
											<td class="right">Сериен номер</td>
											<td>${requestReviewObject.objectInstanceInOperation}</td>
										</tr>
																	
										<tr>
											<td class="right">Коментар (Устройство)</td>
											<td>${requestReviewObject.itemComment}</td>
											</tr>		
									</tbody>																											
								</table>
							</div>
						</div>
					</div><!-- end of first row -->	

					<div class="row">
						<div class="col-xs-12 " >	
							<div class="panel panel-danger">
								<div class="panel-heading text-center">ВНИМАНИЕ! ВНИМАНИЕ! ВНИМАНИЕ!</div>
								<div class="panel-body text-center">
									<div>Заявител и Устройство може да бъде променяно САМО ОТ Администратор! ЗАПИСВА СЕ името на потребителя въвел / редактирал Заявката!
									</div>
									
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
								<form:form action="${pageContext.request.contextPath}/requestFormUpdate/update"
													   modelAttribute="helperFormExistingRequest"
												       class="form-horizontal"
												       method="POST">
												       
									<form:hidden id="requestIdEdit" path="requestId" value="${helperFormExistingRequest.requestId}" />			       
									<form:hidden id="requesterNameEdit" path="requesterName" value="${helperFormExistingRequest.requesterName}" />
									<form:hidden id="dateSelectedEdit" path="dateSelected" value="${helperFormExistingRequest.dateSelected}" />
									<form:hidden id="requesterCommentEdit" path="requesterComment" value="${helperFormExistingRequest.requesterComment}" />
									
									<form:hidden id="checkBoxInServiceEdit" path="checkBoxInService" value="${helperFormExistingRequest.checkBoxInService}" />
									<form:hidden id="objectTypeIdInServiceEdit" path="objectTypeIdInService" value="${helperFormExistingRequest.objectTypeIdInService}" />
									<form:hidden id="objectModelIdInServiceEdit" path="objectModelIdInService" value="${helperFormExistingRequest.objectModelIdInService}" />
									<form:hidden id="objectInstanceIdInServiceEdit" path="objectInstanceIdInService" value="${helperFormExistingRequest.objectInstanceIdInService}" />
									
									<form:hidden id="checkBoxInOperationEdit" path="checkBoxInOperation" value="${helperFormExistingRequest.checkBoxInOperation}" />
									<form:hidden id="objectTypeIdInOperationEdit" path="objectTypeIdInOperation" value="${helperFormExistingRequest.objectTypeIdInOperation}" />
									<form:hidden id="objectModelIdInOperationEdit" path="objectModelIdInOperation" value="${helperFormExistingRequest.objectModelIdInOperation}" />
									<form:hidden id="objectInstanceIdInOperationEdit" path="objectInstanceIdInOperation" value="${helperFormExistingRequest.objectInstanceIdInOperation}" />
																											
									<form:hidden id="itemCommentEdit" path="itemComment" value="${helperFormExistingRequest.itemComment}" />
									
									<form:hidden id="savedCheckBoxInServiceEdit" path="savedCheckBoxInService" value="${helperFormExistingRequest.savedCheckBoxInService}" />
									<form:hidden id="savedCheckBoxInOperationEdit" path="savedCheckBoxInOperation" value="${helperFormExistingRequest.savedCheckBoxInOperation}" />							
					
										<button type="submit" class="btn btn-primary">
											<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Редактирай
										</button>
										
										&nbsp;
																				
										<a class="btn btn-default" href="${pageContext.request.contextPath}/" role="button">
											<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Отказ
										</a>
								
								</form:form>	
							</div>
						</div>			  	
						
						<div class="col-xs-6">							
							<div class="save text-right form-group btn-right">
								<form:form action="${pageContext.request.contextPath}/requestFormUpdate/saveExisting"
													   modelAttribute="helperFormSavingUpdate"
												       class="form-horizontal"
												       method="POST">
												       
									<form:hidden id="requestIdSave" path="requestId" value="${helperFormExistingRequest.requestId}" />			       
									<form:hidden id="requesterNameSave" path="requesterName" value="${helperFormExistingRequest.requesterName}" />
									<form:hidden id="dateSelectedSave" path="dateSelected" value="${helperFormExistingRequest.dateSelected}" />
									<form:hidden id="requesterCommentSave" path="requesterComment" value="${helperFormExistingRequest.requesterComment}" />
									
									<form:hidden id="checkBoxInServiceSave" path="checkBoxInService" value="${helperFormExistingRequest.checkBoxInService}" />
									<form:hidden id="objectTypeIdInServiceSave" path="objectTypeIdInService" value="${helperFormExistingRequest.objectTypeIdInService}" />
									<form:hidden id="objectModelIdInServiceSave" path="objectModelIdInService" value="${helperFormExistingRequest.objectModelIdInService}" />
									<form:hidden id="objectInstanceIdInServiceSave" path="objectInstanceIdInService" value="${helperFormExistingRequest.objectInstanceIdInService}" />
									
									<form:hidden id="checkBoxInOperationSave" path="checkBoxInOperation" value="${helperFormExistingRequest.checkBoxInOperation}" />
									<form:hidden id="objectTypeIdInOperationSave" path="objectTypeIdInOperation" value="${helperFormExistingRequest.objectTypeIdInOperation}" />
									<form:hidden id="objectModelIdInOperationSave" path="objectModelIdInOperation" value="${helperFormExistingRequest.objectModelIdInOperation}" />
									<form:hidden id="objectInstanceIdInOperationSave" path="objectInstanceIdInOperation" value="${helperFormExistingRequest.objectInstanceIdInOperation}" />
																											
									<form:hidden id="itemCommentSave" path="itemComment" value="${helperFormExistingRequest.itemComment}" />
									
									<security:authentication var="user" property="principal.username"/>
									<form:hidden id="userNameSave" path="userName" value="${user} " />	
					       
										<button type="submit" id="saveButton" class="btn btn-danger" disabled="disabled">
											<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Запиши
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











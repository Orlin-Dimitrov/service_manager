<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	<title>Иформация за Заявка</title>

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

	<!-- Style for printing info page -->
	<link type="text/css"
			  rel="stylesheet"
			  href="${pageContext.request.contextPath}/resources/css/print-info-page.css">
			  
	<!-- Script for printing -->
	<script src="<c:url value="/resources/js/print-info-page.js" />"></script>
		  
</head>

<body>
	
	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>

	<div class="jumbotron vertical-center">
		<div class="container">

			<div class="row" >
			<div class="col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2 ">
			
				<div class="text-right btn-pdf-print ">	
					<a class="btn btn-default btn-sm hidden-print" href="${pageContext.request.contextPath}/requests/${existingRequest.id}/pdf" role="button" data-toggle="tooltip" data-placement="top" title="Запамети в PDF">
						<span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span>
					</a>
												
					<button class="btn btn-default btn-sm hidden-print"  onclick="printFunction()" data-toggle="tooltip" data-placement="top" title="Отпечатай"><span class="glyphicon glyphicon-print" aria-hidden="true"></span></button>								
				</div>
				
			<div class="panel  main-panel-review-object" >
			<div class="panel-body ">

					<div class="row" >
						<div class="col-xs-12" >
							<div class="panel panel-object" id="panelObjectInfo">
								<div class="panel-heading ">Иформация за Заявка</div>
							
								<table class="table ">								
									<thead>
										<tr>
											<th class="col-xs-4 col-sm-4 right"><span class="glyphicon glyphicon-tag" aria-hidden="true"></span>&nbsp;Заявка N </th>
											<th class="col-xs-8 col-sm-8 text-center">${existingRequest.id}</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="right">Дата</td>
											<td>${formatedDate}</td>
										</tr>
											
										<tr>
											<td class="right">Заявител</td>
											<td>${existingRequest.requester.name}</td>
										</tr>
										<tr>
											<td class="right">Коментар (Заявител)</td>
											<td>${existingRequest.requesterComment}</td>
										</tr>
													
										<tr>
											<td class="right"><span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>&nbsp;Устройство в Сервиз</td>
											<td>${existingRequest.itemInService.objectModel.objectType.type}</td>
										</tr>
												
										<tr>
											<td class="right">Модел</td>
											<td>${existingRequest.itemInService.objectModel.model}</td>
										</tr>
													
										<tr>
											<td class="right">Сериен номер</td>
											<td>${existingRequest.itemInService.serialNumber}</td>
										</tr>
													
										<tr>
											<td class="right"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span>&nbsp;Устройство в Експлоатация</td>
											<td>${existingRequest.itemInOperation.objectModel.objectType.type}</td>
										</tr>
												
										<tr>
											<td class="right">Модел</td>
											<td>${existingRequest.itemInOperation.objectModel.model}</td>
										</tr>
													
										<tr>
											<td class="right">Сериен номер</td>
											<td>${existingRequest.itemInOperation.serialNumber}</td>
										</tr>
																		
										<tr>
											<td class="right">Коментар (Устройство)</td>
											<td>${existingRequest.itemComment}</td>
										</tr>
										
										<tr>
											<td class="right"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;Въведено от потребител</td>
											<td>${existingRequest.userName}</td>
										</tr>
										
										<tr>
											<td class="right">Дата на последна Промяна</td>
											<td>${formatedModificationDate}</td>
										</tr>
												
									</tbody>																										
								</table>
							</div>
						</div>
					</div><!-- end of first row -->	
					

					<div class="row ">
						<div class="col-xs-6 " >					  
							<div class="text-left form-group btn-left">
								<form:form action="${pageContext.request.contextPath}/search/resultsDateRequester"
															   modelAttribute="searchDateRequester"
														       class="form-horizontal"
														       method="POST">
											
									<form:hidden path="dateStart" value="${searchDateRequesterToInfo.dateStart}" />
									<form:hidden path="dateEnd" value="${searchDateRequesterToInfo.dateEnd}" />	
									<form:hidden path="requesterId" value="${searchDateRequesterToInfo.requesterId}" />	
											
									<form:hidden path="dataTablesUniqueId" value="${searchDateRequesterToInfo.dataTablesUniqueId}" />																				
																	
									<button type="submit" class="btn btn-default hidden-print">
										<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Към Списък
									</button>										
											       
								</form:form>
							</div>
						</div>			  	
						
						<div class="col-xs-6">							
							<div class="update text-right form-group btn-right">
								<form:form action="${pageContext.request.contextPath}/requestFormUpdate/update"
													   modelAttribute="helperFormExistingRequest"
												       class="form-horizontal"
												       method="POST">

									<form:hidden path="requestId" value="${helperFormExistingRequest.requestId}" />			       
									<form:hidden path="requesterName" value="${helperFormExistingRequest.requesterName}" />
									<form:hidden path="dateSelected" value="${helperFormExistingRequest.dateSelected}" />
									<form:hidden path="requesterComment" value="${helperFormExistingRequest.requesterComment}" />
									
									<form:hidden path="checkBoxInService" value="${helperFormExistingRequest.checkBoxInService}" />
									<form:hidden path="objectTypeIdInService" value="${helperFormExistingRequest.objectTypeIdInService}" />
									<form:hidden path="objectModelIdInService" value="${helperFormExistingRequest.objectModelIdInService}" />
									<form:hidden path="objectInstanceIdInService" value="${helperFormExistingRequest.objectInstanceIdInService}" />
									
									<form:hidden path="checkBoxInOperation" value="${helperFormExistingRequest.checkBoxInOperation}" />
									<form:hidden path="objectTypeIdInOperation" value="${helperFormExistingRequest.objectTypeIdInOperation}" />
									<form:hidden path="objectModelIdInOperation" value="${helperFormExistingRequest.objectModelIdInOperation}" />
									<form:hidden path="objectInstanceIdInOperation" value="${helperFormExistingRequest.objectInstanceIdInOperation}" />
																											
									<form:hidden path="itemComment" value="${helperFormExistingRequest.itemComment}" />
									
									<form:hidden path="savedCheckBoxInService" value="${helperFormExistingRequest.checkBoxInService}" />
									<form:hidden path="savedCheckBoxInOperation" value="${helperFormExistingRequest.checkBoxInOperation}" />
																											
																		
									<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
															
										<button type="submit" class="btn btn-success hidden-print">
											<span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span> Актуализирай
										</button>
										
									</security:authorize>										
									     
								</form:form>
							</div>
						</div>						
					</div><!-- end of second row -->	
								
			</div>	
			</div><!-- Outer Panel -->
			</div>
			</div><!-- Outer row -->	
	
		</div><!-- end of container-->	
	</div><!-- end of jumbotron-->		

</body>

</html>











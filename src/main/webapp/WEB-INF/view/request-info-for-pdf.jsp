<?xml version="1.0" encoding="utf-8"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html >

<html >
<head>
	<title>Request Info</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"></meta>

	<link rel="stylesheet" type="text/css" media="print" href="bootstrap.min.css"></link>
	<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"/>

<style>

@font-face {
  font-family: Open Sans;
  src: url(${pageContext.request.contextPath}/resources/fonts/open-sans.ttf);

}

@font-face {
  font-family: GLYPHICONS Halflings;
  src: url(${pageContext.request.contextPath}/resources/fonts/glyphicons-halflings-regular.ttf);
 
}

@page { size: letter; 
}


body{font-family: "Open Sans";
}


th {
	text-align: center;
	vertical-align: middle !important; /* override bootstrap settings */	
}

th.right {
	padding-right: 1 !important;
    padding-left: 1 !important;
    text-align: right;   
}

td {
	text-align: center;
	vertical-align: middle !important;	
}

td.right {
	font-weight: bold;	
	text-align: right;
	padding-right: 1 !important;
    padding-left: 1 !important;
}

.panel-heading {
	font-weight: bold;
}

.panel-body {
	padding-bottom:0 !important;	
}


.panel-default .panel-heading {
	background-color: #E8E8E8 !important;
	border:0px solid black !important;
}

table.table-bordered{
    border:1px solid black !important;
     
  }
table.table-bordered > thead > tr > th{
    border:1px solid black !important;
}
table.table-bordered > tbody > tr > td{
    border:1px solid black !important;
}

</style>

</head>

<body >

						 <div class="panel panel-default" id="panelRequestInfo" style="min-width: 380px; margin-top:20px; ">
							<div class="panel-heading">Информация за заявка</div>
			
							<table class=" table table-bordered " >								
								<thead>
									<tr>
										<th style="min-width: 100px;" class="col-xs-4 col-sm-4 col-md-4 right"><span style="font-family: GLYPHICONS Halflings" class="glyphicon glyphicon-tag" aria-hidden="true"></span> Заявка N </th>
										<th class="col-xs-8 col-sm-8 col-md-8 text-center"><c:out value="${existingRequest.id}" /></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="min-width: 150px;" class="right">Дата</td>
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
										<td class="right"><span style="font-family: GLYPHICONS Halflings" class="glyphicon glyphicon-wrench" aria-hidden="true"></span> Устройство в Сервиз
										</td>
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
										<td class="right"><span style="font-family: GLYPHICONS Halflings" class="glyphicon glyphicon-globe" aria-hidden="true"></span> Устройство в Експлоатация 
										</td>
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
										<td class="right"><span style="font-family: GLYPHICONS Halflings" class="glyphicon glyphicon-user" aria-hidden="true"></span> Въведено от потребител</td>
										<td>${existingRequest.userName}</td>
									</tr>
									
									<tr>
										<td class="right">Дата на последна Промяна</td>
										<td>${formatedModificationDate}</td>
									</tr>
												
								</tbody>
							</table>							
						</div>											

	<div style="margin-top:40px;">
	Запитване направено в <span style=" font-weight: bold;">${currentTime} ч.</span> на <span style=" font-weight: bold;">${currentDate} г.</span> 

	</div>

	<div class="footer navbar-fixed-bottom" style="text-align: center; margin-bottom:0px;padding-botom:0px;">		
		<div class="container-fluid">
		
			<div>
				<h6 style="font-weight: normal;">БДЖ-ПП ИТ Сервизен Мениджър
				<br />
				2020 © Орлин Димитров</h6>
			</div>
		</div>
	</div>
</body>
</html>
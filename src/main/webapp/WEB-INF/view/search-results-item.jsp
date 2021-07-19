<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	<title>Резултати от Търсенето</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- Style of the Search Info Panel-->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/search-info-panel.css">
	
	<!-- style sheet of requests result table-->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/list-requests.css" />
		  
	<!-- import Bootstrap and jQuery  ATTENTION !!! bootstrap must be inserted after page shape css-->	  
	<c:import url="../view/fragments/bootstrap.jsp"></c:import>
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap.min.css"/>
 
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>

	
	<!-- Style of the jumbotron for vertical centering when using navbar -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/jumbotron-vertCenter-when-navbar-used.css">
	

	<!-- Creating URL used by DataTables for retrieving data from JSON-->
	<c:url var="searchItem" value="/json/searchItem" >
		<c:param name = "requesterId" value = "${searchItemResult.requesterId}"/>
   		<c:param name = "objectTypeId" value = "${searchItemResult.objectTypeId}"/>
   		<c:param name = "objectModelId" value = "${searchItemResult.objectModelId}"/>
   		<c:param name = "objectInstanceId" value = "${searchItemResult.objectInstanceId}"/>
   		<c:param name = "startDate" value = "${searchItemResult.dateStart}"/>
   		<c:param name = "endDate" value = "${searchItemResult.dateEnd}"/>
	</c:url>
	
	<c:url var="home" value="/"/>

	<!-- MY CUSTOM SCRIPTS -->
	<!-- Import Handle db-error, session-expired -->
	<c:import url="../view/fragments/dberror-sessionexpired.jsp"></c:import>	


	<!-- Script to submit search query parameters and open request info page -->
	<script type="text/javascript">

		function submitFormJavaScript(pageId) {
	        //form id = infoPage/
	              //SpringMultiCheckbox is context path of project
	        document.getElementById('infoPage').action = "${pageContext.request.contextPath}/search/resultsItem/" + pageId;
	        document.getElementById('infoPage').submit();
	    }
	</script>


	<!-- Pipeline cashing script for DataTables when ServerSide pagination i used -->
	<script src="<c:url value="/resources/js/datatables-pipeline-cashing.js"/>"></script>

	<!-- Script for populating DataTables -->
	<script type="text/javascript">

	$(document).ready(function() {
		
		var dataTablesUniqueId = "<c:out value="${searchItemResult.dataTablesUniqueId}" />";
		//console.log("Inside jsp, page ID: " + dataTablesUniqueId );
		
	    $('#listSearchItem').DataTable( {
	    	
	    	"language": {
	    		
	    	    "sProcessing":   "Обработка на резултатите...",
	    	    "sLengthMenu":   "Показване на _MENU_ резултата",
	    	    "sZeroRecords":  "Няма намерени резултати",
	    	    "sInfo":         "Показване на резултати от _START_ до _END_ от общо _TOTAL_",
	    	    "sInfoEmpty":    "Показване на резултати от 0 до 0 от общо 0",
	    	    "sInfoFiltered": "(филтрирани от общо _MAX_ резултата)",
	    	    "sInfoPostFix":  "",
	    	    "sSearch":       "Търсене:",
	    	    "sUrl":          "",
	    	    "oPaginate": {
	    	        "sFirst":    "Първа",
	    	        "sPrevious": "Предишна",
	    	        "sNext":     "Следваща",
	    	        "sLast":     "Последна"
	    	    }
	    	},
	    	
	    	"processing": true,
	    	"serverSide": true,
	    	
			"ajax": $.fn.dataTable.pipeline(  {
				"url":"${searchItem}",
				"dataSrc": "data",
				"pages": 5
			}),
			
			"searching": false,
			"stateSave": true,
			
			"stateSaveCallback": function(settings,data) {
				  localStorage.setItem( dataTablesUniqueId, JSON.stringify(data) )
				},
			"stateLoadCallback": function(settings) {
				  return JSON.parse( localStorage.getItem( dataTablesUniqueId ) )},
						
			"order": [[ 0, "desc" ]],
			"pagingType": "full_numbers",

			"columnDefs": [
				    { "orderable": false, "targets": [3,4,5,6] },
				    { "width": "5%", "targets": [0] },
				    { "width": "12%", "targets": [1] },
				    { "width": "15%", "targets": [2] },
				    { "width": "15%", "targets": [3] },
				    { "width": "25%", "targets": [4,5]},
				    { "width": "3%", "targets": [6] }
				  ],
			"columns": [
			    	{ "data": "id"},
		      		{ "data": "date" },
					{ "data": "requester" },
				 	{ "data": "requesterComment" },
				 	{ "data": "typeModelInService" },
					{ "data": "typeModelInOperation" },
					{ "data": null,
					      "bSortable": false,
					      "mRender": function(data) {
						    	 var page = data.id;
						    	
						        return '<button class="btn-primary btn-xs" type="button" onclick="submitFormJavaScript(' + page + ');"><i class="glyphicon glyphicon-tag"></i></button>';
				      		}	
					}
			]	     
	    } );
	} );
	
	</script>
</head>

<body>
	
	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>
	

	<div class="jumbotron vertical-center " id="mainView">
		<div class="container" id="container" >
			<div class="row" >
				<div class="col-sm-8 col-sm-offset-2 col-lg-6 col-lg-offset-3">
					
					<div class="row" >				
						<div class="panel panel-primary search-info-pannel" id="panelSearchInfo">
							<div class="panel-heading">Критерии на Търсенето</div>
						
							<table class="table search-info">								

								<tbody>							
									<tr>
										<td class="col-xs-4 left-column ">Начална Дата </td>
										<td class="col-xs-8 right-column" >${searchItemResult.dateStart}</td>
									</tr>
									
									<tr>
										<td class="left-column">Крайна Дата </td>
										<td class="right-column">${searchItemResult.dateEnd}</td>
									</tr>
								
									<tr>
										<td class="left-column">Заявител </td>
										<td class="right-column">${searchItemResult.requesterName}</td>
									</tr>

									<tr>
										<td class="left-column">Тип </td>
										<td class="right-column">${searchItemResult.objectTypeName}</td>
									</tr>
									
									<tr>
										<td class="left-column">Модел </td>
										<td class="right-column">${searchItemResult.objectModelName}</td>
									</tr>
									
									<tr>
										<td class="left-column">Сериен номер </td>
										<td class="right-column">${searchItemResult.objectInstanceName}</td>
									</tr>
																		
								</tbody>								
							</table>
						</div>
					</div><!-- end of first row -->
								 																		
				</div><!-- end of col-sm-8 col-sm-offset-2 -->	
			</div><!-- end of parent row -->
			
			
		<!--  add our html table here -->		
		<div class="row">		
	        <div class="col-sm-12">
	        
	        	<div class="row">
				
			
				<table id="listSearchItem" class="table table-striped table-bordered table-hover text-center mydatatable">
				        <thead >
				            <tr>
				                <th style="padding-left: 25px">N</th>
				                <th style="padding-left: 25px">Дата</th>
				                <th style="padding-left: 25px">Заявител</th>
				                <th>Коментар (Заявител)</th>
				                <th><span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>&nbsp;Устройство в Сервиз</th>
				                <th><span class="glyphicon glyphicon-globe" aria-hidden="true"></span>&nbsp;Устройство в Експлоатация</th>
				                <th>Инфо</th>
				            </tr>
				        </thead>
				</table>
						
				</div><!-- end of row -->
			</div><!-- col-sm-12 -->
		</div><!-- end of parent row -->
			
		<div class="row"> 	   
			<div class="cancel text-left back-to-search-btn">
				<a class="btn btn-primary" href="${pageContext.request.contextPath}/search/selectItem" role="button"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Към Търсене</a>
			</div>
		</div><!-- end button row -->	
		
		<!-- Form to submit search query options and open request info page-->
		<div class="row"> 			
	
			<form:form id="infoPage" action="${pageContext.request.contextPath}/search/resultsItem/pageId"
															   modelAttribute="searchItemToInfo"
														       class="form-horizontal"
														       method="POST">
				<form:hidden path="dateStart" value="${searchItemResult.dateStart}" />
				<form:hidden path="dateEnd" value="${searchItemResult.dateEnd}" />	
				<form:hidden path="requesterId" value="${searchItemResult.requesterId}" />	
				<form:hidden path="objectTypeId" value="${searchItemResult.objectTypeId}" />	
				<form:hidden path="objectModelId" value="${searchItemResult.objectModelId}" />	
				<form:hidden path="objectInstanceId" value="${searchItemResult.objectInstanceId}" />
				
				<form:hidden path="dataTablesUniqueId" value="${searchItemResult.dataTablesUniqueId}" />	
			</form:form>
		</div>
			
					
		</div><!-- end of container -->	
	</div><!-- end of jumbotron -->	
</body>

</html>











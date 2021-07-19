<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	<title>Списък на Заявките</title>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<!-- reference our style sheet -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/list-requests.css" />
	
	
	<!-- import Bootstrap and jQuery -->	  
	<c:import url="../view/fragments/bootstrap.jsp"></c:import>

	<!-- Style of the jumbotron for vertical centering when using navbar -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/jumbotron-vertCenter-when-navbar-used.css">


	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap.min.css"/>
 
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>
 
	<c:url var="listRequestsURL" value="/json/listRequestsPaginated" />

	<c:url var="home" value="/"/>

	<!-- MY CUSTOM SCRIPTS -->
	<!-- Import Handle db-error, session-expired -->
	<c:import url="../view/fragments/dberror-sessionexpired.jsp"></c:import>	
		
	
	<!-- Script to submit search query parameters and open request info page -->
	<script type="text/javascript">

		function submitFormJavaScript(pageId) {
	        //form id = infoPage/
	              //SpringMultiCheckbox is context path of project
	        document.getElementById('infoPage').action = "${pageContext.request.contextPath}/requests/" + pageId;
	        document.getElementById('infoPage').submit();
	    }
	</script>

	<!-- Pipeline cashing script for DataTables when ServerSide pagination i used -->
	<script src="<c:url value="/resources/js/datatables-pipeline-cashing.js"/>"></script>

	<!-- TESTING datatable -->
	<script type="text/javascript">
	
	$(document).ready(function() {
		
		$.fn.dataTable.ext.errMode = 'alert';
		
		var dataTablesUniqueIdListRequests = "<c:out value="${listRequests.dataTablesUniqueId}" />";
		
	    $('#listSS').DataTable( {
	    	
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
			"ajax":$.fn.dataTable.pipeline( {
				"url": "${listRequestsURL}",
				"dataSrc": "data",
				"pages": 5
			}),
			"searching": false,
			"stateSave": true,
			
			"stateSaveCallback": function(settings,data) {
				  localStorage.setItem( dataTablesUniqueIdListRequests, JSON.stringify(data) )
				},
			"stateLoadCallback": function(settings) {
				  return JSON.parse( localStorage.getItem( dataTablesUniqueIdListRequests ) )},			
			
			"pagingType": "full_numbers",
			"order": [[0, "desc"]],
			"columnDefs": [
				    { "orderable": false, "targets": [0,1,2,3,4,5,6] },
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


	<div class="jumbotron vertical-center" id="mainView">
	
	<div class="container" id="container" >
	
		<div id="content">
		
			<div class="row" >
				<div class="alert text-center title-banner" role="alert">Списък на всички Заявки</div>
			</div>
			<!--  add our html table here -->			
			<div class="row">		
		        <div class="col-sm-12" >
				
					<div class="row mydatatable-margin-bottom" >	
						<table id="listSS" class="table table-striped table-bordered table-hover text-center mydatatable ">
						        <thead>
						            <tr>
						                <th style="padding-left: 25px">N</th>
						                <th>Дата</th>
						                <th>Заявител</th>
						                <th>Коментар (Заявител)</th>
						                <th><span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>&nbsp;Устройство в Сервиз</th>
						                <th><span class="glyphicon glyphicon-globe" aria-hidden="true"></span>&nbsp;Устройство в Експлоатация</th>
						                <th>Инфо</th>
						            </tr>
						        </thead>
						</table>
					</div>
				
				</div><!-- col-sm-12 -->
			</div><!-- end of parent row -->
			
			
					<!-- Form to submit search query options and open request info page-->
		<div class="row"> 			
	
			<form:form id="infoPage" action="${pageContext.request.contextPath}/requests/pageId"
															   modelAttribute="listRequestsInfo"
														       class="form-horizontal"
														       method="POST">
				
				<form:hidden path="dataTablesUniqueId" value="${listRequests.dataTablesUniqueId}" />	
			</form:form>
		</div>
	
		</div><!-- end of content -->	
	</div><!-- end of container -->
	
	</div><!-- end of jumbotron -->
</body>

</html>










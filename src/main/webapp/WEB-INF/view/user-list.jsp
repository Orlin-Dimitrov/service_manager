<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	<title>Списък на Потребителите</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	
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
	
	
	<c:url var="listUsersCS" value="/admin-json/listUsers" />
	
	<security:authorize access="hasRole('SUADMIN')">
		<c:url var="listUsersCS" value="/admin-json/listUsersSu" />
	</security:authorize>

	<!-- MY CUSTOM SCRIPTS -->
	<!-- Import Handle db-error, session-expired -->
	<c:import url="../view/fragments/dberror-sessionexpired.jsp"></c:import>	
	

	<script type="text/javascript">

		function submitFormChangePassword(userInput) {

	        document.getElementById('changePasswordPage').action = "${pageContext.request.contextPath}/userManagement/changePassword/";	        
	        document.getElementById('user-name-change-password').value= userInput;
	        document.getElementById('changePasswordPage').submit();			
	    };
	    
		function submitFormChangeLevel(userInput) {

	        document.getElementById('changeLevelPage').action = "${pageContext.request.contextPath}/userManagement/changeLevel/";	        
	        document.getElementById('user-name-level').value= userInput;
	        document.getElementById('changeLevelPage').submit();			
	    };
	    
		function submitFormDelete(userInput) {

	        document.getElementById('deletePage').action = "${pageContext.request.contextPath}/userManagement/delete/";	        
	        document.getElementById('user-name-delete').value= userInput;
	        document.getElementById('deletePage').submit();			
	    };
	
		function submitFormStatus(userInput) {

	        document.getElementById('statusPage').action = "${pageContext.request.contextPath}/userManagement/status/";	        
	        document.getElementById('user-name-status').value= userInput;
	        document.getElementById('statusPage').submit();			
	    };	    
	    
	</script>


	<!-- Script for populating DataTables -->
	<script type="text/javascript">
	

	
	$(document).ready(function() {
		
		var dataTablesUniqueId = "<c:out value="${listUsers.dataTablesUniqueId}" />";
//		console.log("Inside jsp, page ID: " + dataTablesUniqueId );
		
	    $('#listUsers').DataTable( {
	    	
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
	    	
			"ajax": {
				"url":"${listUsersCS}",
				"error": function (jqxhr, textStatus, error) {
					
					dbErrorAndSessionExpiredHandler (jqxhr, textStatus, error);

				}
			},
			"sAjaxDataProp": "",
			"searching": true,
			"stateSave": true,
			
			"stateSaveCallback": function(settings,data) {
				  localStorage.setItem( dataTablesUniqueId, JSON.stringify(data) )
				},
			"stateLoadCallback": function(settings) {
				  return JSON.parse( localStorage.getItem( dataTablesUniqueId ) )},

						
			"order": [[ 0, "asc" ]],
			"pagingType": "full_numbers",

			"columnDefs": [
				    { "orderable": true, "targets": [0,1] },
				    { "width": "30%", "targets": [0] },
				    { "width": "20%", "targets": [1] },
				    { "width": "50%", "targets": [2] },

				  ],
			"columns": [
			    	{ "data": "userName"},
		      		{ "data": "formatedAuthority"},

					{ "data": null,
					      "bSortable": false,
					      "mRender": function(data) {
					    	  var user = data.userName;
					    	 	user = "'" + user + "'";
						        return '' +
						        		'<button class="btn-default btn-sm active" type="button" onclick="submitFormStatus(' + user + ');">Статус</button>'
						      			+ '&nbsp;&nbsp;&nbsp;' + 
						        		'<button class="btn-info btn-sm active" type="button" onclick="submitFormChangePassword(' + user + ');">Смяна на Парола</button>'
						        		+ '&nbsp;&nbsp;&nbsp;' +
						        		'<button class="btn-warning btn-sm active" type="button" onclick="submitFormChangeLevel(' + user + ');">Смяна на Ниво Достъп</button>'
						        		+ '&nbsp;&nbsp;&nbsp;' +
						        		'<button class="btn-danger btn-sm active" type="button" onclick="submitFormDelete(' + user + ');">Изтриване</button>'
						        		+ ''
						        		;
				      		
				      		}	
					}
			]
,
	    } );
	} );
	
	</script>
</head>

<body>
	
	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>
	

	<div class="jumbotron vertical-center " id="mainView">
		<div class="container" id="container" >
		
		<!--  add our html table here -->		
		<div class="row">		
	        <div class="col-sm-12">
	        
	        	<div class="row mydatatable-margin-bottom">
				
			
				<table id="listUsers" class="table table-striped table-bordered table-hover text-center mydatatable" style="width:100%; min-width:990px">
				        <thead >
				            <tr>
				                <th style="padding-left: 25px"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;Потребител</th>
				                <th style="padding-left: 25px"><span class="glyphicon glyphicon-signal" aria-hidden="true"></span>&nbsp;Ниво на Достъп</th>
				                <th><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>&nbsp;Управление</th>
				            </tr>
				        </thead>
				</table>
						
				</div><!-- end of row -->
			</div><!-- col-sm-12 -->
		</div><!-- end of parent row -->
		
		<!-- Form to submit username for changing password-->
		<div class="row"> 				
			<form:form id="changePasswordPage" action="${pageContext.request.contextPath}/userManagement/changePassword"
															   modelAttribute="userChangePassword"
														       class="form-horizontal"
														       method="POST">

				<form:hidden id="user-name-change-password" path="userName" value="" />
				<form:hidden id="dataTablesUniqueIdchangePasswordPage" path="dataTablesUniqueId" value="${listUsers.dataTablesUniqueId}" />
			
			</form:form>
		</div>
		
		<!-- Form to submit username for changing access level-->
		<div class="row"> 				
			<form:form id="changeLevelPage" action="${pageContext.request.contextPath}/userManagement/changeLevel"
															   modelAttribute="userChangeLevel"
														       class="form-horizontal"
														       method="POST">

				<form:hidden id="user-name-level" path="userName" value="" />
				<form:hidden id="dataTablesUniqueIdchangeLevelPage" path="dataTablesUniqueId" value="${listUsers.dataTablesUniqueId}" />
			
			</form:form>
		</div>
			
		
		<!-- Form to submit username for deleting user-->
		<div class="row"> 				
			<form:form id="deletePage" action="${pageContext.request.contextPath}/userManagement/delete"
															   modelAttribute="userDelete"
														       class="form-horizontal"
														       method="POST">

				<form:hidden id="user-name-delete" path="userName" value="" />
				<form:hidden id="dataTablesUniqueIddeletePage" path="dataTablesUniqueId" value="${listUsers.dataTablesUniqueId}" />
			
			</form:form>
		</div>
		
		<!-- Form to submit username for status info-->
		<div class="row"> 				
			<form:form id="statusPage" action="${pageContext.request.contextPath}/userManagement/status"
															   modelAttribute="userStatus"
														       class="form-horizontal"
														       method="POST">

				<form:hidden id="user-name-status" path="userName" value="" />
				<form:hidden id="dataTablesUniqueIdstatusPage" path="dataTablesUniqueId" value="${listUsers.dataTablesUniqueId}" />
			
			</form:form>
		</div>	
			
					
		</div><!-- end of container -->	
	</div><!-- end of jumbotron -->	
</body>

</html>











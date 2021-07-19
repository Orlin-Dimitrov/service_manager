<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	
	<title>Смяна Ниво Достъп на Потребител</title>
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

	<script type="text/javascript">

		function backToUserList() {
	        document.getElementById('backToUserList').action = "${pageContext.request.contextPath}/userManagement/list/";	        
	        document.getElementById('backToUserList').submit();				    
		}
	</script>



</head>

<body>

	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>
	
	<div class="jumbotron vertical-center " >
		<div class="container">
		
				<div class="row" >
				<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<div class="row" >
		
				<div id="searchbox" class="mainbox">
					<div class="panel panel-edit edit-color">
		
						<div class="panel-heading edit-color-heading">
							<div class="panel-title">Смяна Ниво Достъп на Потребител</div>
						</div>
		
						<div class="panel-body">
		
							<!-- Change Password Form -->
							<form:form action="${pageContext.request.contextPath}/userManagement/processChangeLevel"
									   modelAttribute="formUser" 
									   method="POST" 
								       class="form-horizontal"
								       id="registrationForm">								
								
								<form:hidden path="dataTablesUniqueIdFormUser"  value="${formUser.dataTablesUniqueIdFormUser}"/>	
							
								<div class="row item-panel "  >
								<div class="panel panel-default edit-color col-xs-12 col-sm-10 col-sm-offset-1"  >
								<div class="panel-body ">
							
																																							
									<!-- UserName -->
									<div class="row">										
										<div class="col-xs-12">
											<div class="text-center form-group user-text">
												<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
												<span class="red">${formUser.userName}</span>
											</div>
										</div>
									</div>
									
									<form:hidden path="userName"  value="${formUser.userName}"/>
									<form:hidden path="confirmationUserName"  value="${formUser.userName}"/>
									
									<form:hidden path="password"  value="${formUser.password}"/>
									<form:hidden path="confirmationPassword"  value="${formUser.password}"/>
																	
									
									<!-- Access level -->

									
									<div class="row">										
										<div class="col-xs-12 access-level-group text-center">
									
											<div class="row access-label">
												<span class="glyphicon glyphicon-signal" aria-hidden="true"></span>												
												<label class="control-label" for="radioButtonsGroup">Ниво на Достъп</label>
											</div>
									
											<div class="row">											
												<label class="radio-inline" for="inlineRadio1">
													<form:radiobutton  name="User" id="inlineRadio1" value="User" path="accessLevel"/>Потребител
												</label>
												<label class="radio-inline" for="inlineRadio2">
													<form:radiobutton  name="Manager" id="inlineRadio2" value="Manager" path="accessLevel"/>Мениджър
												</label>
												    
												<security:authorize access="hasRole('SUADMIN')">
													<label class="radio-inline" for="inlineRadio3">
														<form:radiobutton  name="Administrator" id="inlineRadio3" value="Administrator" path="accessLevel"/>Администратор
													</label>
												</security:authorize>									
											</div>
											
											<div class="error">
												<form:errors path="accessLevel" />
											</div>
										</div>
									</div>
								
								</div>
								</div>
								</div>								

								<div class="row"> 
									<div class="col-xs-6" >
										<div class="cancel text-left btn-left">															
											<button class="btn btn-default" type="button" onclick="backToUserList();">
											<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Към Списък</button>
										
										</div>
									</div>
												
									<div class="col-xs-6">																													
										<div class="save  text-right btn-right form-group">									
											<button type="submit" class="btn btn-warning btn-acclvl" >
												<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Смени</button>										 
										</div>
									</div>
								</div>								
							
							</form:form>
												
						
							<form:form id="backToUserList" action="${pageContext.request.contextPath}/userManagement/list"
																   modelAttribute="listUsers"
															       class="form-horizontal"
															       method="POST">
												
								<form:hidden path="dataTablesUniqueId" value="${formUser.dataTablesUniqueIdFormUser}" />	  							
							</form:form>

						</div><!-- end of panel-body -->
					</div><!-- end of panel -->					
				</div><!-- end of mainbox -->	
				
				</div>
				</div>
				</div>								
		</div><!-- end of container -->
	<!-- END JUMBOTRON -->		
	</div>
</body>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	
	<title>Добави Нов Потребител</title>
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
					<div class="panel panel-primary">
		
						<div class="panel-heading">
							<div class="panel-title">Добави Нов Потребител</div>
						</div>
		
						<div class="panel-body">
		
							<!-- Change Password Form -->
							<form:form action="${pageContext.request.contextPath}/userManagement/processAdd"
									   modelAttribute="formUser" 
									   method="POST" 
								       class="form-horizontal"
								       id="registrationForm">								
									
							
								<div class="row item-panel "  >
								<div class="panel panel-default add-color col-xs-12 col-sm-10 col-sm-offset-1"  >
								<div class="panel-body ">																									
									

									<!-- UserName -->
									<div class="row form-group form-group-custom">
										<div class="input-group pad-left-zero col-xs-12">										
											<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 																
											<form:input path="userName" placeholder="Име на Потребител!" class="form-control" autocomplete="off"/>												
										</div>
										<form:errors path="userName" cssClass="error"/>
										<form:errors path="userNamesMatch" cssClass="error"/>
									</div>
																		
									<!-- Confirmation UserName -->
									<div class="row form-group form-group-custom before-alert-success">
										<div class="input-group pad-left-zero col-xs-12">										
											<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 																
											<form:input path="confirmationUserName" placeholder="Потвърди Името!" class="form-control" autocomplete="off"/>												
										</div>
										<form:errors path="confirmationUserName" cssClass="error"/>
										<form:errors path="userNamesMatch" cssClass="error"/>
									</div>

									<!-- Password ToolTip-->
									<div class="row">										
										<div class="col-xs-12 ">
											<div class="alert alert-success text-center form-group" role="alert">
											<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
											 Паролата трябва да съдържа <span style="font-weight:bold"> поне една </span>голяма буква, малка буква, число и символ!
										 	<span style="font-weight:bold">Минимална дължина 10 знака</span>! Използвай само <span style="font-weight:bold">Латински</span> букви!
											</div>
										</div>
									</div>
																										
									<!-- Password -->
									<div class="row form-group form-group-custom">																																	
										<div class="input-group pad-left-zero col-xs-12">
											<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 																
											<form:password path="password" placeholder="Парола!" class="form-control" />																			 
									    </div>
										<form:errors path="password" cssClass="error"/>
										<form:errors path="passwordsMatch" cssClass="error"/>														    
									</div>

									<!-- Confirmation password -->
									<div class="row form-group form-group-custom user-confirm-password-last-field">																																	
										<div class="input-group pad-left-zero col-xs-12">																		
											<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 																
											<form:password path="confirmationPassword" placeholder="Потвърди Паролата!" class="form-control" />
										</div>									
										<form:errors path="confirmationPassword" cssClass="error"/>
										<form:errors path="passwordsMatch" cssClass="error"/>						    								
									</div>	

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
											<a class="btn btn-default"  href="${pageContext.request.contextPath}/" role="button">
											<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Отказ</a>
										</div>	
									</div>
												
									<div class="col-xs-6">
										<div class="save  text-right btn-right form-group">									
											<button type="submit" id="saveButton" class="btn btn-primary">
												<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Добави</button>										 
										</div>
									</div>
								</div>								
							
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
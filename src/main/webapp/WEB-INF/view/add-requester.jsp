<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	
	<title>Добави Заявител</title>
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
		
				<div id="inputbox" class="mainbox">
					<div class="panel panel-primary" >
		
						<div class="panel-heading">
							<div class="panel-title">Добави НОВ Заявител</div>
						</div>
		
						<div class="panel-body">
		
							<!-- Add Requester Form -->
							<form:form action="${pageContext.request.contextPath}/component/reviewRequester"
									   modelAttribute="newRequester" 
									   method="POST" 
								       class="form-horizontal"
								       id="addRequester">								
									
								<div class="row item-panel"  >
								<div class="panel panel-default add-color col-xs-12 col-sm-10 col-sm-offset-1" >
								<div class="panel-body">

								<!-- Input Requester Name -->																        									 								
								<div class="row form-group form-group-custom">																																	
									<div>
										<label for="requester">Наименование на Заявителя</label>																													
									</div>
									<div class="pad-left-zero col-xs-12">
										<form:input id="requester" type="text" class="form-control" path="name" autocomplete="off" />																				 
								    </div>
								    <form:errors path="name"  cssClass="error"/>														    
								</div>																
								
								</div>
								</div>
								</div>
								
								<!-- Navigation buttons -->
								<div class="row">
									<div class="col-xs-6">								
										<div class="cancel text-left btn-left">
											<a class="btn btn-default"  href="${pageContext.request.contextPath}/" role="button">
											<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Отказ</a>
										</div>								
									</div>
									
									<div class="col-xs-6">
										<div class="save  text-right btn-right form-group">
											<button type="submit" class="btn btn-primary" ><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Потвърди</button>										 																		
										</div>								
									</div>									
								</div>								
								
							</form:form>							
						</div>
					</div>
				<!-- end of input -->							
				</div>
				
				</div>
				</div>
				</div>
								
		<!-- end of container -->		
		</div>
	<!-- END JUMBOTRON -->		
	</div>
</body>
</html>
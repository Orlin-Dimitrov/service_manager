<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	
	<title>Добави Устройство</title>
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
				<div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
								
				<div id="additembox" class="mainbox ">
					<div class="panel panel-primary" >
		
						<div class="panel-heading">
							<div class="panel-title">Добави Устройство</div>
						</div>
		
						<div class="panel-body">		
							<div>
								<a class="btn btn-default btn-lg customButton btn-block" href="${pageContext.request.contextPath}/component/addType" role="button">Тип</a>
							</div>
							<div>
								<a class="btn btn-default btn-lg customButton btn-block" href="${pageContext.request.contextPath}/component/addModel" role="button">Модел</a>
							</div>
							<div>
								<a class="btn btn-default btn-lg customButton btn-block" href="${pageContext.request.contextPath}/component/addInstance" role="button">Сериен номер</a>																			
							</div>
						</div>
						
					</div>
				<!-- end of additembox -->							
				</div>
				
				
				</div>
				</div>				
		<!-- end of container -->		
		</div>
	<!-- END JUMBOTRON -->		
	</div>
</body>
</html>
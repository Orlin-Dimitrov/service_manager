<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	<title>Грешка при изтриване на Тип устройство</title>
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
	
	<!-- style result panel -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/panel-result-custom.css" />
	
</head>

<body>
	
	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>
	

	<div class="jumbotron vertical-center">
		<div class="container">
		
				<div class="row" >
				<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<div class="row" >
		
				<div id="info-box" class="mainbox">
					<div class="panel panel-red">							
						<div class="panel-heading panel-red-heading">
							<div class="panel-title text-center">Грешка при изтриване на Тип!</div>
						</div>

						<div class="panel-body">																
							<div class="well well-result text-center" >
								
								<span class="glyphicon glyphicon-warning-sign red-text" aria-hidden="true"></span>
								&nbsp;Типът устройство е изтрит!										
								
							</div>								
							
							<div class="row"> 	   
								<div class="text-center">
									<a class="btn btn-default btn-center" href="${pageContext.request.contextPath}/componentDelete/deleteItem" role="button">Към изтриване на устройство</a>
								</div>	
							</div><!-- end row -->
							
						</div><!-- end of panel body -->
					</div><!-- end of panel -->					
				</div><!-- end of mainbox -->
				
				</div>
				</div>
				</div>							
		</div><!-- end of container -->	
	</div><!-- END JUMBOTRON -->	
</body>

</html>

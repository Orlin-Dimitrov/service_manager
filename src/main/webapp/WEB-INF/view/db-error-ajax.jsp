<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	
	<title>Грешка База Данни!</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		
	
	<!-- style result panel -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/panel-result-custom.css" />


</head>

<body>
	
	<div class="jumbotron vertical-center">
		<div class="container">
		
				<div class="row" >
				<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<div class="row" >
		
				<div id="info-box" class="mainbox">
					<div class="panel panel-red">							
						<div class="panel-heading panel-red-heading">
							<div class="panel-title text-center">ВНИМАНИЕ !!!</div>
						</div>

						<div class="panel-body">																
							<div class="well well-result text-center" >
								
								<span class="glyphicon glyphicon-warning-sign red-text" aria-hidden="true"></span>
								&nbsp;Настъпи грешка с База Данни! Свържете се с Администратор!										
								
							</div>								
							
							<div class="row"> 	   
								<div class="text-center">
									<a class="btn btn-default btn-center" href="${pageContext.request.contextPath}/" role="button">Към Начална</a>
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

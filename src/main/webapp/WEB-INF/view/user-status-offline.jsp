<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	
	<title>Статус на Потребителя</title>
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
		
				<div class="row">
				<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<div class="row">
		
				<div id="infobox" class="mainbox">
					<div class="panel panel-gray grey-color">
		
						<div class="panel-heading grey-color-heading">
							<div class="panel-title">Статус на Потребителя</div>
						</div>
		
						<div class="panel-body form-horizontal">

							<div class="row item-panel"  >
							<div class="panel grey-color panel-default col-xs-12 col-sm-10 col-sm-offset-1"  >
							<div class="panel-body ">
																																								
									<!-- UserName -->
									<div class="row">										
										<div class="col-xs-12">
											<div class="text-center form-group user-text">
											<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
											<span class="red">${formUserDisconnect.userName}</span>
											</div>
										</div>
									</div>
									
									<!-- Status -->
									<div class="row">										
										<div class="col-xs-12">
											<div class="text-center form-group user-text">
												Статус:<span class="red"> Не Свързан</span>
											</div>
										</div>
									</div>																		    								
																								
									<!-- Last online -->
									<div class="row">										
										<div class="col-xs-12">
											<div class="text-center form-group user-text" >
												Последно влизане на:<span class="blue last-login-date-time"> ${lastLoginDateTime}</span>
											</div>
										</div>
									</div>

							</div>
							</div>
							</div>

								<!-- Navigation Buttons -->
								<div class="row"> 
									<div class="col-xs-6" >
										<div class="cancel text-left btn-left">																																																				
											<button class="btn btn-default" type="button" onclick="backToUserList();">
												<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Към Списък</button>											
										</div>
									</div>
								</div>																										
						
								<form:form id="backToUserList" action="${pageContext.request.contextPath}/userManagement/list"
																   modelAttribute="listUsers"
															       class="form-horizontal"
															       method="POST">
												
									<form:hidden path="dataTablesUniqueId" value="${formUserDisconnect.dataTablesUniqueId}" />	  							
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
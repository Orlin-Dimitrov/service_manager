<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>

<title>ИТ Сервизен Мениджър</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>

	<link rel="stylesheet"
	      type="text/css"
		  href="${pageContext.request.contextPath}/resources/css/login.css">

<!--  
<security:authorize access="isAuthenticated()">

	<c:redirect url="/"/> 
	
</security:authorize>
-->
</head>

<body>

	<div class="jumbotron vertical-center">

		<div class="container" >
			<div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
				<div class="panel panel-login shadow">
					<div class="panel-heading panel-login-heading">
						<h3 class="panel-title">
							<strong>Вход</strong>
						</h3>
					</div>

					<div class="panel-body">
						<div class="app-title">
							ИТ Сервизен Мениджър
						</div>
										
						<!-- Login Form -->
						<form:form action="${pageContext.request.contextPath}/authenticateTheUser" 
							  method="POST" class="form-horizontal">
	
						    <!-- Place for messages: error, alert etc ... -->
						    <div class="form-group messages">
						        
									<c:set var="maxSessions" value="Maximum sessions of 2 for this principal exceeded"></c:set>
									<c:set var="badCredentials" value="Bad credentials"></c:set>
									<c:set var="noConnectionToDb" value="No AuthenticationProvider found for org.springframework.security.authentication.UsernamePasswordAuthenticationToken"></c:set>
										
									<!-- Check for login error -->		
									<c:if test="${param.error != null}"> 
										<div class="alert alert-danger col-xs-offset-1 col-xs-10 text-center">
											 	
											<c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message eq badCredentials}">
	                								Невалиден потребител или парола.
	              							</c:if>
											
											<c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message eq maxSessions}">
	                								Максимален брой сесии достигнат.
	              							</c:if>
	              								
	              							<c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message eq noConnectionToDb}">
	                								Системата е временно недостъпна.
	              							</c:if>
	
										</div>
										
									</c:if>
										
									<!-- Check for logout -->
									<c:if test="${param.logout != null}">
														            
										<div class="alert alert-success col-xs-offset-1 col-xs-10 text-center">
											Излязохте от системата.
										</div>
								
									</c:if>	
						        
						    </div>
	
							<!-- User name -->
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 								
								<input type="text" name="username" placeholder="потребител" class="form-control" autocomplete="off">							
							</div>
	
							<!-- Password -->
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
								<input type="password" name="password" placeholder="парола" class="form-control" autocomplete="off">
							</div>
	
							<!-- Login/Submit Button -->
							<div class="form-group login-button">						
								<div class="controls  col-sm-6 col-sm-offset-3" >
									<button type="submit" class="btn btn-success btn-block" >Вход</button>
								</div>
							</div>	
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>

</html>

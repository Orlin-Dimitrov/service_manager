<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	<title>ИТ Сервизен Мениджър</title>
	
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
		  
		<!-- style for home page -->	
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/home.css" />
</head>

<body>

	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>

	<div class="jumbotron vertical-center " >
		<div class="container">

			<div class="row">	  
			<div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
			<div class="row" >
	
				<div class="panel panel-welcome">
					
					<div class="panel-heading panel-welcome-heading" >
						<div class="panel-title" >Здравейте !</div>
					</div>
					
					<div class="panel-body">				
						<div class="col-sm-12">
					    	<div class="row row-padding">
					    		<span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp; Потребител : <span class="user-info-text"><security:authentication property="principal.username" /></span>
					    	</div>
					    	
					    	<div class="row row-padding">
					    		<span class="glyphicon glyphicon-signal" aria-hidden="true"></span>&nbsp; Ниво на Достъп : <span class="user-info-text">${accessLevel}</span>
					    	</div>
					    	
					    	<div class="row">
					    		<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>&nbsp; Дата : <span class="user-info-text">${formatedDate}</span>
					    	</div>
					    </div>		
					</div>
				
				</div>
	
			</div>
			</div>
			</div>
	
		</div>
	</div>
	
	<div class="footer navbar-fixed-bottom">		
		<div class="container-fluid">		
			<div class="custom-footer">
				<div class="app-name">ИТ Сервизен Мениджър</div>
				<div class="author">2021 © Орлин Димитров</div>
			</div>
		</div>
	</div>
	
</body>

</html>
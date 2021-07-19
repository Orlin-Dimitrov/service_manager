<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	<!-- navbar css -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/navbar.css" />

	<!-- logout css -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/btn-logout.css" />

	<!-- background css -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/background.css" />

<!-- 	navbar-fixed-top
<style>
body { padding-top: 90px; }
</style>
 -->
</head>


<body>

<nav class="navbar navbar-default hidden-print navbar-shadow ">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<spring:url value="/"/>"><span class="glyphicon glyphicon-home"   aria-hidden="true"></span></a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">

		<li><a href="<spring:url value="/requests/list"/>"><span class="glyphicon glyphicon-tags" aria-hidden="true"></span>&nbsp; Списък</a></li>

		<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
 		<li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;Добави<span class="caret"></span></a>
          <ul class="dropdown-menu">
          	<li><a href="<spring:url value="/requestForm/add"/>"><span class="glyphicon glyphicon-tag" aria-hidden="true"></span>&nbsp;Заявка</a></li>
          	<li role="separator" class="divider"></li>

            <li><a href="<spring:url value="/component/addItem"/>">Устройство</a></li>          
            <li><a href="<spring:url value="/component/addRequester"/>">Заявител</a></li>           

          </ul>
        </li>
		</security:authorize>
<!-- 
		<li><a href="<spring:url value="/requestForm/add"/>">Add Request</a></li>
		
		<li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Add Component<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="<spring:url value="/component/addRequester"/>">Add Requester</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="<spring:url value="/component/addItem"/>">Add Item</a></li>
          </ul>
        </li>
 -->        
		<li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;Търсене<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="<spring:url value="/search/selectItem"/>">Устройство</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="<spring:url value="/search/selectDateRequester"/>">Дата / Заявител</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="<spring:url value="/search/selectRequest"/>" ><span class="glyphicon glyphicon-tag" aria-hidden="true"></span>&nbsp;Заявка</a></li>
          </ul>
        </li>
 		
 		<li><a href="<spring:url value="/statistics/select"/>"><span class="glyphicon glyphicon-stats" aria-hidden="true"></span>&nbsp; Статистика</a></li>
 		   
        <security:authorize access="hasRole('ADMIN')">
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>&nbsp;Администриране<span class="caret"></span></a>
          <ul class="dropdown-menu">
         	<li><a href="<spring:url value="/requestFormAdmin/select"/>" style="color:red"><span class="glyphicon glyphicon-tag" aria-hidden="true"></span>&nbsp;Редактирай Заявка</a></li>
         	
            <li role="separator" class="divider"></li> 
            
            <li><a href="<spring:url value="/componentEdit/editItem"/>">Редактирай Устройство</a></li>
            
            <li><a href="<spring:url value="/componentEdit/editRequester"/>">Редактирай Заявител</a></li>
            
            <li role="separator" class="divider"></li>
            
            <li><a href="<spring:url value="/componentDelete/deleteItem"/>" style="color:red">Изтрий Устройство</a></li>
            
            <li><a href="<spring:url value="/componentDelete/deleteRequester"/>" style="color:red">Изтрий Заявител</a></li>
            
            <li role="separator" class="divider"></li>
            
            <li><a href="<spring:url value="/userManagement/activeUsers"/>">Активни Потребители</a></li>
                  
            <li><a href="<spring:url value="/userManagement/list"/>">Списък Потребители</a></li>
            
            <li><a href="<spring:url value="/userManagement/add"/>">Добави Нов Потребител</a></li>

          </ul>
          
        </li>
      
      </security:authorize>
      
     <!-- 
      <security:authorize access="hasRole('ADMIN')">
      	<li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">TEST<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="<spring:url value="/test/addEntriesToDB"/>">Add Multiple Request to DB</a></li>
            <li role="separator" class="divider"></li>
          </ul>
        </li>
     </security:authorize>
     -->
      </ul>
      

		<!-- Add a Logout button! -->
		
		
		
		<form:form action="${pageContext.request.contextPath}/logout"
			   method="POST" 
			   class="nav navbar-nav navbar-form navbar-right" >
		
		
		<span class="well well-sm" style="border:1px solid grey; border-radius: 5px; background-color:#e2eeff;"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;<span style="color:#09c332;font-weight: bold"><security:authentication property="principal.username"/></span></span>
		&nbsp;
		<button  type="submit" class="btn btn-default btn-logout" value="Logout" ><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> Изход</button> 
 
		</form:form>
		
    
      
    </div><!-- /.navbar-collapse -->
    
    
  </div><!-- /.container-fluid -->
</nav>

</body>
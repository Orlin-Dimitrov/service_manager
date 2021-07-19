<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<!-- Global contextRootPath -->
	<script type="text/javascript">
	
		var ctx = "${pageContext.request.contextPath}";		
	</script>

	<!-- Script for DBError and Sessiontimeout handling -->
	<script src="<c:url value="/resources/js/handle-dberror-sessionexpired.js"/>"></script>

	 <!-- used by AJAX-->
	<meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
	
		
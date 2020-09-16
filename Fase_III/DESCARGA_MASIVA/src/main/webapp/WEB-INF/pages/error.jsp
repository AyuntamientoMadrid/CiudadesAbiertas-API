<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 
<c:set var="title" value="<%=LiteralConstants.TITLE_HEAD_LOGIN%>" scope="session"/>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Error - Base</title>
	<link rel="stylesheet" href="<c:url value="/resources/ayre-assets/css/vendor.min.css"/>">	
	<link rel="stylesheet" href="<c:url value="/resources/ayre-assets/css/proyecto-base.min.css"/>">
	
</head>


<body>	
	<div class="login">
    	<div class="wrapper col-sm-10 col-md-6 col-lg-5">
	        <div class="login_header">
	            <img class="logo_ayre" src="<c:url value="/resources/ayre-assets/images/base_logo.png"/>" alt="">
	            <img class="logo_madrid" src="<c:url value="/resources/ayre-assets/images/logo_madrid_login.png"/>" alt="">
	        </div>
	        
	        <form name='loginForm' id='loginForm'>
	         	
		 			<div class="advice">
				        <div class="container error">
				            <h4>Se ha producido un error</h4>
				            <p>Si el error permanece, por favor contacte con su proveedor.</p>
				        </div>
				    </div>
		 			
	                    
	                
                                                           
	        </form>
	             
	        <div class="login_footer">
			   
			</div>
        

<%@include file="includes/public/footPublic.jsp"%>	
<script src="<c:url value="/resources/js/login.js"/>"></script>

	
</body>
</html>
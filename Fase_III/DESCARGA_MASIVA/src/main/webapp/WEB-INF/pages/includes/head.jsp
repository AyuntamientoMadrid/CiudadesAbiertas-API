<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 


<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title><%=session.getAttribute("title") %><%=LiteralConstants.TITLE_HEAD_SUFFIX%></title>	
	<link rel="stylesheet" href="<c:url value="/resources/datetime/jquery.timepicker.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/ayre-assets/css/vendor.min.css"/>">		
	<link rel="stylesheet" href="<c:url value="/resources/ayre-assets/css/proyecto-base.min.css"/>">	
	<link rel="icon" type="image/png" href="<c:url value="/resources/ayre-assets/images/favicon.ico"/>" />

	
	<link rel="stylesheet" href="<c:url value="/resources/ayre-assets/css/appsIAM.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/base.css"/>">		
</head>


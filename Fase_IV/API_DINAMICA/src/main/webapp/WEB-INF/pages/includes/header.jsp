<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %>



	<header class="header" id="top">
		<div class="container-title">
			<div class="brand">
				<div class="row">
					<div class="col-md-10 titulo">						
						<h1><a class="" title="<%=LiteralConstants.TITULO_CABECERA%>" href="<c:url value="/home"/>"><%=LiteralConstants.TITULO_CABECERA%></a></h1>
					</div>
					<div class="col-md-2 header-logo">
						<a href="#" accesskey="I"><img src="<c:url value="/resources/ayre-assets/images/logo_madrid_cabecera.png"/>" alt="Logo Madrid"></a>
					</div>
				</div>
			</div>
		</div>
		<!-- Menu secundario -->
		
		<div class="menu_secundario">
			<div class="container">
				<div class="row">
					<div class="col-lg-1 col-xl-1 ayre-logo above">
						<a class="header_logo" title="" href="<c:url value="/home"/>">   
							<img class="logo_ayre" src="<c:url value="/resources/ayre-assets/images/base_logo.png"/>" alt="Logo Ayre">
						</a>
					</div>
					<div class="col-xs-12 col-lg-8 col-xl-8 options">
						<ul class="nav justify-content-end">
							    <li class="nav-item">
							        <a class="nav-link" href="<c:url value="/home"/>">Inicio</a>
							    </li>
							    <li class="nav-item">
							        <a class="nav-link" href="<c:url value="/login?logout"/>">Desconectar</a>
							    </li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row small_icons">
			<div class="col-8 enlaces_tablet">
				 <ul class="nav justify-content-start">
					     <li class="nav-item">
					         <a class="nav-link" href="#">Cerrar</a>
					     </li>
				 </ul>
			</div>
			<div class="col-6 ayre-logo">
				<a class="header_logo" title="" href="#">
					<img class="logo_ayre" src="<c:url value="/resources/ayre-assets/images/base_logo.png"/>" alt="Logo Ayre">
				</a>
			</div>
		</div>
	</header>






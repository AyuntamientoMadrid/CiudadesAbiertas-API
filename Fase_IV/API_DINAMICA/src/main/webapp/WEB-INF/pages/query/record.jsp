<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 
<c:set var="title" value="<%=LiteralConstants.TITLE_HEAD_CONSULTAS_SQL%>" scope="session"/>
<!DOCTYPE html>
<html lang="es">
<%@include file="../includes/head.jsp"%>	
<body>
	<%@include file="../includes/header.jsp"%>	
	
	<%@include file="../includes/menuLateral.jsp"%>
	
	<div class="user_name">
		<div class="container">
			<div class="row">
				<p><i class="icon-id-card-3"></i> ${userName}</p>
			</div>
		</div>
	</div>
	

	
   	<div class="title mt-0">
		<div class="container">
			<div class="col-md-12">
				<p id="parrafoTitulo">${object.code}</p>
			</div>
		</div>
	</div>
	
	
	
	<div class="form_steps">
		<ul>
		  <li class="done" id="1"><a href="<c:url value="/home"/>">1. <span>Inicio</span></a></li>
		  <li class="done" id="2">2. <span>Consultas SQL</span></li>
		  <li class="selected" id="3"><a href="<c:url value="/query"/>">3. <span>Listado</span></a></li>		  
		  <li class="selected" id="4">4. <span>Ficha</span></li>
		</ul>
	</div>
	




	<div class="share_menu">
		<div class="brand">
			<div class="row">
				<div class="col-3">
					<a href="<c:url value="/query"/>"><i class="ico icon-arrow-left"></i> Volver</a>
				</div>
				<div class="col-9 options">
					<ul>
						<li>
							<a title="Editar" href="<c:url value="/query/edit/${object.id}"/>" id="enlaceEditar"><i class="ico icon-exchange"></i><span class="text">Editar</span></a>
						</li>
						<li>
							<a title="Eliminar" href="<c:url value="/query/delete/${object.id}"/>" id="enlaceBorrar"/><i class="ico icon-close"></i><span class="text">Eliminar</span></a>
						</li>						
						<li class="font_size">
							<a title="Tamaño de letra" href="#" class="enlace"><i class="ico icon-font-size"></i><span class="text">Tamaño de letra</span><i class="ico icon-arrow-down"></i></a>
							<div class="font_size_options">
								<ul>
									<li id="font-up"><a href="#">+ Aumentar</a></li>
									<li id="font-down"><a href="#">- Reducir</a></li>
								</ul>
							</div>
						</li> 
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="scroll_button_top">
    <a href="#bottom">
        <img src="<c:url value="/resources/ayre-assets/images/scroll_button_bottom.png"/>" alt="scroll down"/>
    </a>
</div>

<div class="row bloque1">
	<div class="col-xs-12 col-md-12 col-lg-10 col-xl-10">
	    <jsp:include page="../includes/query/readOnly.jsp" />
	</div>
</div>	
<div class="scroll_button_bottom">
    <a href="#top">
        <img src="<c:url value="/resources/ayre-assets/images/scroll_button_top.png"/>" alt="scroll up"/>
    </a>
</div>


	
	<%@include file="../includes/footer.jsp"%>	
	<%@include file="../includes/foot.jsp"%>
	
	<script>	
		$(document).ready(function()
		{
			 
			// Cambiamos la ruta de la imagen del calendario
			$( ".calendar, .to, .from" ).datepicker( "option", "buttonImage", "<c:url value="/resources/ayre-assets/images/calendar.png"/>" );
			
			//ajustamos texto
			var text = $('#texto');					
			text.height('auto');
			text.height(text.prop('scrollHeight'));
		});		
		
	</script>	
</body>
</html>
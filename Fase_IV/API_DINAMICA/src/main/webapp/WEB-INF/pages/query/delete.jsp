<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 
<c:set var="title" value="<%=LiteralConstants.TITLE_HEAD_HOME%>" scope="session"/>
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
				<p>¿Eliminar ${object.code}?</p>
			</div>
		</div>
	</div>
	
	
	
	<div class="form_steps">
		<ul>
		<ul>
		  <li class="done" id="1"><a href="<c:url value="/home"/>">1. <span>Inicio</span></a></li>
		  <li class="done" id="2">2. <span>Consultas SQL</span></li>
		  <li class="selected" id="3"><a href="<c:url value="/query"/>">3. <span>Listado</span></a></li>		  
		  <li class="selected" id="4"><a href="<c:url value="/query/${object.code}"/>">4. <span>Ficha</span></a></li>
		  <li class="selected" id="5">5. <span>Borrar</span></li>
		</ul>
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
	
		<div class="row buttons">
	        <div class="col-md-4">                
	          <a class="btn btn-secondary btn-block" href="<c:url value="/query/${object.code}"/>" role="button"  id="botonVolver">Volver</a>
	        </div>
	        <div class="col-md-4 offset-4">      
	        	  <form action="<c:url value='/query/delete/${object.code}' />" method='POST'>
	        	  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />         
	          	<button type="submit" class="btn btn-danger btn-block" id="botonBorrar">Borrar</button>
	           </form>             
	        </div>              
	     </div>
	
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
					//Y un poco de margen
					$('#description').css({				    
				        marginBottom: '10px'
				    });
					
				});
		
		
	</script>	
</body>
</html>
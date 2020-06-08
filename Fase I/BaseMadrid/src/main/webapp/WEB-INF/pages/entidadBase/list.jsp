<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 

<c:set var="title" value="<%=LiteralConstants.TITLE_HEAD_ENTIDAD_BASE%>" scope="session"/>
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
				<p id="parrafoTitulo">Listado CRUD Básico</p>
			</div>
		</div>
	</div>
	
	<div class="form_steps">
		<ul>
		  <li class="done" id="1"><a href="<c:url value="/home"/>">1. <span>Inicio</span></a></li>
		  <li class="done" id="2">2. <span>CRUD Básico</span></li>
		  <li class="selected" id="3">3. <span>Listado</span></li>		  
		</ul>
	</div>
	
	<div class="share_menu">
		<div class="brand">
			<div class="row">
				<div class="col-3">
					<a href="<c:url value="/home"/> "><i class="ico icon-arrow-left"></i> Volver</a>
				</div>
				<div class="col-9 options">
					<ul>
						<li>
							<a title="Añadir" href="<c:url value="/entidadBase/add"/>"><i class="ico icon-add"></i><span class="text">Añadir</span></a>
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
	
	<div class="advice">
    	<c:choose>
		  <c:when test="${empty updated}">
		    <div class="container info d-none" id="expInfo">
		  </c:when>			  
		  <c:otherwise>
		    <div class="container info" id="expInfo">
		  </c:otherwise>
		</c:choose>		        
            <h4 id="updatedAdviceTitle"><%=LiteralConstants.TEXT_UPDATED%></h4>
            <p><%=LiteralConstants.OPERACION_OK%>.</p>
        </div>
    </div>
    
    <div class="advice">
    	<c:choose>
		  <c:when test="${empty added}">
		    <div class="container info d-none" id="expInfo">
		  </c:when>			  
		  <c:otherwise>
		    <div class="container info" id="expInfo">
		  </c:otherwise>
		</c:choose>		        
            <h4 id="addedAdviceTitle"><%=LiteralConstants.TEXT_ADDED%></h4>
            <p><%=LiteralConstants.OPERACION_OK%>.</p>
        </div>
    </div>
    
    <div class="advice">
    	<c:choose>
		  <c:when test="${empty deleted}">
		    <div class="container info d-none" id="expInfo">
		  </c:when>			  
		  <c:otherwise>
		    <div class="container info" id="expInfo">
		  </c:otherwise>
		</c:choose>		        
            <h4 id="deletedAdviceTitle"><%=LiteralConstants.TEXT_DELETED%></h4>
            <p><%=LiteralConstants.OPERACION_OK%>.</p>
        </div>
    </div>
    
    <div class="advice">
    	<c:choose>
		  <c:when test="${empty error}">
		    <div class="container error d-none" id="expInfo">
		  </c:when>			  
		  <c:otherwise>
		    <div class="container error" id="expInfo">
		  </c:otherwise>
		</c:choose>		        
            <h4 id="errorAdviceTitle"><%=LiteralConstants.TEXT_ERROR%></h4>
            <p><%=LiteralConstants.OPERACION_KO%>.</p>
        </div>
    </div>
    
    
    <div class="advice">
    	<c:choose>
		  <c:when test="${empty errorObject}">
		    <div class="container error d-none" id="expInfo">
		  </c:when>			  
		  <c:otherwise>
		    <div class="container error" id="expInfo">
		  </c:otherwise>
		</c:choose>		        
            <h4 id="errorObjectAdviceTitle"><%=LiteralConstants.TEXT_ERROR%> listado</h4>
            <p><%=LiteralConstants.OPERACION_KO%>.</p>
        </div>
    </div>
    
    
    
    <c:choose>
	  <c:when test="${empty list}">
		<div class="advice">    	
		   	<div class="container info" id="expInfo">		        
            	<h4 id="updatedAdviceTitle">Sin datos</h4>
            	<p>No hay existen elementos que mostrar.</p>
        	</div>
    	</div>
	  </c:when>			  
	  <c:otherwise>
			
		<div class="table-pagination">
	    	<% request.setCharacterEncoding("utf-8"); %>
	    	<jsp:include page="../includes/paginacion.jsp" >
				<jsp:param name="texto" value="Entidades básicas" />
			</jsp:include>	
		</div>
		<div class="contents_list">
			<ul>
				<c:forEach var="entidad" items="${list}" varStatus="loop">   
					
					<c:choose>
					  <c:when test="${loop.count eq 1}">
					    <li class="dest">
					  </c:when>			  
					  <c:otherwise>
					    <li>
					  </c:otherwise>
					</c:choose>
					
			            <h4 id="title_element_${loop.count}"><a href="<c:url value="/entidadBase/${entidad.id}"/>"><c:out value="${entidad.texto}" /></a></h4>
			            <p><c:out value="${entidad.textoLargo}" /></p>     
			            <p><span class="fecha"><fmt:formatDate pattern="dd-MM-yyyy" value="${entidad.fecha}" /></span></p>
			        </li>
			     </c:forEach>   
			</ul>
		</div>
		<div class="table-pagination">
			<jsp:include page="../includes/paginacion.jsp" >
				<jsp:param name="texto" value="Entidades básicas" />
			</jsp:include>	
		    
		</div>
		
		</c:otherwise>
		</c:choose>
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
					$( ".calendar, .to, .from" ).datepicker( "option", "buttonImage", "<c:url value='/resources/ayre-assets/images/calendar.png'/>" );
					
					
				});
		
		
	</script>	
</body>
</html>
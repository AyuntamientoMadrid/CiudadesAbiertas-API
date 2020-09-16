<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>

<div class="boton_menu_share_menu">
		<a href="#" title="menu" class="icon-menu1"></a>
		<ul>
			<li class="list_title"><span>Menú Principal</span> <a href="#" title="cerrar" class="back_button"><i class="icon-close "></i></a></li>
			<li><a href="<c:url value="/query"/>">Consultas SQL</a></li>
			<li><a href="<c:url value="/swaggerDef"/>">Definiciones de SWAGGER</a></li>			
			
			<!-- 
			<li class="has_children">
				<a href="#">Datos Semánticos</a>
				<ul>
					<li class='back_button first'><a href='#'>Volver</a></li>
					<li><a href="<c:url value="/semanticDef"/>">Definiciones</a></li>
					<li><a href="<c:url value="/prefix"/>">Prefijos</a></a></li>					
				</ul>
			</li>
			
			 -->			
			<!-- 
			<li><a href="<c:url value="/entidadBase"/>">CRUD Básico</a></li>
			<li class="has_children">
				<a href="#">Opcion 2</a>
				<ul>
					<li class='back_button first'><a href='#'>Volver</a></li>
					<li><a title="Opcion 1" href="#">Opcion 1</a></li>
					<li><a title="Opcion 1" href="#">Opcion 2</a></li>
					<li><a title="Opcion 1" href="#">Opcion 3</a></li>
				</ul>
			</li>
			<li><a href="#">Opcion 3</a></li>
			<li><a href="#">Opcion 4</a></li>
			<li><a href="#">Opcion 5</a></li>
			<li><a href="#">Opcion 6</a></li>
			<li><a href="#">Opcion 7</a></li>
			 -->
		</ul>
	</div>
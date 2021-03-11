<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 
<c:set var="title" value="<%=LiteralConstants.TITLE_HEAD_HOME%>" scope="session"/>
<!DOCTYPE html>
<html lang="es">
<%@include file="includes/head.jsp"%>	
<body>
	<%@include file="includes/header.jsp"%>	
	
	<%@include file="includes/menuLateral.jsp"%>
	

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
				<p>Inicio</p>
			</div>
		</div>
	</div>

	<div class="bg_greysoft">
		<div class="row bloque1">
			<div class="col-xs-12 col-md-12 col-lg-12 col-xl-12">
				<div class="tiny-text">
					<p>Bienvenid@ a la aplicación API Dinámica. </p>
					<p>Puede acceder a los distintos módulos de la aplicación a través del botón menú de la izquierda.</p>
				</div>			
				<c:if test ="${not empty errorFicheros}">
					<div class=" entradilla">
						<p>Errores en ficheros de configuración</p>							
					</div>
					<fieldset>
						<div class="form-row">
							<div class="form-group col-md-3">
								<label class="control-label bold">Ficheros no cargados</label>
								<p>${errorFicheros}</p>
							</div>
						</div>
					</fieldset>
				</c:if>	
				<div class=" entradilla">
					<p>Estadísticas</p>							
				</div>
				<fieldset>
					<div class="form-row">
						<div class="form-group col-md-3">
							<label class="control-label bold">Consultas SQL "Apificadas"</label>
							<p>${numQueries}</p>
						</div>
						<div class="form-group col-md-3">
							<label class="control-label bold">URL más utilizada</label>
							<p>${urlMostWanted}</p>
						</div>
						<div class="form-group col-md-3 ">
							<label class="control-label bold">Última llamada</label>
							<p>${lastPetition}</p>
						</div>
						<div class="form-group col-md-3 ">
							<label class="control-label bold">Llamadas en los últimos 7 días</label>
							<p>${lastWeekPetitions}</p>
						</div>								
					</div>
				</fieldset>
		
			</div>
		</div>
	</div>



	
	<%@include file="includes/footer.jsp"%>	
	<%@include file="includes/foot.jsp"%>	
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
	
	<%@include file="../includes/modal_include.jsp"%>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	
	


	
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
				<p>Definición Semántica</p>
			</div>
		</div>
	</div>
	
	
	
	<div class="form_steps">
		<ul>
		  <li class="done" id="1"><a href="<c:url value="/home"/>">1. <span>Inicio</span></a></li>
		  <li class="done" id="2">2. <span>Datos Semánticos</span></li>
		   <li class="done" id="3">3. <span>Definiciones</span></li>
		  <li class="selected" id="4"><a href="<c:url value="/semanticDef"/>">4. <span>Listado</span></a></li>		  
		  <li class="selected" id="5">5. <span>Añadir</span></li>
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
		<div class="form">
			 <form name='semanticForm' id='semanticForm' action="<c:url value='/semanticDef/save' />" method='POST'>
			 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			 	<input type="hidden" name="prefixSelectedHidden" id="prefixSelectedHidden" value="" />			 	
			 	<div class="tiny-text">
			    	<p>Por favor introduzca todos los campos para dar de alta una nueva Consulta. </p>
          		</div>
		          <div class="col-xs-12 text-right">
		            <p>Campos obligatorios *</p>
		          </div>
		          <div class="form-group title">
		            <div class="col-xs-12">
		              <h4>Configuración incial</h4>
		            </div>
		          </div>            
		          <fieldset>
		          	<div class="form-row">
		              	<div class="form-group col-md-12">
		              	  <div class="row">
			              	  <div class="col-md-11">
			              	  	<label class="control-label" for="query">Consulta *</label>
			              	  </div>
			              	  <div class="col-md-1">
			              	  	<div class="icon icon-informacion pointer" align="center" onclick="activarModal(textoModal_1);"></div>
			              	  </div>
		              	  </div>			              
			              <select class="form-control simple" name="query" id="query" required="true" data-parsley-required="true">
			             		<option value="<c:out value="" />">Seleccione una consulta</option>	  
			              		<c:forEach var="entry" items="${availableQueries}">
								  <option value="<c:out value="${entry.key}" />"><c:out value="${entry.value.summary}" /></option>
								</c:forEach>			              
							</select>	
			        	</div>				        		        	
			        </div>
			        
			        <div class="form-row">
		              	<div class="form-group col-md-6">
			              <label class="control-label" for="query">Prefijos Seleccionados </label>
			              <select class="form-control multiple" name="prefixSelected" id="prefixSelected" multiple="multiple" path="skills">
			              </select>
			        	</div>
			        	
			        	
			        	
			        	<div class="form-group col-md-6">
		                	<div class="col-sm-12 col">
		                  		<label class="control-label">Prefijos disponibles</label>                    
			                 	<select class="form-control multiple" name="prefixAvailable" id="prefixAvailable"  multiple="multiple">	                 								    
							    	<c:forEach var="prefix" items="${prefixList}">
								  		<option title="${prefix.url}" value="<c:out value="${prefix.id}" />"><c:out value="${prefix.id} ${prefix.url}" /></option>
									</c:forEach>
								</select>						
		                	</div>                           
		              	</div>
		              	
			        </div>
			         
			        </fieldset>
			        
			        
			        <div class="form-group title">
		            	<div class="col-xs-12">
		            	  <h4>URL del Tipo (rdf:type)</h4>
		            	</div>
		          	</div> 
			        <fieldset>
			          	<div class="form-row">
			              	<div class="form-group col-md-3">
				              <label class="control-label" for="query">Prefijo</label>
				              <select class="form-control simple" name="prefixType" id="prefixType">
				             		<option value="<c:out value="-1" />">Seleccione un prefijo</option>
								</select>	
				        	</div>
				        	<div class="form-group col-md-9">
				              <div class="form-group">
					                <label class="control-label" for="summary">Fin de URL / URL Completa</label>
					                <input type="text" class="form-control" required="true" id="typeURI" name="typeURI" value="${object.summary}"
					              	data-parsley-trigger="change"
									data-parsley-minlength="2" 
									data-parsley-maxlength="100"
									data-parsley-required="true"				
					              >
					              <span class="hint d-none"></span>
					            </div>	
				        	</div>	
				        </div>			        
			        </fieldset>
			        
			        
			        <div class="form-group title">
		            	<div class="col-xs-12">
		            	  <h4>Relaciones</h4>
		            	</div>
		          	</div> 
			        <fieldset id="relaciones">
			        	<fieldset id="fieldsetCampoZero">
				          	<div class="form-row">
				          		<div class="form-group col-md-3">
					              <label class="control-label" for="query">Atributo</label>
					              <input type="text" class="form-control" required="true" id="fieldNameCampoZero" name="fieldNameCampoZero" value="atributoValue">
					        	</div>
				              	<div class="form-group col-md-3">
					              <label class="control-label" for="query">Prefijo</label>
					              <select class="form-control simple" name="prefixTypeCampoZero" id="prefixTypeCampoZero">
					             		<option value="<c:out value="-1" />">Seleccione un prefijo</option>
									</select>	
					        	</div>
					        	<div class="form-group col-md-6">
					              <div class="form-group">
						                <label class="control-label" for="urlCampoZero">Fin de URL / URL Completa</label>
						                <input type="text" class="form-control" required="true" id="urlCampoZero" name="urlCampoZero" value=""
						              	data-parsley-trigger="change"
										data-parsley-minlength="2" 
										data-parsley-maxlength="100"
										data-parsley-required="true"				
						              >
						              <span class="hint d-none"></span>
						            </div>	
					        	</div>					        	
					        </div>
					        <div class="form-row">			          		
				              	<div class="form-group col-md-4 offset-md-8">
				              		<div class="form-group">
						              <label class="control-label" for="query">Tipo</label>
						              <select class="form-control simple" name="fieldTypeCampoZero" id="fieldTypeCampoZero">
						             		<c:forEach var="dataType" items="${xsdDataTypes}">
								  				<option value="<c:out value="${dataType.value}" />"><c:out value="${dataType.name} ${dataType.example}" /></option>
											</c:forEach>
									  </select>	
						        	</div>
						        </div>
					        			        	
					        </div>	
				        </fieldset>			 			        
			        </fieldset>
			        
			        
			        
			        <div class="row buttons">
			            <div class="col-md-4">                
			              <a class="btn btn-secondary btn-block" href="<c:url value="/semanticDef"/>" role="button" id="botonVolver">Volver</a>
			            </div>
			            <div class="col-md-4 offset-4">                
			              <button type="button" class="btn btn-success btn-block" onClick="validateForm()" id="botonGuardar">Guardar</button>
			            </div>              
					</div>
			 </form>
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
	<script id="taskScript" src="<c:url value="/resources/js/semantic/commons.js?v=1"/>" data-context="${contextPath}"></script>
	
	<script>
	var textoModal_1 =  "1 Separador del path es <b>\\</b> Ejemplo: D:\\temp\\dir\\fichero. <br>"+
					 	"1 Se permite incluir las anotaciones del tipo <b>FECHA</b> y <b>HORA</b> para concatenar al nombre del fichero. <br>" +
					 	"1 Ejemplo: <b>D:\\temp\\dir\\fichero_FECHA_HORA</b> ";
	var textoModal_2 =  "2 Separador del path es <b>\\</b> Ejemplo: D:\\temp\\dir\\fichero. <br>"+
					 	"2 Se permite incluir las anotaciones del tipo <b>FECHA</b> y <b>HORA</b> para concatenar al nombre del fichero. <br>" +
					 	"2 Ejemplo: <b>D:\\temp\\dir\\fichero_FECHA_HORA</b> ";
	var textoModal_3 =  "3 Separador del path es <b>\\</b> Ejemplo: D:\\temp\\dir\\fichero. <br>"+
					 	"3 Se permite incluir las anotaciones del tipo <b>FECHA</b> y <b>HORA</b> para concatenar al nombre del fichero. <br>" +
					 	"3 Ejemplo: <b>D:\\temp\\dir\\fichero_FECHA_HORA</b> ";
	</script>
	
	<style>
		#fieldsetCampoZero{
			display:none;
		}
	</style>
	
		
</body>

</html>
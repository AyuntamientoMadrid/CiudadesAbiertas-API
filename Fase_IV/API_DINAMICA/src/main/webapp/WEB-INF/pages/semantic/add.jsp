<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 
<c:set var="title" value="<%=LiteralConstants.TITLE_HEAD_SEMANTIC_DEF%>" scope="session"/>
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
					<a href="<c:url value="/semanticDef"/>"><i class="ico icon-arrow-left"></i> Volver</a>
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
			    	<p>Por favor introduzca todos los campos para dar de alta una nueva definición. </p>
          		</div>
		          <div class="col-xs-12 text-right">
		            <p>Campos obligatorios *</p>
		          </div>
		          
		          
		          <div id="capaConfiguracionInicial">
		          
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
				              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaConsulta);"></div>
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
				              <div class="row">
				              	  <div class="col-md-10">
				              	  	<label class="control-label" for="query">Prefijos Seleccionados</label>
				              	  </div>
				              	  <div class="col-md-2">
				              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaPrefijoSel);"></div>
				              	  </div>
			              	  </div>
				              <select class="form-control multiple" name="prefixSelected" id="prefixSelected" multiple="multiple" path="skills">
				              </select>
				        	</div>
				        	
				        	
				        	
				        	<div class="form-group col-md-6">
			                	<div class="col-sm-12 col">		                  		
			                  		<div class="row">
					              	  <div class="col-md-10">
					              	  	<label class="control-label">Prefijos disponibles</label>
					              	  </div>
					              	  <div class="col-md-2">
					              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaPrefijoDis);"></div>
					              	  </div>
				              	  	</div>                  
				                 	<select class="form-control multiple" name="prefixAvailable" id="prefixAvailable"  multiple="multiple">	                 								    
								    	<c:forEach var="prefix" items="${prefixList}">
									  		<option title="${prefix.url}" value="<c:out value="${prefix.id}" />"><c:out value="${prefix.code} ${prefix.url}" /></option>
										</c:forEach>
									</select>						
			                	</div>                           
			              	</div>
			              	
				        </div>
				         
				        </fieldset>
			        
			        </div>
			        
			        <div id="capaTipo">
			        	<div class="form-group title">
			            	<div class="col-xs-12">
			            	  <h4>URI de cada elemento</h4>
			            	</div>
			          	</div> 
			          	
			          	     <fieldset>
				          	<div class="form-row">				              	
					        	<div class="form-group col-md-8">
					              <div class="form-group">					                
						                <div class="row">
						              	  <div class="col-md-10">
						              	  	  <label class="control-label" for="summary">Inicio de URI</label>
						              	  </div>
						              	  <div class="col-md-2">
						              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaInputURI);"></div>
						              	  </div>
					              	  	</div> 
						                <input type="text" class="form-control" id="inputObjectURI" name="inputObjectURI" value="" disabled="disabled">
						              <span class="hint d-none"></span>
						            </div>	
					        	</div>
					        	<div class="form-group col-md-4">
					              <div class="form-group">					                
						                <div class="row">
						              	  <div class="col-md-10">
						              	  	  <label class="control-label" for="summary">Fin de URI</label>
						              	  </div>
						              	  <div class="col-md-2">
						              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaFinURI);"></div>
						              	  </div>
					              	  	</div> 
						                 <select class="form-control simple" name="fieldObjectURI" id="fieldObjectURI" required="">
					             			<option value="">Seleccione un campo</option>
										</select>
						              <span class="hint d-none"></span>
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
					              	<div class="row">
					              	  <div class="col-md-8">
					              	  	  <label class="control-label" for="query">Prefijo</label>
					              	  </div>
					              	  <div class="col-md-4">
					              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaPrefijoType);"></div>
					              	  </div>
				              	  	</div> 
					              <select class="form-control simple" name="prefixType" id="prefixType">
					             		<option value="<c:out value="-1" />">Seleccione un prefijo</option>
									</select>	
					        	</div>
					        	<div class="form-group col-md-9">
					              <div class="form-group">					                
						                <div class="row">
						              	  <div class="col-md-10">
						              	  	  <label class="control-label" for="summary">Fin de URL / URL Completa</label>
						              	  </div>
						              	  <div class="col-md-2">
						              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaTypeURL);"></div>
						              	  </div>
					              	  	</div> 
						                <input type="text" class="form-control" id="typeURI" name="typeURI" value="${object.summary}">
						              <span class="hint d-none"></span>
						            </div>	
					        	</div>	
					        </div>			        
				        </fieldset>			        
			        </div>
			        
			        <div class="form-group title">
			       		<div class="col-xs-12">
          	 				<h4 id="listadoRelaciones">Listado Relaciones</h4>
          				</div>
          				<fieldset id="relacionesListado">
          				
          					<table class="table-striped" id="relacionesTabla" style="width:100%">
          					</table>
          				
          				</fieldset>
          			</div>
			        
			        
			        <jsp:include page="../includes/semantic/campoZero.jsp" />
					
					
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
	<script id="taskScript" src="<c:url value="/resources/js/semantic/commons.js"/>" data-context="${contextPath}"></script>
	<script src="<c:url value="/resources/js/semantic/help.js"/>"></script>
	
	<script>	
	$("#capaTipo").hide();
	$("#capaRelaciones").hide();
	
	$("#botonGuardar").attr("disabled", true);
	
	var querySelected="";
	var objectURI="${objectURI}";
	
	</script>
	
	<style>
		#fieldsetCampoZero{
			display:none;
		}
	</style>
	
		
</body>

</html>
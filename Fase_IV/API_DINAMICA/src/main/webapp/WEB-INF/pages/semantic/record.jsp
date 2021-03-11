<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 
<%@ page errorPage="/errorHandler.jsp" %>

<c:set var="title" value="<%=LiteralConstants.TITLE_HEAD_SEMANTIC_DEF%>" scope="session"/>
<!DOCTYPE html>
<html lang="es">
<%@include file="../includes/head.jsp"%>	

<body>

	<%@include file="../includes/header.jsp"%>	
	
	<%@include file="../includes/menuLateral.jsp"%>
	
	<%@include file="../includes/modal_include.jsp"%>
	
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
				<p id="parrafoTitulo">${object.rml.query}</p>
			</div>
		</div>
	</div>
	
	
	
	<div class="form_steps">
		<ul>
		  <li class="done" id="1"><a href="<c:url value="/home"/>">1. <span>Inicio</span></a></li>
		  <li class="done" id="2">2. <span>Datos Semánticos</span></li>
		  <li class="done" id="3">3. <span>Definiciones</span></li>
		  <li class="selected" id="3"><a href="<c:url value="/semanticDef"/>">3. <span>Listado</span></a></li>		  
		  <li class="selected" id="4">4. <span>Ficha</span></li>
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
						<li>
							<a title="Editar" href="<c:url value="/semanticDef/edit/${object.rml.query}"/>" id="enlaceEditar"><i class="ico icon-exchange"></i><span class="text">Editar</span></a>
						</li>
						<li>
							<a title="Eliminar" href="<c:url value="/semanticDef/delete/${object.rml.query}"/>" id="enlaceBorrar"/><i class="ico icon-close"></i><span class="text">Eliminar</span></a>
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
		<div class="form">
			 			 		
		          <div class="form-group title">
		            <div class="col-xs-12">
		              <h4>Configuración inicial</h4>
		            </div>
		          </div>            
		          <fieldset>
		          	<div class="form-row">
		              	<div class="form-group col-md-12">
		              	  <div class="row">
			              	  <div class="col-md-11">
			              	  	<label class="control-label" for="query">Consulta</label>
			              	  </div>
			              	  <div class="col-md-1">
			              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaConsulta);"></div>
			              	  </div>
		              	  </div>			              
			              <select class="form-control simple" name="query" id="query" required="true" data-parsley-required="true" disabled="disabled">
			             		<option value="<c:out value="${object.rml.query}" />">${object.rml.query}</option>
							</select>	
			        	</div>				        		        	
			        </div>
			        
			        <div class="form-row">
		              			        	
			        	<div class="form-group col-md-12">
		                	<div class="col-sm-12 col">		                  		
		                  		<div class="row">
				              	  <div class="col-md-10">
				              	  	<label class="control-label">Prefijos seleccionados</label>
				              	  </div>
				              	  <div class="col-md-2">
				              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaPrefijoSel);"></div>
				              	  </div>
			              	  	</div>                  
			                 	<select class="form-control multiple" name="prefixAvailable" id="prefixAvailable"  multiple="multiple" size="${object.prefixes.size()}" disabled="disabled">	                 								    
							    	<c:forEach var="prefix" items="${object.prefixes}">
								  		<option title="${prefix.url}" value="<c:out value="${prefix.id}" />"><c:out value="${prefix.code} ${prefix.url}" /></option>
									</c:forEach>
								</select>						
		                	</div>                           
		              	</div>
		              	
			        </div>
			         
			        </fieldset>
			        
			        
			         <div id="capaTipo">
			        
			        	<div class="form-group title">
			            	<div class="col-xs-12">
			            	  <h4>URI de cada elemento</h4>
			            	</div>
			          	</div> 
			          	
			          	 <fieldset>
				          	<div class="form-row">				              	
					        	<div class="form-group col-md-12">
					              <div class="form-group">					                
						                <div class="row">
						                <div class="col-md-10">
				              	  			<label class="control-label">&nbsp;</label>
				              	 		 </div>
						              	  <div class="col-md-2">
						              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaInputURI);"></div>
						              	  </div>
					              	  	</div> 
						                <input type="text" class="form-control" id="inputObjectURI" name="inputObjectURI" value="${object.subjectURI}" disabled="disabled">
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
					              	<select class="form-control simple" name="prefixType" id="prefixType" disabled="disabled">
					             		<option value="<c:out value="-1" />">${object.prefixTypeURL}</option>
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
						                <input type="text" class="form-control" id="typeURI" name="typeURI" value="${object.typeURL}" disabled="disabled"
						              	data-parsley-trigger="change"
										data-parsley-minlength="2" 
										data-parsley-maxlength="100"
										data-parsley-required="false"				
						              >
						              <span class="hint d-none"></span>
						            </div>	
					        	</div>	
					        </div>			        
				        </fieldset>
			        
			        </div>
			        
			        
			        <c:if test="${not empty object.fields}">
			        
			        	<jsp:include page="../includes/semantic/campoZero.jsp" />
			        
			        </c:if>
			        
					
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
	
	<script src="<c:url value="/resources/js/semantic/help.js"/>"></script>
	<script src="<c:url value="/resources/js/semantic/drawField.js"/>"></script>
	
	
	<script>
	
		var elementsAdded=new Array();
		<c:forEach var="entry" items="${object.fields}">
			  drawField('${entry.field}',
					  	'${entry.predicate}',
					  	'${entry.objectType}',
					  	'${entry.objectReference}',
					  	'${entry.objectReferenceType}',
					  	'${entry.blankNodeType}',
					  	'${entry.blankNodeId}',
					  	'${entry.blankNodeProperty}',
					  	true
			  );
			  
		</c:forEach>
		
	</script>	
	
	<style>
		#fieldsetCampoZero{
			display:none;
		}
	</style>

</body>

</html>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.Constants" %>  
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 



	<c:choose>
	  <c:when test="${param.type eq  'add'}">
	    <form name='queryForm' id='queryForm' action="<c:url value='/query/save' />" method='POST'>
	  </c:when>			  
	  <c:when test="${param.type eq  'edit'}">
	    <form name='queryForm' id='queryForm' action="<c:url value='/query/update/${object.code}' />" method='POST'>
	  </c:when>
	  <c:otherwise>
	    <form></form>
	  </c:otherwise>
	</c:choose>

	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<input type="hidden" name="originalCode" value="${object.code}" />
          <div class="tiny-text">
          
         	<c:choose>
			  <c:when test="${param.type eq  'add'}">
			    <p>Por favor introduzca todos los campos para dar de alta una nueva Consulta. </p>
			  </c:when>			  
			  <c:when test="${param.type eq  'edit'}">
			    <p>Por favor modifique los campos que desee en esta Consulta. </p>
			  </c:when>
			  <c:otherwise>
			    <p>Parámetro desconocido</p>
			  </c:otherwise>
			</c:choose>	
            
          </div>
          <div class="col-xs-12 text-right">
            <p>Campos obligatorios *</p>
          </div>
          <div class="form-group title">
            <div class="col-xs-12">
              <h4>Consulta SQL</h4>
            </div>
          </div>            
          <fieldset>
          	<div class="form-row">
              	<div class="form-group col-md-6">
	              <label class="control-label" for="code">Código *</label>
	              <input type="text" class="form-control" required="true" id="code" name="code" value="${object.code}"
	              	data-parsley-trigger="change"
					data-parsley-minlength="2" 
					data-parsley-maxlength="50"
					data-parsley-required="true">
	              	<span class="hint d-none"></span>
	        	</div>
	        	<div class="form-group col-md-6">
                	<div class="col-sm-12 col">
                  		<label class="control-label">Conexión de Base de Datos</label>                    
	                 	<select class="form-control simple" name="database" id="database" required="true">	                 								    
					    	<c:forEach items="${databases}" var="bd">
					    		<c:choose>
								  <c:when test="${bd == databaseSelected}">
								    <option value="<c:out value="${bd}" />" selected><c:out value="${bd}" /></option>
								  </c:when>			  
								  <c:otherwise>
								    <option value="<c:out value="${bd}" />"><c:out value="${bd}" /></option>
								  </c:otherwise>
								</c:choose>
						    </c:forEach>
						</select>						
                	</div>                           
              	</div>
	        </div>
            <div class="form-group">
              <label for="texto">Consulta SQL *</label>
              <textarea class="form-control"  id="texto" name="texto" rows="3" 
              	data-parsley-trigger="change"
			data-parsley-minlength="0" 
			data-parsley-maxlength="4000"
			data-parsley-required="true"				
              >${object.texto}</textarea>
            </div>
                        
            <div class="form-group">
                <label class="control-label" for="summary">Descripción</label>
                <input type="text" class="form-control" required="true" id="summary" name="summary" value="${object.summary}"
              	data-parsley-trigger="change"
				data-parsley-minlength="2" 
				data-parsley-maxlength="100"
				data-parsley-required="true"				
              >
              <span class="hint d-none"></span>
            </div>
            <div class="form-group">
                <label class="control-label">Etiquetas</label>
                <input type="text" class="form-control" id="tags" name="tags" value="${object.tags}"
              	data-parsley-trigger="change"
				data-parsley-minlength="2" 
				data-parsley-maxlength="255"
				data-parsley-required="false"				
              >
              <span class="hint d-none"></span>                
            </div>
            
           <c:if test="${(not empty definitions)}">
            
            <div class="form-row">
              	<div class="form-group col-md-6">
	              <label class="control-label" for="code">Definición</label>	              
	              	<select class="form-control simple" name="definition" id="definition" required="true">
	              			<option value="-1">Selección de un modelo</option>	                 								    
					    	<c:forEach items="${definitions}" var="def">					    		
					    		<c:choose>
								  <c:when test="${def.code == definitionSelected}">
								    <option value="<c:out value="${def.code}" />" selected><c:out value="${def.description}" /></option>
								  </c:when>			  
								  <c:otherwise>
								    <option value="<c:out value="${def.code}" />"><c:out value="${def.description}" /></option>
								  </c:otherwise>
								</c:choose>
						    </c:forEach>
					</select>	
	        	</div>
	        </div>
	        
	       </c:if>
	       
          </fieldset>
          
          <div class="row buttons" id="addParamsDiv">            
				<div class="col-md-3">                
		        	<button type="button" class="btn btn-success btn-block" onClick="showParamsDiv()" id="buttonAddParam">Añadir Parámetros</button>
		        </div>              
		  </div>	
          
          <div id="capaParametros">
          
          <div class="form-group title">
            <div class="col-xs-12">
              <h4>Parámetros</h4>
            </div>
          </div>            
          
          <c:choose>
			  <c:when test="${((param.type eq 'add') || ((param.type eq 'edit') && (empty paramList)))}">
							  
			  	  <fieldset id="fieldSetParametros">
          
		            <div id="capaParametro0">
		          
		          		<fieldset class="fieldsetParam">
		          
			          	<div class="form-row">
			              	<div class="form-group col-md-6">
				              <label class="control-label" for="code">Nombre *</label>
				              <input type="text" class="form-control" id="code" name="code0" value=""
				              	data-parsley-trigger="change"
								data-parsley-minlength="2" 
								data-parsley-maxlength="20"
								data-parsley-required="true">
				              	<span class="hint d-none"></span>
				        	</div>
				        	<div class="form-group col-md-6">
			                	<div class="col-sm-12 col">
			                  		<label class="control-label">Conexión de Base de Datos</label>                    
				                 	<select class="form-control simple" name="type0" id="type" required="true">              								    
								    	<option value="<%=Constants.TEXT%>">Texto</option>
										<option value="<%=Constants.NUMBER%>">Número</option>
										<option value="<%=Constants.DATE%>">Fecha</option>
									</select>						
			                	</div>                           
			              	</div>
				        </div>      
			            
			            <div class="form-row">
			              	<div class="form-group col-md-6">
				              <label class="control-label" for="code">Descripción</label>
				              <input type="text" class="form-control" id="description" name="description0" value=""
				              	data-parsley-trigger="change"
								data-parsley-minlength="2" 
								data-parsley-maxlength="20"
								data-parsley-required="false">
				              	<span class="hint d-none"></span>
				        	</div>
				        	<div class="form-group col-md-6">
				              <label class="control-label" for="code">Ejemplo</label>
				              <input type="text" class="form-control" id="example" name="example0" value=""
				              	data-parsley-trigger="change"
								data-parsley-minlength="2" 
								data-parsley-maxlength="20"
								data-parsley-required="false">
				              	<span class="hint d-none"></span>
				        	</div>
				        </div>
				        
				        <div class="row buttons" id="botonBorrar0">            
				            <div class="col-md-4 offset-8">                
				              <button type="button" class="btn btn-danger btn-block" onClick="removeParamFromForm(0)" id="removeParam">Borrar Parámetro</button>
				            </div>              
		         		</div>
			        
			        	</fieldset>
			        </div>
			        
			        <div class="row buttons" id="addParam0">            
						<div class="col-md-3 offset-9">                
				        	<button type="button" class="btn btn-success btn-block" onClick="addParamToForm()" id="buttonAddParam">Añadir Parámetro</button>
				        </div>              
					</div>
			        
		          </fieldset>
			    
			  </c:when>			  
			  
			  
			  
			  <c:when test="${(param.type eq  'edit') && (not empty paramList) }">
			  
			  
			  	 
			  
			     <fieldset id="fieldSetParametros">		          		
	          		<c:forEach var="entidad" items="${paramList}" varStatus="loop">		          		
		          		<div id="capaParametro${loop.count-1}">		          
			          		<fieldset class="fieldsetParam">			          
					          	<div class="form-row">
					              	<div class="form-group col-md-6">
						              <label class="control-label" for="code">Nombre *</label>
						              <input type="text" class="form-control" id="code" name="code${loop.count-1}" value="${entidad.name}"
						              	data-parsley-trigger="change"
										data-parsley-minlength="0" 
										data-parsley-maxlength="20"
										data-parsley-required="false">
						              	<span class="hint d-none"></span>
						        	</div>
						        	<div class="form-group col-md-6">
					                	<div class="col-sm-12 col">
					                  		<label class="control-label">Conexión de Base de Datos</label>                    
						                 	<select class="form-control simple" name="type${loop.count-1}" id="type" required="true">      
						                 		<c:if test="${entidad.type == Constants.TEXT}">
								    				<option value="<%=Constants.TEXT%>" selected>Texto</option>
								    				<option value="<%=Constants.NUMBER%>">Número</option>
													<option value="<%=Constants.DATE%>">Fecha</option>
								  				</c:if>        								    
								  				<c:if test="${entidad.type == Constants.NUMBER}">
								    				<option value="<%=Constants.TEXT%>">Texto</option>
								    				<option value="<%=Constants.NUMBER%>" selected>Número</option>
													<option value="<%=Constants.DATE%>">Fecha</option>
								  				</c:if>
										    	<c:if test="${entidad.type == Constants.DATE}">
								    				<option value="<%=Constants.TEXT%>">Texto</option>
								    				<option value="<%=Constants.NUMBER%>">Número</option>
													<option value="<%=Constants.DATE%>" selected>Fecha</option>
								  				</c:if>
											</select>						
					                	</div>                           
					              	</div>
						        </div>   
					            <div class="form-row">
					              	<div class="form-group col-md-6">
						              <label class="control-label" for="code">Descripción</label>
						              <input type="text" class="form-control" id="description" name="description${loop.count-1}" value="${entidad.description}"
						              	data-parsley-trigger="change"
										data-parsley-minlength="0" 
										data-parsley-maxlength="20"
										data-parsley-required="false">
						              	<span class="hint d-none"></span>
						        	</div>
						        	<div class="form-group col-md-6">
						              <label class="control-label" for="code">Ejemplo</label>
						              <input type="text" class="form-control" id="example" name="example${loop.count-1}" value="${entidad.example}"
						              	data-parsley-trigger="change"
										data-parsley-minlength="0" 
										data-parsley-maxlength="20"
										data-parsley-required="false">
						              	<span class="hint d-none"></span>
						        	</div>
						        </div>					        
						        
						        			        
						        <div class="row buttons" id="botonBorrar${loop.count-1}">            
						            <div class="col-md-4 offset-8">                
						              <button type="button" class="btn btn-danger btn-block" onClick="removeParamFromForm(${loop.count-1})" id="removeParam">Borrar Parámetro</button>
						            </div>              
				         		</div>				        
				         		
				         		
				        	</fieldset>
				        </div>
				    </c:forEach>
			        
			        <div class="row buttons" id="addParam0">            
						<div class="col-md-3 offset-9">                
				        	<button type="button" class="btn btn-success btn-block" onClick="addParamToForm()" id="buttonAddParam">Añadir Parámetro</button>
				        </div>              
					</div>					
								        
		          </fieldset>
			  </c:when>
			  			  
			</c:choose>	
          
        <!-- Div capa parámetros -->  
        </div>
          
		
          
       	<div class="row buttons">
            <div class="col-md-4">                
              <a class="btn btn-secondary btn-block" href="<c:url value="/query"/>" role="button" id="botonVolver">Volver</a>
            </div>
            <div class="col-md-4 offset-4">                
              <button type="button" class="btn btn-success btn-block" onClick="validateForm()" id="botonGuardar">Guardar</button>
            </div>              
		</div>
         </form>

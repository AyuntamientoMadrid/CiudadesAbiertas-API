<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.Constants" %>  
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 


<div id="capaRelaciones">



	<div class="form-group title">
          	<div class="col-xs-12">
          	  <h4>Relaciones</h4>
          	</div>
        	</div> 
       <fieldset id="relaciones">
       	<fieldset id="fieldsetCampoZero">
       		<div class="form-row">
		        	<div class="col-md-12 flechasNav" align="right">			        		       	
		        		<a href="#listadoRelaciones"  title="Desplazarse al listado de relaciones"><span class="icon icon-arrowshaftup pointer"  /></a>
		        		<a href="#bottom"  title="Desplazarse al botón guardar"><span class="icon icon-arrow-shaft-down pointer" /></a>		        		
		        	</div>
	        	</div>
          	<div class="form-row">
          		<div class="form-group col-md-12">					              
	              <div class="row">
	              	  <div class="col-md-10">
	              	  	<label class="control-label" for="query">Campo de la consulta</label>
	              	  </div>
	              	  <div class="col-md-2">
	              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaCampoConsulta);"></div>
	              	  </div>
              	  </div>
	              <input type="text" class="form-control" required="true" id="fieldNameCampoZero" name="fieldNameCampoZero" value="atributoValue" readonly="readonly">
	        	</div>
	        </div>
	        <div class="row">
             	  <div class="col-md-10">
             	  	<span>Predicado</span>
             	  </div>
             	  <div class="col-md-2">
             	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaPredicado);"></div>
             	  </div>
            </div>					        		          			
	        <fieldset>
	        <div class="form-row">
              	<div class="form-group col-md-4">					              
	              <div class="row">
	              	  <div class="col-md-10">
	              	  	<label class="control-label" for="query">Prefijo</label>
	              	  </div>
	              	  <div class="col-md-2">
	              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaPredicadoPrefix);"></div>
	              	  </div>
              	  </div>
	              <select class="form-control simple" name="prefixTypeCampoZero" id="prefixTypeCampoZero">
	             		<option value="<c:out value="-1" />">Seleccione un prefijo</option>
					</select>	
	        	</div>
	        	<div class="form-group col-md-8">
	              <div class="form-group">						               
		                <div class="row">
			              	  <div class="col-md-10">
			              	  	 <label class="control-label" for="urlCampoZero">Fin de URL / URL Completa</label>
			              	  </div>
			              	  <div class="col-md-2">
			              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaPredicadoURI);"></div>
			              	  </div>
		              	</div>
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
	        </fieldset>					        
          		<div class="row">
             	  <div class="col-md-10">
             	  	<span>Objeto</span>
             	  </div>
             	  <div class="col-md-2">
             	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaObjeto);"></div>
             	  </div>
            </div>			          			
	        <fieldset>
	        	<div class="form-row">
			        <div class="form-group col-md-5">

	              		<div class="form-check">
	              			
		              		<div class="row">
			              		<div class="col-md-3">					             
					              	<input class="form-check-input" type="radio" value="simple" id="objectTypeSimpleCampoZero" name="objectTypeCampoZero" checked="true" onchange="javascript:typeObjectChange(this,'CampoZero')">						              
								    <label class="form-check-label" for="simple">Simple</label>
								</div>
								<div class="col-md-3">	
						    		 <input class="form-check-input" type="radio" value="url" id="objectTypeUrlCampoZero" name="objectTypeCampoZero" onchange="javascript:typeObjectChange(this,'CampoZero')">						              
						    		 <label class="form-check-label" for="url">URL</label>
						    	</div>
						    	<div class="col-md-6">
							    	<input class="form-check-input" type="radio" value="blank" id="objectTypeBlanckCampoZero" name="objectTypeCampoZero" onchange="javascript:typeObjectChange(this,'CampoZero')">						              
							   		<label class="form-check-label" for="blank">Nodo en blanco</label>
							    </div>
						    </div>	
			        	</div>
			        </div>						        
		     	</div>
		     
		     <div class="form-row" id="capaTipoSimpleCampoZero">
			     <div class="form-group col-md-7">					              
	              <div class="row">
	              	  <div class="col-md-10">
	              	  	<label class="control-label" for="query">Valor</label>
	              	  </div>
	              	  <div class="col-md-2">
	              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaObjetoSimple);"></div>
	              	  </div>
              	  </div>
	              <input type="text" class="form-control" required="true" id="objectValueCampoZero" name="objectValueCampoZero" value="atributoValue">
	        	</div>
		     	<div class="form-group col-md-5">
              		<div class="form-group">
		              
		              <div class="row">
	              	  <div class="col-md-10">
	              	  	<label class="control-label" for="query">Tipo de objeto</label>
	              	  </div>
	              	  <div class="col-md-2">
	              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaTypes);"></div>
	              	  </div>
              	  </div>
		              <select class="form-control simple" name="fieldTypeCampoZero" id="fieldTypeCampoZero">
		             		<c:forEach var="dataType" items="${xsdDataTypes}">
				  				<option value="<c:out value="${dataType.value}" />"><c:out value="${dataType.name} ${dataType.example}" /></option>
							</c:forEach>
					  </select>	
		        	</div>
		        </div>
		      </div>
		      
		      
		      <div class="form-row" id="capaTipoURLCampoZero" style="display:none">
			     <div class="form-group col-md-12">					              
	              <div class="row">
	              	  <div class="col-md-10">
	              	  	<label class="control-label" for="query">Valor</label>
	              	  </div>
	              	  <div class="col-md-2">
	              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaObjectoURI);"></div>
	              	  </div>
              	  </div>
	              <input type="text" class="form-control" required="true" id="objectValueURLCampoZero" name="objectValueURLCampoZero" value="atributoValue">
	        	</div>
		     	
		      </div>
        			
		    <div id="capaNodoBlancoCampoZero" style="display:none">
		    					    
			     <div class="form-row">			          		
	              	<div class="form-group col-md-6">
	              		<div class="form-group">						                
			                <div class="row">
			              	  <div class="col-md-10">
			              	  	<label class="control-label" for="urlCampoZero">Identificador del nodo en blanco</label>
			              	  </div>
			              	  <div class="col-md-2">
			              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaNodoId);"></div>
			              	  </div>
				            </div>
			                <input type="text" class="form-control" required="true" id="nodeIdCampoZero" name="nodeIdCampoZero" value=""
			              	data-parsley-trigger="change"
							data-parsley-minlength="2" 
							data-parsley-maxlength="100"
							data-parsley-required="true"				
			              >
			              <span class="hint d-none"></span>
			            </div>	
			        </div>
			    </div>
		    

           		<div class="row">
              	  <div class="col-md-10">
              	  	<span>URL del Tipo (rdf:type) del nodo en blanco</span>
              	  </div>
              	  <div class="col-md-2">
              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaNodoType);"></div>
              	  </div>
	            </div>
	            
		     	<fieldset>	
				    <div class="form-row">	
				        <div class="form-group col-md-4">
			              <label class="control-label" for="query">Prefijo</label>
			              <select class="form-control simple" name="prefixNodePredicateTypeCampoZero" id="prefixNodePredicateTypeCampoZero">
			             		<option value="<c:out value="-1" />">Seleccione un prefijo</option>
							</select>	
			        	</div>
			        	<div class="form-group col-md-8">
			              <div class="form-group">
				                <label class="control-label" for="summary">Fin de URL / URL Completa</label>
				                <input type="text" class="form-control" required="true" id="urlNodePredicateTypeCampoZero" name="urlNodePredicateTypeCampoZero" value=""
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
           		<div class="row">
              	  <div class="col-md-10">
              	  	<span>Predicado que apunta al nodo en blanco</span>
              	  </div>
              	  <div class="col-md-2">
              	  	<div class="icon icon-informacion pointer" align="right" onclick="activarModal(ayudaNodoPredicate);"></div>
              	  </div>
	            </div>
	            
		     	<fieldset>	
				    <div class="form-row">	
				      	<div class="form-group col-md-4">
		              		<label class="control-label" for="prefixTypeNodePredicateCampoZero">Prefijo</label>
		              		<select class="form-control simple" name="prefixNodePredicateCampoZero" id="prefixNodePredicateCampoZero">
		             			<option value="<c:out value="-1" />">Seleccione un prefijo</option>
							</select>	
		        		</div>
		        		<div class="form-group col-md-8">
			              <div class="form-group">
				                <label class="control-label" for="summary">Fin de URL / URL Completa</label>
				                <input type="text" class="form-control" required="true" id="urlNodePredicateCampoZero" name="urlNodePredicateCampoZero" value=""
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
       
       		</div>
       
       	</fieldset>
       	
       	</fieldset>
       
	       
			
	</fieldset>
</div>
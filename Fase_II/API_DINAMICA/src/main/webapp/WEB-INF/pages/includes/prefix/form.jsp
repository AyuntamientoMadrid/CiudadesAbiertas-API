<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.Constants" %>  
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 

<%@include file="../modal_include.jsp"%>

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

	<c:choose>
	  <c:when test="${param.type eq  'add'}">
	    <form name='queryForm' id='queryForm' action="<c:url value='/prefix/save' />" method='POST'>
	  </c:when>			  
	  <c:when test="${param.type eq  'edit'}">
	    <form name='queryForm' id='queryForm' action="<c:url value='/prefix/update/${object.id}' />" method='POST'>
	  </c:when>
	  <c:otherwise>
	    <form></form>
	  </c:otherwise>
	</c:choose>

	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<input type="hidden" name="originalCode" value="${object.id}" />
          <div class="tiny-text">
          
         	<c:choose>
			  <c:when test="${param.type eq  'add'}">
			    <p>Por favor introduzca todos los campos para dar de alta un nuevo Prefijo. </p>
			  </c:when>			  
			  <c:when test="${param.type eq  'edit'}">
			    <p>Por favor modifique los campos que desee en este Prefijo. </p>
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
              <h4>Prefijo</h4>
            </div>
          </div>            
          <fieldset>
          	<div class="form-row">
              	<div class="form-group col-md-3">
	              <label class="control-label" for="code">Identificador *</label>
	              <input type="text" class="form-control" required="true" id="id" name="id" value="${object.id}"
	              	data-parsley-trigger="change"
					data-parsley-minlength="2" 
					data-parsley-maxlength="50"
					data-parsley-required="true">
	              	<span class="hint d-none"></span>
	        	</div>
	        	<div class="form-group col-md-8">
                	<div class="col-sm-12 col">
                  		<label class="control-label" for="summary">URL</label>
               			<input type="text" class="form-control" required="true" id="url" name="url" value="${object.url}"
              				data-parsley-trigger="change"
							data-parsley-minlength="2" 
							data-parsley-maxlength="100"
							data-parsley-required="true"				
          			    >						
                	</div>                           
              	</div>
              	<div class="form-group col-md-1">
					<div class="col-sm-1 col">
						<label class="control-label">Ayuda</label>
						<div class="icon icon-informacion" align="center" onclick="activarModal(textoModal_1);"></div>
					</div>
				</div>
	        </div>   
	       
          </fieldset>      
         
          
    
		
          
       	<div class="row buttons">
            <div class="col-md-4">                
              <a class="btn btn-secondary btn-block" href="<c:url value="/prefix"/>" role="button" id="botonVolver">Volver</a>
            </div>
            <div class="col-md-4 offset-4">                
              <button type="button" class="btn btn-success btn-block" onClick="validateForm()" id="botonGuardar">Guardar</button>
            </div>              
		</div>
         </form>

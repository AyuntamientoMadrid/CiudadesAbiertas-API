<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 



	<c:choose>
	  <c:when test="${param.type eq  'add'}">
	    <form name='entidadBaseForm' id='entidadBaseForm' action="<c:url value='/entidadBase/save' />" method='POST'>
	  </c:when>			  
	  <c:when test="${param.type eq  'edit'}">
	    <form name='entidadBaseForm' id='entidadBaseForm' action="<c:url value='/entidadBase/update/${object.id}' />" method='POST'>
	  </c:when>
	  <c:otherwise>
	    <form></form>
	  </c:otherwise>
	</c:choose>

	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          <div class="tiny-text">
          
         	<c:choose>
			  <c:when test="${param.type eq  'add'}">
			    <p>Por favor introduzca todos los campos para dar de alta una nueva Entidad Base. </p>
			  </c:when>			  
			  <c:when test="${param.type eq  'edit'}">
			    <p>Por favor modifique los campos que desee en esta Entidad Base. </p>
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
              <h4>Entidad Base</h4>
            </div>
          </div>            
          <fieldset>
            <div class="form-group">
              <label class="control-label">Texto *</label>
              <input type="text" class="form-control" required="true" id="texto" name="texto" value="${object.texto}"
              	data-parsley-trigger="change"
			data-parsley-minlength="2" 
			data-parsley-maxlength="100"
			data-parsley-required="true"				
              >
              <span class="hint d-none"></span>
            </div>              
            <div class="form-group">
              <label for="exampleFormControlTextarea1">Texto Largo</label>
              <textarea class="form-control"  id="textoLargo" name="textoLargo" rows="3" 
              	data-parsley-trigger="change"
			data-parsley-minlength="0" 
			data-parsley-maxlength="4000"
			data-parsley-required="false"				
              >${object.textoLargo}</textarea>
            </div>
            <div class="form-row">
              <div class="form-group col-md-6">
                <div class="col-sm-12 col">
                  <label class="control-label">Campo fecha</label>                    
                  <input type="text" class="calendar" id="fecha" name="fecha" placeholder="Campo fecha" value="${object.fecha}"
                   	data-parsley-validate-date="DD/MM/YYYY"
                  	data-parsley-trigger="change"
					data-parsley-required="false"
					data-parsley-errors-container="#date-errors"
					/>						
                </div>
                <div id="date-errors"></div>	                  
              </div>
            </div>
            
            <div class="form-row">
              <div class="form-group col-md-6">
                <label class="control-label">Campo numérico entero</label>
                <input type="text" class="form-control" id="numeroEntero" name="numeroEntero" value="${object.numeroEntero}"
                	data-parsley-trigger="change"
                	data-parsley-required="false"
                	data-parsley-type="integer"
                >
              </div>
              <div class="form-group col-md-6">
                <label class="control-label">Campo numérico con decimales</label>
                <input type="text" class="form-control" id="numeroDecimal" name="numeroDecimal" step=".01" value="${object.numeroDecimal}"
                	data-parsley-trigger="change"
			data-parsley-required="false"
			data-parsley-type="number"	>
                
              </div>
            </div>
          </fieldset>
       		<div class="row buttons">
            <div class="col-md-4">                
              <a class="btn btn-secondary btn-block" href="<c:url value="/entidadBase"/>" role="button" id="botonVolver">Volver</a>
            </div>
            <div class="col-md-4 offset-4">                
              <button type="button" class="btn btn-success btn-block" onClick="validateForm()" id="botonGuardar">Guardar</button>
            </div>              
         	</div>
         </form>

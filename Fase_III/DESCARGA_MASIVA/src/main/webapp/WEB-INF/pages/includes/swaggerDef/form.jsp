<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 



	<c:choose>
	  <c:when test="${param.type eq  'add'}">
	    <form name='swaggerDefForm' id='swaggerDefForm' action="<c:url value='/swaggerDef/save' />" method='POST'>
	  </c:when>			  
	  <c:when test="${param.type eq  'edit'}">
	    <form name='swaggerDefForm' id='swaggerDefForm' action="<c:url value='/swaggerDef/update/${object.code}' />" method='POST'>
	  </c:when>
	  <c:otherwise>
	    <form></form>
	  </c:otherwise>
	</c:choose>

	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          <div class="tiny-text">
          
         	<c:choose>
			  <c:when test="${param.type eq  'add'}">
			    <p>Por favor introduzca todos los campos para dar de alta una nueva Definición de Swagger. </p>
			  </c:when>			  
			  <c:when test="${param.type eq  'edit'}">
			    <p>Por favor modifique los campos que desee en esta Definción de Swagger. </p>
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
              <h4>Definición de Swagger</h4>
            </div>
          </div>            
          <fieldset>
            <div class="form-group">
              <label class="control-label">Código *</label>
              <input type="text" class="form-control" required="true" id="code" name="code" value="${object.code}"
              	data-parsley-trigger="change"
			data-parsley-minlength="2" 
			data-parsley-maxlength="100"
			data-parsley-required="true"				
              >
              <span class="hint d-none"></span>
            </div>   
            <div class="form-group">
              <label for="exampleFormControlTextarea1">Descripción</label>
              <textarea class="form-control"  id="description" name="description"
              	data-parsley-trigger="change"
			data-parsley-minlength="0" 
			data-parsley-maxlength="200"
			data-parsley-required="true"				
              >${object.description}</textarea>
            </div>           
            <div class="form-group">
              <label for="exampleFormControlTextarea1">Definición</label>
              <textarea class="form-control"  id="text" name="text" rows="3" 
              	data-parsley-trigger="change"
			data-parsley-minlength="0" 
			data-parsley-maxlength="4000"
			data-parsley-required="true"				
              >${object.text}</textarea>
            </div>
       
          </fieldset>
       		<div class="row buttons">
            <div class="col-md-4">                
              <a class="btn btn-secondary btn-block" href="<c:url value="/swaggerDef"/>" role="button" id="botonVolver">Volver</a>
            </div>
            <div class="col-md-4 offset-4">                
              <button type="button" class="btn btn-success btn-block" onClick="validateForm()" name="botonGuardar" id="botonGuardar">Guardar</button>
            </div>              
         	</div>
         </form>

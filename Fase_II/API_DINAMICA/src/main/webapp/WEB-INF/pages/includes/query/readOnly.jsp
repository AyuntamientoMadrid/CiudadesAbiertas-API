<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>

<div class="texto">
	<div class="container">
		<div class="row">
             <h4>Consulta SQL</h4>            
        </div>
		<div class="row">
			<div class="col-12">	
				<dl>			
					<dt>Código</dt>
					<dd>${object.code}</dd>
					<dt>SQL</dt>					
				</dl>
			</div>		
			<div class="col-12">              
              <textarea class="form-control"  id="texto" name="texto" disabled="disabled">${object.texto}</textarea>
         </div> 	
		</div>
		<div class="row">
			<div class="col-6">
				<dl>			
					<dt>Conexión de Base de Datos</dt>
					<dd>${databaseSelected}</dd>
				</dl>
			</div>
			<div class="col-6">
				<dl>
					<dt>Descripción</dt>
					<dd>${object.summary}</dd>
					<dt>Etiquetas</dt>
					<dd>${object.tags}</dd>
					<dt>Definición</dt>
					<dd>${object.definition}</dd>
				</dl>
			</div>
		</div>		
		
	  <c:if test="${not empty paramList}">
	  
	  	<div class="row">
             <h4>Parámetros de la consulta</h4>            
        </div>
        
        <c:forEach var="entidad" items="${paramList}">   
        
	        <fieldset>
	        	<div class="row">        	
					<div class="col-6">	
						<dl>			
							<dt>Nombre</dt>
							<dd>${entidad.name}</dd>
							<dt>Tipo</dt>
							<dd>${entidad.type}</dd>
						</dl>
					</div>			
					<div class="col-6">	
						<dl>			
							<dt>Descripción</dt>
							<dd>${entidad.description}</dd>
							<dt>Ejemplo</dt>
							<dd>${entidad.example}</dd>
						</dl>
					</div>
				</div>
			</fieldset>
		
		</c:forEach>	
		
	  </c:if>		  
	  
	</div>
</div>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page
	import="org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants"%>
<%@ page import="org.ciudadesAbiertas.madrid.utils.constants.Constants"%>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>

<%@include file="../modal_include.jsp"%>

<script>
var textoModal = "Separador del path es <b>\\</b> Ejemplo: D:\\temp\\dir\\fichero. <br>"+
				 "Se permite incluir las anotaciones del tipo <b>FECHA</b> y <b>HORA</b> para concatenar al nombre del fichero. <br>" +
				 "Ejemplo: <b>D:\\temp\\dir\\fichero_FECHA_HORA</b> ";
</script>

<c:choose>
	<c:when test="${param.type eq  'add'}">
		<form name='queryForm' id='queryForm'
			action="<c:url value='/query/save' />" method='POST'>
	</c:when>
	<c:when test="${param.type eq  'edit'}">
		<form name='queryForm' id='queryForm'
			action="<c:url value='/query/update/${object.code}' />" method='POST'>
	</c:when>
	<c:otherwise>
		<form></form>
	</c:otherwise>
</c:choose>

<input type="hidden" name="${_csrf.parameterName}"
	value="${_csrf.token}" />
<input type="hidden" name="originalCode" value="${object.code}" />
<div class="tiny-text">

	<c:choose>
		<c:when test="${param.type eq  'add'}">
			<p>Por favor introduzca todos los campos para dar de alta una
				nueva Consulta.</p>
		</c:when>
		<c:when test="${param.type eq  'edit'}">
			<p>Por favor modifique los campos que desee en esta Consulta.</p>
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
			<label class="control-label" for="code">Código *</label> <input
				type="text" class="form-control" required="true" id="code"
				name="code" value="${object.code}" data-parsley-trigger="change"
				data-parsley-minlength="2" data-parsley-maxlength="20"
				data-parsley-required="true"> <span class="hint d-none"></span>
		</div>
		<div class="form-group col-md-6">
			<div class="col-sm-12 col">
				<label class="control-label">Conexión de Base de Datos</label> <select
					class="form-control simple" name="database" id="database"
					required="true">
					<c:forEach items="${databases}" var="bd">
						<c:choose>
							<c:when test="${bd == databaseSelected}">
								<option value="<c:out value="${bd}" />" selected><c:out
										value="${bd}" /></option>
							</c:when>
							<c:otherwise>
								<option value="<c:out value="${bd}" />"><c:out
										value="${bd}" /></option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label for="texto">Consulta SQL *</label>
		<textarea class="form-control" id="texto" name="texto" rows="3"
			data-parsley-trigger="change" data-parsley-minlength="0"
			data-parsley-maxlength="4000" data-parsley-required="true">${object.texto}</textarea>
	</div>

	<div class="form-group">
		<label class="control-label" for="summary">Descripción</label> <input
			type="text" class="form-control" required="true" id="summary"
			name="summary" value="${object.summary}"
			data-parsley-trigger="change" data-parsley-minlength="2"
			data-parsley-maxlength="100" data-parsley-required="true">
		<span class="hint d-none"></span>
	</div>


</fieldset>
<div class="form-group title">
	<div class="col-xs-12">
		<h4>Configuración</h4>
	</div>
</div>
<fieldset>
	<div class="form-row">
		<div class="form-group col-md-11">
			<div class="col-sm-11 col">
				<label class="control-label">Ruta y nombre de los ficheros sin extensión (Usar <b>\</b> como caracter separador)</label>
				<input type="text" class="form-control" required="false" id="path"
					name="path" value="${configuration.path}"
					data-parsley-trigger="change" 
					data-parsley-minlength="5"
					data-parsley-maxlength="200" 
					data-parsley-required="true"
					pattern="^(?:[\w]\:|[\\]?)(\\[a-zA-Z_\-\s0-9]+)+$">
			</div>
		</div>
		<div class="form-group col-md-1">
			<div class="col-sm-1 col">
				<label class="control-label">Ayuda</label>
				<div class="icon icon-informacion" align="center" onclick="activarModal(textoModal);"></div>
			</div>
		</div>
	</div>
	<div class="form-row">
		<div class="form-group col-md-6">
			<label class="control-label" for="code">Modo</label> <select
				class="form-control simple" name="mode" id="mode" required="true"
				value="${configuration.mode}">
				<c:choose>
					<c:when test="${configuration.mode == 'manual'}">
						<option value="<c:out value="manual" />" selected>Manual</option>
						<option value="<c:out value="auto" />">Automático</option>
					</c:when>
					<c:otherwise>
						<option value="<c:out value="manual" />">Manual</option>
						<option value="<c:out value="auto" />" selected>Automático</option>
					</c:otherwise>
				</c:choose>




			</select>
		</div>
		<!-- 
		<div class="form-group col-md-6">
			<div class="col-sm-12 col">
				<label class="control-label">Expresión Cron</label> <input
					type="text" class="form-control" required="false" id="cron"
					name="cron" placeholder="* * * * * *" value="${configuration.cron}"
					data-parsley-trigger="change" data-parsley-minlength="2"
					data-parsley-maxlength="100" data-parsley-required="true">
			</div>
		</div>
		 -->
	</div>
	<div>
	<label class="control-label">Ejecución</label>
	<fieldset>
	<div class="form-row">
		<div class="form-group col-md-4">
			<label class="control-label" for="minute">Minuto</label> 
			<select class="form-control multiple" name="minute" id="minute" required="true" multiple="multiple">				
				<c:choose>
			        <c:when test="${fn:contains(minutesSel, '*')}">			            
			            <option value="*" selected="selected">Cada minuto</option>	
			        </c:when>			
			        <c:otherwise>
			       		<option value="*">Cada minuto</option>			           
			        </c:otherwise>
			    </c:choose>
				<c:forEach var="item" items="${minutes}">
				<c:choose>
			        <c:when test="${fn:contains(minutesSel, item)}">			            
			             <option value="${item}" selected="selected">${item}</option>	
			        </c:when>			
			        <c:otherwise>
			       		 <option value="${item}">${item}</option>			           
			        </c:otherwise>
			    </c:choose>
				</c:forEach>							
			</select>
		</div>
		<div class="form-group col-md-4">
			<label class="control-label" for="hour">Hora</label> 
			<select class="form-control multiple" name="hour" id="hour" required="true" multiple="multiple">
				<c:choose>
			        <c:when test="${fn:contains(hoursSel, '*')}">			            
			            <option value="*" selected="selected">Cada hora</option>	
			        </c:when>			
			        <c:otherwise>
			       		<option value="*">Cada hora</option>			           
			        </c:otherwise>
			    </c:choose>
				<c:forEach var="item" items="${hours}">
				<c:choose>
			        <c:when test="${fn:contains(hoursSel, item)}">			            
			             <option value="${item}" selected="selected">${item}</option>	
			        </c:when>			
			        <c:otherwise>
			       		 <option value="${item}">${item}</option>			           
			        </c:otherwise>
			    </c:choose>
				</c:forEach>
									
			</select>
		</div>
		<div class="form-group col-md-4">
			<label class="control-label" for="dayM">Día del mes</label> 
			<select class="form-control multiple" name="dayM" id="dayM" required="true" multiple="multiple">
				<c:choose>
			        <c:when test="${fn:contains(daysMSel, '*')}">			            
			            <option value="*" selected="selected">Cada día</option>	
			        </c:when>			
			        <c:otherwise>
			       		<option value="*">Cada día</option>			           
			        </c:otherwise>
			    </c:choose>
				<c:forEach var="item" items="${daysM}">
				<c:choose>
			        <c:when test="${fn:contains(daysMSel, item)}">			            
			             <option value="${item}" selected="selected">${item}</option>	
			        </c:when>			
			        <c:otherwise>
			       		 <option value="${item}">${item}</option>			           
			        </c:otherwise>
			    </c:choose>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="form-row">
		<div class="form-group col-md-4 offset-md-2">
			<label class="control-label" for="dayW">Día de la semana</label> 
			<select class="form-control multiple" name="dayW" id="dayW" required="true" multiple="multiple">
				<c:choose>
			        <c:when test="${fn:contains(daysWSel, '*')}">			            
			            <option value="*" selected="selected">Cada día</option>	
			        </c:when>			
			        <c:otherwise>
			       		<option value="*">Cada día</option>			           
			        </c:otherwise>
			    </c:choose>
				<c:forEach var="item" items="${valuesDayW}">
				<c:choose>
			        <c:when test="${fn:contains(daysWSel, item)}">			            
			             <option value="${item}" selected="selected">${textDayW.get(item-1)}</option>	
			        </c:when>			
			        <c:otherwise>
			       		 <option value="${item}">${textDayW.get(item-1)}</option>			           
			        </c:otherwise>
			    </c:choose>
				</c:forEach>
				
			</select>
		</div>
		
		<div class="form-group col-md-4">
			<label class="control-label" for="month">Mes</label> 
			<select class="form-control multiple" name="month" id="month" required="true" multiple="multiple">
			    <c:choose>
			        <c:when test="${fn:contains(monthSel, '*')}">			            
			            <option value="*" selected="selected">Cada mes</option>	
			        </c:when>			
			        <c:otherwise>
			       		<option value="*">Cada mes</option>			           
			        </c:otherwise>
			    </c:choose>	
			    <c:forEach var="item" items="${valuesMonth}">
				<c:choose>
			        <c:when test="${fn:contains(monthSel, item)}">			            
			             <option value="${item}" selected="selected">${textMonth.get(item-1)}</option>	
			        </c:when>			
			        <c:otherwise>
			       		 <option value="${item}">${textMonth.get(item-1)}</option>			           
			        </c:otherwise>
			    </c:choose>
				</c:forEach>
				
			</select>
		</div>
	</div>
	</fieldset>
	</div>
	<div class="form-group">
		<label for="zip">Generar en formato ZIP</label>
		<c:choose>
			<c:when test="${configuration.zip == true}">
				<input type="checkbox" id="zip" value="${configuration.zip}"
					name="zip" checked="checked" />
			</c:when>
			<c:otherwise>
				<input type="checkbox" id="zip" value="${configuration.zip}"
					name="zip" />
			</c:otherwise>
		</c:choose>
	</div>

	<div class="form-group">
		<label for="items">Partir la información por número de
			registros</label> 
		<input  id="items" name="items"
			value="${configuration.items}"
			data-parsley-type="digits" 
			pattern="^[1-9][0-9]*$"
			data-parsley-trigger="change"			
			data-parsley-required="false" />
	</div>

	<div class="form-group">
		<label for="overwrite">Sobreescribir fichero anterior</label>
		<c:choose>
			<c:when test="${configuration.overwrite == true}">
				<input type="checkbox" id="overwrite"
					value="${configuration.overwrite}" name="overwrite"
					checked="checked" />
			</c:when>
			<c:otherwise>
				<input type="checkbox" id="overwrite"
					value="${configuration.overwrite}" name="overwrite" />
			</c:otherwise>
		</c:choose>
	</div>

</fieldset>

<div class="row buttons">
	<div class="col-md-4">
		<a class="btn btn-secondary btn-block" href="<c:url value="/query"/>"
			role="button" id="botonVolver">Volver</a>
	</div>
	<div class="col-md-4 offset-4">
		<button type="button" class="btn btn-success btn-block"
			onClick="validateForm()" id="botonGuardar">Guardar</button>
	</div>
</div>
</form>


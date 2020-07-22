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
				</dl>
			</div>
		</div>		
		
	  	<div class="form-group title">
            <div class="col-xs-12">
              <h4>Configuración</h4>
            </div>
          </div>            
          <fieldset>
           <div class="row">              	
	        	<div class="col-md-12">
                	<dl>
                  		<dt>Ruta donde generar los ficheros</dt>  
                  		<dd>${configuration.path}</dd>  
                	</dl>                           
              	</div>
	        </div>
          	<div class="row">
              	<div class="col-md-6">
              		<dl>
                  		<dt>Modo</dt>  
                  		<dd>${configuration.mode}</dd>  
                	</dl> 
	        	</div>
	        	<!--
	        	<div class="col-md-6">
	        		<dl>
                  		<dt>Expresión Cron</dt>  
                  		<dd>${configuration.cron}</dd>  
                	</dl>                          
              	</div>
              	-->
	        </div>
	        
	        
	        <div>
				<label class="control-label">Ejecución</label>
				<fieldset>
				<div class="form-row">
					<div class="form-group col-md-4">
						<label class="control-label" for="minute">Minuto</label> 
						<select class="form-control multiple" name="minute" id="minute" required="true" multiple="multiple" disabled="disabled">				
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
						<select class="form-control multiple" name="hour" id="hour" required="true" multiple="multiple" disabled="disabled">
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
						<select class="form-control multiple" name="dayM" id="dayM" required="true" multiple="multiple" disabled="disabled">
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
						<select class="form-control multiple" name="dayW" id="dayW" required="true" multiple="multiple" disabled="disabled">
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
						<select class="form-control multiple" name="month" id="month" required="true" multiple="multiple" disabled="disabled">
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
	        
	        
            <div class="row">
            	<div class="col-md-12">
            		<dl>
                  		<dt>Generar en formato ZIP</dt>
                  		<c:choose>
                  		<c:when test="${configuration.zip == true}">
				  			<dd><input type="checkbox" id="zip" value="${configuration.zip}" name="zip" checked="checked" disabled="disabled"/></dd>
				 		 </c:when>			  
				  		<c:otherwise>
				   			 <dd><input type="checkbox" id="zip" value="${configuration.zip}" name="zip" disabled="disabled"/></dd>
				  		</c:otherwise>
				  		</c:choose>
                	</dl>
                </div>
            </div>
            
            <div class="row">
            	<div class="col-md-12">
            		<dl>
                  		<dt>Partir la información por numero de registros</dt>
                  		<dd>${configuration.items}</dd>  
     				</dl>
     			</div>
            </div>
            
            <div class="row">
            	<div class="col-md-12">
            		<dl>
                  		<dt>Sobreescribir fichero anterior</dt>
                  		<c:choose>
                  		<c:when test="${configuration.overwrite == true}">
				  			<dd><input type="checkbox" id="overwrite" value="${configuration.overwrite}" name="overwrite" checked="checked" disabled="disabled"/></dd>
				 		 </c:when>			  
				  		<c:otherwise>
				   			 <dd><input type="checkbox" id="overwrite" value="${configuration.overwrite}" name="overwrite" disabled="disabled"/></dd>
				  		</c:otherwise>
				  		</c:choose>
                	</dl>
                </div>
            </div>
                       
	       
          </fieldset>   	  
	  
	</div>
</div>


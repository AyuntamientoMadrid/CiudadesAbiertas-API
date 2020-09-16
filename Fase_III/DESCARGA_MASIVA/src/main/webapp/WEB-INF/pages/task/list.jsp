<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page
	import="org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants"%>
<%@ page import="org.ciudadesAbiertas.madrid.model.dynamic.TaskD"%>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>

<c:set var="title"
	value="<%=LiteralConstants.TITLE_HEAD_TASKS%>" scope="session" />
<!DOCTYPE html>
<html lang="es">
<%@include file="../includes/head.jsp"%>


<body>
	<%@include file="../includes/header.jsp"%>

	<%@include file="../includes/menuLateral.jsp"%>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<div class="user_name">
		<div class="container">
			<div class="row">
				<p>
					<i class="icon-id-card-3"></i> ${userName}
				</p>
			</div>
		</div>
	</div>

	<div class="title mt-0">
		<div class="container">
			<div class="col-md-12">
				<p id="parrafoTitulo">Listado Tareas</p>
			</div>
		</div>
	</div>

	<div class="form_steps">
		<ul>
			<li class="done" id="1"><a href="<c:url value="/home"/>">1.
					<span>Inicio</span>
			</a></li>
			<li class="done" id="2">2. <span>Tareas</span></li>
			<li class="selected" id="3">3. <span>Listado</span></li>
		</ul>
	</div>

	<div class="share_menu">
		<div class="brand">
			<div class="row">
				<div class="col-3">
					<a href="<c:url value="/home"/>"><i class="ico icon-arrow-left"></i>
						Volver</a>
				</div>
				<div class="col-9 options">
					<ul>
						
						<li class="font_size"><a title="Tamaño de letra" href="#"
							class="enlace"><i class="ico icon-font-size"></i><span
								class="text">Tamaño de letra</span><i
								class="ico icon-arrow-down"></i></a>
							<div class="font_size_options">
								<ul>
									<li id="font-up"><a href="#">+ Aumentar</a></li>
									<li id="font-down"><a href="#">- Reducir</a></li>
								</ul>
							</div></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="scroll_button_top">
		<a href="#bottom"> <img
			src="<c:url value="/resources/ayre-assets/images/scroll_button_bottom.png"/>"
			alt="scroll down" />
		</a>
	</div>




	<div class="row bloque1">
		<div class="col-xs-12 col-md-12 col-lg-10 col-xl-10">
		
		<div class="advice">
			<c:choose>
				<c:when test="${empty errorObject}">
					<div class="container error d-none" id="expInfo">
				</c:when>
				<c:otherwise>
					<div class="container error" id="expInfo">
				</c:otherwise>
			</c:choose>
			<h4 id="errorObjectAdviceTitle">${errorObject}</h4>
			<p>${errorObject}</p>
		</div>
	</div>

	<div class="form">
          
          <form id="busquedaForm" action="<c:url value='${searchPage}' />" method="POST">   
          	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          	<input type="hidden" name="pageSize" id="pageSize">
          	<input type="hidden" name="page" id="page">
          
          	       
            <div class="form-group title">
              <div class="col-xs-12">
                <h4>Búsqueda</h4>
              </div>
            </div>
            
            <fieldset>
            <div class="form-row">
                <div class="form-group col-md-4">
                  <label class="control-label">Consulta</label>
                  <div class="custom-select"><select class="form-control simple" name="query" id="query">
                    <option value="-1">Todas</option>	
                    	<c:forEach var="entidad" items="${queryList}" varStatus="loop">
						<c:choose>
					        <c:when test="${querySearch eq entidad}">			            
					           <option value="${entidad}" selected="selected">${entidad}</option>
					        </c:when>				        	
					        <c:otherwise>
					       		<option value="${entidad}">${entidad}</option>	           
					        </c:otherwise>
			        	</c:choose>  
				    </c:forEach>
                  </select><span class="custom-select__arrow" aria-hidden="true"><span class="icon-arrow-full-down"></span></span></div>
                </div>
                <div class="form-group col-md-4">
                  <label class="control-label">Estado</label> 
                  <div class="custom-select"><select class="form-control simple" name="status" id="status">
                    <option value="-1">Todos</option>
                    <c:forEach var="entidad" items="${statusList}" varStatus="loop">			        	
						<c:choose>
					        <c:when test="${statusSearch eq entidad}">			            
					           <option value="${entidad}" selected="selected">${entidad}</option>
					        </c:when>				        	
					        <c:otherwise>
					       		<option value="${entidad}">${entidad}</option>	           
					        </c:otherwise>
			        	</c:choose>  
				    </c:forEach>
                  </select><span class="custom-select__arrow" aria-hidden="true"><span class="icon-arrow-full-down"></span></span></div>
                </div>
              
               <div class="form-group col-md-4">
                  <label class="control-label">Ejecución</label>
                  <div class="custom-select"><select class="form-control simple" name="mode" id="mode">
                    <option value="-1">Todas</option>
                    <c:forEach var="entidad" items="${modeList}" varStatus="loop">			        	
						<c:choose>
					        <c:when test="${modeSearch eq entidad}">			            
					           <option value="${entidad}" selected="selected">${entidad}</option>
					        </c:when>				        	
					        <c:otherwise>
					       		<option value="${entidad}">${entidad}</option>	           
					        </c:otherwise>
			        	</c:choose>  
				    </c:forEach>
                  </select><span class="custom-select__arrow" aria-hidden="true"><span class="icon-arrow-full-down"></span></span></div>
                </div>
              
              </div>    
              
              <div class="form-row">
              	<div class="form-group col-md-3">
                    <label class="control-label">Fecha desde</label>
                    <input type="text" id="start" class="calendar" name="start" placeholder="Fecha desde" value="${startDateSearch}"
                    pattern="^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[13-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$"
                    data-parsley-trigger="change" 
                    data-parsley-minlength="10"
					data-parsley-maxlength="10" 
					data-parsley-required="false" 	
					data-parsley-errors-container="#date-errors-1"				
					/>
					
              	</div>    
              	<div class="form-group col-md-3">
                    <label class="control-label">Hora desde</label>
                    <input class="form-control simple" type="text" id="timeStart" name="timeStart" placeholder="Hora desde" value="${startTimeSearch}" 
                    pattern="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$"
                    data-parsley-trigger="change" 
                    data-parsley-minlength="5"
					data-parsley-maxlength="5" 
					data-parsley-required="false"
					data-parsley-errors-container="#date-errors-2"	 					
					/>
					
              	</div> 
              	          
             	  <div class="form-group col-md-3">
                    <label class="control-label">Fecha hasta</label>
                      <input type="text" id="finish" class="calendar" name="finish" placeholder="Fecha hasta"
                      pattern="^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[13-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$"
                      data-parsley-trigger="change" 
                    data-parsley-minlength="10"
					data-parsley-maxlength="10" 
					data-parsley-required="false" 	
					data-parsley-errors-container="#date-errors-3"					
					/>
            	  </div>
            	  
            	  <div class="form-group col-md-3">
                    <label class="control-label">Hora hasta</label>
                    <input class="form-control simple" type="text" id="timeFinish" name="timeFinish" placeholder="Hora desde" value="${finishTimeSearch}"
                    pattern="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$"
                    data-parsley-trigger="change" 
                    data-parsley-minlength="5"
					data-parsley-maxlength="5" 
					data-parsley-required="false" 	
					data-parsley-errors-container="#date-errors-4"	                    
                    />                 
              	</div> 
               </div>
              	<div class="form-row">
	              	<div class="form-group col-md-3">
	              		<div id="date-errors-1"></div>
	              	</div>
	              	<div class="form-group col-md-3">
	              		<div id="date-errors-2"></div>
	              	</div>
	              	<div class="form-group col-md-3">
	              		<div id="date-errors-3"></div>
	              	</div>
	              	<div class="form-group col-md-3">
	              		<div id="date-errors-4"></div>
	              	</div>
              	</div>
           
                       
              
            </fieldset>  
            
            <div class="row buttons">
	            <div class="col-md-4">                
	              <button type="button" class="btn btn-secondary btn-block" onclick="clearForm()" role="button" id="botonLimpiar">Limpiar</button>
	            </div>
	            <div class="col-md-4 offset-4">                
	              <button type="button" class="btn btn-success btn-block" onclick="validateForm()" id="botonBuscar">Buscar</button>
	            </div>              
			</div>
            
            
                      
                     
           
          </form>
        </div>

	<c:choose>
		<c:when test="${empty list}">
			<div class="advice">
				<div class="container info" id="expInfo">
					<h4 id="updatedAdviceTitle"><%=LiteralConstants.SIN_DATOS%></h4>
					<p><%=LiteralConstants.SIN_DATOS_DESC%></p>
				</div>
			</div>
		</c:when>
		<c:otherwise>

			<div class="table-pagination" id="tablePaginacionSup">
				<%
				  request.setCharacterEncoding("utf-8");
				%>
				<jsp:include page="../includes/paginacionPost.jsp">
					<jsp:param name="texto" value="Consultas SQL" />
					<jsp:param name="formulario" value="busquedaForm" />
				</jsp:include>
			</div>

			
			
			<div class="tables no_horizontal_margin_xs">
			    
			    <table class="table-striped">
			        <thead id="thead">
			            <tr>
			                <th data-sortable="true">
			                	<c:choose>
							        <c:when test="${sortField eq 'query' && sortOrder eq 'desc'}">			            
							           <a id="thead1" href="${orderPage}+query">Consulta <i class="icon-arrow-full-down"></i></a>
							        </c:when>
							        <c:when test="${sortField eq 'query' && sortOrder eq 'asc'}">			            
							           <a id="thead1" href="${orderPage}-query">Consulta <i class="icon-arrow-full-up"></i></a>
							        </c:when>			
							        <c:otherwise>
							       		<a id="thead1" href="${orderPage}+query">Consulta</a>		           
							        </c:otherwise>
						        </c:choose>			                	
			                </th>
			                <th data-sortable="true">
			                	<c:choose>
							        <c:when test="${sortField eq 'status' && sortOrder eq 'desc'}">			            
							           <a href="${orderPage}+status">Estado <i class="icon-arrow-full-down"></i></a>
							        </c:when>
							        <c:when test="${sortField eq 'status' && sortOrder eq 'asc'}">			            
							           <a href="${orderPage}-status">Estado <i class="icon-arrow-full-up"></i></a>
							        </c:when>			
							        <c:otherwise>
							       		<a href="${orderPage}+status">Estado</a>		           
							        </c:otherwise>
						        </c:choose>	
			                </th>
			                <th data-sortable="true">
			                	<c:choose>
							        <c:when test="${sortField eq 'start' && sortOrder eq 'desc'}">			            
							           <a href="${orderPage}+start">Fecha Inicio <i class="icon-arrow-full-down"></i></a>
							        </c:when>
							        <c:when test="${sortField eq 'start' && sortOrder eq 'asc'}">			            
							           <a href="${orderPage}-start">Fecha Inicio <i class="icon-arrow-full-up"></i></a>
							        </c:when>			
							        <c:otherwise>
							       	   <a href="${orderPage}+start">Fecha Inicio</a>		           
							        </c:otherwise>
						        </c:choose>
			                </th>
			                <th data-sortable="true">
			                <c:choose>
							        <c:when test="${sortField eq 'finish' && sortOrder eq 'desc'}">			            
							           <a href="${orderPage}+finish">Fecha fin <i class="icon-arrow-full-down"></i></a>
							        </c:when>
							        <c:when test="${sortField eq 'finish' && sortOrder eq 'asc'}">			            
							           <a href="${orderPage}-finish">Fecha Fin <i class="icon-arrow-full-up"></i></a>
							        </c:when>			
							        <c:otherwise>
							       	   <a href="${orderPage}+finish">Fecha Fin</a>		           
							        </c:otherwise>
						        </c:choose>
			                </th>
			                <th data-sortable="true">
			                 <c:choose>
			                	 <c:when test="${sortField eq 'mode' && sortOrder eq 'desc'}">			            
							           <a href="${orderPage}+mode">Ejecución <i class="icon-arrow-full-down"></i></a>
							        </c:when>
							        <c:when test="${sortField eq 'mode' && sortOrder eq 'asc'}">			            
							           <a href="${orderPage}-mode">Ejecución <i class="icon-arrow-full-up"></i></a>
							        </c:when>			
							        <c:otherwise>
							       	   <a href="${orderPage}+mode">Ejecución</a>		           
							        </c:otherwise>
							  </c:choose>
			                </th>			                
			            </tr>
			        </thead>
			        <tbody>			        
			        	<c:forEach var="entidad" items="${list}" varStatus="loop">			        	
				        	<tr>
				        	<td data-label="consulta">${entidad.taskD.query}</td>
				            <td data-label="estado">${entidad.taskD.status}</td>
				            <td data-label="fechaInicio"><fmt:formatDate type = "both" value = "${entidad.taskD.start}" /></td>
				            <td data-label="fechaFin"><fmt:formatDate type = "both" value = "${entidad.taskD.finish}" /></td>
				            <td data-label="modo">${entidad.taskD.mode}</td>
							</tr>					
						</c:forEach>
					</tbody>			        
			    </table>			    
			</div>

			
			
			
			
			<div class="table-pagination">
				<jsp:include page="../includes/paginacionPost.jsp">
					<jsp:param name="texto" value="Consultas SQL" />
					<jsp:param name="formulario" value="busquedaForm" />
				</jsp:include>

			</div>
		</c:otherwise>
	</c:choose>

	</div>
	</div>

	<div class="scroll_button_bottom">
		<a href="#top"> <img
			src="<c:url value="/resources/ayre-assets/images/scroll_button_top.png"/>"
			alt="scroll up" />
		</a>
	</div>



	<%@include file="../includes/footer.jsp"%>
	<%@include file="../includes/foot.jsp"%>


    <script id="taskScript" 
    	data-method="${method}"
    	data-context="${contextPath}"
    	data-running="<%=TaskD.RUNNING%>"
    	data-startDate="${startDateSearch}"
    	data-startTime="${startTimeSearch}"
    	data-finishDate="${finishDateSearch}"
    	data-finishTime="${finishTimeSearch}"
    	src="<c:url value="/resources/js/task/task.js"/>" ></script>

	<style>
	.custom-select__arrow {	
		margin-right: 20px;
	}	
	</style>
</body>
</html>
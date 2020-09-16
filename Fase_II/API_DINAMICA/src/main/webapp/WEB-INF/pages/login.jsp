<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 
<c:set var="title" value="<%=LiteralConstants.TITLE_HEAD_LOGIN%>" scope="session"/>
<!DOCTYPE html>
<html lang="es">
<%@include file="includes/public/headPublic.jsp"%>	
<body>	
	<div class="login">
    <div class="wrapper col-sm-10 col-md-6 col-lg-5">
        <div class="login_header">
            <img class="logo_ayre" src="<c:url value="/resources/ayre-assets/images/base_logo.png"/>" alt="">
            <img class="logo_madrid" src="<c:url value="/resources/ayre-assets/images/logo_madrid_login.png"/>" alt="">
        </div>
        
        <c:out value="${login}"/>
        <c:out value="${clave}"/>
        
        <form name='loginForm' id='loginForm' action="<c:url value='/login' />" method='POST' onsubmit="return validateForm()">
	        <div class="advice">
		        <c:choose>
				  <c:when test="${empty  error}">
				    <div class="container error d-none" id="autError">
				  </c:when>			  
				  <c:otherwise>
				    <div class="container error" id="autError">
				  </c:otherwise>
				</c:choose>		        
		            <h4>Error de autenticación</h4>
		            <p>Las credenciales utilizadas no son válidas.</p>
		        </div>
		    </div>
		    <div class="advice">
		    	<c:choose>
				  <c:when test="${empty expired}">
				    <div class="container info d-none" id="expInfo">
				  </c:when>			  
				  <c:otherwise>
				    <div class="container info" id="expInfo">
				  </c:otherwise>
				</c:choose>		        
		            <h4>Sessión expirada</h4>
		            <p>Debido a la inactividad del usuario, ha sido desconectado.</p>
		        </div>
		    </div>
        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="form-group">
                <label for="username">Usuario</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Usuario" required="required" maxlength="45" minlength="4" value="${login}">                
            </div>
            <div class="form-group">
                <label for="password">Contraseña</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Contraseña" required="required" maxlength="45" minlength="6" value="${clave}">
            </div>         
            <button type="submit" class="btn btn-success btn-block" id="buttonIn">Entrar</button>            
        </form>      

 
        
        <div class="login_footer">
		    <div class="row">
		        <div class="col-sm-12 col-md-6 col-lg-6">
		            <a class="cambiar_contraseña" href="#">Cambiar contraseña</a>
		        </div>
		        <div class="col-sm-12 col-md-6 col-lg-6 text-right">
		            <a class="olvidado_contraseña" href="#">Has olvidado contraseña</a>
		        </div>
		    </div>
		    <div class="row">
		        <div class="col-sm-12 col-md-12 col-lg-12">
		            <p>Incidencias: 33033 o 915133033 (fuera de la red municipal)</p>
		        </div>
		    </div>
		</div>
        

<%@include file="includes/public/footPublic.jsp"%>	
<script src="<c:url value="/resources/js/login.js"/>"></script>

	
</body>
</html>
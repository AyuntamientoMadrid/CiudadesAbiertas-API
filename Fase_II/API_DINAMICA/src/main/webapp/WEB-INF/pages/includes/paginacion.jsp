<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 


			
		    <div class="row">
		        <div class="col-xs-12 col-md-6 col-lg-6 col-xl-6">
		            <ul>
		                <li class="results_text"><span class="results_text"><c:out value="${param.texto}"/></span></li>
		                <li class="results"><span class="results">Página ${actual} de ${total} </span></li>
		            </ul>
		        </div>
		        <div class="col-xs-12 col-md-6 col-lg-6 col-xl-6">
		            <nav aria-label="Page navigation example">
		                <ul class="pagination justify-content-center justify-content-md-end">
		                	<c:if test="${not empty first}">
			                	<li class="page-item">
				                        <a class="page-link" href="<c:url value="${first}"/>" aria-label="First">
				                            <span aria-hidden="true"><i class="icon-arrow-left"></i><i class="icon-arrow-left"></i></span>
				                            <span class="sr-only">First</span>
				                        </a>
				                </li>
			                </c:if>		                	
		                	<c:if test="${not empty prev}">
			                    <li class="page-item">
			                        <a class="page-link" href="<c:url value="${prev}"/>" aria-label="Previous">
			                            <span aria-hidden="true"><i class="icon-arrow-left"></i></span>
			                            <span class="sr-only">Previous</span>
			                        </a>
			                    </li>
		                    </c:if>
		                    <li class="page-item active" ><a class="page-link" href="<c:url value="${self}"/>">${actual}</a></li>		                    
		                    <c:if test="${not empty next}">
			                    <li class="page-item">
			                        <a class="page-link" href="<c:url value="${next}"/>" aria-label="Next">
			                            <span aria-hidden="true"><i class="icon-arrow-right"></i></span>
			                            <span class="sr-only">Next</span>
			                        </a>
			                    </li>
		                    </c:if>
		                    <c:if test="${not empty last}">
		                    	<li class="page-item">
			                        <a class="page-link" href="<c:url value="${last}"/>" aria-label="Last">
			                            <span aria-hidden="true"><i class="icon-arrow-right"></i><i class="icon-arrow-right"></i></span>
			                            <span class="sr-only">Last</span>
			                        </a>
			                    </li>
			                </c:if>
		                </ul>
		            </nav>
		        </div>
		    </div>
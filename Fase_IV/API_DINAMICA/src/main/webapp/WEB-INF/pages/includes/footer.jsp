<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.StartVariables" %>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %>


<footer class="footer">
			
		<div class="links">
			<div class="footer_content_xl">
				<img src="<c:url value="/resources/ayre-assets/images/escudo_madrid.png"/>" alt="escudo del ayuntamiento de Madrid" />
				<ul>
				    <li class="footer_title"><%=LiteralConstants.TITLE_FOOTER_ORGANISMO%> <%=StartVariables.year%></li>
					<li class="footer_text" id="bottom">Copyright <%=StartVariables.year%>. <%=LiteralConstants.DERECHOS_FOOTER%></li>
					<li><a href="#" title="Aviso legal"><%=LiteralConstants.AVISO_LEGAL_FOOTER%></a></li>
				</ul>
			</div>
		</div>

</footer>
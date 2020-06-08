<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>


<div class="texto ">
	<div class="container">
		<div class="row">
			<div class="col-xs-12">
				<div class="tiny-text">
					<h4>${object.texto}</h4>
					<p>${object.textoLargo}</p>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-6">
		<dl>
			<dt>Id</dt>
			<dd>${object.id}</dd>
			<dt>Fecha</dt>
			<dd>
				<fmt:formatDate pattern="dd-MM-yyyy" value="${object.fecha}" />
			</dd>
		</dl>
	</div>
	<div class="col-6">
		<dl>
			<dt>Número Entero</dt>
			<dd>${object.numeroEntero}</dd>
			<dt>Número Decimal</dt>
			<dd>
				<fmt:formatNumber pattern="#,##0.00" value="${object.numeroDecimal}" />
			</dd>
		</dl>
	</div>
</div>
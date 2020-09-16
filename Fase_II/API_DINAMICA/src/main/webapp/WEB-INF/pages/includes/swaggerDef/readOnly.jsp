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
					<h4>${object.code}</h4>
					<p>${object.description}</p>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row">
		<div class="col-12">	
			<dl>			
				<dt>Definición</dt>								
			</dl>
		</div>
		<div class="col-12">              
              <textarea class="form-control"  id="description" name="description" disabled="disabled">${object.text}</textarea>
         </div>  				
	</div>
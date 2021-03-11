<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>

<div class="texto">
	<div class="container">
		<div class="row">
             <h4>Prefijo</h4>            
        </div>
		<div class="row">
			<div class="col-3">	
				<dl>			
					<dt>Código</dt>
					<dd>${object.code}</dd>
										
				</dl>
			</div>		
			<div class="col-9">              
              <dl>			
					<dt>URL</dt>
					<dd>${object.url}</dd>
									
				</dl>
         </div> 	
		</div>
		
		
	  
	  
	</div>
</div>


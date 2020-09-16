<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>

 		<!-- BOTON AUXILIAR PARA ACTIVAR LA MODAL NO VISIBLE -->
        <button id="invokeModalAviso" class="btn btn-primary btn-lg oculto" data-toggle="modal" data-target="#myModalAviso"></button >
            <!-- INCLUIMOS VENTANA MODAL PARA LOS TEXTOS DE LOS AVISOS -->
         <div class="modal fade in" id="myModalAviso" tabindex="-1" role="dialog" aria-labelledby="myModalAvisoLabel" aria-hidden="true" style="display: none;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalAvisoLabel">Aviso</h4>
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					</div>
					<div class="modal-body">
						<p id="modalTextAviso"></p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<!--  <button type="button" id="buttonConfirm" class="btn hide btn-primary" onclick="javascript:confirmOK();" >Aceptar</button>	-->						
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- FIN VENTANA MODAL PARA LOS TEXTOS DE LOS AVISOS -->


<script>

  var buttonConfirm = false;
  var formularioName;
  function activarModal(texto){
    
    	$("#modalTextAviso").html(texto);
    	$("#invokeModalAviso").click();
    	
   }
  
  function activarModalConfirm(texto,forname){ 
	formularioName=forname;
	$("#buttonConfirm").attr('class', 'btn btn-primary');  
  	$("#modalTextAviso").html(texto);
  	$("#invokeModalAviso").click();
  }
  
  function confirmOK(){
	  buttonConfirm=true;
	  sendForm(formularioName);
  }
    
</script>

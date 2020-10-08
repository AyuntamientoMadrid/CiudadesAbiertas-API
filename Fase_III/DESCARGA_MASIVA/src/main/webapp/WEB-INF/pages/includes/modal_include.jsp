<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>

<!-- BOTON AUXILIAR PARA ACTIVAR LA MODAL NO VISIBLE -->
<button id="invokeModalAviso" class="btn btn-primary btn-lg oculto" data-toggle="modal" data-target="#myModalAviso"></button >
<!-- INCLUIMOS VENTANA MODAL PARA LOS TEXTOS DE LOS AVISOS -->
<div class="modal fade in" id="myModalAviso" tabindex="-1" role="dialog" aria-labelledby="myModalAvisoLabel" aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="advice">
				<div class="container success" id="containerModalStyle">
					<h4 id="modalTitulo">Información</h4>
					<div class="form-row">
						<p id="modalTextAviso"></p>
					</div>
					<div class="form-row">
						<div class="col-md-12 text-right">
							<button id="modalCancelButton" type="button"
								class="btn btn-default btn-secondary" data-dismiss="modal">Cancelar</button>
							<button id="modalCloseButton" type="button"
								class="btn btn-default btn-secondary" data-dismiss="modal">Cerrar</button>
							<button id="modalAcceptButton" type="button"
								class="btn btn-default btn-success" data-dismiss="modal">Aceptar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
		
		
		
		
		
			


<script>
  
	var buttonConfirm = false;
	var formularioName;
	function activarModal(texto) {
		
		$("#containerModalStyle").removeClass("warning");
		$("#containerModalStyle").addClass("success");

		
		$("#modalTitulo").html("Información");
		
		$("#modalAcceptButton").hide();
		$("#modalCancelButton").hide();
		$("#modalCloseButton").show();

		$("#modalTextAviso").html(texto);
		$("#invokeModalAviso").click();

	}

	function activarConfirm(texto, functionC, functionD) {
		
		$("#containerModalStyle").removeClass("success");
		$("#containerModalStyle").addClass("warning");
		
		$("#modalTitulo").html("Aviso");
		
		$("#modalAcceptButton").show();
		$("#modalCancelButton").show();
		$("#modalCloseButton").hide();
		
		
		$("#modalAcceptButton").click(functionC);
		$("#modalCancelButton").click(functionD);
		
		
		$("#modalTextAviso").html(texto);
		$("#invokeModalAviso").click();
	}

	
</script>

<style>

.advice {
	margin: 0 0 0px;
}

.advice .container {
	padding: 20px 30px 10px 15px;
}

#modalTextAviso{
	margin-bottom: 10px;
}


</style>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import= "org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants" %> 
<%@ page session="true"%>
<%@ page isELIgnored="false" %> 
<c:set var="title" value="<%=LiteralConstants.TITLE_HEAD_HOME%>" scope="session"/>
<!DOCTYPE html>
<html lang="es">
<%@include file="includes/head.jsp"%>	
<body>
	<%@include file="includes/header.jsp"%>	
	
	<div class="boton_menu_share_menu">
		<a href="#" title="menu" class="icon-menu1"></a>
		<ul>
			<li class="list_title"><span>Título de menú</span> <a href="#" title="cerrar" class="back_button"><i class="icon-close "></i></a></li>
			<li><a href="#">Opcion 1</a></li>
			<li class="has_children">
				<a href="#">Opcion 2</a>
				<ul>
					<li class='back_button first'><a href='#'>Volver</a></li>
					<li><a title="Opcion 1" href="#">Opcion 1</a></li>
					<li><a title="Opcion 1" href="#">Opcion 2</a></li>
					<li><a title="Opcion 1" href="#">Opcion 3</a></li>
				</ul>
			</li>
			<li><a href="#">Opcion 3</a></li>
			<li><a href="#">Opcion 4</a></li>
			<li><a href="#">Opcion 5</a></li>
			<li><a href="#">Opcion 6</a></li>
			<li><a href="#">Opcion 7</a></li>
		</ul>
	</div>

	<div class="user_name">
		<div class="container">
			<div class="row">
				<p><i class="icon-id-card-3"></i> Guybrush Threepwood</p>
			</div>
		</div>
	</div>
	
   	<div class="title mt-0">
		<div class="container">
			<div class="col-md-12">
				<p>Título</p>
			</div>
		</div>
	</div>

	<div class="form_steps">
		<ul>
		  <li class="done" id="1"><a href="#">1. <span>Paso realizado con enlace</span></a></li>
		  <li class="done" id="2">2. <span>Paso realizado sin enlace</span></li>
		  <li class="selected" id="3">3. <span>Paso actual</span></li>
		  <li id="4">4. <span>Paso por realizar</span></li>
		</ul>
	</div>

	<div class="bg_greysoft">
		<div class="row bloque1">
			<div class="col-xs-12 col-md-12 col-lg-12 col-xl-12">
				<div class="form">
					<form id="defaultForm">
						<div class="tiny-text">
						  <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce vitae orci lacinia, elementum arcu vel, pellentesque nisl. Duis finibus est sit amet quam suscipit tristique. Suspendisse rhoncus id erat a sodales. Phasellus vel mi tellus. Nam ac lectus id est volutpat molestie id a lorem. Quisque arcu diam, maximus et vehicula sit amet, porta ut lacus. Vestibulum lobortis id enim et elementum. </p>
						</div>
						<div class="col-xs-12 text-right">
						  <p>Campos obligatorios *</p>
						</div>
						<div class="form-group title">
						  <div class="col-xs-12">
							<h4>Titulo del grupo</h4>
						  </div>
						</div>
						
						<fieldset>
						  <div class="form-group">
							<label class="control-label">Campo texto normal *</label>
							<input type="text" class="form-control errorField" name="username" required="true"/>
							<span class="hint">Texto cuando hay un <strong>'error'</strong>. </span>
						  </div>
						  <div class="form-row">
							<div class="form-group col-md-6">
							  <label class="control-label">Campo texto normal *</label>
							  <input type="text" class="form-control" name="username" required="true"/>
							</div>
							<div class="form-group col-md-6">
							  <label class="control-label">Campo texto deshabilitado</label>
							  <input type="text" class="form-control" name="username" disabled/>
							</div>
						  </div>
						  <div class="form-row">
							<div class="form-group col-md-6">
							  <label class="control-label">Campo texto error *</label>
							  <input type="text" class="form-control errorField" name="username" required="true" />
							  <span class="hint">Texto cuando hay un <strong>'error'</strong>. </span>
							</div>
							<div class="form-group col-md-6">
							  <label class="control-label">Campo texto ok *</label>
							  <input type="email" class="form-control okField" name="username" required="true" />
							</div>
						  </div>
						  <div class="form-group">
							<label for="exampleFormControlTextarea1">Campo texto (Bloque)</label>
							<textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
						  </div>
						  <div class="form-row">
							<div class="form-group col-md-6">
							  <label for="exampleFormControlTextarea1">Campo texto (Bloque)</label>
							  <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
							</div>
							<div class="form-group col-md-6">
							  <label for="exampleFormControlTextarea1">Campo texto (Bloque)</label>
							  <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
							</div>
						  </div>
						  
						  
						</fieldset>
						<div class="form-group title">
						  <div class="col-xs-12">
							<h4>Título del grupo</h4>
						  </div>
						</div>
						<fieldset>
						  <div class="form-row">
							<div class="form-group col-md-6">
							  <label class="control-label">Campo numérico entero</label>
							  <input type="number" class="form-control" name="username" />
							</div>
							<div class="form-group col-md-6">
							  <label class="control-label">Campo numérico con decimales</label>
							  <input type="number" class="form-control" name="decimals"/>
							</div>
						  </div>
						  <div class="form-row">
							<div class="form-group col-md-6">
							  <div class='col-sm-12 col'>
								<label class="control-label">Campo fecha *</label>
								<input type="text" id="date" class="from" name="date" placeholder="Campo fecha"/>
							  </div>
							</div>
						  </div>
						  <div class="form-group">
							<label class="switch">
							  <input type="checkbox">
							  <span class="slider round"></span>
							</label>
							<span class="texto_switch">Casilla de verificación</span>
						  </div>
						</fieldset>
						<div class="form-group title">
						  <div class="col-xs-12">
							<h4>Titulo del grupo</h4>
						  </div>
						</div>
						<fieldset>
						  <div class="form-group">
							<label class="control-label">Lista de valores (desplegable)</label>
							<select class="form-control simple" name="desplegable">
							  <option value="">Seleccione una opción</option>
							  <option value="op1">opción 1</option>
							  <option value="op2">opción 2</option>
							  <option value="op3">opción 3</option>
							</select>
						  </div>
						  <div class="form-row">
							<div class="form-group col-md-6">
							  <label class="control-label">Lista de valores (desplegable)</label>
							  <select class="form-control simple" name="desplegable">
								<option value="">Seleccione una opción</option>
								<option value="op1">opción 1</option>
								<option value="op2">opción 2</option>
								<option value="op3">opción 3</option>
							  </select>
							</div>
							<div class="form-group col-md-6">
							  <label class="control-label">Lista de valores (desplegable)</label>
							  <select class="form-control simple" name="desplegable">
								<option value="">Seleccione una opción</option>
								<option value="op1">opción 1</option>
								<option value="op2">opción 2</option>
								<option value="op3">opción 3</option>
							  </select>
							</div>
						  </div>
						  
						  <div class="form-row">
							<div class="form-group col-md-6">
							  <p>Lista de valores (Selecciè´¸n multiple)</p>
							  <div class="form-check">
								<input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
								<label class="form-check-label" for="defaultCheck1">
								  Azul
								</label>
							  </div>
							  <div class="form-check">
								<input class="form-check-input" type="checkbox" value="" id="defaultCheck2">
								<label class="form-check-label" for="defaultCheck2">
								  Amarillo
								</label>
							  </div>
							  <div class="form-check">
								<input class="form-check-input" type="checkbox" value="" id="defaultCheck3">
								<label class="form-check-label" for="defaultCheck3">
								  Blanco
								</label>
							  </div>
							</div>
							<div class="form-group col-md-6">
							  <p>Lista de valores (Botones de opción)</p>
							  <div class="form-check">
								<input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios1" value="option1" checked>
								<label class="form-check-label" for="exampleRadios1">
								  Azul
								</label>
							  </div>
							  <div class="form-check">
								<input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios2" value="option2">
								<label class="form-check-label" for="exampleRadios2">
								  Amarillo
								</label>
							  </div>
							  <div class="form-check">
								<input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios3" value="option3">
								<label class="form-check-label" for="exampleRadios3">
								  Blanco
								</label>
							  </div>
							</div>
						  </div>
						  <div class="form-group">
							  <label class="control-label">Campo texto error *</label>
							  <input type="text" class="form-control errorField" name="username" required="true" />
							  <span class="hint">Texto cuando hay un <strong>'error'</strong>. </span>
							</div>
						  <div class="form-group">
							<input type="file" class="custom-file-input" id="inputGroupFile01">
							<label class="custom-file-label label_show" for="inputGroupFile01">Seleccione un archivo</label>
							<label class="custom-file-label label_hidden hidden" for="inputGroupFile01"></label>
							<i class="custom-file-icon icon icon-close hidden" alt="borrar" title="borrar"></i>
						  </div>
						</fieldset>
						<div class="tiny-text">
						  <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce vitae orci lacinia, elementum arcu vel, pellentesque nisl. Duis finibus est sit amet quam suscipit tristique. Suspendisse rhoncus id erat a sodales. Phasellus vel mi tellus. Nam ac lectus id est volutpat molestie id a lorem. Quisque arcu diam, maximus et vehicula sit amet, porta ut lacus. Vestibulum lobortis id enim et elementum. </p>
						</div>
						<div class="row buttons">
						  <div class="col-md-4">
							<button type="button" class="btn btn-success btn-block">Botón verde</button>
						  </div>
						  <div class="col-md-4">
							<button type="button" class="btn btn-secondary btn-block">Botón gris</button>
						  </div>
						  <div class="col-md-4">
							<button type="button" class="btn btn-danger btn-block">Botón rojo</button>
						  </div>
						</div>
						<div class="row buttons">
						  <div class="col-md-4">
							<a class="btn btn-success btn-block" href="#" role="button">Enlace tipo Botón verde</a>
						  </div>
						  <div class="col-md-4">
							<a class="btn btn-secondary btn-block" href="#" role="button">Enlace tipo Botón gris</a>
						  </div>
						  <div class="col-md-4">
							<a class="btn btn-danger btn-block" href="#" role="button">Enlace tipo Botón rojo</a>
						  </div>
						</div>
					  </form>
				</div>
			</div>
		</div>
	</div> <!-- greysoft -->
	
	<%@include file="includes/footer.jsp"%>	
	

	 <%@include file="includes/foot.jsp"%>	
</body>
</html>
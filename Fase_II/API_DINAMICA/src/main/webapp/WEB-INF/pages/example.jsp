<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page session="true"%>
<!DOCTYPE html>
<%@include file="includes/headPublic.jsp"%>
<body class="kss-body indraweb-2018">
	<%@include file="includes/headerPublic.jsp"%>
	<div id="kss-wrapper" class="kss-wrapper">
		<div id="kss-nav">
			<aside>
				<nav class="kss-nav">
          <ul class="kss-menu" data-kss-ref="0">
            <li class="kss-menu-item"><a href="./index.html"><span class="kss-ref">0</span><span class="kss-name">Introducci�n</span></a></li>
            <li class="kss-menu-item">
            	<a href="section-1.html"><span class="kss-ref">1</span><span class="kss-name">Base</span></a>
            </li>
            <li class="kss-menu-item">
            	<a href="section-2.html"><span class="kss-ref">2</span><span class="kss-name">M�dulos comunes</span></a>
            </li>
            <li class="kss-menu-item">
            	<a href="section-3.html"><span class="kss-ref">3</span><span class="kss-name">Elementos comunes</span></a>
            </li>
            <li class="kss-menu-item">
            	<a href="section-4.html"><span class="kss-ref">4</span><span class="kss-name">Formularios</span></a>
            </li>
            <li class="kss-menu-item">
            	<a href="section-5.html"><span class="kss-ref">5</span><span class="kss-name">Botones</span></a>
            </li>
            <li class="kss-menu-item">
            	<a href="section-6.html"><span class="kss-ref">6</span><span class="kss-name">Variables</span></a>
            </li>
            <li class="kss-menu-item">
            	<a href="section-7.html"><span class="kss-ref">7</span><span class="kss-name">Aplicaciones Externas</span></a>
            </li>
          </ul>
        </nav>
			</aside>
		</div>
		<div class="kss-content">
      <article class="kss-article">
        <section id="section-0" class="kss-section kss-overview">
          <h1 id="introducci-n">Introducci�n</h1>
<p>Esta gu�a describe la metodolog�a de desarrollo de interfaz, estructura de p�ginas, m�dulos y componentes, utilizados en el portal Web de la Intranet del Ayuntamiento de Madrid de acuerdo al nuevo estilo y dise�o gr�fico de la Intranet. Est� construido utilizando un modelo adaptativo siguiendo las recomendaciones del Responsive Web Design.</p>
<p>Se ha estructurado en tres apartados: estructura base, m�dulos generales y m�dulos espec�ficos. En cada bloque se detallan los diferentes componentes, su presentaci�n y composici�n mediante estilos.</p>
<p>Esta gu�a se complementa con un amplio conjunto de plantillas maquetadas para utilizar como base y referencia.</p>
<h3>Necesidades no contempladas:</h3>

<p>Cuando las funcionalidades de la aplicaci�n o portal hagan necesario el uso de elementos no contemplados en esta gu�a, es conveniente que el c�digo que se genere cumpla las pautas generales que han guiado el desarrollo del portal, en lo que se refiere a estilos visuales.</p>
<p>Para la construcci�n de esta gu�a se ha usado &quot;styleguide grunt&quot; por lo que puede haber alguna referencia a la hoja de estilo &quot;KSS&quot;, estos estilos son usados exclusivamente en esta gu�a y no corresponden al modelo de interfaz del Ayuntamiento.</p>

        </section>
      </article>
    </div>
	</div>
	<%@include file="includes/footerPublic.jsp"%>
	<%@include file="includes/footPublic.jsp"%>	
</body>
</html>
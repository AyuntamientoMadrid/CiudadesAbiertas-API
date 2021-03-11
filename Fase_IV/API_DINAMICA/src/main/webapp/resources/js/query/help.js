var ayudaCodigo = 	"Este código se utilizará para realizar la petición a esta llamada.<br/><br/>"+
					"Si su código es 'telefono' esta llamada acabará en '.../telefono'.<br/><br/>"+
					"Este código no debe tener mayusculas, espacios o caracteres especiales.";
var ayudaBBDD = "En esta lista se encuentran las distintas bases de datos que están disponibles para extraer la información.<br/><br/>"+
				"La 'base de datos por defecto' es la base de datos interna de esta aplicación y NO contendrá las tablas necesarias para lanzar sus consultas.";
var ayudaConsulta = "<div class='row'>"+
					"<div class='col-md-6'>"+
					"Esta es la consulta SQL que se va a ejecutar cuando se llame a la URL de esta consulta.<br/><br/>"+
					"Aunque se puede escribir una consulta solicitando todos los datos de una tabla con *. <b>Se recomienda NO realizar esta práctica.</b>.<br/><br/>"+
					"Las consultas multilinea están soportadas:<br/><br/>"+
					"<b>Select id, title <br/>"+
					"&nbsp;&nbsp;from subvencion</b><br/><br/>"+
					"Tambien se pueden solicitar los campos uno por uno, y utilizar ' as ' para darle el nombre que más se ajuste a sus necesidades:<br/><br/>"+
					"<b>Select id, <br/>"+
					"&nbsp;&nbsp;importe, <br/>"+
					"&nbsp;&nbsp;area_id as areaId <br/>"+
					"from subvencion</b><br/><br/>"+
					"Si se han definido parámetros se puede utilizar escribiendo el nombre del parametro.<br/><br/>"+
					"<b>Select id, <br/>"+
					"&nbsp;&nbsp;importe, <br/>"+
					"&nbsp;&nbsp;area_id as areaId <br/>"+
					"from subvencion<br/>"+
					"where id like paramId</b><br/><br/>"+
					"</div>"+
					"<div class='col-md-6'>"+
					"Si se quiere realizar una consulta geográfica con puntos x e y en ETRS89, se deben utilizar los campos <b>xETRS89</b> e <b>ETRS89</b>.<br/><br/>"+
					"Si estos campos no existen en la tabla se deben utilizar  ' as ' para renombrarlos. Ejemplo: <br/><br/>"+
					"<b>Select id, <br/>"+
			        "&nbsp;&nbsp;title, <br/>"+
			        "&nbsp;&nbsp;x_etrs89 AS xETRS89, <br/>"+
			        "&nbsp;&nbsp;y_etrs89 AS yETRS89 <br/>"+        
			        "from local_comercial</b><br/><br/>"+
					"Si se quiere realizar una consulta geográfica con una geometría (polígono o multipolígono) se debe utilizar el campo <b>hasGeometry</b>.<br/><br/>"+
					"Si este campo no existe en la tabla se debe utilizar  ' as ' para renombrarlo. Ejemplo: <br/><br/>"+
					"<b>Select id, <br/>"+
			        "&nbsp;&nbsp;title, <br/>"+
			        "&nbsp;&nbsp;geometry AS hasGeometry, <br/>"+   
			        "from municipio</b><br/><br/>"+
                    "Si se quiere realizar una búsqueda dentro de una circunferencia a partir de su centro y un radio, se puede hacer utilizando tres parametros: x (xEtrs89P) e y (yEtrs89P) en formato ETRS89 y el radio en metros(metersP).<br/><br/>"+
					"Además de estos parámetros, se debe generar una subconsulta contenga el campo 'distance' como en el siguiente ejemplo:<br/><br/>"+
					"<b>select id, title, municipioId, xETRS89, yETRS89, distance from<br/>"+
					"&nbsp;&nbsp;(select id, <br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;title, <br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;municipio_id as municipioId, <br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;x_etrs89 as xETRS89, <br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;y_etrs89 as yETRS89, <br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;SQRT((x_etrs89-xEtrs89P)*(x_etrs89-xEtrs89P)+(y_etrs89-yEtrs89P)*(y_etrs89-yEtrs89P)) as distance<br/>"+              
					"&nbsp;&nbsp;from <br/>"+
					"&nbsp;&nbsp;local_comercial ) as consultaGeo <br/>"+         
					"where <br/>"+
					"&nbsp;&nbsp;distance<=metersP <br/>"+       
					"order by <br/>"+
					"&nbsp;&nbsp;distance asc</b>"+
					"</div>"+
					"</div>";
var ayudaDes = 	"Descripción del comportamiento de la consulta.<br/><br/>"+
				"Este texto aparecerá en la documentación de SWAGGER."
var ayudaEtiquetas ="Etiquetas para clasificar cada llamada dentro de la documentación SWAGGER.<br/><br/>"+
					"Se pueden añadir varias etiquetas, hay que usar comas (,) para separarlas.<br/><br/>"+
					"Ejemplo: Locales Comerciales"
var ayudaDefinicion = "Si ha configurado el modelo de una consulta, puede seleccionarla a través de esta lista.";
var ayudaParamNombre = 	"Nombre del parámetro, la cadena que aquí se escriba se utilizará como parámetro en la URL de la llamada, y se reemplazará en la consulta.<br/><br/>"+
						"Se recomienda añadir un sufijo como 'P' o 'Param' en todos los parámetros para no confundir con datos de la consulta.<br/><br/>"+
						"Ejemplos:<br/>"+
						"&nbsp;&nbsp;xEtrs89P<br/>"+
						"&nbsp;&nbsp;yEtrs89P<br/>"+
						"&nbsp;&nbsp;identificadorParam<br/>"+
						"&nbsp;&nbsp;nombreParam";
var ayudaParamTipo = 	"En esta lista se debe seleccionar el tipo de parámetro. Estan soportados los siguientes tipos:<br/><br/>"+
						"&nbsp;&nbsp;-&nbsp;Texto: cualquier cadena de texto. Dentro de la query su nombre será reemplazado por su valor entre comillas.<br/>"+
						"&nbsp;&nbsp;-&nbsp;Número: números tanto enteros como decimales.<br/>"+
						"&nbsp;&nbsp;-&nbsp;Fecha: fecha en formato: 'AAAAMMDD' ('20160329') .<br/>";
						"&nbsp;&nbsp;-&nbsp;Fecha y hora: fecha y hora en formato: 'AAAAMMDDhhmmss' ('20160329123059') .<br/>";
var ayudaParamDes = "Descripción del parámetro.<br/><br/>"+
					"Este texto aparecerá en la documentación de SWAGGER."
var ayudaParamEjemplo = "Texto para añadir un ejemplo del parámetro. Estos valores se utilizarán para llamar a la consulta desde la parte interna.";

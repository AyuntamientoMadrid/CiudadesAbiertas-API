var codigoDoc =  "Código de la definición.<br/><br/>Este código aparece en la documentación de SWAGGER.<br/><br/> Ejemplo: LocalComercial";

var descripcionDoc =  "Descripción de la definición.<br/><br/>Este código aparece en la pantalla de las consultas para ser seleccionado.<br/><br/> Ejemplo: Definición Local Comercial";

var definicionDoc =  "Definición del modelo de la consulta en formato JSON siguiendo el standard OpenAPI.<br/><br/>"+
					 "Se corresponde con la version '3.25.0' de Swagger UI.<br/><br/>"+
				     "A continuación mostramos una definición <b>reducida</b> de Local Comercial.<br/><br/>"+
					"{<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;\"LocalComercial\":&nbsp;{<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\":&nbsp;\"object\",<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"properties\":&nbsp;{<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"id\":&nbsp;{<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\":&nbsp;\"string\",<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"description\":&nbsp;\"Identificador&nbsp;del&nbsp;local&nbsp;comercial.&nbsp;Ejemplo:&nbsp;270002391\"<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"title\":&nbsp;{<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\":&nbsp;\"string\",<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"description\":&nbsp;\"Nombre&nbsp;del&nbsp;local&nbsp;comercial.&nbsp;Ejemplo:&nbsp;OGAME\"<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"aforo\":&nbsp;{<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\":&nbsp;\"integer\",<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"format\":&nbsp;\"int32\",<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"description\":&nbsp;\"Capacidad&nbsp;del&nbsp;local,&nbsp;expresada&nbsp;como&nbsp;el&nbsp;número&nbsp;de&nbsp;personas&nbsp;que&nbsp;caben&nbsp;en&nbsp;él.&nbsp;Ejemplo:&nbsp;3\"<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"municipioTitle\":&nbsp;{<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\":&nbsp;\"string\",<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"description\":&nbsp;\"Nombre&nbsp;del&nbsp;municipio&nbsp;del&nbsp;local&nbsp;comercial.&nbsp;Ejemplo:&nbsp;Madrid\"<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"streetAddress\":&nbsp;{<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\":&nbsp;\"string\",<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"description\":&nbsp;\"Calle&nbsp;del&nbsp;local&nbsp;comercial.&nbsp;Ejemplo:&nbsp;Calle&nbsp;Bravo&nbsp;Murillo&nbsp;Num&nbsp;360\"<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"telephone\":&nbsp;{<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\":&nbsp;\"string\",<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"description\":&nbsp;\"Teléfono&nbsp;del&nbsp;local&nbsp;comercial.&nbsp;Ejemplo:&nbsp;919999991\"<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"xETRS89\":&nbsp;{<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\":&nbsp;\"number\",<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"description\":&nbsp;\"Coordenada&nbsp;X&nbsp;del&nbsp;local&nbsp;comercial.&nbsp;Ejemplo:&nbsp;441201.60999147\"<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"yETRS89\":&nbsp;{<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\":&nbsp;\"number\",<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"description\":&nbsp;\"Coordenada&nbsp;Y&nbsp;del&nbsp;local&nbsp;comercial.&nbsp;Ejemplo:&nbsp;4479589.52997752\"<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"title\":&nbsp;\"LocalComercial\"<br/>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;}<br/>"+
					"}<br/>";







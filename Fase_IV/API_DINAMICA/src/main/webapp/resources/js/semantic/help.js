var ayudaConsulta =  "En este control se debe seleccionar la consulta que se quiere semantizar.";
var ayudaPrefijoSel =  "Aquí están todos los prefijos que se han seleccionado, para deseleccionarlos se debe hacer un doble click. <br/><br/>"+
						"Los prefijos se utilizan para evitar tener que escribir el comienzo de las URIs continuamente. <br/><br/>"+
						"Ejemplo de una URI sin prefijo: <b>http://www.w3.org/2002/07/owl#</b>Thing <br/><br/>"+
						"Ejemplo de una URI con prefijo: <b>owl</b>:Thing"
var ayudaPrefijoDis =  "Aquí están todos los prefijos que están disponibles, para seleccionarlos se debe hacer un doble click. <br/><br/>"+
						"Los prefijos se utilizan para evitar tener que escribir el comienzo de las URIs continuamente. <br/><br/>"+
						"Ejemplo de una URI sin prefijo: <b>http://www.w3.org/2002/07/owl#</b>Thing <br/><br/>"+
						"Ejemplo de una URI con prefijo: <b>owl</b>:Thing"
var ayudaPrefijoType =  "Este es el prefijo que usa para definir el tipo. <br/><br/>"+
						"&nbsp;&nbsp;&nbsp;...rdf:type <b>prefijo</b>:finDeLaURI.<br/><br/>"+
						"Ejemplo: <br/><br/>"+
						"&nbsp;&nbsp;&nbsp...rdf:type owl:Thing.<br/><br/>"+
						"Se está definiendo la tripleta donde se define el tipo (rdf:type) de cada elemento de la consulta.<br/><br/>"+
						"Sujeto rdf:type <b>Objeto</b>.<br/><br/>";
var ayudaTypeURL =  "Este es el final de la URL que usa para definir el tipo (rdf:type). <br/><br/>"+
						"&nbsp;&nbsp;&nbsp;...rdf:type prefijo:<b>FinDeLaURI</b><br/><br/>"+
						"<b>Siempre</b> empieza por una letra mayúscula.<br/><br/>"+
						"Ejemplo: <br/><br/>"+
						"&nbsp;&nbsp;&nbsp...rdf:type owl:Thing.<br/><br/>"+
						"Se está definiendo la tripleta donde se define el tipo (rdf:type) de cada elemento de la consulta.<br/><br/>"+
						"Sujeto rdf:type <b>Objeto</b>.<br/><br/>";
						
						
var ayudaCampoConsulta =  "Nombre del campo que viene de la consulta seleccionada. <br/><br/>"+
						  "Si en la consulta se han definido varios campos (select <b>nombre, edad, direccion</b> from ...), apareceran todos esos campos (nombre, edad y dirección).";
						
var ayudaPredicado = 	"En esta sección se va a definir el predicado del campo seleccionado.  <br/><br/>"+
						"Se estan definiendo tripletas de tipo: 'Sujeto <b>Predicado</b> Objeto' <br/><br/>"+
						"Para definir la URI del predicado, hay que seleccionar prefijo que se utiliza (opcional), y el final de la URI.<br/><br/>"+
						"Ejemplo de tripleta con predicado sin prefijo: &lt;https://.../agenda-cultural/evento/AG0058&gt; &lt;http://purl.org/dc/terms/title> 'Viajes de un día para mayores'.<br/><br/>"+
						"Ejemplo de tripleta con predicado con prefijo: &lt;https://.../agenda-cultural/evento/AG0058&gt; dcterms:title 'Viajes de un día para mayores'.";
						
						
var ayudaPredicadoPrefix = "En este control se debe seleccionar el prefijo del predicado. No es obligatorio.";
var ayudaPredicadoURI = "En este control se debe escribir el final de la URI del predicado (si se ha elegido previamente su prefijo) o su URI completa.<br/><br/>"+
						"Ejemplo de tripleta con predicado sin prefijo: &lt;https://.../agenda-cultural/evento/AG0058&gt; &lt;http://purl.org/dc/terms/title&gt; 'Viajes de un día para mayores'.<br/><br/>"+
						"Ejemplo de tripleta con predicado con prefijo: &lt;https://.../agenda-cultural/evento/AG0058&gt; dcterms:title 'Viajes de un día para mayores'.";

	
var ayudaObjeto = "En esta sección se va a definir el objecto del campo seleccinado.  <br/><br/>"+
					"Se estan definiendo tripletas de tipo: 'Sujeto Predicado <b>Objeto</b>'  <br/><br/>"+
					"Los objectos pueden ser simples (un número, una cadena de texto, ...), que apunten a una URI (que puede apuntar a otro objecto) o a un nodo en blanco.<br/><br/>"+
					"Ejemplo de tripleta con un objecto simple: <br/><br/>"+
					"&emsp;&emsp;&lt;https://.../agenda-cultural/evento/AG0058&gt; esagenda:fechaFin <b>'2018-10-26'^^xsd:date</b>.<br/><br/>"+
					"Ejemplo de tripleta con un objecto que apunta a una URI: <br/><br/>"+
					"&emsp;&emsp;&lt;https://.../agenda-cultural/evento/AG0058&gt; esadm:municipio <b>&lt;https://.../territorio/municipio/28006&gt;</b>.<br/><br/>"+
					"Ejemplo de tripleta con un objecto que apunta a un nodo en blanco: <br/><br/>"+
					"&emsp;&emsp;&lt;https://.../agenda-cultural/evento/AG0058&gt; esequip:equipamiento <b>[a esequip:Equipamiento ;</b><br/>"+
					"&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<b>dcterms:title 'Casa de la Juventud' </b> <br/>"+
					"&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<b>]</b> .";
					
var ayudaObjetoSimple = "En este control se debe escribir el final de la URI del predicado (si se ha elegido previamente su prefijo) o su URI completa.<br/><br/>"+
						"Ejemplo de tripleta con predicado sin prefijo: &lt;https://.../agenda-cultural/evento/AG0058&gt; &lt;http://purl.org/dc/terms/title&gt; 'Viajes de un día para mayores'.<br/><br/>"+
						"Ejemplo de tripleta con predicado con prefijo: &lt;https://.../agenda-cultural/evento/AG0058&gt; dcterms:title 'Viajes de un día para mayores'.";

var ayudaTypes = "Por defecto todos los objectos 'simples' se consideran cadenas, por lo que especificar el tipo del objeto si es de otro tipo (número entero, booleano, fecha,...).<br/><br/>"+
						"Ejemplo de tripleta con un objecto simple de tipo fecha: <br/><br/>"+
					"&emsp;&emsp;&lt;https://.../subvencion/S0012&gt; essubv:fechaAdjudicacion <b>'2018-10-26'^^xsd:date</b>.<br/><br/>"+
					"Ejemplo de tripleta con un objecto simple de tipo float (con decimales): <br/><br/>"+
					"&emsp;&emsp;&lt;https://.../subvencion/S0012&gt; essubv:importe <b>'3100.5'^^xsd:float</b>.<br/><br/>"+
					"Ejemplo de tripleta con un objecto simple de cadena: <br/><br/>"+
					"&emsp;&emsp;&lt;https://.../subvencion/S0012&gt; dcterms:identifier <b>'S0012'</b>.<br/><br/>";
					
var ayudaObjectoURI="Un objecto puede apuntar a una URI que se encuentre dentro de la propia API o que apunte a una URI externa.<br/><br/>"+
					"Se puede modificar el valor para añadir la partes de la que URI que se necesiten. Si se hace esto el nombre del valor debe estar entre {}.<br/><br/>"+
					"Ejemplo: Si el valor es areaId se podría modificar así: https://.../OpenCitiesAPI/organigrama/organizacion/{areaId} <br/><br/>" +
					"Se debe verificar que todas las URIs que se utilicen sean accesibles y devuelvan información. <br/><br/>"+
					"Ejemplo de tripleta con un objecto que apunta una URI: <br/><br/>"+
					"&emsp;&emsp;&lt;https://.../subvencion/S0012&gt; essubv:tipoInstrumento <b>'&lt;http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/subvencion/tipo-instrumento/subvencion-y-entrega-dineraria-sin-contraprestacion&gt;</b>.<br/><br/>";			


var ayudaNodoId="El identificador del nodo se utiliza a nivel interno (no se expone en el RDF generado) y se utiliza para identificar cada nodo en blanco. De esta forma se puede reutilizar el mismo nodo, para añadir más tripletas sobre este.";
					
var ayudaNodoType="Aquí se selecciona el tipo (rdf:type) del nodo en blanco.<br/><br/>"+
					"Es importante tener en cuenta que los nodos que comparten identificador son el mismo nodo, porque el su tipo tiene que ser el mismo.<br/><br/>"+
					"&emsp;&emsp;&lt;https://.../agenda-cultural/evento/AG0058&gt; esequip:equipamiento [<b>a esequip:Equipamiento</b> ;<br/>"+
					"&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;dcterms:title 'Casa de la Juventud'  <br/>"+
					"&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;].";

					
var ayudaNodoPredicate="Aquí se selecciona el predicado que apunta al nodo en blanco que se está definiendo.<br/><br/>"+
					"&emsp;&emsp;&lt;https://.../agenda-cultural/evento/AG0058&gt; <b>esequip:equipamiento</b> [a esequip:Equipamiento ;<br/>"+
					"&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;dcterms:title 'Casa de la Juventud'  <br/>"+
					"&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;].";
					
var ayudaInputURI="Cada elemento que devuelva la consulta sera identificado por esta URI. Esta URI sera el sujeto de cada tripleta. <br/><br/>"+
                  "Se estan definiendo tripletas de tipo: '<b>Sujeto</b> Predicado Objeto'";

var ayudaFinURI="Se debe elegir un campo que identifique inequívocamente a cada elemento (columna <b>id</b>). Así se garantiza que la URI de cada elemento es única. <br/><br/>"+
                  "Se estan definiendo tripletas de tipo: '<b>Sujeto</b> Predicado Objeto'";

var textoModal_1 =  "";
	
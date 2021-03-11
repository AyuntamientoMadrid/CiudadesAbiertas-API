var script_tag = document.getElementById('taskScript');
var contextParam = script_tag.getAttribute("data-context");
var elementsAdded=new Array();



$(function() {
 
	
	$('#prefixAvailable').dblclick(function(){prefixSelected(this)}); 
	$('#prefixSelected').dblclick(function(){checkPrefixUnSelected(this)}); 
	
	$( "#query" ).change(function() {checkQueryChanged(this)});
	

	
	
});

function testPruebas (valor) {
	
	var selectOption = $("#prefixAvailable");
	prefixSelected(selectOption);
}


function checkQueryChanged(selectedOption)
{
	var valueSel=$("option:selected", selectedOption).val();
		
	var confirmFunction = function() { 
    	queryChanged(selectedOption)
	};
	
	var cancelFunction = function() {	
    	$("#query").val(querySelected);
	};
	
	if (querySelected=="")
	{
		if (valueSel!=null)
		{		
			queryChanged(selectedOption);
		}
	
	}
	else
	{	
		if (valueSel!=null)
		{		
			activarConfirm("Se va a peder gran parte de la configuración asociada a esa consulta, ¿desea continuar?",confirmFunction, cancelFunction);			
		}
	}
	
}


function typeURIChanged(inputType)
{
	//console.log($(inputType).val());
	//console.log($(inputType).val().length);
	if ($(inputType).val().length>5)
	{
		$("#capaRelaciones").show();
		$("#botonGuardar").attr("disabled", false);
	}
	else
	{
		$("#botonGuardar").attr("disabled", true);
	}
}

/*Funcion que ejecuta cuando cambia la consulta seleccionada*/
function queryChanged(selectedOption)
{
	var valueSel=$("option:selected", selectedOption).val();	
	var theURL=contextParam+"/query/fields/"+valueSel;
	
	$("#relacionesTabla").empty();
	
	for (i = 0; i < elementsAdded.length; i++) 
	{
		$("#"+elementsAdded[i]).remove();	 
	}
	elementsAdded.splice(0, elementsAdded.length)
	
	if (valueSel!="")
	{
		$.ajax({
		  url: theURL	  
		}).done(function( data ) {
			
		 	drawAllFields(data)
		
			$("#capaTipo").show();
			$("#capaRelaciones").show();
			$("#botonGuardar").attr("disabled", false);
			
			querySelected=valueSel;
			$("#prefixType").val(-1);
			$("#typeURI").val("");			

			//Vaciamos los prefijos seleccionados
		   	var originOptions = $("#prefixSelected > option").clone();	

			$('#prefixAvailable').append(originOptions);
			$('#prefixSelected').find('option').remove();
			
			
			$("#prefixSelectedHidden").val("");

			$("#inputObjectURI").val(objectURI+valueSel+"/");
					
		}).fail( function( jqXHR, textStatus, errorThrown ) {
	    	//console.log( jqXHR );
			console.log( textStatus );
			console.log( errorThrown );
		});
	}
	else
	{
		$("#capaTipo").hide();
		$("#capaRelaciones").hide();
		$("#botonGuardar").attr("disabled", true);
	}
}


/*Funcion que pinta cada campo que recibe dentro de itemsData*/
function drawAllFields(itemsData)
{		
	var SUFFIX='CampoAdded_';
	var relationList=new Array();
	for (var item in itemsData)
	{
		relationList.push(item);
	}
	var matrixRecursos=listToMatrix(relationList,4);
	
	for (var i=0;i<matrixRecursos.length;i++)
	{
		var rawData=matrixRecursos[i];		
		rawData[0]="<a href=#fieldsetCampoAdded_"+rawData[0]+">"+rawData[0]+"</a>";
		if (rawData[1]==null)
		{
			rawData[1]="";
		}else{
			rawData[1]="<a href=#fieldsetCampoAdded_"+rawData[1]+">"+rawData[1]+"</a>";
		}
		if (rawData[2]==null)
		{
			rawData[2]="";
		}else{
			rawData[2]="<a href=#fieldsetCampoAdded_"+rawData[2]+">"+rawData[2]+"</a>";
		}
		if (rawData[3]==null)
		{
			rawData[3]="";
		}else{
			rawData[3]="<a href=#fieldsetCampoAdded_"+rawData[3]+">"+rawData[3]+"</a>";
		}
		$("#relacionesTabla").append("<tr><td>" + rawData[0] + "</td><td>" + rawData[1] + "</td><td>" + rawData[2]+ "</td><td>" + rawData[3]+"</td></tr>");
	}
	  
  
	
	
	

	//Por cada elemento replico la capa0
	for (var item in itemsData) 
	{
		//console.log(item);
		//console.log(itemsData[item]);
		var content=$("#fieldsetCampoZero").html();
		content="<fieldset id='fieldsetCampoZero'>"+content+"</fieldset>";				
		while (content.indexOf("CampoZero")>=0)
		{
			content=content.replace("CampoZero",SUFFIX+item);		
		}	
		
		while (content.indexOf("atributoValue")>=0)
		{
			content=content.replace("atributoValue",item);
		}
				
		$("#relaciones").append(content);
		elementsAdded.push("fieldset"+SUFFIX+item);
		
		$("#fieldObjectURI").append($('<option>',{value:item, text:item}));
		
		
	}	
}

/*Funcion para copiar las opciones de un select a otro (eliminando lo que tiene el destino)*/
function copyOptions(origen, destino)
{
	var originOptions = $("#"+origen+" > option").clone();
	
	$('#'+destino).find('option').remove();
	$('#'+destino).append(originOptions);
}

/*Funcion que refresca los prefijos seleccionados en el input hidden que los contiene*/
function actualizaHiddenPrefix()
{
	var prefixSelectedHidden=$("#prefixSelectedHidden");
	prefixSelectedHidden.val("");
	var selectedP="";
	$("#prefixSelected option").each(function(){	
		selectedP+=$( this ).val()+";"
	});
	prefixSelectedHidden.val(selectedP);
	//console.log(selectedP);
	
}

/*Funcion que se ejecuta cuando se selecciona un prefijo*/
function prefixSelected(selectedOption)
{			
	
	if($("#query").val()=="")
	{
		activarModal("Antes de seleccionar un prefijo se debe especificar la consulta a semantizar.");
		return;
	}
	
	var textSel=$("option:selected", selectedOption).text();
	var valueSel=$("option:selected", selectedOption).val();
	var titleSel=$("option:selected", selectedOption).attr("title");
	var prefixSel=textSel.substring(0,textSel.indexOf(" "));
			
	if (valueSel!=null)
	{	
		$("#prefixSelected").prepend($('<option>', {value:valueSel, text:textSel}).attr("title",titleSel));	
		$("option:selected", selectedOption).remove();
		
		
		$("#prefixType").append($('<option>', {value:valueSel, text:prefixSel}).attr("title",prefixSel));
		$("#prefixTypeCampoZero").append($('<option>', {value:valueSel, text:prefixSel}).attr("title",prefixSel));
		
		
		$('[id^=prefixTypeCampoAdded_], [id^=prefixNodePredicateTypeCampoAdded_], [id^=prefixNodePredicateCampoAdded_]').each(function() {
			var selectedOp=$( this ).val();
		    copyOptions("prefixTypeCampoZero",$( this ).attr("id"));
			$( this ).val(selectedOp);
		});
		
			
		actualizaHiddenPrefix();
		
	}	
}

/* Funcion que chequea si un prefijo se esta usando cuando se quiere borrar
   Si no se esta usando se borra
   Si se usa se muestra una capa que simula un confirm y se le pasa la funcion que hace el borrado, por si el usuario confirma la acción
*/
function checkPrefixUnSelected(selectedOption)
{
	var valueSel=$("option:selected", selectedOption).val();
	
	//console.log('checkPrefixUnSelected: '+valueSel);
	
	var confirmFunction = function() { 
    	prefixUnSelected(selectedOption)
	};
	
	
	if (valueSel!=null)
	{	
		var uso=prefixInUse(selectedOption);
		if (uso)
		{
			activarConfirm("Este prefijo se está utilizando, si continua los campos que lo utilizan perderan esta información, ¿desea continuar?",confirmFunction, null);			
		}
		else
		{
			prefixUnSelected(selectedOption);
		}
	}
	
}


/*Función que elimina un prefijo seleccionado*/
function prefixUnSelected(selectedOption)
{	
	var textSel=$("option:selected", selectedOption).text();
	var valueSel=$("option:selected", selectedOption).val();
	var titleSel=$("option:selected", selectedOption).attr("title");
	
	
	//console.log(valueSel);
	if (valueSel!=null)
	{	
		$("#prefixAvailable").prepend($('<option>', {value:valueSel, text:textSel}).attr("title",titleSel));	
		$("option:selected", selectedOption).remove();
		
		//Si se usa lo pongo a -1 antes de eliminar
		if ( $("#prefixType").val() == valueSel ) {
		     $("#prefixType").val(-1);
		}
		
		$("#prefixType option[value='"+valueSel+"']").remove();
		$("#prefixTypeCampoZero option[value='"+valueSel+"']").remove();
		
		//Si se usa lo pongo a -1 antes de eliminar
		$('[id^=prefixTypeCampoAdded_], [id^=prefixNodePredicateTypeCampoAdded_],[id^=prefixNodePredicateCampoAdded_]').each(function() {
						
			 if ( $(this).val() == valueSel ) {
			       $(this).val(-1);
			    }
		});
		
		//ahora elimino
		$('[id^=prefixTypeCampoAdded_]  option, [id^=prefixNodePredicateTypeCampoAdded_] option,[id^=prefixNodePredicateCampoAdded_]  option').each(function() {
						
			 if ( $(this).val() == valueSel ) {
			        $(this).remove();
			    }
		});
		
		
		
		actualizaHiddenPrefix();			
	}	

}



/*Funcion que valida el formulario*/ 
function validateForm()
 {
	var validated=true;
	var firstError="";
	
	var queryCode="#query";
	var fieldObjectURI="#fieldObjectURI";
	var prefixType="#prefixType";
	var uriType="#typeURI";

	
	var validationArray = new Array();
	validationArray.push(queryCode);
	validationArray.push(fieldObjectURI);
	
	var validationCustomArray = new Array();
	validationCustomArray.push(prefixType);
	validationCustomArray.push(uriType);

	$(prefixType).removeClass("errorField");
	$(uriType).removeClass("errorField");	
	for (var i=0;i<validationArray.length;i++)
	{	
		var controlActual=validationArray[i];
		$(controlActual).removeClass("errorField");
		$(controlActual).removeClass("okField");
	}
	
	for (var i=0;i<validationCustomArray.length;i++)
	{	
		var controlActual=validationCustomArray[i];
		$(controlActual).removeClass("errorField");
		$(controlActual).removeClass("okField");
	}
	
	
	for (var i=0;i<validationArray.length;i++)
	{
		var controlActual=validationArray[i];		
		
		//console.log(controlActual);
		$(controlActual).removeClass("errorField");
		
		field = $(controlActual).parsley();	
		if (field!=null)
		{
			field.validate();			
			if (!field.isValid())
			{	
				//console.log("error "+controlActual);
				$(controlActual).addClass("errorField");
				validated=false;
				if (firstError=="")
				{
					firstError=controlActual;
				}
			}else{
				$(controlActual).addClass("okField");				
			}
		}
		
	}
	
	
	
	if (!firstError)
	{
		$(firstError).focus();
	}
	
	
	var validElement=validateTypeAndURI(prefixType,uriType);
	if (validElement<0)
	{
		$(prefixType).addClass("errorField");
		$(uriType).addClass("errorField");	
		$(prefixType).focus();
		activarModal("La configuración del Tipo (rfd:type) no es válida");
		return;
	}
	
	
	//Validaciones a medida: rdf type y demas relaciones	
	var validType=validateTypeAndURI(prefixType,uriType);
	
	var field_PrefixArray=new Array();
	$('[id^=prefixTypeCampoAdded_]').each(function() {
		field_PrefixArray.push($( this ).attr("id"));
	});
	var field_URIArray=new Array();
	$('[id^=urlCampoAdded_]').each(function() {
		field_URIArray.push($( this ).attr("id"));
	});
	
	//console.log(firstError);
	
	var numValidFields=0;
	for  (var i=0;i<field_PrefixArray.length;i++)
	{
		var validElement=validateTypeAndURI('#'+field_PrefixArray[i],'#'+field_URIArray[i]);
		if (validElement>-1)
		{
			numValidFields+=validElement;			
		}else{
			$('#'+field_PrefixArray[i]).addClass("errorField");
			$('#'+field_URIArray[i]).addClass("errorField");	
			if (!firstError)
			{		
				firstError='#'+field_PrefixArray[i];		
			}	
			validated=false;
			break;
		}
	}

	//console.log(validated);
	//console.log(firstError);
	if (validated==true)
	{
		 //console.log("isValid");
		 if ((validType>0)||(numValidFields>0))	 
		 {
			$("#semanticForm").submit();
		 }
		else
		{			
			activarModal("No se puede guardar debido a que no se han definido predicados correctamente.");
		}
	}else{
		if (firstError)
		{			
			$(firstError).focus();
		}
		activarModal("No se puede guardar debido a que no se han definido predicados correctamente.");
		
		
	}
	
 }

/* 
	Devuelve -1 si hay errores 
	Devuelve 0 si es valido pero no se ha especificado nada
	Devuelve 1 si es valido y se especificado una definición
*/
function validateTypeAndURI(prefix, uri)
{
	var prefixTypeV=$(prefix).val();
	var uriTipeV=$(uri).val();
	
	//console.log(prefixTypeV+" with "+uriTipeV);
	
	var validType=false;
	if ((prefixTypeV==-1) && (uriTipeV==''))
	{
		//$(prefix).addClass("okField");
		//$(uri).addClass("okField");
		
		validType=0;
	}
	else if ((prefixTypeV!=-1) && (uriTipeV==''))
	{
		//$(prefix).addClass("okField");
		$(uri).addClass("errorField");
		validType=-1;
		$(uri).focus();
	}
	else if ((prefixTypeV==-1) && (uriTipeV!=''))
	{
		var validPrefix = uriTipeV.toLowerCase().startsWith("http"); 
		if (validPrefix==false)
		{
			validType=-2;
		}
		else
		{
			validType=1;
		}
		
	}
	else if ((prefixTypeV==-1) && (uriTipeV!=''))
	{
		//$(prefix).addClass("okField");
		if (uriTipeV.indexOf("http")==0)
		{
			//$(uri).addClass("okField");
			validType=1;	
		}else{
			$(uri).addClass("errorField");
			$(uri).focus();
		}		
	}
	else
	{
		//$(prefix).addClass("okField");
		//$(uri).addClass("okField");		
		validType=1;
	}	
	
	return validType;
}
 

/*Funcion que cambia el interfaz dependiendo del tipo de objecto (tripleta: sujeto predicado objeto))*/ 
function typeObjectChange(control,suffixName)
{
	
	var tipo=$(control).val();
	
	if (tipo=='blank')
	{
		$("#capaTipoSimple"+suffixName).show();	
		$("#capaNodoBlanco"+suffixName).show();
		$("#capaTipoURL"+suffixName).hide();
		
	}
	else if (tipo=='url')
	{
		$("#capaTipoSimple"+suffixName).hide();	
		$("#capaNodoBlanco"+suffixName).hide();
		$("#capaTipoURL"+suffixName).show();
	}
	else if (tipo=='simple')
	{
		$("#capaTipoSimple"+suffixName).show();	
		$("#capaNodoBlanco"+suffixName).hide();
		$("#capaTipoURL"+suffixName).hide();
	}
}

/*Función para chequear que un prefijo está en uso*/ 
function prefixInUse(selectedOption)
{
	var valueSel=$("option:selected", selectedOption).val();
	
	var founded=false;
	
	
	
	if ( $("#prefixType").val() == valueSel ) {
		        founded=true;
	}
	
	if (founded==false)
	{	
		$('[id^=prefixTypeCampoAdded_], [id^=prefixNodePredicateTypeCampoAdded_], [id^=prefixNodePredicateCampoAdded_]').each(function() {
			 if ( $(this).val() == valueSel ) {
			        founded=true;
					return false;
			    }
		});
	}
	
	
	
	return founded;
}

function listToMatrix(list, elementsPerSubArray) {
    var matrix = [], i, k;

    for (i = 0, k = -1; i < list.length; i++) {
        if (i % elementsPerSubArray === 0) {
            k++;
            matrix[k] = [];
        }

        matrix[k].push(list[i]);
    }

    return matrix;
}


//Literales Ayuda


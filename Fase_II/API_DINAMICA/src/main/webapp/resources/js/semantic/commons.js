var script_tag = document.getElementById('taskScript');
var contextParam = script_tag.getAttribute("data-context");
var elementsAdded=new Array();

$(function() {
 
	
	$('#prefixAvailable').dblclick(function(){prefixSelected(this)}); 
	$('#prefixSelected').dblclick(function(){prefixUnSelected(this)}); 
	
	$( "#query" ).change(function() {queryChanged(this)});
	
	

});

function queryChanged(selectedOption)
{
	var textSel=$("option:selected", selectedOption).text();
	var valueSel=$("option:selected", selectedOption).val();
	
	var theURL=contextParam+"/query/fields/"+valueSel;
	
	
	for (i = 0; i < elementsAdded.length; i++) 
	{
		$("#"+elementsAdded[i]).remove();	 
	}
	elementsAdded.splice(0, elementsAdded.length)
	
	if (valueSel!=-1)
	{
		$.ajax({
		  url: theURL	  
		}).done(function( data ) {
			
		 	drawAllFields(data)
		}).fail( function( jqXHR, textStatus, errorThrown ) {
	    	//console.log( jqXHR );
			console.log( textStatus );
			console.log( errorThrown );
		});
	}
}


function drawAllFields(itemsData)
{	
	var SUFFIX='CampoAdded_';
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
		
		content=content.replace("atributoValue",item);
				
		$("#relaciones").append(content);
		elementsAdded.push("fieldset"+SUFFIX+item);
		
	}	
}

function copyOptions(origen, destino)
{
	var originOptions = $("#"+origen+" > option").clone();
	
	$('#'+destino).find('option').remove();
	$('#'+destino).append(originOptions);
}


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

function prefixSelected(selectedOption)
{	
	var textSel=$("option:selected", selectedOption).text();
	var valueSel=$("option:selected", selectedOption).val();
	var titleSel=$("option:selected", selectedOption).attr("title");
	
	
	
	if (valueSel!=null)
	{	
		$("#prefixSelected").prepend($('<option>', {value:valueSel, text:textSel}).attr("title",titleSel));	
		$("option:selected", selectedOption).remove();
		
		
		$("#prefixType").prepend($('<option>', {value:valueSel, text:valueSel}).attr("title",titleSel));
		$("#prefixTypeCampoZero").append($('<option>', {value:valueSel, text:valueSel}).attr("title",titleSel));
		
		
		$('[id^=prefixTypeCampoAdded_]').each(function() {
			//console.log($( this ).attr("id"));
		    copyOptions("prefixTypeCampoZero",$( this ).attr("id"));
		});
		
		actualizaHiddenPrefix();
		
	}
}




function prefixUnSelected(selectedOption)
{	
	var textSel=$("option:selected", selectedOption).text();
	var valueSel=$("option:selected", selectedOption).val();
	var titleSel=$("option:selected", selectedOption).attr("title");
	
	if (valueSel!=null)
	{	
		$("#prefixAvailable").prepend($('<option>', {value:valueSel, text:textSel}).attr("title",titleSel));	
		$("option:selected", selectedOption).remove();
		
		$("#prefixType option[value='"+valueSel+"']").remove();
		$("#prefixTypeCampoZero option[value='"+valueSel+"']").remove();
		
		actualizaHiddenPrefix();
	}
}

function validateForm()
 {
	var validated=true;
	var firstError="";
	
	var queryCode="#query";
	var typeURI="#typeURI";	
	
	
	
	var validationArray = new Array();
	validationArray.push(queryCode);
	validationArray.push(typeURI);

	
	
	for (var i=0;i<validationArray.length;i++)
	{	
		var controlActual=validationArray[i];
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
	
	
	
	if (firstError!="")
	{
		$(firstError).focus();
	}
	
	if (validated)
	{
		//console.log("isValid");	
		$("#semanticForm").submit();
	}
	
 }
 
 


 function validateForm()
 {
	var validated=true;
	
	var firstError=null;
	
	var textCode="#code";
	var comboDB="#database";	
	var textoConsulta="#texto";
	var textoSummary="#summary";
	var textoTags="#tags";
	var textoDefinition="#definition";
	var textoPath="#path";
	var textoItems="#items";
	
	
	var validationArray = new Array();
	validationArray.push(textCode);
	validationArray.push(comboDB);
	validationArray.push(textoConsulta);
	validationArray.push(textoSummary);
	validationArray.push(textoTags);
	validationArray.push(textoDefinition);
	validationArray.push(textoPath);
	validationArray.push(textoItems);
	
	
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
				if (firstError==null)
				{
					firstError=controlActual;
				}
			}else{
				$(controlActual).addClass("okField");				
			}
		}
		
	}
	//console.log("validated:"+validated)
	if (validated)
	{
		//console.log("isValid");
		$("#queryForm").submit();
	}
	else
	{		
		$(firstError).focus();
	}
 }
 
 
 function addParamToForm()
 {	 
	var modifiedHTML=htmlParamCode; 
	
	modifiedHTML=modifiedHTML.replace("capaParametro0","capaParametro"+numParams);
	modifiedHTML=modifiedHTML.replace("botonBorrar0","botonBorrar"+numParams);
	modifiedHTML=modifiedHTML.replace("code0","code"+numParams);
	modifiedHTML=modifiedHTML.replace("type0","type"+numParams);
	modifiedHTML=modifiedHTML.replace("example0","example"+numParams);
	modifiedHTML=modifiedHTML.replace("description0","description"+numParams);
	modifiedHTML=modifiedHTML.replace("removeParamFromForm(0)","removeParamFromForm("+numParams+")");
	modifiedHTML=modifiedHTML.replace("addParam0","addParam"+numParams);
	
	//en edición
	modifiedHTML+=buttonAddParaCode.replace("addParam0","addParam"+numParams);
		
 	$("#fieldSetParametros").append(modifiedHTML);
 	
 	
 	$('input[name ="code'+numParams+'"]').val("");
 	$('input[name ="description'+numParams+'"]').val("");
 	$('input[name ="example'+numParams+'"]').val("");
 	$('select[name ="type'+numParams+'"]').val("text");
 	
 	 	
 	var allListElements = $("[id^=addParam]"); 	
 	if (allListElements.length>1)
 	{
	 	for (var i=0;i<allListElements.length-1;i++)
	 	{
	 		//console.log("borro: "+allListElements.get(i).id)
	 		$( allListElements.get(i) ).remove();
	 	}
 	} 	
 
 	numParams++;
 	
 }
 
 
 function removeParamFromForm(num)
 {	
	$( "#capaParametro"+num ).remove();	
 	var allListElements = $("[id^=addParam]"); 	
 	if (allListElements.length>1)
 	{
	 	for (var i=1;i<allListElements.length;i++)
	 	{
	 		$( allListElements.get(i) ).remove();
	 	}
 	}
 	
 }	
 
 
 function showParamsDiv()
 {
	 $("#capaParametros").show();
	 $("#addParamsDiv").hide();
	 
 }
 
 
 $(function() {	

	    $('#zip, #overwrite').change(function() {
	        if(this.checked){ 
	         
	            $(this).val("true")
	        }else{
	       	
	        	$(this).val("false")
	        }	              
	    });
	});

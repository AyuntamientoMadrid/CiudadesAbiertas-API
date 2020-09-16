 function validateForm()
 {
	var validated=true;
	
	var codeId="#code";
	var descriptionId="#description";
	var textId="#text";
	
	var validationArray = new Array();
	validationArray.push(codeId);
	validationArray.push(descriptionId);
	validationArray.push(textId);	
	
	for (var i=0;i<validationArray.length;i++)
	{	
		var controlActual=validationArray[i];
		$(controlActual).removeClass("errorField");
		$(controlActual).removeClass("okField");
	}
	
	
	for (var i=0;i<validationArray.length;i++)
	{
		var controlActual=validationArray[i];
		$(controlActual).removeClass("errorField");
		
		field = $(controlActual).parsley();			
		field.validate();			
		if (!field.isValid())
		{	
			//console.log("error "+controlActual);
			$(controlActual).addClass("errorField");
			validated=false;
		}else{
			$(controlActual).addClass("okField");
			
		}
	}
	
	if (validated)
	{
		//console.log("isValid");	
		$("#swaggerDefForm").submit();
	}else{
		//console.log("noValid");
	}
 }
 

 
 

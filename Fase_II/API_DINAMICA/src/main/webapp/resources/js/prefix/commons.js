 function validateForm()
 {
	var validated=true;
	
	var textId="#id";
	var textURL="#url";	

	
	
	var validationArray = new Array();
	validationArray.push(textId);
	validationArray.push(textURL);

	
	for (var i=0;i<validationArray.length;i++)
	{	
		var controlActual=validationArray[i];
		$(controlActual).removeClass("errorField");
		$(controlActual).removeClass("okField");
	}
	
	
	for (var i=0;i<validationArray.length;i++)
	{
		var controlActual=validationArray[i];		
		
		console.log(controlActual);
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
			}else{
				$(controlActual).addClass("okField");				
			}
		}
		
	}
	
	if (validated)
	{
		//console.log("isValid");	
		$("#queryForm").submit();
	}
	
 }
 
 


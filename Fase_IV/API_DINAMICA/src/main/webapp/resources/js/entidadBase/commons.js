 function validateForm()
 {
	var validated=true;
	
	var textId="#texto";
	var textoLargoId="#textoLargo";
	var fechaId="#fecha";
	var fechaId="#fecha";
	var decimalId="#numeroDecimal";
	var enteroId="#numeroEntero";
	
	var validationArray = new Array();
	validationArray.push(textId);
	validationArray.push(textoLargoId);
	validationArray.push(fechaId);
	validationArray.push(enteroId);
	validationArray.push(decimalId);
	
	
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
			console.log("error "+controlActual);
			$(controlActual).addClass("errorField");
			validated=false;
		}else{
			$(controlActual).addClass("okField");
			
		}
	}
	
	if (validated)
	{
		console.log("isValid");	
		$("#entidadBaseForm").submit();
	}else{
		console.log("noValid");
	}
 }
 

 
window.Parsley
   .addValidator('validateDate', {
     requirementType: 'string',
     validateString: function(value, requirement) {       
       return moment(value, requirement, true).isValid();
     },
     messages: {
       en: 'Invalid date',
       es: 'Fecha incorrecta'
     }
});
 

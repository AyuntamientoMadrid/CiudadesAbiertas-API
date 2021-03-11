		function drawField(itemName, itemPredicate, objectType, objectValue,objectValueType, nodeType, nodeId, nodeProperty, readOnly)
		{	
			var SUFFIX='CampoAdded_';	
			var predicatePrefix="";
			var predicateURL=itemPredicate;
				
			var content=$("#fieldsetCampoZero").html();
			content="<fieldset id='fieldsetCampoZero'>"+content+"</fieldset>";				
			while (content.indexOf("CampoZero")>=0)
			{
				content=content.replace("CampoZero",SUFFIX+itemName);		
			}	
			
			while (content.indexOf("atributoValue")>=0)
			{
				content=content.replace("atributoValue",itemName);
			}
					
			$("#relaciones").append(content);
			
			if (predicateURL.indexOf(":")>0)
			{
				var res = predicateURL.split(":");
				predicatePrefix=res[0];
				predicateURL=res[1];
				
				$("#prefixTypeCampoAdded_"+itemName)
				    .find('option')
				    .remove()
				    .end()
				    .append('<option value="'+predicatePrefix+'">'+predicatePrefix+'</option>')
				    .val(predicatePrefix)
				;
			}else{
				$("#prefixTypeCampoAdded_"+itemName)
			    .find('option')
			    .remove()
			    .end();
			    
			}			
			
			$("#urlCampoAdded_"+itemName).val(predicateURL);
			
			if(readOnly)
			{
				$("#urlCampoAdded_"+itemName).attr("disabled", true);
				$("#prefixTypeCampoAdded_"+itemName).attr("disabled", true);
				$('input[name=objectTypeCampoAdded_'+itemName+']').attr("disabled",true);
			}
			
			
			if (objectType=='simple')
			{
				$("#objectValueCampoAdded_"+itemName).val(objectValue);
				
				if (objectValueType=== "")
				{	
					if (readOnly)
					{					
						$("#fieldTypeCampoAdded_"+itemName).append('<option value="String">String</option>')
					}
					else
					{
						$("#fieldTypeCampoAdded_"+itemName).val('string');
					}
				}
				else
				{	
					if (readOnly)
					{					
						$("#fieldTypeCampoAdded_"+itemName).append('<option value="'+objectValueType+'">'+objectValueType+'</option>')
					}
					else
					{
						$("#fieldTypeCampoAdded_"+itemName).val(objectValueType);
					}
					
				}
					
				
				if(readOnly)
				{
					$("#objectValueCampoAdded_"+itemName).attr("disabled", true);
					$("#fieldTypeCampoAdded_"+itemName).attr("disabled", true);		
				}
			
			}
			else if (objectType=='url')
			{						
				$("#capaTipoSimpleCampoAdded_"+itemName).hide();
				$("#capaTipoURLCampoAdded_"+itemName).show();
								
				$("#objectValueURLCampoAdded_"+itemName).val(objectValue);	
				
				
			
				//console.log($("#urlCampoAdded_"+itemName));
				
				$("#objectTypeUrlCampoAdded_"+itemName).prop("checked", true);		
				
				if(readOnly)
				{
					$("#objectValueURLCampoAdded_"+itemName).attr("disabled", true);
				}		
				
			}
			else if (objectType=='blank')
			{
				//console.log("Soy un nodo");
				
				$("#capaTipoSimpleCampoAdded_"+itemName).hide();
				$("#capaNodoBlancoCampoAdded_"+itemName).show();
				
				$("#nodeIdCampoAdded_"+itemName).val(nodeId);
				
				
				
				if (nodeType.indexOf(":")>0)
				{
					var res = nodeType.split(":");
					predicatePrefix=res[0];
					predicateURL=res[1];
					
					$("#prefixNodePredicateTypeCampoAdded_"+itemName)
					    .find('option')
					    .remove()
					    .end()
					    .append('<option value="'+predicatePrefix+'">'+predicatePrefix+'</option>')
					    .val(predicatePrefix)
					;
				}else{
					$("#prefixNodePredicateTypeCampoAdded_"+itemName)
				    .find('option')
				    .remove()
				    .end();
				    
				}
				$("#urlNodePredicateTypeCampoAdded_"+itemName).val(predicateURL);	
			
			
				
				
				if (nodeProperty.indexOf(":")>0)
				{
					var res = nodeProperty.split(":");
					predicatePrefix=res[0];
					predicateURL=res[1];
					
					$("#prefixNodePredicateCampoAdded_"+itemName)
					    .find('option')
					    .remove()
					    .end()
					    .append('<option value="'+predicatePrefix+'">'+predicatePrefix+'</option>')
					    .val(predicatePrefix)
					;
				}else{
					$("#urlNodePredicateCampoAdded_"+itemName)
				    .find('option')
				    .remove()
				    .end();
				    
				}
				
				$("#urlNodePredicateCampoAdded_"+itemName).val(predicateURL);
				
				
				
				
				$("#objectTypeBlanckCampoAdded_"+itemName).prop("checked", true);			
				
				if(readOnly)
				{
					$("#nodeIdCampoAdded_"+itemName).attr("disabled", true);
					
					$("#urlNodePredicateTypeCampoAdded_"+itemName).attr("disabled", true);
					$("#prefixNodePredicateTypeCampoAdded_"+itemName).attr("disabled", true);
				
					$("#urlNodePredicateCampoAdded_"+itemName).attr("disabled", true);
					$("#prefixNodePredicateCampoAdded_"+itemName).attr("disabled", true);
				}		
				
			}
			
			elementsAdded.push("fieldset"+SUFFIX+itemName);
			
			
		}
		
		function drawEmptyField(itemName)
		{	
			var SUFFIX='CampoAdded_';	
				
			var content=$("#fieldsetCampoZero").html();
			content="<fieldset id='fieldsetCampoZero'>"+content+"</fieldset>";				
			while (content.indexOf("CampoZero")>=0)
			{
				content=content.replace("CampoZero",SUFFIX+itemName);		
			}	
			
			while (content.indexOf("atributoValue")>=0)
			{
				content=content.replace("atributoValue",itemName);
			}
					
			$("#relaciones").append(content);
			
			
			elementsAdded.push("fieldset"+SUFFIX+itemName);
			
		}
		
		function addPrefixes()
		{
			var selectIds = new Array();
			
			$('select[id^="prefixTypeCampoAdded_"]').each(function() {				
			  selectIds.push($( this ).attr('id'));
			});
			
			$('select[id^="prefixNodePredicateTypeCampoAdded_"]').each(function() {
			  selectIds.push($( this ).attr('id'));
			});
			
			$('select[id^="prefixNodePredicateCampoAdded_"]').each(function() {
			  selectIds.push($( this ).attr('id'));
			});
			
			
			for (var i = 0; i < selectIds.length; i++) {
			  var selectedOp=$( "#"+ selectIds[i]).val();			 
			  copyOptions("prefixType",selectIds[i]);
			  $( "#"+ selectIds[i]).val(selectedOp);
			} 
			
			copyOptions("prefixType","prefixTypeCampoZero");	
			$( "#prefixTypeCampoZero").val(-1);
			
			
		}
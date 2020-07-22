
var contextParam="";
var methodParam="";
var runningParam="";
var startDateParam="";
var startTimeParam="";
var finishDateParam="";
var finishTimeParam="";

$(document).ready(function()
{
	console.log('ready');
	
	var script_tag = document.getElementById('taskScript')
	methodParam = script_tag.getAttribute("data-method");
	runningParam = script_tag.getAttribute("data-running");
	contextParam = script_tag.getAttribute("data-context");
	startDateParam = script_tag.getAttribute("data-startDate");
	startTimeParam = script_tag.getAttribute("data-startTime");
	finishDateParam = script_tag.getAttribute("data-finishDate");
	finishTimeParam = script_tag.getAttribute("data-finishTime");

	
	// Cambiamos la ruta de la imagen del calendario
	$( ".calendar" ).datepicker( "option", "buttonImage", contextParam+"/resources/ayre-assets/images/calendar.png" );			
	
	//Hacemos scroll automáticamente cuando no hay parámetros (al inicio no)					
	if (methodParam!='GET')
	{
		$("html, body").animate({ scrollTop: 750 }, 0);
	}
				
	$('#timeStart').timepicker({
	    timeFormat: 'HH:mm',
	    interval: 30,			    
	    defaultTime: startTimeParam,			    
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true
	});
	
	$('#timeFinish').timepicker({
	    timeFormat: 'HH:mm',
	    interval: 30,			    
	    defaultTime: finishTimeParam,			    
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true
	});
	
	
	
	$("#start").datepicker().datepicker('setDate', startDateParam);
	$("#finish").datepicker().datepicker('setDate', finishDateParam);			
			
});
		
function checkProcess(code, data)
{
	//console.log(code);
	//console.log(code.id);
	var theURL=contextParam+"/task/"+data.id;
	$.ajax({
		  url: theURL  
	})
	.done(function( data ) {			     
	     
	      if (data.finish!=null)
	      {
	    	  //console.log("finish: "+data.finish);
	    	  $("#"+code+"Icon").removeClass('icon-exchange'); 
			  $("#"+code+"Icon").addClass('icon-play'); 
	      }
	      else
	      {
	    	  //console.log("Search Again")
	    	  setTimeout(function(){ checkProcess(code, data); }, 5000);	 
	      }			     		      
	});
	
}
		
function runProcess(code)
{
	var theURL=contextParam+"/process/"+code;
	$.ajax({
		  url: theURL,
		  beforeSend: function(  ) {
			$("#"+code+"Icon").removeClass('icon-play'); 
			$("#"+code+"Icon").addClass('icon-exchange'); 
		  }				  
	})
	.done(function( data ) {
	      //Codigo para controlar el cambio de estado de la tarea
	      if (data.status==runningParam) {
	    	  $("#"+code+"Icon").removeClass('icon-exchange'); 
			  $("#"+code+"Icon").addClass('icon-play'); 
		  } else {
			setTimeout(function() {
				checkProcess(code, data);
			}, 5000);
		}
	}).fail( function( jqXHR, textStatus, errorThrown ) {
    	//console.log( jqXHR );
		console.log( textStatus );
		console.log( errorThrown );
	});
}
		
function clearForm()
{
	
	$("#query").val(-1);
	$("#status").val(-1);
	$("#mode").val(-1);
	$("#start").val('');
	$("#finish").val('');
	$("#timeStart").val('00:00');
	$("#timeFinish").val('23:59');
}
		
function validateForm()
{	
	var validated=true;
	
	var validationArray = new Array();			
	validationArray.push("#start");		
	validationArray.push("#finish");		
	validationArray.push("#timeStart");		
	validationArray.push("#timeFinish");	
	
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
			}else{
				$(controlActual).addClass("okField");				
			}
		}		
	}
	
	if (validated)
	{	
		$("#busquedaForm").submit();
	}
}
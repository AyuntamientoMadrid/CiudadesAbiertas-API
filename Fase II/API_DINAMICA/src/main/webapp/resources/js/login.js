var usernameId="#username";
var passwordId="#password"; 

function validateForm()
 {
	console.log("validateForm");	
	$(usernameId).removeClass("errorField");
	$(passwordId).removeClass("errorField");
	if (($(usernameId).val().length<4) || ($(usernameId).val().length>45))
	{		
		$(usernameId).addClass("errorField");		
	 	return false;
 	}
	if (($(passwordId).val().length<6) || ($(passwordId).val().length>45))
	{
		$(passwordId).addClass("errorField");
	 	return false;
 	}
	console.log("isValid");
	return true;
 }
 

/*
if (($(usernameId).val()!="") && ($(passwordId).val()!=""))
{
	$("#buttonIn").click();
}
*/

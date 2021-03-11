
function geoquery()
{
	addParamToForm();
		
	addParamToForm();
		
	$("#summary").val("Búsqueda geografica a partir de un punto (x e y) y un radio de búsqueda");
		
	var sqlText="select id, title, municipioId, xETRS89, yETRS89, distance from (\n"+
		"  select \n"+
		"  id, \n"+
		"  title, \n"+
        "  municipio_id as municipioId, \n"+
        "  x_etrs89 as xETRS89, \n"+
        "  y_etrs89 as yETRS89, \n"+
		"  SQRT((x_etrs89-xEtrs89P)*(x_etrs89-xEtrs89P)+(y_etrs89-yEtrs89P)*(y_etrs89-yEtrs89P)) as distance \n"+                      
		"  from table \n"+
		") as consultaGeo \n"+                     
		"where \n"+
		"	distance<=metersP \n"+             
		"order by \n"+
		"	distance asc";
				
	$("#texto").val(sqlText);
	
	$("input[name='code0']").val("xEtrs89P");
	$("input[name='type0']").val("number");
	$("input[name='description0']").val("Coordenada X del punto (en formato ETRS89)");
	$("input[name='example0']").val("441201.60999147");
	
	$("input[name='code1']").val("yEtrs89P");
	$("input[name='type1']").val("number");
	$("input[name='description1']").val("Coordenada Y del punto (en formato ETRS89)");
	$("input[name='example1']").val("4479589.52997752");
	
	$("input[name='code2']").val("metersP");
	$("input[name='type2']").val("number");
	$("input[name='description2']").val("Radio de búsqueda en metros");
	$("input[name='example2']").val("500");	
	
}

function groupby()
{
	addParamToForm();
		
	addParamToForm();
	
	addParamToForm();
	
	$("#summary").val("Búsqueda agrupada");
		
	var sqlText="Select \n"+
		"    fieldsP  \n"+
		"from \n"+
		"    (SELECT \n"+
		"        id, \n"+
		"        title, \n"+
		"        area_id as areaId, \n"+
		"        area_title as areaTitle, \n"+
		"        importe \n"+        
		"    FROM \n"+
		"        table) as consultaGroupBy\n"+
		"where \n"+
		"    whereP\n"+
		"group by \n"+
		"    groupP\n"+
		"having \n"+
		"    havingP";            
				
	$("#texto").val(sqlText);
	
	$("input[name='code0']").val("fieldsP");
	$("input[name='type0']").val("text");
	$("input[name='description0']").val("Campos por que se desean solicitar, incluyendo el campo con la operacion agrupada");
	$("input[name='example0']").val("title,count(id) as contador");
	
	$("input[name='code1']").val("whereP");
	$("input[name='type1']").val("text");
	$("input[name='description1']").val("Condicion por la que filtrar campos que no tienen que ver la agrupación");
	$("input[name='example1']").val("importe>100");
	
	$("input[name='code2']").val("groupP");
	$("input[name='type2']").val("text");
	$("input[name='description2']").val("Campo o campos por los que realizar la agrupación");
	$("input[name='example2']").val("title");
	
	$("input[name='code3']").val("havingP");
	$("input[name='type3']").val("text");
	$("input[name='description3']").val("Condicion por la que filtrar campos de la agrupación");
	$("input[name='example3']").val("count(id)>10");
}



function distinct()
{
	addParamToForm();
	
	$("#summary").val("Búsqueda para obtener los distintos valores de un atributo");
	
	var sqlText="select \n"+
		"	distinct fieldP \n"+
		"from \n"+
		"	(  SELECT \n"+
		"		id, \n"+
		"		title, \n"+
		"		area_id as areaId, \n"+
		"		area_title as areaTitle, \n"+
		"		... \n"+
		"	FROM \n"+
		"		table) as consultaDistinct";
		
	$("#texto").val(sqlText);
	
	$("input[name='code0']").val("fieldP");
	$("input[name='type0']").val("text");
	$("input[name='description0']").val("Campo del que se quieren obtener todos sus valores");
	$("input[name='example0']").val("title");
}

function latlon()
{
	$("#summary").val("Búsqueda con coordenadas");
	
	var sqlText="SELECT \n"+
		"	... \n"+
		"	x_etrs89 AS xETRS89, \n"+
		"	y_etrs89 AS yETRS89  \n"+
		"FROM \n"+
		"	tabla" ;
		
	$("#texto").val(sqlText);
	
	
}

/**
 * 
 */
package org.ciudadesabiertas.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author localidata
 *
 */
public class Datasets
{
	private Map<String,Boolean> datasetsToLoad=new HashMap<String,Boolean>();
	
	public Datasets()
	{
		super();
		datasetsToLoad=new HashMap<String,Boolean>();		
		datasetsToLoad.put("agendaCultural", false);
		datasetsToLoad.put("alojamiento", false);
		datasetsToLoad.put("aparcamiento", false);
		datasetsToLoad.put("avisoQuejaSugerencia", false);
		datasetsToLoad.put("calidadAireEstacion", false);
		datasetsToLoad.put("calidadAireObservacion", false);
		datasetsToLoad.put("calidadAireSensor", false);
		datasetsToLoad.put("callejeroPortal", false);
		datasetsToLoad.put("callejeroTramoVia", false);
		datasetsToLoad.put("callejeroVia", false);
		datasetsToLoad.put("equipamiento", false);
		datasetsToLoad.put("instalacionDeportiva", false);
		datasetsToLoad.put("localComercialAgrupacionComercial", false);
		datasetsToLoad.put("localComercialLicenciaActividad", false);
		datasetsToLoad.put("localComercial", false);
		datasetsToLoad.put("localComercialTerraza", false);
		datasetsToLoad.put("monumento", false);
		datasetsToLoad.put("organigrama", false);
		datasetsToLoad.put("puntoWifi", false);
		datasetsToLoad.put("puntoInteresTuristico", false);
		datasetsToLoad.put("subvencion", false);
		datasetsToLoad.put("tramite", false);
		
		datasetsToLoad.put("dsd", false);
		datasetsToLoad.put("dsdDimension", false);
		datasetsToLoad.put("dsdDimensionValue", false);
		datasetsToLoad.put("dsdMeasure", false);
		
		datasetsToLoad.put("padronCuboEdad", false);
		datasetsToLoad.put("padronCuboEstudios", false);
		datasetsToLoad.put("padronCuboIndicadores", false);
		datasetsToLoad.put("padronCuboNacionalidad", false);
		datasetsToLoad.put("padronCuboProcedencia", false);

		datasetsToLoad.put("territorioPais", false);
		datasetsToLoad.put("territorioAutonomia", false);
		datasetsToLoad.put("territorioProvincia", false);
		datasetsToLoad.put("territorioMunicipio", false);
		datasetsToLoad.put("territorioDistrito", false);
		datasetsToLoad.put("territorioBarrio", false);
		datasetsToLoad.put("territorioSeccion", false);

		datasetsToLoad.put("agendaMunicipalEvento", false);
		datasetsToLoad.put("agendaMunicipalDocumento", false);
		datasetsToLoad.put("agendaMunicipalRolEvento", false);

		datasetsToLoad.put("contratosAward", false);
		datasetsToLoad.put("contratosItem", false);
		datasetsToLoad.put("contratosLot", false);
		datasetsToLoad.put("contratosLotRelItem", false);
		datasetsToLoad.put("contratosOrganization", false);
		datasetsToLoad.put("contratosProcess", false);
		datasetsToLoad.put("contratosTender", false);
		datasetsToLoad.put("contratosTenderRelItem", false);
	}
	
	public List<String> getDatasetNames()
	{			
		List<String> keys = new ArrayList<String>(datasetsToLoad.keySet());		
		return keys;
	}
	
	public boolean getDataset(String name) throws Exception
	{
		if (datasetsToLoad.containsKey(name)==false)
		{
			throw new Exception("Dataset not Found: "+name);
		}
		return datasetsToLoad.get(name);		
	}
	
	public boolean setDataset(String name, boolean status) throws Exception
	{
		if (datasetsToLoad.containsKey(name)==false)
		{
			throw new Exception("Dataset not Found: "+name);
		}
		return datasetsToLoad.put(name,status);
	}

	@Override
	public String toString()
	{
		String response="";
		List<String> keys =	new ArrayList<String>(datasetsToLoad.keySet());
		
		for (String key:keys)
		{
			response+=key+":"+datasetsToLoad.get(key)+"\n";
		}
		
		return "Datasets [datasetsToLoad=" + datasetsToLoad + "]";
	}
	
	
	
}

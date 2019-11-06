/**
 * 
 */
package org.ciudadesabiertas.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
			throw new Exception("Dataset not Found");
		}
		return datasetsToLoad.get(name);		
	}
	
	public boolean setDataset(String name, boolean status) throws Exception
	{
		if (datasetsToLoad.containsKey(name)==false)
		{
			throw new Exception("Dataset not Found");
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

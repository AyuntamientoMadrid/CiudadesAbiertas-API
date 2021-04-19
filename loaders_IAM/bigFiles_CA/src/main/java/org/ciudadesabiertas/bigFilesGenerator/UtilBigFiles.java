package org.ciudadesabiertas.bigFilesGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.ciudadesAbiertas.rdfGeneratorZ.Formato;
import org.ciudadesAbiertas.rdfGeneratorZ.Peticion;
import org.ciudadesAbiertas.rdfGeneratorZ.TransformadorBasicoRdf;
import org.ciudadesabiertas.dataset.model.CuboEdad;
import org.ciudadesabiertas.dataset.model.CuboEdadGrupoQuinquenal;
import org.ciudadesabiertas.dataset.model.CuboEstudios;
import org.ciudadesabiertas.dataset.model.CuboIndicadores;
import org.ciudadesabiertas.dataset.model.CuboNacionalidad;
import org.ciudadesabiertas.dataset.model.CuboPaisNacimiento;
import org.ciudadesabiertas.dataset.model.CuboProcedencia;
import org.ciudadesabiertas.model.ICallejero;
import org.ciudadesabiertas.model.ICallejeroVia;
import org.ciudadesabiertas.model.IConvenio;
import org.ciudadesabiertas.model.IEquipamiento;
import org.ciudadesabiertas.model.IGeoModelGeometry;
import org.ciudadesabiertas.model.IGeoModelXY;
import org.ciudadesabiertas.model.IGestionadoPor;
import org.ciudadesabiertas.model.ITrafico;
import org.ciudadesabiertas.model.ITramoIncidencia;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.CoordinateTransformer;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
import org.ciudadesabiertas.utils.converters.CustomerMappingStrategy;
import org.jasypt.util.text.BasicTextEncryptor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class UtilBigFiles
{
	private static final String PLACEHOLDER = "placeholder";

	private static final Logger log = LoggerFactory.getLogger(UtilBigFiles.class);
	
	public static ObjectMapper jsonMapper;
	
	//private static CSVConverter csvConverter;
	
	private static BasicTextEncryptor textEncryptor;
	
	private static CloseableHttpClient httpClient;
	
	private static JSONParser JSONParser=new JSONParser();
	
	//Parser utilizado solo para pretty print
	private static JsonParser prettyPrintParser = new JsonParser();
	//Gson utilizado solo para pretty print
	private static Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

	
	
	static {
		jsonMapper = new ObjectMapper();
		jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		// Fechas sin timestamps
		jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// Y con este formato
		jsonMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
		jsonMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
		
		

		textEncryptor=new BasicTextEncryptor();												
		textEncryptor.setPassword(Constants.SEMILLA_PROPERTIES);

		
	}
	
	
	public static <T> void toJSON(List<T> result,String fileName)
	{
		
		
		try
		{
			UtilBigFiles.jsonMapper.writeValue(new File(fileName), result);
		} catch (JsonGenerationException e)
		{
			log.error("Error generating JSON",e);			
		} catch (JsonMappingException e)
		{
			log.error("Error generating JSON",e);
		} catch (IOException e)
		{
			log.error("Error generating JSON",e);
		}catch (Exception e)
		{
			log.error("Error generating JSON",e);
		}
	}

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> void toCSV(List<T> result, String fileName) throws IOException  {
	 
		//byte[] bomBytes = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		
		Writer writer = Files.newBufferedWriter(Paths.get(fileName));
		//writer.write(bomBytes);
		
		CustomerMappingStrategy mappingStrategy = new CustomerMappingStrategy();
	    mappingStrategy.setType(result.get(0).getClass());
	    StatefulBeanToCsv<T> beanToCsv =
                new StatefulBeanToCsvBuilder(writer)
                			/*.withSeparator(';')*/
                          .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                          .withMappingStrategy(mappingStrategy)
                          .withEscapechar(CSVWriter.NO_ESCAPE_CHARACTER)
                          .withLineEnd(CSVWriter.RFC4180_LINE_END)                            
                          .build();
      try {
          beanToCsv.write(result);
          writer.close();
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
	 
	 
	  
	    
	}
	
	public static <T> void toRDF(List<T> listado, String fileName, String URIBase, String context)  {
		
	
		log.info("[toRDF] [listado] [fileName:"+fileName+"] [URIBase:"+URIBase+"] [context:"+context+"]");
		TransformadorBasicoRdf rdfT=new TransformadorBasicoRdf(URIBase,context);
			
		if (listado.size()>0)
		{
			RDFModel firstObject=(RDFModel) listado.get(0);
			Map<String, String> prefixesRDF = firstObject.prefixes();
			if ((prefixesRDF!=null)&&(prefixesRDF.size()>0))
			{
				rdfT.setPrefixes(prefixesRDF);
			}
		}
		
		StringBuilder respuesta=new StringBuilder();
		
		Result<T> result=new Result<T>(); 
		
		result.setRecords(listado);
	
		Peticion peticion=new Peticion();
		Formato f=new Formato();
		f.setExtension("ttl");
		peticion.setFormato(f);
		
		
		boolean primero=true;
		String prefijo="";
		
			
		try
		{
			rdfT.transformarObjeto(respuesta,result, peticion, primero, prefijo);
			
			OutputStreamWriter writer = new OutputStreamWriter(
				    new FileOutputStream(fileName),
				    Charset.forName("UTF-8").newEncoder() 
				);
			
		
			//OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody(), charset);    			
			writer.append(respuesta);
			writer.close();
		}
		catch (Exception e)
		{
			log.error("Error generating RDF",e);		
		}
	
	}
	
	
	static void deleteFilesInFolder(String path)
	{
		deleteFilesInFolder(path,"");
	}
	
	static void deleteFilesInFolder(String path, String extension)
	{
		int counter=0;
		File dir = new File(path);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			   for (File child : directoryListing) {
				   if (child.getName().equals(PLACEHOLDER)==false)
				   {
					   if (Util.validValue(extension))
					   {
						   if (child.getName().toLowerCase().endsWith(extension.toLowerCase()))
						   {
							   child.delete();
							   counter++;
						   }
					   }
					   else
					   {
						   child.delete();
						   counter++;
					   }
				   }
			   }
		}
		if (counter>0)
		{
			log.info(counter+" files deleted in "+path);
		}
	}
	
	
	static void deleteDirectoryInFolder(String path)
	{
		int counter=0;
		File dir = new File(path);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			   for (File child : directoryListing) {
				   if (child.isDirectory())
				   {
					   deleteFilesInFolder(child.getAbsolutePath());
					   if (child.delete()) {
						   counter++;
					   }
				   }
			   }
		}
		if (counter>0)
		{
			log.info(counter+" directory deleted in "+path);
		}
	}
	
	static void deleteAllInFolder(String path)
	{
		deleteFilesInFolder(path);
		deleteDirectoryInFolder(path);
	}
	
	public static String checkAndSetEncodedProperty(String propertyValue)
	{		
		String decrypted="";
		if (propertyValue!=null) {
			if  (textEncryptor ==null)
				{
					textEncryptor=new BasicTextEncryptor();												
					textEncryptor.setPassword(Constants.SEMILLA_PROPERTIES);
				}
			
			if (propertyValue.startsWith(Constants.PREFIX_PROPERTY_ENCODED))
			{	    	
				String encryptedText=propertyValue;
				encryptedText=encryptedText.substring(Constants.PREFIX_PROPERTY_ENCODED.length(),encryptedText.length()-1);
				decrypted=textEncryptor.decrypt(encryptedText);
			}
			else
			{
				decrypted=propertyValue;
			}		 
		}
		return decrypted;
	}
	
	

	public static void generaDataCubeInfo(List listado)
	{	
		//control para los objetos que o extienden de GeoModel		
		if (listado!=null && listado.size()>0) {
			for (int i=0;i<listado.size();i++)		
			{
				
				try {
					if (listado.get(i) instanceof CuboEdadGrupoQuinquenal ) {
						((CuboEdadGrupoQuinquenal)listado.get(i)).asignaCubo();
						((CuboEdadGrupoQuinquenal)listado.get(i)).asignaDSD();
						if (i==0)
							log.info("[generaDataCubeInfo] [listado] is a object CuboEdadGrupoQuinquenal");
						
					}else if (listado.get(i) instanceof CuboEdad) {
						((CuboEdad)listado.get(i)).asignaCubo();
						((CuboEdad)listado.get(i)).asignaDSD();
						if (i==0)
							log.info("[generaDataCubeInfo] [listado] is a object CuboEdad");
						
					}else if (listado.get(i) instanceof CuboPaisNacimiento) {
						((CuboPaisNacimiento)listado.get(i)).asignaCubo();
						((CuboPaisNacimiento)listado.get(i)).asignaDSD();
						if (i==0)
							log.info("[generaDataCubeInfo] [listado] is a object CuboPaisNacimiento");
						
					}else if (listado.get(i) instanceof CuboEstudios) {
						((CuboEstudios)listado.get(i)).asignaCubo();
						((CuboEstudios)listado.get(i)).asignaDSD();
						if (i==0)
							log.info("[generaDataCubeInfo] [listado] is a object CuboEstudios");
						
					}else if (listado.get(i) instanceof CuboIndicadores) {
						((CuboIndicadores)listado.get(i)).asignaCubo();
						((CuboIndicadores)listado.get(i)).asignaDSD();
						if (i==0)
							log.info("[generaDataCubeInfo] [listado] is a object CuboIndicadores");
					}else if (listado.get(i) instanceof CuboNacionalidad) {
						((CuboNacionalidad)listado.get(i)).asignaCubo();
						((CuboNacionalidad)listado.get(i)).asignaDSD();
						if (i==0)
							log.info("[generaDataCubeInfo] [listado] is a object CuboNacionalidad");
					}else if (listado.get(i) instanceof CuboProcedencia) {
						((CuboProcedencia)listado.get(i)).asignaCubo();
						((CuboProcedencia)listado.get(i)).asignaDSD();
						if (i==0)
							log.info("[generaDataCubeInfo] [listado] is a object CuboProcedencia");
					}else {
						//NADA
					}
				}catch (Exception e) {
					if (i==0)
						log.info("[generaDataCubeInfo] [listado] No is a object DataCubeModel");
				}//Fin control
			}					
			
		}//fin listado
		
	}
	
	public static void generaCoordenadasAll(String source, String srId, List listado)
	{	
		//control para los objetos que o extienden de GeoModel
		boolean isTransformacionXY = true;
		if (isObjectGeoModel(listado)) {
			
			String target = StartVariables.SRID_LAT_LON_APP;
			//Comprobamos el srId si es para x e y o Lat y lon
			if (validValue(srId) && CoordinateTransformer.comprobarSrIdLatLon(srId)) {
				target = srId;
				isTransformacionXY = false;
			}
			//1 obtenemos lat y lon Siempre se genera esta transformación, activamos true, para que permita la transformacion
			CoordinateTransformer ct1 = new CoordinateTransformer(source,target);
						
			for (int i=0;i<listado.size();i++)		
			{
				IGeoModelXY geomodel=(IGeoModelXY) listado.get(i);
				if ((validValue(geomodel.getX()))&&(validValue(geomodel.getY())))
				{
					BigDecimal x = geomodel.getX();
					BigDecimal y = geomodel.getY();		
					//Alternamos posición X e Y
					double[] transformCoordinates = ct1.transformCoordinates(y.doubleValue(), x.doubleValue());	
					//Alternamos la salida del vector para Lat y lon
					geomodel.setLatitud(new BigDecimal(transformCoordinates[1]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
					geomodel.setLongitud(new BigDecimal(transformCoordinates[0]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
				}
			}
			

			
			//Es necesario comprobar si debe aplicar transformación a las X e Y ya que puede ser que no sea el caso
			if (validValue(srId) && isTransformacionXY) {
				// 2 transformamos x e y segun el parametro srId y las coordenadas lat / long
				// generadas anteriormente.
				CoordinateTransformer ct2 = new CoordinateTransformer(source, srId);

				for (int i = 0; i < listado.size(); i++) {
					IGeoModelXY geomodel = (IGeoModelXY) listado.get(i);
					if ((validValue(geomodel.getX()))&&(validValue(geomodel.getY())))
					{
						BigDecimal x = geomodel.getX();
						BigDecimal y = geomodel.getY();		
						
						double[] transformCoordinates = ct2.transformCoordinates(x.doubleValue(), y.doubleValue());	
						
						geomodel.setX(new BigDecimal(transformCoordinates[1]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
						geomodel.setY(new BigDecimal(transformCoordinates[0]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
					}
				}
			}//fin if validValue(srId)
			
		}//fin if isGeomodel 
		
		//GeoModelGeometry
		if (isObjectGeoModelGeometry(listado)) {
			  
			  
		  	String target = StartVariables.SRID_LAT_LON_APP;
			//Comprobamos el srId si es para x e y o Lat y lon
			if (validValue(srId) && CoordinateTransformer.comprobarSrIdLatLon(srId)) {
				target = srId;
				isTransformacionXY = false;
			}
			//1 obtenemos lat y lon Siempre se genera esta transformación, activamos true, para que permita la transformacion
			CoordinateTransformer ct1 = new CoordinateTransformer(source,target);
						
			for (int i=0;i<listado.size();i++)		
			{
				IGeoModelGeometry geomodel=(IGeoModelGeometry) listado.get(i);
		  
				String hasGeometry=geomodel.getGeometry();
				
				if (validValue(hasGeometry))
				{
					JSONObject geoObj=Util.stringToJSONObject(hasGeometry);
					if (geoObj!=null)
					{
						JSONArray features=(JSONArray) geoObj.get("features");
						if (features!=null)
						{
							JSONObject actualFeature=(JSONObject)features.get(0);
							if ( isTransformacionXY)
							{
    	  						if (actualFeature!=null)
    	  						{
    	  						  JSONObject geometry=(JSONObject)actualFeature.get("geometry");
    	  						  JSONArray coordinates=(JSONArray)geometry.get("coordinates");
    	  						  JSONArray coordinatesTransformed=new JSONArray();
    	  						  for (int j=0;j<coordinates.size();j++)
    	  						  {
    	  							JSONArray vectorActual=(JSONArray) coordinates.get(j);
    	  							JSONArray vectorActualTransformed=new JSONArray();
    	  							for (int k=0;k<vectorActual.size();k++)
    	  							{
    	  							  JSONArray coordinate=(JSONArray) vectorActual.get(k);
    	  							  JSONArray coordinateTransformed=new JSONArray();
    	  							  
    	  							  //Alternamos posición X e Y
    	  							  double[] transformCoordinates = ct1.transformCoordinates((double)coordinate.get(1), (double)coordinate.get(0));	
    	  							  //Alternamos la salida del vector para Lat y lon
    	  							  coordinateTransformed.add(new BigDecimal(transformCoordinates[0]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
    	  							  coordinateTransformed.add(new BigDecimal(transformCoordinates[1]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));	
    	  							  
    	  							  vectorActualTransformed.add(coordinateTransformed);
    	  							}							
    	  							coordinatesTransformed.add(vectorActualTransformed);							
    	  						  }
    	  						  geometry.put("coordinates", coordinatesTransformed);
    	  						  actualFeature.put("geometry",geometry);
    	  						  						  
    	  						  geomodel.setGeometry(actualFeature.toString());
    	  						  geomodel.setHasGeometry(actualFeature);
    	  						}
							}else {  	  						  						  
  	  						  	geomodel.setGeometry(actualFeature.toString());
  	  						  	geomodel.setHasGeometry(actualFeature);
							}
						}
					}
				}
			}//fin for
		 
		}//fin GeoModelGeometry
		
		if(isObjectITrafico(listado)) 
		{
			String target = StartVariables.SRID_LAT_LON_APP;
			//Comprobamos el srId si es para x e y o Lat y lon
			if (validValue(srId) && CoordinateTransformer.comprobarSrIdLatLon(srId)) {
				target = srId;
				isTransformacionXY = false;
			}
			//1 obtenemos lat y lon Siempre se genera esta transformación, activamos true, para que permita la transformacion
			CoordinateTransformer ct1 = new CoordinateTransformer(source,target);
						
			for (int i=0;i<listado.size();i++)		
			{
				ITrafico iTrafico = (ITrafico) listado.get(i);
				if ((validValue(iTrafico.getFinX()))&&(validValue(iTrafico.getFinY())))
				{
					BigDecimal x = iTrafico.getFinX();
					BigDecimal y = iTrafico.getFinY();		
					//Alternamos posición X e Y
					double[] transformCoordinates = ct1.transformCoordinates(y.doubleValue(), x.doubleValue());	
					//Alternamos la salida del vector para Lat y lon
					iTrafico.setFinLatitud(new BigDecimal(transformCoordinates[1]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
					iTrafico.setFinLongitud(new BigDecimal(transformCoordinates[0]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
				}
			}
			

			
			//Es necesario comprobar si debe aplicar transformación a las X e Y ya que puede ser que no sea el caso
			if (validValue(srId) && isTransformacionXY) {
				// 2 transformamos x e y segun el parametro srId y las coordenadas lat / long
				// generadas anteriormente.
				CoordinateTransformer ct2 = new CoordinateTransformer(source, srId);

				for (int i = 0; i < listado.size(); i++) {
					ITrafico iTrafico = (ITrafico) listado.get(i);
					if ((validValue(iTrafico.getFinX()))&&(validValue(iTrafico.getFinY())))
					{
						BigDecimal x = iTrafico.getFinX();
						BigDecimal y = iTrafico.getFinY();		
						
						double[] transformCoordinates = ct2.transformCoordinates(x.doubleValue(), y.doubleValue());	
						
						iTrafico.setFinX(new BigDecimal(transformCoordinates[1]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
						iTrafico.setFinY(new BigDecimal(transformCoordinates[0]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
					}
				}
			}//fin if validValue(srId)
		//fin if isObjectDobleGeoModel	
		}
		
	}
	
	
	public static boolean validValue(String value)
	{
		if (value != null && !"".equals(value))
			return true;
		return false;
	}
	
	public static boolean validValue(Boolean value)
	{
		if (value != null )
			return true;
		return false;
	}
	
	

	public static boolean validValue(BigDecimal value)
	{
		if (value != null)
			return true;
		return false;
	}

	public static boolean validValue(Date value)
	{
		if (value != null )
			return true;
		return false;
	}
	
	public static boolean validValue(Integer value)
	{
		if (value != null )
			return true;
		return false;
	}
	
	public static boolean validValue(Object value)
	{
		if (value != null )
			return true;
		return false;
	}
	
	/**
	 * Metodo para ver si los objetos de un listado extienden de Geomodel.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectGeoModel(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				IGeoModelXY aux=(IGeoModelXY) listado.get(0);
				result = true;
				log.debug("[isObjectGeoModel] [listado] is a object GeoModel");
			}catch (Exception e) {
				log.debug("[isObjectGeoModel] [listado] No is a object GeoModel");
				result = false;
			}//Fin control
		}
		return result;
	}
	
	/**
	 * Metodo para ver si los objetos de un listado extienden de Geomodel.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectGeoModelGeometry(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				IGeoModelGeometry aux=(IGeoModelGeometry) listado.get(0);
				result = true;
				log.debug("[IGeoModelGeometry] [listado] is a object GeoModelGeometry");
			}catch (Exception e) {
				log.debug("[IGeoModelGeometry] [listado] No is a object GeoModelGeometry");
				result = false;
			}//Fin control
		}
		return result;
	}
	
	/**
	 * Metodo para ver si los objetos de un listado extienden de DobleGeomodel.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectITrafico(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				ITrafico aux=(ITrafico) listado.get(0);
				result = true;
				log.debug("[isObjectITrafico] [listado] is a object ITrafico");
			}catch (Exception e) {
				log.debug("[isObjectITrafico] [listado] No is a object ITrafico");
				result = false;
			}//Fin control
		}
		return result;
	}
	
	/**
	 * Metodo para ver si los objetos de un listado extienden de DobleGeomodel.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectITramoIncidencia(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				ITramoIncidencia aux=(ITramoIncidencia) listado.get(0);
				result = true;
				log.debug("[isObjectITramoIncidencia] [listado] is a object ITramoIncidencia");
			}catch (Exception e) {
				log.debug("[isObjectITramoIncidencia] [listado] No is a object ITramoIncidencia");
				result = false;
			}//Fin control
		}
		return result;
	}
	
	
	/**
	 * Metodo para ver si los objetos de un listado extienden de DobleGeomodel.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectICallejero(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				ICallejero aux=(ICallejero) listado.get(0);
				result = true;
				log.info("[isObjectICallejero] [listado] is a object isObjectICallejero");
			}catch (Exception e) {
				log.info("[isObjectICallejero] [listado] No is a object ICallejero");
				result = false;
			}//Fin control
		}
		return result;
	}
	
	/**
	 * Metodo para ver si los objetos de un listado extienden de DobleGeomodel.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectIEquipamiento(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				IEquipamiento aux=(IEquipamiento) listado.get(0);
				result = true;
				log.info("[isObjectIEquipamiento] [listado] is a object IEquipamiento");
			}catch (Exception e) {
				log.info("[isObjectIEquipamiento] [listado] No is a object IEquipamiento");
				result = false;
			}//Fin control
		}
		return result;
	}
	
	/**
	 * Metodo para ver si los objetos de un listado extienden de DobleGeomodel.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectICallejeroVia(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				ICallejeroVia aux=(ICallejeroVia) listado.get(0);
				result = true;
				log.info("[isObjectICallejeroVia] [listado] is a object ICallejeroVia");
			}catch (Exception e) {
				log.info("[isObjectICallejeroVia] [listado] No is a object ICallejeroVia");
				result = false;
			}//Fin control
		}
		return result;
	}
	
	/**
	 * Metodo para realizar la integración con callejero.
	 * @param listado
	 * @return
	 */
	public static void getInfoCallejero(List listado, boolean exiteDatasetCallejero) {
		
		if (listado!=null && listado.size()>0) {
			for (int i=0;i<listado.size();i++) {
				try {
					ICallejero aux=(ICallejero) listado.get(i);
					if (exiteDatasetCallejero) {
						if ( UtilBigFiles.validValue(aux.getPortalId())){
							aux.setStreetAddress(null);
							aux.setPostalCode(null);
						}
					}else {
						aux.setPortalIdIsolated(aux.getPortalId());
						aux.setPortalId(null);
					}
					
				}catch (Exception e) {
					log.info("[getInfoCallejero] [listado] No is a object ICallejero");
					
				}//Fin control
			}
		}
		
	}
	
	/**
	 * Metodo para realizar la integración con callejero.
	 * @param listado
	 * @return
	 */
	public static void getInfoCallejeroVia(List listado, boolean exiteDatasetCallejero) {
		
		if (listado!=null && listado.size()>0) {
			for (int i=0;i<listado.size();i++) {
				try {
					ICallejeroVia aux=(ICallejeroVia) listado.get(i);
					if (exiteDatasetCallejero) {
						if ( UtilBigFiles.validValue(aux.getIdVia())){
							aux.setTitleVia(null);
							aux.setTipoVia(null);
						}
					}else {
						aux.setIdViaIsolated(aux.getIdVia());
						aux.setIdVia(null);
					}
					
				}catch (Exception e) {
					log.info("[getInfoCallejero] [listado] No is a object ICallejero");
					
				}//Fin control
			}
		}
		
	}
	
	/**
	 * Metodo para realizar la integración con callejero.
	 * @param listado
	 * @return
	 */
	public static void getInfoITramoIncidencia(List listado, boolean exiteDatasetTraficoTramo) {
		
		if (listado!=null && listado.size()>0) {
			for (int i=0;i<listado.size();i++) {
				try {
					ITramoIncidencia aux=(ITramoIncidencia) listado.get(i);
					if (exiteDatasetTraficoTramo) {
						if ( Util.validValue(((ITramoIncidencia) aux).getIncidenciaEnTramo())){
							((ITramoIncidencia) aux).setIncidenciaTramoDescription(null);
							
						}
					}else {
						((ITramoIncidencia) aux).setIncidenciaEnTramoIdIsolated(((ITramoIncidencia) aux).getIncidenciaEnTramo());
						((ITramoIncidencia) aux).setIncidenciaEnTramo(null);
					}
					
				}catch (Exception e) {
					log.info("[getInfoCallejero] [listado] No is a object ICallejero");
					
				}//Fin control
				
			}
		}
		
	}
	
	
	/**
	 * Metodo para realizar la integración con callejero.
	 * @param listado
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static void getInfoEquipamiento(List listado, boolean exiteDatasetEquipamiento) {
		
		if (listado!=null && listado.size()>0) {
			for (int i=0;i<listado.size();i++) {
				try {
					IEquipamiento aux=(IEquipamiento) listado.get(i);
					if (exiteDatasetEquipamiento) {
						if ( UtilBigFiles.validValue(aux.getEquipamientoId())){
							aux.setEquipamientoTitle(null);
						}
					}else {
						aux.setEquipamientoIdIsolated(aux.getEquipamientoId());
						aux.setEquipamientoId(null);
					}
					
				}catch (Exception e) {
					log.info("[getInfoCallejero] [listado] No is a object ICallejero");
					
				}//Fin control
			}
		}
		
	}
	
	/**
	 * Metodo para ver si los objetos de un listado extienden de GestionadoPor.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectGestionadoPor(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				IGestionadoPor aux=(IGestionadoPor) listado.get(0);
				result = true;
				log.info("[IGestionadoPor] [listado] is a object IGestionadoPor");
			}catch (Exception e) {
				log.info("[IGestionadoPor] [listado] No is a object IGestionadoPor");
				result = false;
			}//Fin control
		}
		return result;
	}
	
	/**
	 * Metodo para realizar la integración con isObjectGestionadoPor.
	 * @param listado
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static void getGestionadoPor(List listado) {
		
		if (listado!=null && listado.size()>0) {
			for (int i=0;i<listado.size();i++) {
				try {
					IGestionadoPor aux=(IGestionadoPor) listado.get(i);
					// CMG Controlamos en la integración 
					if ( Util.validValue(aux.getGestionadoPorOrganization()) && aux.getGestionadoPorOrganization()) {
						aux.setDistritoId(null);
						aux.setDistritoTitle(null);

					}else if (Util.validValue(aux.getGestionadoPorDistrito()) && aux.getGestionadoPorDistrito()) {
						aux.setOrganizationId(null);
					}else {
						aux.setDistritoId(null);
						aux.setDistritoTitle(null);
						aux.setOrganizationId(null);
					}
					
				}catch (Exception e) {
					log.error("[getGestionadoPor] [listado] [ERROR:"+e.getMessage()+"]");
					
				}//Fin control
			}
		}
		
	}
	
	/**
	 * Metodo para ver si los objetos de un listado extienden de IConvenio.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectIConvenio(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				IConvenio aux=(IConvenio) listado.get(0);
				result = true;
				log.info("[IConvenio] [listado] is a object IConvenio");
			}catch (Exception e) {
				log.info("[IConvenio] [listado] No is a object IConvenio");
				result = false;
			}//Fin control
		}
		return result;
	}
	
	/**
	 * Metodo para realizar la integración con isObjectGestionadoPor.
	 * @param listado
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static void getIntegraConvenio(List listado) {
		
		if (listado!=null && listado.size()>0) {
			for (int i=0;i<listado.size();i++) {
				try {
					IConvenio aux=(IConvenio) listado.get(i);
					// CMG Controlamos en la integración 
					if (Util.validValue(aux.getInstrumentaId()))
					{
						aux.setInstrumentaTitle(null);
					}else {
								
						aux.setInstrumentaIdIsolated(aux.getInstrumentaId());						
						aux.setInstrumentaId(null);											
						
					}
					
					
				}catch (Exception e) {
					log.error("[getIntegraConvenio] [listado] [ERROR:"+e.getMessage()+"]");
					
				}//Fin control
			}
		}
		
	}
	
	public static CloseableHttpClient getClientWithoutValidationSSL() {
	  
	  CloseableHttpClient httpClient = null;
	  
	  try
	  {
	  TrustManager[] trustAllCerts = new TrustManager[] {
	       new X509TrustManager() {
	    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	        return null;
	    }
	    public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

	    public void checkServerTrusted(X509Certificate[] certs, String authType) {  }
	    }
	    };

	    SSLContext sc = SSLContext.getInstance("SSL");
	    sc.init(null, trustAllCerts, new SecureRandom());
	    httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(sc).build();
	  }
	  catch (Exception e)
	  {
		log.error("Error creating client without ssl validation",e);
	  }
	   
	   
	 

	  return httpClient;
	}
	
	
	
	public static InputStream readURL(String address) {
	 
	  try {
		if (httpClient == null) {
		  
		  httpClient=getClientWithoutValidationSSL();	  
		  
		  //httpClient = HttpClientBuilder.create().build();
		}

		
		
		HttpGet request = new HttpGet(address);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		int responseCode = response.getStatusLine().getStatusCode();
		if (responseCode == 200) {
		  InputStream inputStream = entity.getContent();
		  return inputStream;
		} else {
		  log.error("Status " + responseCode + " in URL: " + address);
		  return null;
		}
	  } catch (Exception e) {
		log.error("Error reading URL", e);
		return null;
	  }
	  
	}
	
	
	public static void readAndWriteURL(String address, String filename) {
	  try
	  {
	    	InputStream urlStream = UtilBigFiles.readURL(address);    				
	  	
	  	if (urlStream!=null)
	  	{
	  	  File targetFile = new File(filename);
	  	  FileUtils.copyInputStreamToFile(urlStream, targetFile);
	  	  log.info("Writted");
	  	}
	  }
	  catch  (Exception e)
	  {
	    log.error("Error writting File", e);
	  }
	}
	
	
	public static void sleep(int milliseconds) {
	  try {
	    Thread.sleep(milliseconds);
	  } catch (InterruptedException e) {
  	   log.error("Error in sleep",e);
	  }
	}
	
	
	public static JSONObject parseJSON(String content)
	{
	  JSONObject obj=null;
	  try {
		obj = (JSONObject) JSONParser.parse(content);
	  } catch (ParseException e) {
		log.error("Error parsing object",e);
	  }
	  return obj;
	}
	
	
	public static String jsonPrettyPrint(String ugglyJson)
	{	
		JsonObject obj = prettyPrintParser.parse(ugglyJson).getAsJsonObject();		
		String prettyJson = prettyGson.toJson(obj);
		return prettyJson;		
	}
	
	
	public static String jsonPrettyPrintArray(String ugglyJson)
	{	
		JsonArray obj = prettyPrintParser.parse(ugglyJson).getAsJsonArray();		
		String prettyJson = prettyGson.toJson(obj);
		return prettyJson;		
	}
	
	public static String inputStreamToString(InputStream theInput)
	{
	  String text="";
	  if (theInput!=null)
	  {
    	  try {
    		text = IOUtils.toString(theInput, StandardCharsets.UTF_8.name());
    	  } catch (IOException e) {
    		log.error("Error reading from input Stream",e);
    	  }
	  }else {
		log.error("The input stream is null");
	  }
	  return text;
	}

	
}
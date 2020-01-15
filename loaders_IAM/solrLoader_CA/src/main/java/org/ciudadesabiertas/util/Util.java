package org.ciudadesabiertas.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Util
{



	private static final Logger log = Logger.getLogger(Util.class);
	
	
	private static Map<String,String> relacionDatasetURL= new HashMap<String,String>();
	
	private static List<String> datasetList=new ArrayList<String>();		
	
	static
	{
		datasetList.add(Constants.TRAMITE );
		datasetList.add(Constants.ORGANIGRAMA );
		datasetList.add(Constants.PUNTO_WIFI );
		datasetList.add(Constants.INSTALACION_DEPORTIVA );
		datasetList.add(Constants.CALLEJERO_VIA );
		datasetList.add(Constants.CALLEJERO_TRAMO_VIA );
		datasetList.add(Constants.CALLEJERO_PORTAL );
		datasetList.add(Constants.CALIDAD_AIRE_OBSERVACION );
		datasetList.add(Constants.CALIDAD_AIRE_ESTACION );
		datasetList.add(Constants.MONUMENTO );
		datasetList.add(Constants.PUNTO_INTERES_TURISTICO );
		datasetList.add(Constants.LOCAL_COMERCIAL_TERRAZA );
		datasetList.add(Constants.LOCAL_COMERCIAL_LICENCIA_ACTIVIDAD );
		datasetList.add(Constants.LOCAL_COMERCIAL_AGRUPACION_COMERCIAL );
		datasetList.add(Constants.LOCAL_COMERCIAL );
		datasetList.add(Constants.AVISO_QUEJA_SUGERENCIA );
		datasetList.add(Constants.EQUIPAMIENTO );
		datasetList.add(Constants.APARCAMIENTO );
		datasetList.add(Constants.ALOJAMIENTO );
		datasetList.add(Constants.AGENDA_CULTURAL );
		datasetList.add(Constants.SUBVENCION );
		datasetList.add(Constants.CALIDAD_AIRE_SENSOR );
		datasetList.add(Constants.DSD);
		datasetList.add(Constants.DSD_DIMENSION);
		datasetList.add(Constants.DSD_DIMENSION_VALUE);
		datasetList.add(Constants.DSD_MEASURE);
		datasetList.add(Constants.PADRON_CUBO_EDAD);
		datasetList.add(Constants.PADRON_CUBO_ESTUDIOS);
		datasetList.add(Constants.PADRON_CUBO_INDICADORES);
		datasetList.add(Constants.PADRON_CUBO_NACIONALIDAD);
		datasetList.add(Constants.PADRON_CUBO_PROCEDENCIA);
		datasetList.add(Constants.TERRITORIO_PAIS);
		datasetList.add(Constants.TERRITORIO_AUTONOMIA);
		datasetList.add(Constants.TERRITORIO_PROVINCIA);
		datasetList.add(Constants.TERRITORIO_MUNICIPIO);
		datasetList.add(Constants.TERRITORIO_DISTRITO);
		datasetList.add(Constants.TERRITORIO_BARRIO);
		datasetList.add(Constants.TERRITORIO_SECCION);

		
		relacionDatasetURL.put(Constants.SUBVENCION, Constants.SUBVENCION_URI);
		relacionDatasetURL.put(Constants.AGENDA_CULTURAL, Constants.AGENDA_URI);
		relacionDatasetURL.put(Constants.ALOJAMIENTO, Constants.ALOJAMIENTO_URI);		
		relacionDatasetURL.put(Constants.APARCAMIENTO, Constants.APARCAMIENTO_URI);		
		relacionDatasetURL.put(Constants.EQUIPAMIENTO, Constants.EQUIPAMIENTO_URI);
		relacionDatasetURL.put(Constants.AVISO_QUEJA_SUGERENCIA, Constants.AVISO_QUEJA_SUGERENCIA_URI);
		relacionDatasetURL.put(Constants.LOCAL_COMERCIAL, Constants.LOCAL_COMERCIAL_URI);
		relacionDatasetURL.put(Constants.LOCAL_COMERCIAL_AGRUPACION_COMERCIAL, Constants.LOCAL_COMERCIAL_AGRUPACION_COMERCIAL_URI);
		relacionDatasetURL.put(Constants.LOCAL_COMERCIAL_LICENCIA_ACTIVIDAD, Constants.LOCAL_COMERCIAL_LICENCIA_ACTIVIDAD_URI);
		relacionDatasetURL.put(Constants.LOCAL_COMERCIAL_TERRAZA, Constants.LOCAL_COMERCIAL_TERRAZA_URI);
		relacionDatasetURL.put(Constants.PUNTO_INTERES_TURISTICO, Constants.PUNTO_INTERES_TURISTICO_URI);
		relacionDatasetURL.put(Constants.MONUMENTO, Constants.MONUMENTO_URI);
		relacionDatasetURL.put(Constants.CALIDAD_AIRE_ESTACION, Constants.CALIDAD_AIRE_ESTACION_URI);
		relacionDatasetURL.put(Constants.CALIDAD_AIRE_OBSERVACION, Constants.CALIDAD_AIRE_OBSERVACION_URI);
		relacionDatasetURL.put(Constants.CALIDAD_AIRE_SENSOR, Constants.CALIDAD_AIRE_SENSOR_URI);
		relacionDatasetURL.put(Constants.CALLEJERO_PORTAL, Constants.CALLEJERO_PORTAL_URI);
		relacionDatasetURL.put(Constants.CALLEJERO_TRAMO_VIA, Constants.CALLEJERO_TRAMO_VIA_URI);
		relacionDatasetURL.put(Constants.CALLEJERO_VIA, Constants.CALLEJERO_VIA_URI);
		relacionDatasetURL.put(Constants.INSTALACION_DEPORTIVA, Constants.INSTALACION_DEPORTIVA_URI);
		relacionDatasetURL.put(Constants.PUNTO_WIFI, Constants.INSTALACION_DEPORTIVA_URI);
		relacionDatasetURL.put(Constants.ORGANIGRAMA, Constants.ORGANIGRAMA_URI);
		relacionDatasetURL.put(Constants.TRAMITE, Constants.TRAMITE_URI);
		relacionDatasetURL.put(Constants.DSD, Constants.DSD_URI);
		relacionDatasetURL.put(Constants.DSD_DIMENSION, Constants.DSD_DIMENSION_URI);
		relacionDatasetURL.put(Constants.DSD_DIMENSION_VALUE, Constants.DSD_DIMENSION_VALUE_URI);
		relacionDatasetURL.put(Constants.DSD_MEASURE, Constants.DSD_MEASURE_URI);
		relacionDatasetURL.put(Constants.PADRON_CUBO_EDAD, Constants.PADRON_CUBO_EDAD_URI);
		relacionDatasetURL.put(Constants.PADRON_CUBO_ESTUDIOS, Constants.PADRON_CUBO_ESTUDIOS_URI);
		relacionDatasetURL.put(Constants.PADRON_CUBO_INDICADORES, Constants.PADRON_CUBO_INDICADORES_URI);
		relacionDatasetURL.put(Constants.PADRON_CUBO_NACIONALIDAD, Constants.PADRON_CUBO_NACIONALIDAD_URI);
		relacionDatasetURL.put(Constants.PADRON_CUBO_PROCEDENCIA, Constants.PADRON_CUBO_PROCEDENCIA_URI);
		relacionDatasetURL.put(Constants.TERRITORIO_PAIS, Constants.TERRITORIO_PAIS_URI);
		relacionDatasetURL.put(Constants.TERRITORIO_AUTONOMIA, Constants.TERRITORIO_AUTONOMIA_URI);
		relacionDatasetURL.put(Constants.TERRITORIO_PROVINCIA, Constants.TERRITORIO_PROVINCIA_URI);
		relacionDatasetURL.put(Constants.TERRITORIO_MUNICIPIO, Constants.TERRITORIO_MUNICIPIO_URI);
		relacionDatasetURL.put(Constants.TERRITORIO_DISTRITO, Constants.TERRITORIO_DISTRITO_URI);
		relacionDatasetURL.put(Constants.TERRITORIO_BARRIO, Constants.TERRITORIO_BARRIO_URI);
		relacionDatasetURL.put(Constants.TERRITORIO_SECCION, Constants.TERRITORIO_SECCION_URI);
		
		
		
	
	}
	
	
	public static boolean validValue(String s)
	{		
		return ((s!=null)&&(!"".equals(s)));
	}

	private static class DefaultTrustManager implements X509TrustManager
	{

		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
		{
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
		{
		}

		public X509Certificate[] getAcceptedIssuers()
		{
			return null;
		}
	}

	private static CloseableHttpClient createAcceptSelfSignedCertificateClient(RequestConfig config) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{

		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
		SSLContext.setDefault(sslContext);

		// we can optionally disable hostname verification.
		// if you don't want to further weaken the security, you don't have to include
		// this.
		HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

		// create an SSL Socket Factory to use the SSLContext with the trust self signed
		// certificate strategy
		// and allow all hosts verifier.
		SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);

		// finally create the HttpClient using HttpClient factory methods and assign the
		// ssl socket factory
		return HttpClients.custom().setSSLSocketFactory(connectionFactory).setDefaultRequestConfig(config).build();
	}

	public static String getPetition(String url) throws Exception
	{

		int timeout = 5;

		StringBuffer result = new StringBuffer();

		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

		// CloseableHttpClient client =
		// HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		CloseableHttpClient client = null;
		try
		{
			client = createAcceptSelfSignedCertificateClient(config);
		} catch (Exception e)
		{
			log.error("Error generating httpclient", e);
		}

		HttpGet get = new HttpGet(url);

		HttpResponse response = client.execute(get);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		while ((line = rd.readLine()) != null)
		{
			result.append(line+System.getProperty("line.separator"));
		}

		client.close();

		return result.toString();
	}

	
	public static ResponseH getPetitionWithHeaders(String url) throws Exception
	{

		int timeout = 5;

		StringBuffer result = new StringBuffer();

		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

		// CloseableHttpClient client =
		// HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		CloseableHttpClient client = null;
		try
		{
			client = createAcceptSelfSignedCertificateClient(config);
		} catch (Exception e)
		{
			log.error("Error generating httpclient", e);
		}

		HttpGet get = new HttpGet(url);

		HttpResponse response = client.execute(get);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		while ((line = rd.readLine()) != null)
		{
			result.append(line+System.getProperty("line.separator"));
		}
		
		Map<String,String> myHeaders=new HashMap<String,String>();
		Header[] allHeaders = response.getAllHeaders();
		for (Header header:allHeaders)
		{
			myHeaders.put(header.getName(), header.getValue());
		}

		
		
		
		client.close();
		
		ResponseH myResponse=new ResponseH();
		myResponse.setResponse(result);
		myResponse.setHeaders(myHeaders);
		myResponse.setStatus(response.getStatusLine().getStatusCode());
		
		return myResponse;
	}
	
	
	public static JSONObject getPetitionJSON(String url)
	{
		int retries=5;
		int errors=0;
		String respuesta ="";		
		while ((errors<retries)&&(respuesta.equals("")))
		{
	    	try
			{
				respuesta = Util.getPetition(url);
			}
	    	catch (Exception e)
			{
	    		Util.sleep(1000);
				log.error("Error consuming URL",e);
				errors++;
			}
		}
    	
    	JSONParser parser=new JSONParser();    	
    	JSONObject respuestaJSON=new JSONObject();
    	try
		{
			respuestaJSON=(JSONObject) parser.parse(respuesta);			
		} catch (ParseException e)
		{
			log.error("Error parsing data",e);
		}
    	
    	return respuestaJSON;
	}
	
	public static ResponseH getPetitionTTL(String url)
	{
		int retries=5;
		int errors=0;
		ResponseH respuesta = new ResponseH();		
		while ((errors<retries)&&(respuesta.getResponse().toString().equals("")))
		{
	    	try
			{
				respuesta = Util.getPetitionWithHeaders(url);
			}
	    	catch (Exception e)
			{
	    		Util.sleep(1000);
				log.error("Error consuming URL",e);
				errors++;
			}
		}   	
    	
    	return respuesta;
	}
	
	
	public static Properties readProperties(String fileName)
	{
		Properties prop = new Properties();
    	try {
    		 prop.load(new FileInputStream(fileName));   	   
    	} 
    	catch (IOException ex) {
    		log.error("Error reading property file",ex);
    	}
		return prop;
	}
	
	
	public static void sleep(int miliseconds)
	{
		try
		{
			Thread.sleep(miliseconds);
		} catch (InterruptedException e)
		{
			log.error("Error in sleep",e);			
		}
	}
	
	
	/*Funcion que te devuelve el final del URL dado un dataset
	 * 
	 * subvencion
	 * 	devuelve
	 * 		subvencion/subvencion
	 * 
	 * */
	public static String urlForDataset(String dataset) throws Exception
	{
		if (relacionDatasetURL.get(dataset)==null)
		{
			throw new Exception("Dataset not Found");
		}
		return relacionDatasetURL.get(dataset);
	}
	
	public static Datasets readDatasetsToImport(Properties prop) throws Exception
	{
		Datasets datasets = new Datasets();
		
		
		
		for (String name:datasetList)
		{
			if ((prop.get(name) != null) && (prop.get(name).toString().toLowerCase().equals("true")))
			{
				datasets.setDataset(name, true);
			}	
		}
	

		return datasets;
	}

}

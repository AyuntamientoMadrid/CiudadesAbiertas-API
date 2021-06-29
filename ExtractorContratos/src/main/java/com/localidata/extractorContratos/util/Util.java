package com.localidata.extractorContratos.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Util {

	private static Logger log = Logger.getLogger(Util.class);
	
	public static  SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
	
	public static  SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	public static  SimpleDateFormat timeFormatter = new SimpleDateFormat(Constants.TIME_FORMAT);
	
	public static  SimpleDateFormat dateTimeFormatterWithoutT = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_B);	
	
	public static  SimpleDateFormat backupDateTimeFormatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_BACKUP_FILE);	
	
	private static CloseableHttpClient httpClient;

	private static JSONParser jsonParser = new JSONParser();	

	//private static final int PRETTY_PRINT_INDENT_FACTOR = 4;
	
	public static JSONObject parseJSONObject(String jsonString)
	{
		JSONObject jObject = null;
		try {
			jObject=(JSONObject) jsonParser.parse(jsonString);
		} catch (ParseException e) {
			log.error("Error parsing json",e);
		}
		return jObject;
	}
	


	public static String xmlToJSON(String content) {
		String jsonText=null;
		try
		{
			org.json.JSONObject xmlJSONObj = XML.toJSONObject(content);
			//jsonText = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
			jsonText = xmlJSONObj.toString();
		}
		catch (Exception e)
		{
			log.error("Error transforming xml to json",e);
		}
		return jsonText;
	}

	public static String xmlFileToJSON(File file) {

		String content = null;
		try {
			content = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e2) {
			log.error("Error reading file: " + file.getAbsolutePath(), e2);
		}
		if (content != null) {
			org.json.JSONObject xmlJSONObj = XML.toJSONObject(content);
			// String jsonPrettyPrintString =
			// xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
			String jsonPrettyPrintString = xmlJSONObj.toString();
			return jsonPrettyPrintString;
		}
		return null;
	}

	public static JSONArray getEntriesFromJSON(String jsonPrettyPrintString) {
		JSONArray entries = new JSONArray();
		JSONObject joContent = null;
		try {
			joContent = (JSONObject) jsonParser.parse(jsonPrettyPrintString);
		} catch (ParseException e) {
			log.error("Error parsing data", e);
		}
		if (joContent != null) {
			JSONObject joFeed = (JSONObject) joContent.get("feed");
			entries = (JSONArray) joFeed.get("entry");
		}
		return entries;
	}

	public static String normalizaTexto(String texto) {
		String result = texto;

		result = StringUtils.remove(result, StringUtils.LF);
		result = StringUtils.remove(result, StringUtils.CR);
		result = StringUtils.remove(result, "&amp;");
		result = StringUtils.remove(result, "'");
		result = StringUtils.remove(result, "\n");
		result = StringUtils.remove(result, System.getProperty("line.separator"));

		return result;
	}

	public static void writeFile(StringBuffer content, String filePath) {
		File destinyFile = new File(filePath);
		try {
			FileUtils.write(destinyFile, content, "UTF-8", false);
		} catch (IOException e) {
			log.error("Error writting file", e);
		}
	}

	public static boolean validValue(String value) {
		if (value == null) {
			return false;
		}
		String copy = value.trim();
		if (copy.contentEquals("") || copy.contentEquals("null")) {
			return false;
		}

		return true;
	}

	public static boolean validValue(Double value) {
		if (value == null) {
			return false;
		}

		return true;
	}
	
	public static boolean validValue(Integer value) {
		if (value == null) {
			return false;
		}

		return true;
	}

	public static boolean validValue(Long value) {
		if (value == null) {
			return false;
		}

		return true;
	}

	public static boolean validValue(Boolean value) {
		if (value == null) {
			return false;
		}

		return true;
	}
	
	public static boolean validValue(Date value)
	{
		if (value != null )
			return true;
		return false;
	}

	private static String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;
	}
	
	/*
	 * Dado un String se devuelve en mayusculas, sin acentos, sin espacios y caracteres alphanumericos
	 */
	public static String cleanString(String dirtyString)
	{
		String cleanString=dirtyString;
		if (Util.validValue(cleanString))
		{
			cleanString = stripAccents(cleanString.toUpperCase());
			cleanString = cleanString.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]", "");
		}
		return cleanString;
	}

	private static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	public static String md5Hex(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return hex(md.digest(message.getBytes("UTF8")));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	public static Date getFecha(String _str)
	{
			return getFecha (_str, null);
	}
	
	public static Date getFecha(String _str, String format)
	{
		Date fecha = null;
		if (_str!=null && !"".equals(_str)) {
			
			if (format==null)
			{
				format=Constants.DATE_TIME_FORMAT;
			}
			try
			{
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				fecha = formatter.parse(_str);
			} catch (Exception ex)
			{
				log.error("Error getting date: [string:"+_str+"][format: "+format+"]"+ ex.getMessage());
			}
		}
		return fecha;
	}
	
	
	public static Date getFechaAtomSQlite(String _str)
	{
		Date fecha = null;
		if (_str!=null && !"".equals(_str)) {
			if (_str.length()==10) {
				fecha = getFecha(_str, Constants.DATE_FORMAT);
			}else if (_str.length()>10 ) {
				fecha = getFecha(_str, Constants.DATE_TIME_FORMAT);
			}else {
				fecha = getFecha(_str, Constants.DATE_FORMAT);
			}
			
		}
		return fecha;
	}
	
	public static String formatearFechas(Date fecha, String formato)
	{
		String result = "";
		String format = Constants.DATE_TIME_FORMAT;
		if (formato != null && !"".equals(formato))
		{
			format = formato;
		}
		SimpleDateFormat sd2 = new SimpleDateFormat(format);
		result = sd2.format(fecha);
		return result;
	}

	public static String formatearFechas(Date fecha)
	{
		return formatearFechas(fecha, "");
	}
	
	public static String formatearFechaHoraSQLServer(String fecha)
	{
		//Para la fecha en formato 01/08/2019 se usa: convert(date,'01/08/2019',23)
		//Fecha 126	- yyyy-mm-ddThh:mi:ss.mmm - 	ISO8601
		return "convert(datetime,'"+fecha+"',126)";
		
	}
	
	public static String formatearFechaSQLServer(String fecha)
	{
		//Para la fecha en formato 01/08/2019 se usa: convert(date,'01/08/2019',23)
		return "convert(date,'"+fecha+"',23)";
		
	}
	
	public static String formatearFechaOracle(String fecha,String format)
	{
		//Para la fecha en oracle ejemplo: TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss')
		return "TO_DATE('"+fecha+"', '"+format+"')";
		
	}
	
	
	public static Map<String, String> readConfigProperties(String fileName)  {
		Map<String, String> config = new HashMap<String, String>();
		log.info("[readConfigProperties] [init] ["+fileName+"]");
		try {
			InputStream input = new FileInputStream(fileName);
	
			Properties prop = new Properties();
			prop.load(input);
	
			Set<Entry<Object, Object>> entrySet = prop.entrySet();
	
			Iterator<Entry<Object, Object>> iterator = entrySet.iterator();
	
			while (iterator.hasNext()) {
				Entry<Object, Object> next = iterator.next();
	
				// Boolean value=Boolean.parseBoolean( (String) next.getValue());
	
				config.put((String) next.getKey(), next.getValue().toString().trim());
			}
		}catch(Exception e) {
			log.error("[readConfigProperties()] Error:"+e.getMessage(), e.getCause());
		}
		log.info("[readConfigProperties] [end]");
		return config;
	}
	
	
	public static Date generateDateFromString(String dateString) {
		Date lastDate=null;
		try {
			lastDate=dateTimeFormatter.parse(dateString);
		} catch (java.text.ParseException e1) {
			log.error("Error parsing last date",e1);
			
		}
		return lastDate;
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
	
	public static int readAndWriteURL(String address, String filename) {
		int status = -1;
		try {
			InputStream urlStream = readURL(address);

			if (urlStream != null) {
				File targetFile = new File(filename);
				FileUtils.copyInputStreamToFile(urlStream, targetFile);
				log.info("Writted");
				status = 0;
			}
		} catch (Exception e) {
			log.error("Error writting File", e);
			status = -1;
		}
		return status;
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
	
	public static String getContentURL(String address) {
		InputStream input = readURL(address);
		BufferedReader rd = new BufferedReader(new InputStreamReader(input));
		StringBuffer result = new StringBuffer();
		String line = "";
		try {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			log.error(e);
		}
		return result.toString();
	}

	public static void main(String[] args) {
	//	String prueba = "Concejalía     Delegada de Mujer, Igualdad y Juventud del Ayuntamiento de Zaragoza";

//		System.out.println(prueba);
//
//		String pruebaLimpia = cleanString(prueba);
//		
//		System.out.println(pruebaLimpia);
//		
//		System.out.println(md5Hex(pruebaLimpia));
		
		String fecha1 = "2020-12-21";
		
		Date date1 = getFechaAtomSQlite(fecha1);
		
		Date otherdate1= getFecha(fecha1, Constants.DATE_FORMAT);
		
		System.out.println("[Tratamiento de fechas[cbc:AwardDate]] [fecha:"+fecha1+"] [date1:"+date1+"] [otherdate1:"+otherdate1+"]");
		
//		String fecha2 = "2020-12-18";
//		
//		Date date2 = getFechaAtomSQlite(fecha2);
//		
//		log.info("[Tratamiento de fechas[cbc:AwardDate]] [fecha:"+fecha2+"] [fechaTratada:"+date2+"]");
		
	}
	


}

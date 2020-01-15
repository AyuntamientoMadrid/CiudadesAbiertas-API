package org.ciudadesabiertas.bigFilesGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.rdfGeneratorZ.Formato;
import org.ciudadesAbiertas.rdfGeneratorZ.Peticion;
import org.ciudadesAbiertas.rdfGeneratorZ.TransformadorBasicoRdf;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.converters.CustomerMappingStrategy;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class Util
{
	private static final String PLACEHOLDER = "placeholder";

	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	private static ObjectMapper jsonMapper;
	
	//private static CSVConverter csvConverter;
	
	private static BasicTextEncryptor textEncryptor;
	
	
	
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
			Util.jsonMapper.writeValue(new File(fileName), result);
		} catch (JsonGenerationException e)
		{
			log.error("Error generating JSON",e);			
		} catch (JsonMappingException e)
		{
			log.error("Error generating JSON",e);
		} catch (IOException e)
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
		int counter=0;
		File dir = new File(path);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			   for (File child : directoryListing) {
				   if (child.getName().equals(PLACEHOLDER)==false)
				   {
					   child.delete();
					   counter++;
				   }
			   }
		}
		if (counter>0)
		{
			log.info(counter+" files deleted in "+path);
		}
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

	
	
	
}

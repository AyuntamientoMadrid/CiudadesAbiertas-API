package org.ciudadesAbiertas.madrid.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

import org.ciudadesAbiertas.madrid.service.dynamic.ProcessService;
import org.ciudadesAbiertas.madrid.utils.converters.DynamicMappingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class CsvWritter {
	
	private static final Logger log = LoggerFactory.getLogger(CsvWritter.class);
	
	private StatefulBeanToCsv beanToCsv;
	
	private OutputStreamWriter outputStream;
	
	public CsvWritter(String path) 
	{
		try
		{		
			File file = new File(path);			
			FileOutputStream fop = new FileOutputStream(file); 
	       
	        
			byte[] bomBytes = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
			
			outputStream = new OutputStreamWriter(fop,Charset.forName("UTF8"));    
			
			ColumnPositionMappingStrategy strategy = new DynamicMappingStrategy(); 
		     
			beanToCsv =
                  new StatefulBeanToCsvBuilder(outputStream)
                  			/*.withSeparator(';')*/
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withMappingStrategy(strategy)
                            .withEscapechar(CSVWriter.NO_ESCAPE_CHARACTER)
                            .withLineEnd(CSVWriter.RFC4180_LINE_END)                            
                            .build();
        
			outputStream.write(new String(bomBytes));
		}
		catch (Exception e)
		{
			log.error("Error initializating CSV file: "+ path);
		}
      
		
	}


	public void write(List<Object> results) {
		if ((beanToCsv!=null)&&(outputStream!=null))
		{
			try {
	            beanToCsv.write(results);
	            outputStream.close();
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
		}
		else
		{
			log.error("Components not initializated");
		}
		
	}
	
	
	
}

/**
 * Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
 * 
 * This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package org.ciudadesAbiertas.madrid.utils.converters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRml;
import org.ciudadesAbiertas.madrid.utils.RMLMapper;
import org.ciudadesAbiertas.madrid.utils.Result;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 * @param <L>
 */
@Component
public class RDFConverter<T, L extends Result> extends AbstractHttpMessageConverter<L> {



private static final Logger log = LoggerFactory.getLogger(RDFConverter.class);



private String format;

public RDFConverter() {
  super(MediaType.valueOf(Constants.mimeTURTLE));
  format = Constants.mimeTURTLE;
}

public RDFConverter(String mediaType) {
  super(MediaType.valueOf(mediaType));
  format = mediaType;
}

@Override
protected boolean supports(Class<?> clazz) {
  return Result.class.isAssignableFrom(clazz);
}

@Override
protected L readInternal(Class<? extends L> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
  HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
  Class<T> t = toBeanType(clazz.getGenericSuperclass());
  strategy.setType(t);
  CSVReader csv = new CSVReader(new InputStreamReader(inputMessage.getBody()));
  CsvToBean<T> csvToBean = new CsvToBean<>();
  List<T> beanList = csvToBean.parse(strategy, csv);
  try {
	L l = clazz.newInstance();
	l.setRecords(beanList);
	return l;
  } catch (Exception e) {
	throw new RuntimeException(e);
  }
}

@SuppressWarnings("unchecked")
@Override
protected void writeInternal(L l, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

  ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<T>();
  strategy = new DynamicMappingStrategy<T>();
  strategy.setType(toBeanType(l.getClass().getGenericSuperclass()));

  boolean checkSeparator = true;
  boolean anotherSeparator = false;

  File temporalDataFile = File.createTempFile("temporal", ".csv");
  OutputStream temporalStream = new FileOutputStream(temporalDataFile);
  OutputStreamWriter temporalStreamWriter = new OutputStreamWriter(temporalStream, Charset.forName("UTF8"));

  StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder(temporalStreamWriter)
	  /* .withSeparator(';') */
	  .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withMappingStrategy(strategy).withEscapechar(CSVWriter.NO_ESCAPE_CHARACTER).withApplyQuotesToAll(false).withLineEnd(CSVWriter.RFC4180_LINE_END).build();
  try {
	List records = l.getRecords();
	Iterator recordsIterator = records.iterator();
	List recordsTranslated = new ArrayList();
	while (recordsIterator.hasNext()) {

	  Object next = recordsIterator.next();
	  if (next instanceof List) {
		List oneList = (List) next;
		int contador = 0;
		Iterator iteratorList = oneList.iterator();
		LinkedHashMap map = new LinkedHashMap();
		while (iteratorList.hasNext()) {
		  map.put("" + (contador++), iteratorList.next());
		}
		if (map.containsKey(StartVariables.geometry_field)) {
		  anotherSeparator = true;
		}
		Set<String> keySet = map.keySet();
		for (String key:keySet)
		{
		  if (map.get(key)==null)
		  {
			if (map.get(key) instanceof String)
			  {
				String value=(String) map.get(key);
				if (Util.validValue(value)==false)
				{
					map.put(key,"");
				}
			  } 
		  }else {
			  if (map.get(key) instanceof String) {
				  String value=(String) map.get(key);
				  map.put(key,Util.limpiarTextosRDF(value));
			  }
		   }
		    
		}
		
		recordsTranslated.add(map);
	  } else {
		LinkedHashMap mapObject=(LinkedHashMap) next;
		Set<String> keySet = mapObject.keySet();
		for (String key:keySet)
		{
		  if (mapObject.get(key)==null)
		  {
			if (mapObject.get(key) instanceof String)
			  {
				String value=(String) mapObject.get(key);
				if (Util.validValue(value)==false)
				{
				  mapObject.put(key,"");
				}
			  } 
		  }else {
				if (mapObject.get(key) instanceof String) {
					String value = (String) mapObject.get(key);
					mapObject.put(key, Util.limpiarTextosRDF(value));
				}
			}
		    
		}
		
		
		
		recordsTranslated.add(mapObject);
		if (checkSeparator) {
		  if (next instanceof LinkedHashMap) {
			if (((LinkedHashMap) next).containsKey(StartVariables.geometry_field)) {
			  anotherSeparator = true;
			}
		  }
		  checkSeparator = false;
		}
	  }
	}

	if (anotherSeparator) {
	  beanToCsv = new StatefulBeanToCsvBuilder(temporalStreamWriter).withSeparator(';').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withMappingStrategy(strategy).withEscapechar(CSVWriter.NO_ESCAPE_CHARACTER)
		  .withLineEnd(CSVWriter.RFC4180_LINE_END).build();
	}

	beanToCsv.write(recordsTranslated);
	temporalStreamWriter.close();
  } catch (Exception e) {
	throw new RuntimeException(e);
  }

  List<SemanticPrefix> prefixes = l.getPrefixes();
  SemanticRml rml = l.getRml();

  l.setQuery(null);
  l.setPrefixes(null);

  // Empezamos las transformaciones
  log.info("Información en " + temporalDataFile.getAbsolutePath());

  String mappingContent = rml.getRml();
  mappingContent = mappingContent.replace("nombreDinamico.csv", temporalDataFile.getAbsolutePath());

  String out = "turtle";
  if (format.equals(Constants.mimeJSONLD)) {
	out = "jsonld";
  } else if (format.equals(Constants.mimeRDF_XML)) {
	out = "rdf";
  } else if (format.equals(Constants.mimeN3)) {
	out = "nquads";
  }

  File temporalMappingFile = File.createTempFile("temporalMapping", "." + out);
  FileUtils.write(temporalMappingFile, mappingContent, "utf8");

  OutputStreamWriter outputStream = new OutputStreamWriter(outputMessage.getBody(), Charset.forName("UTF8"));

  RMLMapper.generateRDF(temporalMappingFile.getAbsolutePath(), prefixes, out, outputStream);

  
  Thread t1 = new Thread(new Runnable() {
	private static final int TIME_TO_SLEEP = 4000;
    public void run()
    {
      log.info("New thread to delete temporal files started");
      try {
    	 log.debug("sleeping "+TIME_TO_SLEEP+" miliseconds");
		Thread.sleep(TIME_TO_SLEEP);
	  } catch (InterruptedException e) {
		log.error("Error sleeping thread for delete temporal files",e);
	  }
      log.debug("temp mapping file deleted: "+FileUtils.deleteQuietly(temporalMappingFile));
      log.debug("temp data file deleted: "+FileUtils.deleteQuietly(temporalDataFile));
      log.info("Thread finished");
    }});  
    t1.start();
  
  
 

}

@SuppressWarnings("unchecked")
private Class<T> toBeanType(Type type) {
  // return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
  return (Class<T>) Object.class;
}

}
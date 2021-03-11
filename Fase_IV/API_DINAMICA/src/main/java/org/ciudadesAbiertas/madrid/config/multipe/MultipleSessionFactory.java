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

package org.ciudadesAbiertas.madrid.config.multipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class MultipleSessionFactory {
	
	private static final Logger log = LoggerFactory.getLogger(MultipleSessionFactory.class);
	
	private Map<String,SessionFactory> factories;
	private Map<String,LocalSessionFactoryBuilder> builders;
	
	public Set<String> getKeys()
	{
		if (factories!=null)
		{
			return factories.keySet();
		}else {
			return new HashSet<String>();
		}
	}

	public Map<String,SessionFactory> getFactories() {
		return factories;
	}



	public void setFactories(Map<String,SessionFactory> factories) {
		this.factories = factories;
	}

	


	public Map<String, LocalSessionFactoryBuilder> getBuilders()
	{
		return builders;
	}

	public void setBuilders(Map<String, LocalSessionFactoryBuilder> builders)
	{
		this.builders = builders;
	}

	public MultipleSessionFactory(MultipleDataSource multipleDataSource) {
		
		factories=new HashMap<String,SessionFactory>();
		builders=new HashMap<String,LocalSessionFactoryBuilder>();
		
		for (String key:multipleDataSource.getKeys())
		{	
			LocalSessionFactoryBuilder builder = null;
			Properties hibernateProperties = new Properties();
			hibernateProperties.put(Constants.DB_HIBERNATE_FORMAT_SQL, multipleDataSource.getFormat_sql().get(key));
			hibernateProperties.put(Constants.DB_HIBERNATE_SHOW_SQL, multipleDataSource.getShow_sql().get(key));
			hibernateProperties.put(Constants.DB_HIBERNATE_DIALECT, multipleDataSource.getDialects().get(key));
			
			String defaultSchema=multipleDataSource.getDefaultSchema().get(key);
			if (defaultSchema!=null)
			{
				log.info("defaultSchema: "+defaultSchema);
				hibernateProperties.put(Constants.DB_HIBERNATE_DEFAULT_SCHEMA, defaultSchema);
			}
			
			if (multipleDataSource.getDataSources().get(key)!=null) {				
				
				builder = new LocalSessionFactoryBuilder(multipleDataSource.getDataSources().get(key));
				builder.addProperties(hibernateProperties);
			}
			
			if (multipleDataSource.getJndis().get(key)!=null){
				builder = new LocalSessionFactoryBuilder(multipleDataSource.getJndis().get(key));
				builder.addProperties(hibernateProperties);
			}
			
			//CMG CONTROL DE EXCEPCIONES SOBRE LA CARGA DE BBDD
			//Si se produce una excepción eliminamos esa configuración de los Map
			try {
				builders.put(key,builder);			
				factories.put(key,builder.buildSessionFactory());
			}catch (Exception e) {
				log.error("[MultipleSessionFactory] [ERROR] [key:"+key+"] ["+e.getMessage()+"]");
				if (builders.containsKey(key)) {
					builders.remove(key);
					log.info("[MultipleSessionFactory] [remove]  [builders] [key:"+key+"]");
				}
				
				if (factories.containsKey(key)) {
					factories.remove(key);
					log.info("[MultipleSessionFactory] [remove]  [factories] [key:"+key+"]");
				}
				
				//Tambien debemos borrar el dataBaseTypes
				if (StartVariables.databaseTypes.containsKey(key)) {
					
					String value = StartVariables.databaseTypes.get(key);
					StartVariables.databaseTypes.remove(key);
					log.info("[MultipleSessionFactory] [remove]  [StartVariables.databaseTypes] [key:"+key+"]");
					//Añadimos al hashMap de Error
					StartVariables.errorDatabaseTypes.put(key, value);
				}
				
				
				
				
			}
		}
		
		
		
	}
	
	
	
	
	
	

}

package org.ciudadesAbiertas.madrid.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jsqlparser.JSQLParserException;

public class DynamicQueryUtils {
	
	
	public static final String AS_SELECT_PRINCIPAL_WHERE = " ) as selectPrincipal where ";

	public static final String SELECT_FROM = "Select * from ( ";

	private static final Logger log = LoggerFactory.getLogger(DynamicQueryUtils.class);

	
	
public static Map<String,String> typesQuery(SessionFactory sessionFactory, String databaseSchema, QueryD query) throws Exception {
		
		Map<String,String> typesQuery=new LinkedHashMap<String,String>();	
		
		String databaseType = StartVariables.databaseTypes.get(query.getDatabase());
		
		String queryText=query.getTexto();
		
		boolean isDistinctQuery = DynamicQueryUtils.checkDistinctQuery(queryText);
		boolean isGroupByQuery =  DynamicQueryUtils.checkGroupByQuery(queryText);
				
		if (databaseType.equals(Constants.SQLSERVER))
		{
			try {			
				queryText=TableNameReplacer.addSchemaToQuery(queryText);	
				queryText=queryText.replace(TableNameReplacer.H_SCHEMA,databaseSchema+".");
			} catch (JSQLParserException e1) {
				log.error("Error parsing query",e1);
			}
		}

		log.debug("texto query:");
		log.debug(queryText);
	

		log.info("Extrayendo metadatos de query");
		
		
		try {
			
			Connection connection = ((SessionImpl)sessionFactory.getCurrentSession()).connection();
			Statement stmt = connection.createStatement();
			
					
			ResultSet rs = stmt.executeQuery(queryText);
			
			ResultSetMetaData metadata = rs.getMetaData();	
							
			for (int i=1;i<=metadata.getColumnCount();i++)
			{							
				typesQuery.put(metadata.getColumnLabel(i),metadata.getColumnClassName(i));				
			}
			
			
	
		} catch (Exception e) {
			log.error("Error en query de metadatos", e);
			log.error(queryText);
		}

		return typesQuery;

		

		
	}

	
	public static boolean checkDistinctQuery(String queryText) {
		boolean isDistinctQuery=false;
		String checkQueryText=queryText;
		checkQueryText=checkQueryText.toLowerCase();
		checkQueryText = StringUtils.normalizeSpace(checkQueryText);
		checkQueryText=checkQueryText.replaceAll(" ", "");		
		if (checkQueryText.startsWith("selectdistinct"))
		{
			isDistinctQuery=true;
		}
		return isDistinctQuery;
	}

	public static boolean checkGroupByQuery(String queryText) {
		boolean isGroupQuery=false;
		String checkQueryText=queryText;
		checkQueryText=checkQueryText.toLowerCase();
		checkQueryText = StringUtils.normalizeSpace(checkQueryText);
		checkQueryText=checkQueryText.replaceAll(" ", "");		
		if (checkQueryText.contains("groupby"))
		{
			isGroupQuery=true;
		}
		return isGroupQuery;
	}
	
}

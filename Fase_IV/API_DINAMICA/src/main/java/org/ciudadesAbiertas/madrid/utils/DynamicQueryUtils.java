package org.ciudadesAbiertas.madrid.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.exception.BadRequestException;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
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

	public static String replaceParameter(	String queryText,
											ParamD param,
											String paramValue, 
											boolean isDistinctQuery, 
											boolean isGroupByQuery,											
											String databaseType
											) throws BadRequestException {
		
		if (Util.validValue(paramValue)) {
			paramValue = Util.decodeURL(paramValue);
		}

		//si es null, dejamos pasarsolo si es group by
		if ((paramValue != null) && (!paramValue.equals("")) || isGroupByQuery) 
		{
			if (param.getType().equals(Constants.TEXT)) 
			{
				if (isGroupByQuery)
				{
					if (param.getName().equals(Constants.WHERE_P)) 
					{
						if (Util.validValue(paramValue))
						{
							paramValue = paramValue.replace("*", "%");
							queryText = queryText.replace(param.getName(), paramValue);
						}
						else
						{
							queryText = StringUtils.normalizeSpace(queryText);
							//Si no existe el parametro where, eliminamos esa linea de la query
							String part1=queryText.substring(0,queryText.indexOf(Constants.WHERE_P)-"where ".length());
							String part2=queryText.substring(queryText.toLowerCase().indexOf("group by"));
							queryText = part1+" "+part2;
						}
					} 
					else if (param.getName().equals(Constants.FIELDS_P)) 
					{
						if (Util.validValue(paramValue))
						{
							queryText = queryText.replace(param.getName(), paramValue);
						}
						else
						{
							throw new BadRequestException("Parámetro requerido: "+Constants.FIELDS_P,"El parámetro "+Constants.FIELDS_P+" es necesario en consultas group by");
						}
					} 
					else if (param.getName().equals(Constants.GROUP_P)) 
					{
						if (Util.validValue(paramValue))
						{
							queryText = queryText.replace(param.getName(), paramValue);
						}
						else
						{
							throw new BadRequestException("Parámetro requerido: "+Constants.GROUP_P,"El parámetro "+Constants.GROUP_P+" es necesario en consultas group by");
						}
					} 
					else if (param.getName().equals(Constants.HAVING_P)) 
					{
						if (Util.validValue(paramValue))
						{
							queryText = queryText.replace(param.getName(), paramValue);
						}
						else
						{
							queryText = StringUtils.normalizeSpace(queryText);
							//Si no existe el parametro where, eliminamos esa linea de la query							
							String withoutHaving=queryText.substring(0, queryText.toLowerCase().indexOf("having"));
							queryText = withoutHaving;
						}					
					} 
					else 
					{
						queryText = queryText.replace(param.getName(), "'" + paramValue + "'");
					}
				}
				else if (isDistinctQuery) 
				{
					queryText = queryText.replace(param.getName(), paramValue);
				} 
				else 
				{					
					paramValue = paramValue.replace("*", "%");
					queryText = queryText.replace(param.getName(), "'" + paramValue + "'");
				}

			} 
			else if (param.getType().equals(Constants.NUMBER)) 
			{
				queryText = queryText.replace(param.getName(), paramValue);
			} 
			else if (param.getType().equals(Constants.DATE)) 
			{
				String compareDate = "";
				if (databaseType.equals(Constants.ORACLE)) 
				{
					compareDate = "TO_DATE(" + paramValue + ",'yyyyMMdd')";
				} 
				else 
				{
					// STR_TO_DATE('21,5,2013','%d,%m,%Y');
					String year = paramValue.substring(0, 4);
					String month = paramValue.substring(4, 6);
					String day = paramValue.substring(6, 8);
					String date = day + "," + month + "," + year;
					compareDate = "STR_TO_DATE('" + date + "','%d,%m,%Y')";
				}
				queryText = queryText.replace(param.getName(), compareDate);
			}
			else if (param.getType().equals(Constants.DATE_TIME)) 
			{
				String compareDate = "";
				if (databaseType.equals(Constants.ORACLE)) 
				{
					compareDate = "TO_DATE(" + paramValue + ",'yyyyMMddhhmmss')";
				} 
				else 
				{
					// STR_TO_DATE('21,5,2013','%d,%m,%Y');
					String year = paramValue.substring(0, 4);
					String month = paramValue.substring(4, 6);
					String day = paramValue.substring(6, 8);
					String hour = "00";
					String minute = "00";
					String second = "00";
					if (paramValue.length()==10)
					{
					  hour = paramValue.substring(8, 10);
					}
					else if (paramValue.length()==12) 
					{
					   hour = paramValue.substring(8, 10);
					   minute = paramValue.substring(10, 12);
					}
					else if (paramValue.length()==14) 
					{
					    hour = paramValue.substring(8, 10);
						minute = paramValue.substring(10, 12);
						second = paramValue.substring(12, 14);
					}
					
					String date = day + "," + month + "," + year+ ","+ hour +","+ minute + ","+second;
					compareDate = "STR_TO_DATE('" + date + "','%d,%m,%Y,%H,%i,%s')";
				}
				queryText = queryText.replace(param.getName(), compareDate);
			}

		}
		return queryText;
	}
	
	
public static Map<String,String> typesQuery(SessionFactory sessionFactory, String databaseSchema, QueryD query, List<ParamD> parameters) throws Exception {
		
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
		
		for (ParamD actualParam:parameters)
		{	
			String paramValue = actualParam.getExample();				
			queryText = DynamicQueryUtils.replaceParameter(queryText,actualParam,paramValue, isDistinctQuery, isGroupByQuery, databaseType);
		}
		log.debug("texto query con parametros:");
		log.debug(queryText);

		log.info("Extrayendo metadatos de query "+query.getCode());
		
		
		
			
			Connection connection = ((SessionImpl)sessionFactory.getCurrentSession()).connection();
			Statement stmt = connection.createStatement();
			
					
			ResultSet rs = stmt.executeQuery(queryText);
			
			ResultSetMetaData metadata = rs.getMetaData();	
							
			for (int i=1;i<=metadata.getColumnCount();i++)
			{							
				typesQuery.put(metadata.getColumnLabel(i),metadata.getColumnClassName(i));				
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

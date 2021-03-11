package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.madrid.config.multipe.MultipleDataSource;
import org.ciudadesAbiertas.madrid.config.multipe.MultipleSessionFactory;
import org.ciudadesAbiertas.madrid.exception.InternalErrorException;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.utils.DynamicQueryUtils;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.TableNameReplacer;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.sf.jsqlparser.JSQLParserException;

@Repository
public class DynamicDaoMultipleDatabase {

	

	@Autowired
	private MultipleSessionFactory multipleSessionFactory;
	
	@Autowired
	private MultipleDataSource multipleDataSource;
	
	private static final Logger log = LoggerFactory.getLogger(DynamicDaoMultipleDatabase.class);
	
	

	public List<Object> query(String database, String query, int page, int pageSize) throws InternalErrorException {

		SessionFactory selectedFactory=multipleSessionFactory.getFactories().get(database);
		
		List<Object> result = new ArrayList<Object>();
		//CONTROL PARA LAS BBDD NO CARGADAS
		if (selectedFactory!=null) {				
		
			String databaseType = StartVariables.databaseTypes.get(database);	
			
			query=TableNameReplacer.addAliases(query);

			if (databaseType.equals(Constants.SQLSERVER)) {
				try {
					query = TableNameReplacer.addSchemaToQuery(query);
				} catch (JSQLParserException e1) {
					log.error("Error parsing query", e1);
				}
			}

			NativeQuery sqlquery = null;

			sqlquery = selectedFactory.getCurrentSession().createSQLQuery(query);

			sqlquery.setFirstResult(page * pageSize);
			sqlquery.setMaxResults(pageSize);
			sqlquery.setResultTransformer(Util.transformadorCamposSqlOrdenados);

			try {
				result = sqlquery.list();
				log.info("devolvemos resultados");

			} catch (Exception e) {
				log.error("Error throwing query", e);
			}
			
		}else {
			log.error("[query][ERROR][database:"+database+"] [NOT FOUND]");
			throw new InternalErrorException("[query][ERROR][database:"+database+"] [NOT FOUND]");
		}

		return result;

	}
	
	@SuppressWarnings({ "rawtypes", "deprecation", "unused" })
	public long rowCount(String database, String query) throws InternalErrorException {

		log.info("[rowCount]");
		log.debug("[rowCount] [query] ["+query+"]");
		
		String COUNTER_CA = "counterCA";
		
		SessionFactory selectedFactory=multipleSessionFactory.getFactories().get(database);
		long totalRegistro = 0;
		String queryRowCount = "Select count(*) as "+COUNTER_CA+" from (" + query + ") as queryCA";

		// CONTROL PARA LAS BBDD NO CARGADAS
		if (selectedFactory != null) {

			String databaseType = StartVariables.databaseTypes.get(database);
			if (databaseType.equals(Constants.SQLSERVER)) {
				try {
					queryRowCount = TableNameReplacer.addSchemaToQuery(queryRowCount);
				} catch (JSQLParserException e1) {
					log.error("Error parsing query", e1);
				}
			}

			if (selectedFactory != null) {

				NativeQuery sqlquery = selectedFactory.getCurrentSession().createSQLQuery(queryRowCount);
				sqlquery.setResultTransformer(Util.transformadorCamposSqlOrdenados);

				try {
				    Object uniqueResult = sqlquery.uniqueResult();
				    if (uniqueResult instanceof HashMap)
				    {
				        HashMap value =  (HashMap) sqlquery.uniqueResult();
		    			if (!value.isEmpty()) {
		    				if (value.get(COUNTER_CA) instanceof BigInteger) {
		    					BigInteger valor = (BigInteger) value.get(COUNTER_CA);
		    					totalRegistro = valor.longValue();
		    				}else {
		    					Integer valor = (Integer) value.get(COUNTER_CA);
		    					totalRegistro = valor.longValue();
		    				}
		    			}
				    }
				    else if (uniqueResult instanceof BigInteger)
				    {
				      BigInteger valor = (BigInteger) uniqueResult;
					  totalRegistro = valor.longValue();		      
				    }
					
				    
					log.info("[rowCount] [devolvemos resultados]");

				} catch (Exception e) {
					log.error("Error throwing query", e);
				}
			} else {
				log.error("Second database without configuration");
			}
		}else {
			log.error("[rowCount][ERROR][database:"+database+"] [NOT FOUND]");
			throw new InternalErrorException("[rowCount][ERROR][database:"+database+"] [NOT FOUND]");
		}
		
		return totalRegistro;

	}

	public Map<String,String> typesQuery(QueryD query, List<ParamD> parameters) throws Exception {
		
		SessionFactory selectedFactory=multipleSessionFactory.getFactories().get(query.getDatabase());
		
		String schema = multipleDataSource.getDefaultSchema().get(query.getDatabase());
		
		return DynamicQueryUtils.typesQuery(selectedFactory, schema, query, parameters);		
	}
	
	
	

}
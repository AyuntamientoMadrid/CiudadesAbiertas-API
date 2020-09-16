package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.utils.AliasToLinkedEntityMapTransformer;
import org.ciudadesAbiertas.madrid.utils.DynamicQueryUtils;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.TableNameReplacer;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;


@Repository
public class DynamicDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger log = LoggerFactory.getLogger(DynamicDao.class);

	

	public List<Object> query( String database, String query, int page, int pageSize) {

		List<Object> result = new ArrayList<Object>();
		
		query=TableNameReplacer.addAliases(query);
		
		String databaseType = StartVariables.databaseTypes.get(database);
		if (databaseType.equals(Constants.SQLSERVER))
		{
			try {			
				query=TableNameReplacer.addSchemaToQuery(query);			
			} catch (JSQLParserException e1) {
				log.error("Error parsing query",e1);
			}
		}

		
		
		
		
		
		NativeQuery sqlquery = sessionFactory.getCurrentSession().createSQLQuery(query);
		sqlquery.setFirstResult(page * pageSize);
		sqlquery.setMaxResults(pageSize);
		//sqlquery.setResultTransformer(Util.transformadorCamposSqlOrdenados);
		sqlquery.setResultTransformer(Util.transformadorCamposSqlOrdenados);
		;
		
		try {

			result = sqlquery.list();

		} catch (Exception e) {
			log.error("Error throwing query", e);
		}

		return result;

	}
	
	public long rowCount(String database, String query) {
		log.info("[rowCount]");
		log.debug("[rowCount] [query] ["+query+"]");
		long totalRegistro = 0;
		String queryRowCount= "Select count(*) as contar from ("+query+") as consulta";
		
		String databaseType = StartVariables.databaseTypes.get(database);
		if (databaseType.equals(Constants.SQLSERVER))
		{
			try {			
				queryRowCount=TableNameReplacer.addSchemaToQuery(queryRowCount);			
			} catch (JSQLParserException e1) {
				log.error("Error parsing query",e1);
			}
		}
		
		
		NativeQuery sqlquery = sessionFactory.getCurrentSession().createSQLQuery(queryRowCount);
	
		sqlquery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		try {
			
			HashMap value =  (HashMap) sqlquery.uniqueResult();
			if (!value.isEmpty()) {
				if (value.get("contar") instanceof BigInteger) {
					BigInteger valor = (BigInteger) value.get("contar");
					totalRegistro = valor.longValue();
				}else {
					Integer valor = (Integer) value.get("contar");
					totalRegistro = valor.longValue();
				}
			}
			
			log.info("[rowCount] [devolvemos resultados]");

		} catch (Exception e) {
			log.error("Error throwing query", e);
		}

		return totalRegistro;

	}

	public List<Object> simpleQuery(QueryD query, List<ParamD> parameters) {		
		
		List<Object> result = new ArrayList<Object>();
		
		String databaseType = StartVariables.databaseTypes.get(query.getDatabase());
		
		String queryText=query.getTexto();
		
		if (databaseType.equals(Constants.SQLSERVER))
		{
			try {			
				queryText=TableNameReplacer.addSchemaToQuery(queryText);			
			} catch (JSQLParserException e1) {
				log.error("Error parsing query",e1);
			}
		}

		for (ParamD actualParam:parameters)
		{
			queryText=queryText.replace(actualParam.getName(), ":"+actualParam.getName());
		}
		NativeQuery sqlquery = sessionFactory.getCurrentSession().createSQLQuery(queryText);
		for (ParamD actualParam:parameters)
		{
			sqlquery.setParameter(actualParam.getName(), actualParam.getExample());
		}
		sqlquery.setFirstResult(0);
		sqlquery.setMaxResults(1);
		sqlquery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		try {

			result = sqlquery.list();

		} catch (Exception e) {
			log.error("Error throwing query", e);
		}

		return result;

		
	}
	
	public Map<String,String> typesQuery(QueryD query, List<ParamD> parameters) throws Exception {
		
		String schema = StartVariables.db_schema;
				
		return DynamicQueryUtils.typesQuery(sessionFactory, schema, query, parameters);		
	}

}
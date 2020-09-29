package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	

	public List<Object> query( String database, String query) throws Exception {

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
		//sqlquery.setResultTransformer(Util.transformadorCamposSqlOrdenados);
		sqlquery.setResultTransformer(Util.transformadorCamposSqlOrdenados);
		
		try {

			result = sqlquery.list();

		} catch (Exception ex) {
			log.error("Error throwing query", ex);
			throw ex;
		}

		return result;

	}
	
	


}
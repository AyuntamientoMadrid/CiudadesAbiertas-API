package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.config.multipe.MultipleDataSource;
import org.ciudadesAbiertas.madrid.config.multipe.MultipleSessionFactory;
import org.ciudadesAbiertas.madrid.exception.InternalErrorException;
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

    public List<Object> query(String database, String query) throws Exception {

	SessionFactory selectedFactory = multipleSessionFactory.getFactories().get(database);

	List<Object> result = new ArrayList<Object>();
	// CONTROL PARA LAS BBDD NO CARGADAS
	if (selectedFactory != null) {

	    String databaseType = StartVariables.databaseTypes.get(database);

	    query = TableNameReplacer.addAliases(query);

	    if (databaseType.equals(Constants.SQLSERVER)) {
		try {
		    query = TableNameReplacer.addSchemaToQuery(query);
		} catch (JSQLParserException e1) {
		    log.error("Error parsing query", e1);
		}
	    }

	    NativeQuery sqlquery = null;

	    sqlquery = selectedFactory.getCurrentSession().createSQLQuery(query);
	    sqlquery.setResultTransformer(Util.transformadorCamposSqlOrdenados);

	    try {
		result = sqlquery.list();
		log.info("devolvemos resultados");

	    } catch (Exception e) {
		log.error("Error throwing query", e);
		throw new Exception("Conection error");
	    }

	} else {
	    log.error("[query][ERROR][database:" + database + "] [NOT FOUND]");
	    throw new Exception("Database not found");
	}

	return result;

    }

}
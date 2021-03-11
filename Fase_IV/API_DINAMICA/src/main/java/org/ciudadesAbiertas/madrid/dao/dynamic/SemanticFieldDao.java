package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.madrid.model.dynamic.SemanticField;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SemanticFieldDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void add(SemanticField entity) {

	Session session = null;
	Transaction tx = null;
	try {

	    

	    session = sessionFactory.openSession();
	    tx = session.beginTransaction();
	    
	    if (entity.getId()==null)
	    {
		entity.setId(UtilDao.getSuffixForId(SemanticField.class.getSimpleName(), UtilDao.maxId(session, SemanticField.class)));
	    }
	    
	    session.save(entity);

	    tx.commit();
	} catch (Exception e) {
	    tx.rollback();
	    throw e;
	} finally {
	    if (session != null) {
		session.close();
	    }
	}

    }

    

    public List<SemanticField> getFieldsFromQuery(String queryName, String orderfields) {
	List<SemanticField> result = new ArrayList<SemanticField>();

	String queryString="FROM SemanticField where query like :queryName";
	if (Util.validValue(orderfields))
	{
	    queryString+=" order by "+orderfields;
	}
	Query query = sessionFactory.getCurrentSession().createQuery(queryString);
	query.setParameter("queryName", queryName);

	result = query.getResultList();

	return result;
    }

    public Map<String, List<SemanticField>> getMapQueryFields() {

	Map<String, List<SemanticField>> contentMap = new HashMap<String, List<SemanticField>>();

	List<SemanticField> result = new ArrayList<SemanticField>();

	Query query = sessionFactory.getCurrentSession().createQuery("FROM SemanticField ORDER BY query");

	result = query.getResultList();

	String actualQuery = "";
	List<SemanticField> listForQuery = new ArrayList<SemanticField>();

	for (SemanticField semanticField : result) {

	    if (semanticField.getQuery().equals(actualQuery) == false) {
		if (listForQuery.isEmpty() == false) {
		    contentMap.put(actualQuery, listForQuery);
		}
		actualQuery = semanticField.getQuery();
		listForQuery = new ArrayList<SemanticField>();		
	    }
	    listForQuery.add(semanticField);
	    
	}	
	contentMap.put(actualQuery, listForQuery);	

	return contentMap;
    }

    public void delete(SemanticField field) {
	try {
	    sessionFactory.getCurrentSession().remove(field);
	} catch (Exception e) {
	    throw e;
	}

    }

}
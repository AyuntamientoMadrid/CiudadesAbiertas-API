package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RelPrefixDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void add(SemanticRelPrefix rel) {

	Session session = null;
	Transaction tx = null;
	try {

	    if (rel.getId() == null) {
		rel.setId(maxId() + "");
	    }

	    session = sessionFactory.openSession();
	    tx = session.beginTransaction();
	    session.save(rel);

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

    public int maxId() {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SemanticRelPrefix.class).setProjection(Projections.rowCount());
	Integer idCount = Integer.valueOf(0);
	Object uniqueResult = criteria.uniqueResult();
	if (uniqueResult != null) {
	    idCount = Integer.parseInt(uniqueResult.toString());

	}
	return idCount.intValue();

    }

    public List<SemanticRelPrefix> getPrefixFromQuery(String queryName) {

	List<SemanticRelPrefix> result = new ArrayList<SemanticRelPrefix>();

	Query query = sessionFactory.getCurrentSession().createQuery("FROM SemanticRelPrefix where query like :queryName");
	query.setParameter("queryName", queryName);

	result = query.getResultList();

	return result;

    }

    public void delete(SemanticRelPrefix rel) {

	try {
	    sessionFactory.getCurrentSession().remove(rel);
	} catch (Exception e) {
	    throw e;
	}

    }

}
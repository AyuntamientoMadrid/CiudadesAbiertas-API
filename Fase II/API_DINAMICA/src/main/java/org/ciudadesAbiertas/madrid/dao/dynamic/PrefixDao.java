package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PrefixDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<SemanticPrefix> list(int firstResult, int maxResult) {

	List<SemanticPrefix> result = new ArrayList<SemanticPrefix>();

	Query<SemanticPrefix> query = sessionFactory.getCurrentSession().createQuery("FROM SemanticPrefix");
	if (maxResult > 0) {
	    query.setMaxResults(maxResult);
	}
	if (firstResult > 0) {
	    query.setFirstResult(firstResult);
	}
	result = query.getResultList();

	return result;
    }

    public SemanticPrefix record(String id) {

	List<SemanticPrefix> result = new ArrayList<SemanticPrefix>();

	Query<SemanticPrefix> query = sessionFactory.getCurrentSession().createQuery("FROM SemanticPrefix where id like :id", SemanticPrefix.class);
	query.setParameter("id", id);
	result = query.list();

	if (result.size() > 0) {
	    return result.get(0);
	}

	return null;
    }

    public int listRowCount() {

	Query<SemanticPrefix> query = sessionFactory.getCurrentSession().createQuery("FROM SemanticPrefix", SemanticPrefix.class);
	return query.list().size();

    }

    public void add(SemanticPrefix prefix) {

	Session session = null;
	Transaction tx = null;
	try {
	    session = sessionFactory.openSession();
	    tx = session.beginTransaction();
	    session.save(prefix);

	    tx.commit();
	} catch (Exception e) {
	    tx.rollback();
	    throw e;
	} finally {
	    session.close();
	}

    }

    public void update(SemanticPrefix prefix, SemanticPrefix oldPrefix) {

	Session session = null;
	Transaction tx = null;
	try {
	    session = sessionFactory.openSession();
	    tx = session.beginTransaction();

	    session.remove(oldPrefix);

	    session.save(prefix);

	    tx.commit();
	} catch (Exception e) {
	    tx.rollback();
	    throw e;
	} finally {
	    session.close();
	}

    }

    public void delete(SemanticPrefix prefixToDelete) {

	Session session = null;
	Transaction tx = null;
	try {
	    session = sessionFactory.openSession();
	    tx = session.beginTransaction();
	    session.delete(prefixToDelete);
	    tx.commit();
	} catch (Exception e) {
	    tx.rollback();
	    throw e;
	} finally {
	    session.close();
	}

    }

    public Map<String,SemanticPrefix> queryMap() {
    	Map<String,SemanticPrefix> result = new HashMap<String, SemanticPrefix>();
	
	List<SemanticPrefix> resultList = list(-1,-1);
	
	for (SemanticPrefix item : resultList) {
	    result.put(item.getId(), item);
	}

	return result;
}

}
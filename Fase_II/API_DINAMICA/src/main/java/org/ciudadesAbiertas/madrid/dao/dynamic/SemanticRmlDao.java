package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRml;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SemanticRmlDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<SemanticRml> list(int firstResult, int maxResult) {

	List<SemanticRml> result = new ArrayList<SemanticRml>();

	Query<SemanticRml> query = sessionFactory.getCurrentSession().createQuery("FROM SemanticRml");
	if (maxResult > 0) {
	    query.setMaxResults(maxResult);
	}
	if (firstResult > 0) {
	    query.setFirstResult(firstResult);
	}

	result = query.getResultList();

	return result;
    }

    public SemanticRml record(String id) {

	List<SemanticRml> result = new ArrayList<SemanticRml>();

	Query<SemanticRml> query = sessionFactory.getCurrentSession().createQuery("FROM SemanticRml where id like :id", SemanticRml.class);
	query.setParameter("id", id);
	result = query.list();

	if (result.size() > 0) {
	    return result.get(0);
	}

	return null;
    }

    public int listRowCount() {

	Query<SemanticRml> query = sessionFactory.getCurrentSession().createQuery("FROM SemanticRml", SemanticRml.class);
	return query.list().size();

    }

    public void add(SemanticRml prefix) {

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

    public void update(SemanticRml prefix, SemanticRml oldPrefix) {

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

    public void delete(SemanticRml prefixToDelete) {

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

   

}
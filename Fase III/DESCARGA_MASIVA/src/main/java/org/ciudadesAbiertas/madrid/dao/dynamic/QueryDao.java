package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class QueryDao  {

	@Autowired
	private SessionFactory sessionFactory;
	
	public List<QueryD> list(int firstResult, int maxResults) {

		List<QueryD> result = new ArrayList<QueryD>();

		Query query = sessionFactory.getCurrentSession().createQuery("FROM QueryD");
		
		if (maxResults > 0)
		{
		    query.setMaxResults(maxResults);
		}
		if (firstResult > 0)
		{
		    query.setFirstResult(firstResult);
		}
		
		result = query.getResultList();

		return result;
	}

		
	
	public QueryD  record(String id) {

		List<QueryD> result = new ArrayList<QueryD>();
		
		Query<QueryD> query = sessionFactory.getCurrentSession().createQuery("FROM QueryD where id like :id",QueryD.class);
		query.setParameter("id", id);
		result = query.list();
		
		if (result.size()>0)
		{
			return result.get(0);
		}
		
		return null;
	}
	
	public QueryD  recordByCode(String code) {

		List<QueryD> result = new ArrayList<QueryD>();
		
		Query<QueryD> query = sessionFactory.getCurrentSession().createQuery("FROM QueryD where code like :code",QueryD.class);
		query.setParameter("code", code);
		result = query.list();
		
		if (result.size()>0)
		{
			return result.get(0);
		}
		
		return null;
	}

		
	public int listRowCount() {
		
		Query<QueryD> query = sessionFactory.getCurrentSession().createQuery("FROM QueryD",QueryD.class);
		return query.list().size();		
		
	}

	public void add(QueryD query) {

		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			session.save(query);
			tx.commit();			
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}

	public void add(QueryD query, QueryConfD conf) {

		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			session.save(query);		
			session.save(conf);
			tx.commit();			
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}
	
	
	public void update(QueryD query, QueryD oldQuery, QueryConfD queryConf, QueryConfD oldQueryConf) {

		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			session.remove(oldQueryConf);
			session.remove(oldQuery);
			session.save(query);
			session.save(queryConf);
			
			tx.commit();			
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}
	
	public void delete(QueryD queryToDelete, QueryConfD conf) {
		
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();	
			session.delete(conf);
			session.delete(queryToDelete);
			
			tx.commit();			
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}

	
}
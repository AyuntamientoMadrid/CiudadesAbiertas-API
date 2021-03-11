package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.UrlD;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class QueryDao  {

	@Autowired
	private SessionFactory sessionFactory;
	
	public List<QueryD> list(int firstResult, int maxResult) {

		List<QueryD> result = new ArrayList<QueryD>();

		Query query = sessionFactory.getCurrentSession().createQuery("FROM QueryD");
		if (maxResult>0)
		{
			query.setMaxResults(maxResult);
		}
		if (firstResult>0)
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

		
	public int listRowCount() {
		
		Query<QueryD> query = sessionFactory.getCurrentSession().createQuery("FROM QueryD",QueryD.class);
		return query.list().size();		
		
	}

	

	public void add(QueryD query, List<ParamD> params, UrlD url) {

		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			session.save(query);		
			
			int numOfRows=maxParamId();			
			for (ParamD param:params)
			{				
				param.setId(++numOfRows);
				param.setQuery(query);
				session.save(param);			
			}				
			
			if (url!=null)
			{
				session.save(url);
			}
			
			
			tx.commit();			
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}
	
	
	public void update(QueryD query, QueryD oldQuery, List<ParamD> newParams, List<ParamD> oldParams, UrlD url, UrlD oldUrl) {

		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();		
			
			int numOfRows=maxParamId();
			
			for (ParamD param:oldParams)
			{
				session.delete(param);			
			}
			
			session.remove(oldQuery);
			
			session.save(query);
			
			for (ParamD param:newParams)
			{				
				param.setId(++numOfRows);
				param.setQuery(query);
				session.save(param);			
			}			
			
						
			/*
			if (oldUrl!=null)
			{
				session.delete(oldUrl);
			}
			
			if (url!=null)
			{
				session.save(url);
			}
			*/
			
			tx.commit();			
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}
	
	public void delete(QueryD queryToDelete, List<ParamD> paramList) {
		
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();		
			
			for (ParamD param:paramList)
			{
				session.delete(param);
			}			
			
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

	
		
	
	

	public int maxParamId () {
		Criteria criteria = sessionFactory.getCurrentSession()
			    .createCriteria(ParamD.class)
			    .setProjection(Projections.max("id"));
		Integer idCount = (Integer)criteria.uniqueResult();
		if(idCount==null){
			idCount= new Integer(0);
		}
		return idCount.intValue();		

	}



	public Map<String,QueryD> queryMap() {
	    	Map<String,QueryD> result = new HashMap<String, QueryD>();
		
		List<QueryD> resultList = list(-1,-1);
		
		for (QueryD item : resultList) {
		    result.put(item.getCode(), item);
		}

		return result;
	}
	
	
	
	

}
package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SwaggerDefinition;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;




@Repository
public class SwaggerDefinitionDao  {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public List<SwaggerDefinition> list(int firstResult, int maxResult) {

		List<SwaggerDefinition> result = new ArrayList<SwaggerDefinition>();
		
		Query<SwaggerDefinition> query = sessionFactory.getCurrentSession().createQuery("FROM SwaggerDefinition",SwaggerDefinition.class);
		if (maxResult>0)
		{
			query.setMaxResults(maxResult);
		}
		query.setFirstResult(firstResult);
		
		result = query.list();
		
		return result;
	}
	
	public int listRowCount() {
		
		Query<SwaggerDefinition> query = sessionFactory.getCurrentSession().createQuery("FROM SwaggerDefinition",SwaggerDefinition.class);
		return query.list().size();		
		
	}
	


	public void add(SwaggerDefinition def) {

		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			session.save(def);
			tx.commit();			
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}
	
	
	public void update(SwaggerDefinition oldDef, SwaggerDefinition newDef, List<QueryD> queriesWithThisDef) {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();			
		
			session.remove(oldDef);
			session.save(newDef);
			
			for (QueryD query:queriesWithThisDef)
			{
				session.update(query);
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
	
	public void remove(SwaggerDefinition def) {

	Session session = null;
	Transaction tx = null;
	try
	{
		session = sessionFactory.openSession();			
		tx = session.beginTransaction();
		session.delete(def);			
		tx.commit();			
	} catch (Exception e) {
		tx.rollback();
		throw e;
	} finally{
		session.close();
	}		
	}
	
	public List<SwaggerDefinition>  list() {

		List<SwaggerDefinition> result = new ArrayList<SwaggerDefinition>();

		Query query = sessionFactory.getCurrentSession().createQuery("FROM SwaggerDefinition");
		result = query.list();
		
		return result;
	}
	
	
	
	

	public SwaggerDefinition find(String code) {
		List<SwaggerDefinition> result = new ArrayList<SwaggerDefinition>();

		Query query = sessionFactory.getCurrentSession().createQuery("FROM SwaggerDefinition where code = :code");
		query.setParameter("code", code);
		
		result = query.list();
		
		if (result.size()>0)
			return result.get(0);
		else
			return null;
		
		
		
	}


	

	
	
	
	

}
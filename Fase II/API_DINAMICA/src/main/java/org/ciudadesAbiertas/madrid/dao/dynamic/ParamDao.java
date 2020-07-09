package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;







@Repository
public class ParamDao  {

	@Autowired
	private SessionFactory sessionFactory;

	
	public List<ParamD>  list(String code) {

		List<ParamD> result = new ArrayList<ParamD>();

		Query query = sessionFactory.getCurrentSession().createQuery("FROM ParamD where query_code = :code");
		query.setParameter("code", code);
		
		result = query.list();
		
		return result;
	}


	public ParamD find(String code, String name) {
		List<ParamD> result = new ArrayList<ParamD>();

		Query query = sessionFactory.getCurrentSession().createQuery("FROM ParamD where query_code = :code and name = :name");
		query.setParameter("code", code);
		query.setParameter("name", name);
		
		result = query.list();
		
		if (result.size()>0)
			return result.get(0);
		else
			return null;
		
		
		
	}
	
	
	

}
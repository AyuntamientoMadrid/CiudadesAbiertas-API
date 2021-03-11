package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.UrlD;
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
public class QueryDao {

	private static final int MIN_QUERY_ID = 4000;
	@Autowired
	private SessionFactory sessionFactory;

	public List<QueryD> list(int firstResult, int maxResult) {

		List<QueryD> result = new ArrayList<QueryD>();
		String sort = " order by summary ";

		Query query = sessionFactory.getCurrentSession().createQuery("FROM QueryD" + sort);
		if (maxResult > 0) {
			query.setMaxResults(maxResult);
		}
		if (firstResult > 0) {
			query.setFirstResult(firstResult);
		}

		result = query.getResultList();

		return result;
	}

	public List<QueryD> queriesWithSwaggerDef(String swaggerDef) {

		List<QueryD> result = new ArrayList<QueryD>();

		Query<QueryD> query = sessionFactory.getCurrentSession().createQuery("FROM QueryD where definition like :code",
				QueryD.class);
		query.setParameter("code", swaggerDef);
		result = query.list();

		result = query.getResultList();

		return result;
	}

	public QueryD recordCode(String code) {

		List<QueryD> result = new ArrayList<QueryD>();

		Query<QueryD> query = sessionFactory.getCurrentSession().createQuery("FROM QueryD where code like :code",
				QueryD.class);
		query.setParameter("code", code);
		result = query.list();

		if (result.size() > 0) {
			return result.get(0);
		}

		return null;
	}

	public QueryD record(int id) {

		List<QueryD> result = new ArrayList<QueryD>();

		Query<QueryD> query = sessionFactory.getCurrentSession().createQuery("FROM QueryD where id like :id",
				QueryD.class);
		query.setParameter("id", id);
		result = query.list();

		if (result.size() > 0) {
			return result.get(0);
		}

		return null;
	}

	public int listRowCount() {

		Query<QueryD> query = sessionFactory.getCurrentSession().createQuery("FROM QueryD", QueryD.class);
		return query.list().size();

	}

	public void add(QueryD query, List<ParamD> params, UrlD url) {

		Session session = null;
		Transaction tx = null;
		if (params == null) {
			params = new ArrayList<ParamD>();
		}
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			query.setId(maxQueryId() + 1);
			session.save(query);

			int numOfRows = maxParamId();
			for (ParamD param : params) {
				param.setId(++numOfRows);
				param.setQuery(query);
				session.save(param);
			}

			if (url != null) {
				session.save(url);
			}

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}

	}

	public void update(QueryD query, List<ParamD> newParams, List<ParamD> oldParams, UrlD url, UrlD oldUrl) {

		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			int numOfRows = maxParamId();

			for (ParamD param : oldParams) {
				session.delete(param);
			}

			session.update(query);

			for (ParamD param : newParams) {
				param.setId(++numOfRows);
				param.setQuery(query);
				session.save(param);
			}

			/*
			 * if (oldUrl!=null) { session.delete(oldUrl); }
			 * 
			 * if (url!=null) { session.save(url); }
			 */

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}

	}

	public void delete(QueryD queryToDelete, List<ParamD> paramList) {

		Session session = null;
		Transaction tx = null;
		if (paramList == null) {
			paramList = new ArrayList<ParamD>();
		}
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			for (ParamD param : paramList) {
				session.delete(param);
			}

			session.delete(queryToDelete);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}

	}

	public int maxParamId() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ParamD.class)
				.setProjection(Projections.max("id"));
		Integer idCount = (Integer) criteria.uniqueResult();
		if (idCount == null) {
			idCount = new Integer(0);
		}
		return idCount.intValue();

	}

	public int maxQueryId() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(QueryD.class)
				.setProjection(Projections.max("id"));
		Integer idCount = (Integer) criteria.uniqueResult();
		if (idCount == null) {
			idCount = Integer.valueOf(MIN_QUERY_ID);
		} else if (idCount < MIN_QUERY_ID) {
			idCount = MIN_QUERY_ID + 1;
		} else {
			idCount++;
		}
		return idCount.intValue();

	}

	public Map<String, QueryD> queryMap() {
		Map<String, QueryD> result = new LinkedHashMap<String, QueryD>();

		List<QueryD> resultList = list(-1, -1);

		for (QueryD item : resultList) {
			result.put(item.getCode(), item);
		}

		return result;
	}

}
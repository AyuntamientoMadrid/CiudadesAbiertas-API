package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
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

	public SemanticPrefix record(int id) {

		List<SemanticPrefix> result = new ArrayList<SemanticPrefix>();

		Query<SemanticPrefix> query = sessionFactory.getCurrentSession()
				.createQuery("FROM SemanticPrefix where id = :id", SemanticPrefix.class);
		query.setParameter("id", id);
		result = query.list();

		if (result.size() > 0) {
			return result.get(0);
		}

		return null;
	}

	public int listRowCount() {

		Query<SemanticPrefix> query = sessionFactory.getCurrentSession().createQuery("FROM SemanticPrefix",
				SemanticPrefix.class);
		return query.list().size();

	}

	public void add(SemanticPrefix prefix) {

		Session session = null;
		Transaction tx = null;
		try {

			prefix.setId(maxPrefixId());
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

	public void update(SemanticPrefix prefix) {

		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();	
			session.update(prefix);
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

	public Map<Integer, SemanticPrefix> queryMap() {
		Map<Integer, SemanticPrefix> result = new HashMap<Integer, SemanticPrefix>();

		List<SemanticPrefix> resultList = list(-1, -1);

		for (SemanticPrefix item : resultList) {
			result.put(item.getId(), item);
		}

		return result;
	}

	public List<SemanticPrefix> getPrefixInQuery(String code) {
		List<SemanticPrefix> result = new ArrayList<SemanticPrefix>();

		String queryString = "select distinct prefix from SemanticPrefix prefix, SemanticRelPrefix rel where prefix.id like rel.prefix and rel.query= :query";

		// queryString="select distinct id, url from SemanticPrefix where id in (select
		// prefix from SemanticRelPrefix where query like :query)";

		Query<SemanticPrefix> query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("query", code);

		result = query.getResultList();

		return result;
	}

	public int maxPrefixId() {
		int MIN_PREFIX_ID = 1;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SemanticPrefix.class)
				.setProjection(Projections.max("id"));
		Integer idCount = (Integer) criteria.uniqueResult();
		if (idCount == null) {
			idCount = Integer.valueOf(MIN_PREFIX_ID);
		} else if (idCount < MIN_PREFIX_ID) {
			idCount = MIN_PREFIX_ID + 1;
		} else {
			idCount++;
		}
		return idCount.intValue();

	}
}
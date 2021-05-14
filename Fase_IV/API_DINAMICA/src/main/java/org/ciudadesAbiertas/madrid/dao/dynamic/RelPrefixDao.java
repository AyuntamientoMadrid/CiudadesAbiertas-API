package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ciudadesAbiertas.madrid.utils.Util;

@Repository
public class RelPrefixDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void add(SemanticRelPrefix rel) {

		Session session = null;
		Transaction tx = null;
		try {

			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			if (rel.getId() == null) {
				rel.setId(UtilDao.getSuffixForId(SemanticRelPrefix.class.getSimpleName(),
						UtilDao.maxId(session, SemanticRelPrefix.class)));
			}

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

	public List<SemanticRelPrefix> getPrefixFromQuery(String queryName) {

		List<SemanticRelPrefix> result = new ArrayList<SemanticRelPrefix>();

		Query query = sessionFactory.getCurrentSession()
				.createQuery("FROM SemanticRelPrefix where query like :queryName");
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

	public List<String> getQueriesWithPrefix(String prefix) {
		
		List<String> result = new ArrayList<String>();
		
		String schema="";
		if (Util.validValue(StartVariables.db_schema))
		{
			schema=StartVariables.db_schema+".";
		}
		
		
		String queryText="SELECT "+schema+"semantic_rel_prefix.query "
				+ "FROM "+schema+"semantic_prefix , "+schema+"semantic_rel_prefix "
				+ "WHERE "+schema+"semantic_prefix.id="+schema+"semantic_rel_prefix.prefix "
				+ "		and "+schema+"semantic_prefix.code like :prefix ";
		
		Query query = sessionFactory.getCurrentSession()
				.createSQLQuery(queryText);
		query.setParameter("prefix", prefix);

		result = query.getResultList();

		return result;
	}

}
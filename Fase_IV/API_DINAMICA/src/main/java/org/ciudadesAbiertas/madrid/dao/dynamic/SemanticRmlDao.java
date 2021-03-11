package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.model.dynamic.SemanticField;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
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
	
	public List<SemanticRml> listAll() {

		List<SemanticRml> result = new ArrayList<SemanticRml>();

		Query<SemanticRml> query = sessionFactory.getCurrentSession().createQuery("FROM SemanticRml");
		
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

    public void add(SemanticRml entity) {

	Session session = null;
	Transaction tx = null;
	try {

	    session = sessionFactory.openSession();
	    tx = session.beginTransaction();

	    if (entity.getId() == null) {
		entity.setId(UtilDao.getSuffixForId(SemanticRml.class.getSimpleName(), UtilDao.maxId(session, SemanticRml.class)));
	    }

	    session.save(entity);

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

    public List<String> queriesWithRML() {
	List<String> result = new ArrayList<String>();

	Query<String> query = sessionFactory.getCurrentSession().createQuery("select distinct query FROM SemanticRml");

	result = query.getResultList();

	return result;
    }

    public SemanticRml recordByQuery(String code) {
	List<SemanticRml> result = new ArrayList<SemanticRml>();

	Query<SemanticRml> query = sessionFactory.getCurrentSession().createQuery("FROM SemanticRml where query like :query", SemanticRml.class);
	query.setParameter("query", code);
	result = query.list();

	if (result.size() > 0) {
	    return result.get(0);
	}

	return null;
    }

    public void addComplex(SemanticRml rml, SemanticField rdfType, List<SemanticField> semanticFields, List<SemanticRelPrefix> relPrefixesList) {
	Session session = null;
	Transaction tx = null;
	try {

	    session = sessionFactory.openSession();
	    tx = session.beginTransaction();

	    int idRelPrefix = UtilDao.maxId(session, SemanticRelPrefix.class);
	    if (relPrefixesList != null) {
		for (SemanticRelPrefix semanticRelPrefix : relPrefixesList) {

		    if (semanticRelPrefix.getId() == null) {
			semanticRelPrefix.setId(UtilDao.getSuffixForId(SemanticRelPrefix.class.getSimpleName(), idRelPrefix++));
		    }

		    session.save(semanticRelPrefix);
		}
	    }

	    int idSemanticField = UtilDao.maxId(session, SemanticField.class);
	    if (semanticFields != null) {
		for (SemanticField semanticField : semanticFields) {

		    if (semanticField.getId() == null) {
			semanticField.setId(UtilDao.getSuffixForId(SemanticField.class.getSimpleName(), idSemanticField++));
		    }

		    session.save(semanticField);
		}
	    }

	    if (rdfType != null && rdfType.getId() == null) {
		rdfType.setId(UtilDao.getSuffixForId(SemanticField.class.getSimpleName(), idSemanticField++));
	    }

	    session.save(rdfType);

	    if (rml != null) {
		if (rml.getId() == null) {
		    rml.setId(UtilDao.getSuffixForId(SemanticField.class.getSimpleName(), idSemanticField++));
		}

		session.save(rml);
	    }

	    tx.commit();
	} catch (Exception e) {
	    tx.rollback();
	    throw e;
	} finally {
	    session.close();
	}

    }

	public void removeComplex(SemanticRml rml, SemanticField rdfType, List<SemanticField> semanticFields, List<SemanticRelPrefix> relPrefixesList) {
	  Session session = null;
	  Transaction tx = null;
	  try {

		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		if (rml != null) {
		  session.remove(rml);
		}

		if (relPrefixesList != null) {
		  for (SemanticRelPrefix semanticRelPrefix : relPrefixesList) {
			session.remove(semanticRelPrefix);
		  }
		}

		if (semanticFields != null) {
		  for (SemanticField semanticField : semanticFields) {
			session.remove(semanticField);
		  }
		}

		if (rdfType != null) {
		  session.remove(rdfType);
		}

		tx.commit();
	  } catch (Exception e) {
		tx.rollback();
		throw e;
	  } finally {
		session.close();
	  }

	}

	public void updateComplex(SemanticRml rml, SemanticField rdfType, List<SemanticField> semanticFields, List<SemanticRelPrefix> relPrefixesList, 
								SemanticRml oldRmlObj, SemanticField oldType, List<SemanticField> oldFieldsFromQuery, List<SemanticRelPrefix> oldPrefixFromQuery) {
	  Session session = null;
	  Transaction tx = null;
	  try {

		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		if (oldRmlObj != null) {
		  session.remove(oldRmlObj);
		}

		if (oldPrefixFromQuery != null) {
		  for (SemanticRelPrefix semanticRelPrefix : oldPrefixFromQuery) {
			session.remove(semanticRelPrefix);
		  }
		}

		if (oldFieldsFromQuery != null) {
		  for (SemanticField semanticField : oldFieldsFromQuery) {
			session.remove(semanticField);
		  }
		}

		if (oldType != null) {
		  session.remove(oldType);
		}

		int idRelPrefix = UtilDao.maxId(session, SemanticRelPrefix.class);

		if (relPrefixesList != null) {
		  for (SemanticRelPrefix semanticRelPrefix : relPrefixesList) {

			if (semanticRelPrefix.getId() == null) {
			  semanticRelPrefix.setId(UtilDao.getSuffixForId(SemanticRelPrefix.class.getSimpleName(), idRelPrefix++));
			}

			session.save(semanticRelPrefix);
		  }
		}

		int idSemanticField = UtilDao.maxId(session, SemanticField.class);
		if (semanticFields != null) {
		  for (SemanticField semanticField : semanticFields) {

			if (semanticField.getId() == null) {
			  semanticField.setId(UtilDao.getSuffixForId(SemanticField.class.getSimpleName(), idSemanticField++));
			}

			session.save(semanticField);
		  }
		}

		if (rdfType != null && rdfType.getId() == null) {
		  rdfType.setId(UtilDao.getSuffixForId(SemanticField.class.getSimpleName(), idSemanticField++));
		}

		session.save(rdfType);

		if (rml != null) {
		  if (rml.getId() == null) {
			rml.setId(UtilDao.getSuffixForId(SemanticField.class.getSimpleName(), idSemanticField++));
		  }

		  session.save(rml);
		}

		tx.commit();
	  } catch (Exception e) {
		tx.rollback();
		throw e;
	  } finally {
		session.close();
	  }

	}

}
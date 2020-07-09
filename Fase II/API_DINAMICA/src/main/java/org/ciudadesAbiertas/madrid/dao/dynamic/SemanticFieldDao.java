package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.model.dynamic.SemanticField;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SemanticFieldDao {

    @Autowired
    private SessionFactory sessionFactory;

    

    public void add(SemanticField entity) {

	Session session = null;
	Transaction tx = null;
	try {
	    
	    if (entity.getId()==null)
	    {
		entity.setId(maxId()+"");
	    }
	    
	    session = sessionFactory.openSession();
	    tx = session.beginTransaction();
	    session.save(entity);

	    tx.commit();
	} catch (Exception e) {
	    tx.rollback();
	    throw e;
	} finally {
	    if (session!=null)
	    {
		session.close();
	    }
	}

    }

   

    
    public int maxId () {
	Criteria criteria = sessionFactory.getCurrentSession()
		    .createCriteria(SemanticField.class)
		    .setProjection(Projections.rowCount());
	Integer idCount= Integer.valueOf(0);
	Object uniqueResult = criteria.uniqueResult();
	if (uniqueResult!=null)
	{
	    idCount = Integer.parseInt(uniqueResult.toString());
	   
	}
	return idCount.intValue();		

}




    public List<SemanticField> getFieldsFromQuery(String queryName) {
	List<SemanticField> result = new ArrayList<SemanticField>();

	Query query = sessionFactory.getCurrentSession().createQuery("FROM SemanticField where query like :queryName");
	query.setParameter("queryName", queryName);

	result = query.getResultList();

	return result;
    }




    public void delete(SemanticField field) {
	try {
	    sessionFactory.getCurrentSession().remove(field);
	} catch (Exception e) {
	    throw e;
	}
	
    }
   

}
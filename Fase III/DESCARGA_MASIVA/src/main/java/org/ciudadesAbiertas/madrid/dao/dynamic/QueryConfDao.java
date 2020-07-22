package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QueryConfDao
{

  @Autowired
  private SessionFactory sessionFactory;

  public List<QueryConfD> list()
  {

    List<QueryConfD> result = new ArrayList<QueryConfD>();

    Query query = sessionFactory.getCurrentSession().createQuery("FROM QueryConfD");
    result = query.getResultList();

    return result;
  }

  public QueryConfD record(String id)
  {

    List<QueryConfD> result = new ArrayList<QueryConfD>();

    Query<QueryConfD> query = sessionFactory.getCurrentSession().createQuery("FROM QueryConfD where id like :id", QueryConfD.class);
    query.setParameter("id", id);
    result = query.list();

    if (result.size() > 0)
    {
      return result.get(0);
    }

    return null;
  }

  public int listRowCount()
  {

    Query<QueryConfD> query = sessionFactory.getCurrentSession().createQuery("FROM QueryConfD", QueryConfD.class);
    return query.list().size();

  }

  public void add(QueryConfD query)
  {

    Session session = null;
    Transaction tx = null;
    try
    {
      session = sessionFactory.openSession();
      tx = session.beginTransaction();
      session.save(query);

      tx.commit();
    }
    catch (Exception e)
    {
      tx.rollback();
      throw e;
    }
    finally
    {
      session.close();
    }

  }

  public void update(QueryConfD queryConf)
  {

    Session session = null;
    Transaction tx = null;
    try
    {
      session = sessionFactory.openSession();
      tx = session.beginTransaction();
      session.update(queryConf);
      tx.commit();
    }
    catch (Exception e)
    {
      tx.rollback();
      throw e;
    }
    finally
    {
      session.close();
    }

  }

  public void delete(QueryConfD queryToDelete)
  {

    Session session = null;
    Transaction tx = null;
    try
    {
      session = sessionFactory.openSession();
      tx = session.beginTransaction();

      session.delete(queryToDelete);

      tx.commit();
    }
    catch (Exception e)
    {
      tx.rollback();
      throw e;
    }
    finally
    {
      session.close();
    }

  }

  

}
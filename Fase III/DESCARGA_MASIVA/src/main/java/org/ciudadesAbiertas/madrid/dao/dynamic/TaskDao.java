package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ciudadesAbiertas.madrid.model.dynamic.TaskD;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDao {
    
    private static final Logger log = LoggerFactory.getLogger(TaskDao.class);

    @Autowired
    private SessionFactory sessionFactory;
    
    public List<TaskD> list(int i, int j, String order) {
	
	return list(-1,-1, order, null);
    }

    public List<TaskD> list(int firstResult, int maxResults, String order, TaskD taskSearch) {

	List<TaskD> result = new ArrayList<TaskD>();

	String queryString="FROM TaskD";
	String where="";
	
	Map<String,Object> parametersMap=new HashMap<String,Object>();
	
	queryString = whereFromTaskD(taskSearch, queryString, where, parametersMap);
	
	if (Util.validValue(order))
	{
	    queryString+=order;
	}else {
	    queryString+=" order by start desc"; 
	}
	
	log.debug(queryString);
	
	Query query = sessionFactory.getCurrentSession().createQuery(queryString);

	for (String key:parametersMap.keySet())
	{
	    query.setParameter(key,parametersMap.get(key));
	}

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

    public TaskD record(String id) {

	List<TaskD> result = new ArrayList<TaskD>();

	Query<TaskD> query = sessionFactory.getCurrentSession().createQuery("FROM TaskD where id like :id", TaskD.class);
	query.setParameter("id", id);
	result = query.list();

	if (result.size() > 0) {
	    return result.get(0);
	}

	return null;
    }

    public int listRowCount(TaskD taskSearch) {
	
	String queryString="FROM TaskD";
	String where="";
	
	Map<String,Object> parametersMap=new HashMap<String,Object>();
	queryString = whereFromTaskD(taskSearch, queryString, where, parametersMap);

	Query<TaskD> query = sessionFactory.getCurrentSession().createQuery(queryString, TaskD.class);
	
	for (String key:parametersMap.keySet())
	{
	    query.setParameter(key,parametersMap.get(key));
	}
	
	return query.list().size();

    }

    private String whereFromTaskD(TaskD taskSearch, String queryString, String where, Map<String, Object> parametersMap) {
	if (taskSearch!=null)
	{
	    if (Util.validValue(taskSearch.getMode()) && (!taskSearch.getMode().contentEquals("-1")))
	    {
		where+=" mode like :mode and ";
		parametersMap.put("mode", taskSearch.getMode());
	    }
	    if (Util.validValue(taskSearch.getStatus()) && (!taskSearch.getStatus().contentEquals("-1")))
	    {
		where+=" status like :status and ";
		parametersMap.put("status", taskSearch.getStatus());
	    }
	    if (Util.validValue(taskSearch.getQuery()) && (!taskSearch.getQuery().contentEquals("-1")))
	    {
		where+=" query like :query and ";
		parametersMap.put("query", taskSearch.getQuery());
	    }
	    if (Util.validValue(taskSearch.getStart()))
	    {
		where+=" start >= :start and ";
		parametersMap.put("start", taskSearch.getStart());
	    }
	    if (Util.validValue(taskSearch.getFinish()))
	    {
		where+=" finish <= :finish and ";
		parametersMap.put("finish", taskSearch.getFinish());
	    }
	    
	    if (where.endsWith(" and "))
	    {
		where=where.substring(0,where.lastIndexOf(" and "));
	    }
	    
	    if (where.length()>0)
	    {
		where=" WHERE "+where;
	    }
	    
	    queryString+=where;
	}
	return queryString;
    }

    public TaskD add(TaskD task) {

	Session session = null;
	Transaction tx = null;

	if ((task.getId() == null) || (task.getId().equals(""))) {
	    String id = nextId();
	    task.setId(Util.generateID(id, StartVariables.NODO_PATTERN, true));
	}
	try {
	    session = sessionFactory.openSession();
	    tx = session.beginTransaction();
	    session.save(task);

	    tx.commit();
	} catch (Exception e) {
	    tx.rollback();
	    throw e;
	} finally {
	    session.close();
	}
	return task;

    }

    public void update(TaskD task) {

	Session session = null;
	Transaction tx = null;
	try {
	    session = sessionFactory.openSession();
	    tx = session.beginTransaction();
	    session.update(task);

	    tx.commit();
	} catch (Exception e) {
	    tx.rollback();
	    throw e;
	} finally {
	    session.close();
	}

    }

    public void delete(TaskD queryToDelete) {

	Session session = null;
	Transaction tx = null;
	try {
	    session = sessionFactory.openSession();
	    tx = session.beginTransaction();

	    session.delete(queryToDelete);

	    tx.commit();
	} catch (Exception e) {
	    tx.rollback();
	    throw e;
	} finally {
	    session.close();
	}

    }

    public synchronized long count_with_JPA() {

	CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
	CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	Root<TaskD> root = criteria.from(TaskD.class);
	criteria.multiselect(builder.count(root.get("id")));

	Long idCount = sessionFactory.getCurrentSession().createQuery(criteria).getResultList().get(0);

	if (idCount == null) {
	    idCount = Long.valueOf(0);
	}

	return idCount;
    }

    public String nextId() {
	long count_with_JPA = count_with_JPA();
	long numOfRows = count_with_JPA + 1;
	int randomNum = Util.genRandomNum(1000, 9999);
	String id = numOfRows + "" + randomNum;
	return id;
    }
    
    
    
    public List<String> distinct(String field) {

	List<String> result = new ArrayList<String>();

	String queryString="select distinct "+field+" FROM task";	
	
	if (Util.validValue(StartVariables.db_schema))
	{   
	    queryString="select distinct "+field+" FROM "+StartVariables.db_schema+".task";
	}
	
	log.debug(queryString);
	
	NativeQuery query = sessionFactory.getCurrentSession().createSQLQuery(queryString);	

	result = query.getResultList();

	return result;
    }
    
    
    public List<?> sqlQuery(String queryString, Map<String, Object> parametersMap) {
	
	log.debug(queryString);
	
	NativeQuery<?> query = sessionFactory.getCurrentSession().createSQLQuery(queryString);	
	if (parametersMap!=null)
	{
        	for (String key:parametersMap.keySet())
        	{
        	    query.setParameter(key,parametersMap.get(key));
        	}
	}
	
	List<?> resultList = query.getResultList();

	return resultList;
    }
    
    
    public String mostUsed() {	

	String result="";
	String queryString="select query, count(*) as n from task group by query order by n desc";	
	
	if (Util.validValue(StartVariables.db_schema))
	{   
	    queryString="select query, count(*) as n from "+StartVariables.db_schema+".task group by query order by n desc";	
	}
	
	List<?> data = sqlQuery(queryString, null);
	
	if (data.size()>0)
	{
	    Object firstResult = data.get(0);
	    if (firstResult instanceof Object[])
	    {
		Object[] objectValues = (Object[]) firstResult;
		result=objectValues[0].toString();
	    }
	   
	}
	
	return result;
    }

    public String lastDateTags(Date date) {
	
	
	
	String result="";
	
	Map<String, Object> parametersMap=new HashMap<String,Object>();
	parametersMap.put("date",date);
	
	String queryString="select count(*) from task where start >= :date ";	
	
	if (Util.validValue(StartVariables.db_schema))
	{
	    queryString="select count(*) from "+StartVariables.db_schema+".task where start >= :date ";	
	}
		
	List<?> data = sqlQuery(queryString,parametersMap);
	
	if (data.size()>0)
	{
	    Object firstResult = data.get(0);
	    if (firstResult instanceof Integer)
	    {
		Integer count = (Integer) firstResult;
		result=count.toString();
	    }
	   
	}
	
	return result;
    }
    
    
    
}
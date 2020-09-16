package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.List;

import org.ciudadesAbiertas.madrid.dao.dynamic.QueryConfDao;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("QueryConfService")
public class QueryConfService
{

    private static final Logger log = LoggerFactory.getLogger(QueryConfService.class);

    @Autowired
    private QueryConfDao QueryConfDao;

    @Transactional(readOnly = true)
    public List<QueryConfD> list()
    {
	log.info("list");
	return QueryConfDao.list();
    }

    @Transactional(readOnly = true)
    public int listRowCount()
    {
	log.info("listRowCount");
	return QueryConfDao.listRowCount();
    }

    @Transactional(readOnly = true)
    public QueryConfD record(String id)
    {
	log.info("record");
	return QueryConfDao.record(id);
    }

    @Transactional
    public void add(QueryConfD entidad)
    {
	log.info("add");
	QueryConfDao.add(entidad);
    }

    @Transactional
    public void update(QueryConfD queryConf)
    {
	log.info("update");
	QueryConfDao.update(queryConf);

    }

    @Transactional
    public void delete(QueryConfD recordToDelete)
    {
	log.info("delete");
	QueryConfDao.delete(recordToDelete);

    }

    

}
package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.List;

import org.ciudadesAbiertas.madrid.dao.dynamic.QueryDao;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("QueryService")
public class QueryService
{

  private static final Logger log = LoggerFactory.getLogger(QueryService.class);

  @Autowired
  private QueryDao queryDao;

  @Transactional(readOnly = true)
  public List<QueryD> list()
  {
    log.info("list");
    return queryDao.list(-1,-1);
  }
  
  @Transactional(readOnly = true)
  public List<QueryD> list(int firstResult, int pageSize)
  {
    log.info("list");
    return queryDao.list(firstResult,pageSize);
  }

  @Transactional(readOnly = true)
  public int listRowCount()
  {
    log.info("listRowCount");
    return queryDao.listRowCount();
  }

  @Transactional(readOnly = true)
  public QueryD record(String id)
  {
    log.info("record");
    return queryDao.record(id);
  }
  
  @Transactional(readOnly = true)
  public QueryD recordByCode(String code)
  {
    log.info("record");
    return queryDao.recordByCode(code);
  }

  @Transactional
  public void add(QueryD entidad)
  {
    log.info("add");
    queryDao.add(entidad);
  }
  
  @Transactional
  public void add(QueryD entidad, QueryConfD conf)
  {
    log.info("add");
    queryDao.add(entidad, conf);
  }

  @Transactional
  public void update(QueryD newQuery, QueryD oldQuery, QueryConfD queryConf, QueryConfD oldQueryConf)
  {
    log.info("update");
    queryDao.update(newQuery, oldQuery, queryConf, oldQueryConf);

  }

  @Transactional
  public void delete(QueryD recordToDelete, QueryConfD conf)
  {
    log.info("delete");
    queryDao.delete(recordToDelete, conf);

  }

}
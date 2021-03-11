package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.madrid.dao.dynamic.QueryDao;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("QueryService")
public class QueryService {

	private static final Logger log = LoggerFactory.getLogger(QueryService.class);

	@Autowired
	private QueryDao queryDao;

	@Transactional(readOnly = true)
	public List<QueryD> list(int firstResult, int maxResults) {
		log.info("list");
		return queryDao.list(firstResult, maxResults);
	}

	@Transactional(readOnly = true)
	public Map<String, QueryD> queryMap() {
		log.info("queryMap");
		return queryDao.queryMap();
	}

	@Transactional(readOnly = true)
	public int listRowCount() {
		log.info("listRowCount");
		return queryDao.listRowCount();
	}

	@Transactional(readOnly = true)
	public QueryD recordCode(String code) {
		log.info("record");
		return queryDao.recordCode(code);
	}

	@Transactional(readOnly = true)
	public QueryD record(int id) {
		log.info("record");
		return queryDao.record(id);
	}

	@Transactional
	public void add(QueryD entidad, List<ParamD> params) {
		log.info("add");
		queryDao.add(entidad, params, null);
	}

	@Transactional
	public void update(QueryD newQuery, List<ParamD> newParamList, List<ParamD> oldParamList) {
		log.info("update");
		queryDao.update(newQuery, newParamList, oldParamList, null, null);

	}

	@Transactional
	public void delete(QueryD recordToDelete, List<ParamD> paramList) {
		log.info("delete");
		queryDao.delete(recordToDelete, paramList);

	}

	@Transactional
	public List<QueryD> queriesWithSwaggerDef(String code) {
		log.info("queriesWithSwaggerDef");
		return queryDao.queriesWithSwaggerDef(code);
	}

}